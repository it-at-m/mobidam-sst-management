package de.muenchen.mobidam.integration.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.endpoint.DefaultClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@Slf4j
public class SecurityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean // To let others override the service
    public WebClient webClient(final @Value("${oauth2.registration.id}")
    String oauth2RegistrationId,
            final @Value("${de.muenchen.mobidam.integration.baseUrl}")
            String baseUrl,
            final ClientRegistrationRepository clientRegistrationRepository) {

        var defaultClientCredentialsTokenResponseClient = new DefaultClientCredentialsTokenResponseClient();
        defaultClientCredentialsTokenResponseClient
                .setRestOperations(getRestTemplateForTokenEndPoint(oauth2RegistrationId));

        var provider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials(c -> c.accessTokenResponseClient(defaultClientCredentialsTokenResponseClient))
                .build();

        var oauth2AuthorizedClientService = new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);

        var authorizedClientServiceOAuth2AuthorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository,
                oauth2AuthorizedClientService);
        authorizedClientServiceOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);

        var oauth = new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientServiceOAuth2AuthorizedClientManager);
        oauth.setDefaultClientRegistrationId(oauth2RegistrationId);

        return WebClient.builder()
                // base path of the client, just path while calling is required
                .baseUrl(baseUrl)
                .apply(oauth.oauth2Configuration())
                .filter(logResourceRequest(oauth2RegistrationId))
                .filter(logResourceResponse(oauth2RegistrationId))
                .build();
    }

    private RestTemplate getRestTemplateForTokenEndPoint(String oauth2RegistrationId) {
        var restTemplateForTokenEndPoint = new RestTemplate();
        restTemplateForTokenEndPoint
                .setMessageConverters(
                        List.of(new FormHttpMessageConverter(),
                                new OAuth2AccessTokenResponseHttpMessageConverter()));
        restTemplateForTokenEndPoint
                .setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        restTemplateForTokenEndPoint
                .setInterceptors(List.of(restTemplateRequestInterceptor(oauth2RegistrationId)));
        return restTemplateForTokenEndPoint;
    }

    private static ExchangeFilterFunction logResourceRequest(final String clientName) {
        return ExchangeFilterFunction.ofRequestProcessor(c -> {
            log.debug(
                    "For Client {}, Sending OAUTH2 protected Resource Request to {}: {}",
                    clientName, c.method(), c.url());
            return Mono.just(c);
        });
    }

    private static ExchangeFilterFunction logResourceResponse(final String clientName) {
        return ExchangeFilterFunction.ofResponseProcessor(c -> {
            log.debug("For Client {}, OAUTH2 protected Resource Response status: {}", clientName, c.statusCode());
            return Mono.just(c);
        });
    }

    private static ClientHttpRequestInterceptor restTemplateRequestInterceptor(final String clientName) {
        return (request, body, execution) -> {
            log.debug("For Client {}, Sending OAUTH2 Token Request to {}", clientName, request.getURI());
            var clientResponse = execution.execute(request, body);
            log.debug("For Client {}, OAUTH2 Token Response: {}", clientName, clientResponse.getStatusCode());
            return clientResponse;
        };
    }
}
