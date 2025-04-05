package com.paymentchain.customer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.repository.CustomerRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    @Autowired
    private CustomerRepository customerRepository;

    private final WebClient.Builder webClientBuilder;

    // Constructor para inyectar el WebClient.Builder
    public CustomerRestController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    // webclient requires HttpClient library to work properly
    HttpClient client = HttpClient.create()
            // Espera 15 segundos como máximo para establecer la conexión.
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15000)
            // Activa el keep-alive, que mantiene la conexión abierta en lugar de cerrarla después de cada solicitud.
            .option(ChannelOption.SO_KEEPALIVE, true)
            // Configura el tiempo de espera de inactividad de la conexión.
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            // Si no hay respuesta al paquete de keep-alive, se vuelve a intentar cada 60 segundos.
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            // Espera 1 segundo máximo para obtener una respuesta del servidor después de haber enviado la solicitud.
            .responseTimeout(Duration.ofSeconds(1))
            // Cuando la conexión se establece, se agregan dos handlers de timeout.
            .doOnConnected(connection -> {
                // Si tarda más de 5 segundos en leer la respuesta, lanza error.
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                // Si tarda más de 5 segundos en enviar la solicitud, lanza error.
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            }
    );

    @Operation(summary = "Lista de clientes")
    @GetMapping()
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Operation(summary = "Obtener un cliente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(name = "id") long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualizar un cliente")
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") long id, @RequestBody Customer input) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer newCustomer = optionalCustomer.get();
            newCustomer.setCode(input.getCode());
            newCustomer.setIban(input.getIban());
            newCustomer.setName(input.getName());
            newCustomer.setSurname(input.getSurname());
            newCustomer.setAddress(input.getAddress());
            newCustomer.setPhone(input.getPhone());
            Customer save = customerRepository.save(newCustomer);
            return new ResponseEntity<>(save, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear un cliente")
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Customer input) {
        input.getProducts().forEach(product -> product.setCustomer(input));
        Customer save = customerRepository.save(input);
        return ResponseEntity.ok(save);
    }

    @Operation(summary = "Eliminar un cliente por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obtener el nombre de un producto: hace una petición HTTP GET al microservicio de productos
    public String getProductName(long id) {
        WebClient build = webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://localhost:8083/product")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8083/product"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();
        assert block != null;
        return block.get("name").asText();
    }

}
