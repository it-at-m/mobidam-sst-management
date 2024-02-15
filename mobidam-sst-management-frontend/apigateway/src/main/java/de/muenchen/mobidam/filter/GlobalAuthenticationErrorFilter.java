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

import com.hazelcast.org.apache.commons.codec.binary.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This {@link GlobalFilter} replaces the body by a generic authentication error body,
 * when a server responses with a {@link HttpStatus#UNAUTHORIZED}.
 * <p>
 * The header {@link HttpHeaders#WWW_AUTHENTICATE} containing the access token is removed
 * by the property 'RemoveResponseHeader' in the corresponding route within 'application.yml'.
 */
@Component
@Slf4j
public class GlobalAuthenticationErrorFilter implements GlobalFilter, Ordered {

    private static final String GENERIC_AUTHENTICATION_ERROR = "{ \"status\":401, \"error\":\"Authentication Error\" }";

    public static final int ORDER_GLOBAL_FILTER = -3;

    @Override
    public int getOrder() {
        return ORDER_GLOBAL_FILTER;
    }

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
        log.debug("Check for authentication errors");
        final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        final String newResponseBody = GENERIC_AUTHENTICATION_ERROR;
        final String EMPTY_JSON_OBJECT = "{}";

        final ServerHttpResponse response = exchange.getResponse();

        final ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {

            /**
             * This overridden method adds the response body given in the parameter of
             * the surrounding method when the http status given in the parameter of
             * the surrounding method is met otherwise the already appended body will be used.
             *
             * @param body The body received by the upstream response.
             * @return Either the body received by the upstream response or
             *         the body given by the parameter.
             */
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                final HttpStatusCode responseHttpStatus = getDelegate().getStatusCode();
                if (body instanceof Flux && responseHttpStatus.equals(httpStatus)) {
                    final DataBufferFactory dataBufferFactory = response.bufferFactory();
                    final DataBuffer newDataBuffer = dataBufferFactory.wrap(
                            StringUtils.getBytesUtf8(ObjectUtils.defaultIfNull(newResponseBody, EMPTY_JSON_OBJECT)));

                    log.debug("Response from upstream {} get new response body: {}", httpStatus, newResponseBody);
                    getDelegate().getHeaders().setContentLength(newDataBuffer.readableByteCount());
                    getDelegate().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    Flux<? extends DataBuffer> flux = (Flux<? extends DataBuffer>) body;

                    return super.writeWith(flux.buffer().map(
                            // replace old body represented by dataBuffer by the new one
                            dataBuffer -> newDataBuffer));
                }
                return super.writeWith(body);
            }

        };

        final ServerWebExchange swe = exchange.mutate().response(decoratedResponse).build();
        return chain.filter(swe);
    };

}
