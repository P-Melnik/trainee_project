package trainee.service.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import trainee.service.gateway.service.CustomJwtService;
import trainee.service.gateway.validator.RouteValidator;

@Component
public class AuthFilter implements GatewayFilter {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private CustomJwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (routeValidator.isSecured.test(exchange.getRequest())) {
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (jwtService.isTokenValid(authorizationHeader)) {
                return chain.filter(exchange);
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }
        return chain.filter(exchange);
    }
}
