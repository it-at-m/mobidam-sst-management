/*
 * The MIT License
 *  Copyright © 2023 Landeshauptstadt München | it@M
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package de.muenchen.mobidam.filter;

import de.muenchen.mobidam.exception.ParameterPollutionException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * This {@link GlobalFilter} is used to detect and to fend off a parameter pollution attack.
 *
 * Within a {@link HttpRequest} each request parameter should only exist once.
 * This check is necessary to avoid e.g. SQL injection split over multiple request parameters with
 * the same name.
 */
@Component
@Slf4j
public class GlobalRequestParameterPollutionFilter implements GlobalFilter, Ordered {

    public static final int ORDER_GLOBAL_FILTER = -3;

    @Override
    public int getOrder() {
        return ORDER_GLOBAL_FILTER;
    }

    /**
     * See {@link GlobalFilter#filter(ServerWebExchange, GatewayFilterChain)}
     *
     * @throws ParameterPollutionException is throw when a request parameter exists multiple times.
     *             The exception represents a http response with status {@link HttpStatus#BAD_REQUEST}.
     */
    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) throws ParameterPollutionException {
        log.debug("Check for parameter pollution attack.");
        ServerHttpRequest request = exchange.getRequest();
        if (!CollectionUtils.isEmpty(request.getQueryParams())) {
            MultiValueMap<String, String> parameterMap = request.getQueryParams();
            for (Map.Entry<String, List<String>> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                if (!CollectionUtils.isEmpty(value) && value.size() > 1) {
                    log.warn("Possible parameter pollution attack detected: Parameter \"{}\" detected more than once in the request!", key);
                    throw new ParameterPollutionException();
                }
            }
        }
        return chain.filter(exchange);
    }

}
