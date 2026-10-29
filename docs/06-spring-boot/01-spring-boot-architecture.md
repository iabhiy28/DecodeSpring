# Spring Boot Architecture, Starters & Application Startup Lifecycle

## 1. The 3 Core Pillars of Spring Boot

1. **Starters (`spring-boot-starter-*`)**: Curated dependency bundles that guarantee dependency version compatibility.
2. **Auto-Configuration (`@EnableAutoConfiguration`)**: Evaluates classpath jars and automatically configures standard beans (Datasources, Jackson converters, Web servers).
3. **Embedded Server**: Packages Apache Tomcat directly into the executable fat JAR (`java -jar app.jar`).

---

## 2. The `@SpringBootApplication` Annotation

A composite annotation bundling:
- **`@SpringBootConfiguration`**: Inherits from `@Configuration`, allowing `@Bean` definitions.
- **`@EnableAutoConfiguration`**: Triggers auto-configuration hooks from `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`.
- **`@ComponentScan`**: Recursively scans the declaring package and all child packages for Spring stereotypes (`@Component`, `@Service`, `@Repository`, `@RestController`).

---

## 3. The `SpringApplication.run()` Execution Sequence

```
1. Determine Web Application Type (Servlet vs Reactive)
2. Prepare Environment (application.yml, OS env vars, command-line arguments)
3. Instantiate ApplicationContext (The IoC Container)
4. Trigger Auto-Configuration & Component Scanning
5. Instantiate and Wire Singleton Beans (Constructor injection)
6. Start Embedded Tomcat Server (Default port 8080)
7. Execute CommandLineRunner / ApplicationRunner hooks
8. Application Ready for HTTP Traffic
```
