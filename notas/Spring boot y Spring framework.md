## Spring Framework
Es un marco de trabajo (framework) **open-source** para el desarrollo de aplicaciones Java. Proporciona una amplia variedad de herramientas y funcionalidades para simplificar la construcción de aplicaciones empresariales.
#### **Características clave del Spring Framework:**

1. **Inversión de control (IoC):** Permite la inyección de dependencias para lograr un diseño desacoplado.
2. **Programación orientada a aspectos (AOP):** Facilita la separación de preocupaciones transversales, como la gestión de transacciones o el manejo de seguridad.
3. **Acceso simplificado a datos:** Ofrece soporte para trabajar con JDBC, JPA, Hibernate, y otros ORM.
4. **Web y REST:** Incluye módulos como Spring MVC para desarrollar aplicaciones web y APIs RESTful.
5. **Integración:** Facilita la integración con otros sistemas mediante herramientas como JMS, mail, y servicios web SOAP o REST.

### **Spring Boot**

Es una extensión del Spring Framework diseñada para simplificar y acelerar el desarrollo de aplicaciones basadas en Spring. Spring Boot elimina la necesidad de configurar manualmente muchos aspectos del framework, proporcionando una configuración automática y listas para usar.
#### **Características clave de Spring Boot:**

1. **Configuración automática:** Detecta las bibliotecas incluidas en el proyecto y configura automáticamente el entorno.
2. **Servidor embebido:** Incluye servidores como Tomcat o Jetty, lo que elimina la necesidad de desplegar la aplicación en un servidor externo.
3. **Filosofía "Convención sobre configuración":** Reduce el tiempo de configuración al ofrecer valores predeterminados útiles que se pueden personalizar fácilmente.
4. **Arranque rápido:** Proporciona "starters," dependencias preconfiguradas para integrar tecnologías como Spring Data, Spring Security, Spring Web, etc.
5. **Archivo único ejecutable:** Permite empaquetar la aplicación como un archivo JAR ejecutable.


### **Diferencias principales entre Spring y Spring Boot**

| **Aspecto**          | **Spring Framework**                         | **Spring Boot**                        |
| -------------------- | -------------------------------------------- | -------------------------------------- |
| **Configuración**    | Requiere configuración manual detallada.     | Configuración automática.              |
| **Facilidad de uso** | Más complejo para principiantes.             | Simplifica el desarrollo y despliegue. |
| **Servidor**         | Necesita un servidor externo (e.g., Tomcat). | Incluye servidores embebidos.          |
| **Arranque rápido**  | Requiere agregar dependencias manualmente.   | Ofrece "starters" preconfigurados.     |

### **¿Cuándo usar cada uno?**

- **Spring Framework**: Ideal para proyectos grandes y complejos que requieren configuraciones personalizadas o integración avanzada.
- **Spring Boot**: Perfecto para proyectos nuevos, rápidos, y modernos que buscan minimizar la configuración inicial y desplegar rápidamente.