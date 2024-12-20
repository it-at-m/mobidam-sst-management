/*
 * The MIT License
 * Copyright © 2023 Landeshauptstadt München | it@M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.muenchen.mobidam.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Service, der einen OIDC /userinfo Endpoint aufruft (mit JWT Bearer Auth) und dort die enthaltenen
 * "Authorities" extrahiert.
 */
@Slf4j
public class UserInfoAuthoritiesService {

    private static final String NAME_AUTHENTICATION_CACHE = "authentication_cache";
    private static final int AUTHENTICATION_CACHE_ENTRY_SECONDS_TO_EXPIRE = 60;

    private static final String CLAIM_AUTHORITIES = "authorities";

    private final String userInfoUri;
    private final RestTemplate restTemplate;
    private final Cache cache;

    /**
     * Erzeugt eine neue Instanz.
     *
     * @param userInfoUri userinfo Endpoint URI
     * @param restTemplateBuilder ein {@link RestTemplateBuilder}
     */
    public UserInfoAuthoritiesService(String userInfoUri, RestTemplateBuilder restTemplateBuilder) {
        this.userInfoUri = userInfoUri;
        this.restTemplate = restTemplateBuilder.build();
        this.cache = new CaffeineCache(NAME_AUTHENTICATION_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(AUTHENTICATION_CACHE_ENTRY_SECONDS_TO_EXPIRE, TimeUnit.SECONDS)
                        .ticker(Ticker.systemTicker())
                        .build());
    }

    /**
     * Ruft den /userinfo Endpoint und extrahiert {@link GrantedAuthority}s aus dem "authorities"
     * Claim.
     *
     * @param jwt der JWT
     * @return die {@link GrantedAuthority}s gem. Claim "authorities" des /userinfo Endpoints
     */
    public Collection<SimpleGrantedAuthority> loadAuthorities(Jwt jwt) {
        ValueWrapper valueWrapper = this.cache.get(jwt.getSubject());
        if (valueWrapper != null) {
            // value present in cache
            @SuppressWarnings("unchecked")
            Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) valueWrapper.get();
            log.debug("Resolved authorities (from cache): {}", authorities);
            return authorities;
        }

        log.debug("Fetching user-info for token subject: {}", jwt.getSubject());
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue());
        final HttpEntity<String> entity = new HttpEntity<>(headers);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = restTemplate.exchange(this.userInfoUri, HttpMethod.GET, entity,
                    Map.class).getBody();

            log.debug("Response from user-info Endpoint: {}", map);
            if (map.containsKey(CLAIM_AUTHORITIES)) {
                authorities = asAuthorities(map.get(CLAIM_AUTHORITIES));
            }
            log.debug("Resolved Authorities (from /userinfo Endpoint): {}", authorities);
            // store
            this.cache.put(jwt.getSubject(), authorities);
        } catch (Exception e) {
            log.error(String.format("Could not fetch user details from %s - user is granted NO authorities",
                    this.userInfoUri), e);
        }

        return authorities;
    }

    private static List<SimpleGrantedAuthority> asAuthorities(Object object) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (object instanceof Collection) {
            Collection<?> collection = (Collection<?>) object;
            object = collection.toArray(new Object[0]);
        }
        if (ObjectUtils.isArray(object)) {
            authorities.addAll(
                    Stream.of(((Object[]) object))
                            .map(Object::toString)
                            .map(String::toUpperCase)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()));
        }
        return authorities;
    }

}
