package trainee.service.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import trainee.service.gateway.security.AuthFilter;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthFilter authFilter;
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("SUMMARY-MICROSERVICE", r -> r
                        .path("/workload/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://SUMMARY-MICROSERVICE"))

                .route("GYM-SERVICE", r -> r
                        .path("/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://GYM-SERVICE"))
                .build();
    }
}
