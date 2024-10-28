package org.example;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class Swagger_config {

    @Bean
    public OpenAPI swagger_customization(){
        return new OpenAPI()
                .info(new Info().title("Gateway"))

                .externalDocs(new ExternalDocumentation()
                        .description(" Company Microsercice")
                        .url("http://localhost:8091/swagger-ui.html"))

    //                .externalDocs(new ExternalDocumentation()
    //                        .description(" JOB Microsercice")
    //                        .url("http://localhost:8092/swagger-ui.html"))
    //
    //                .externalDocs(new ExternalDocumentation()
    //                        .description(" Review Microsercice")
    //                        .url("http://localhost:8093/swagger-ui.html"))

                .servers(Arrays.asList(new Server().url("http://localhost:8091").description("company")));
    }

//    @Autowired
//    private SwaggerUiConfigParameters swaggerUiConfigParameters;
//
//    @Bean
//    public StandardReflectionParameterNameDiscoverer standardReflectionParameterNameDiscoverer() {
//        return new StandardReflectionParameterNameDiscoverer();
//    }
//
//    @Bean
//    public GroupedOpenApi secondMicroserviceApi() {
//        // Register the second microservice's OpenAPI URL with the Swagger UI configuration
//        swaggerUiConfigParameters.addUrl( "http://localhost:8092/v3/api-docs"); // URL of the second microservice's OpenAPI docs
//
//        // Optionally create a grouped OpenAPI if you also have local APIs
//        return GroupedOpenApi.builder()
//                .group("job-microservice")
//                .pathsToMatch("/job/**")
//                .build();
//    }


}
