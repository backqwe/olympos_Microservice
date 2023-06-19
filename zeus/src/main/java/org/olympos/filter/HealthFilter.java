package org.olympos.filter;

import org.apache.shenyu.common.utils.JsonUtils;
import org.springframework.boot.actuate.health.Health;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * The type health filter.
 */
@Component
@Order(-99)
public final class HealthFilter implements WebFilter {

    private static final List<String> URL_PATTERNS = Arrays.asList("/actuator/health", "/health_check");

    @Override
    public Mono<Void> filter(@Nullable final ServerWebExchange exchange, @Nullable final WebFilterChain chain) {
        String urlPath = Objects.requireNonNull(exchange).getRequest().getURI().getPath();
        return URL_PATTERNS.contains(urlPath) ? writeHealthInfo(exchange) : Objects.requireNonNull(chain).filter(exchange);
    }

    private Mono<Void> writeHealthInfo(final ServerWebExchange exchange) {
        String result = JsonUtils.toJson(new Health.Builder().up().build());
        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }
}
