package software.virtualline.hashicorpautoconfig.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.endpoint.WebClientReactiveClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import software.virtualline.hashicorpautoconfig.properties.HashiCorpProperties;

@Configuration
@RequiredArgsConstructor
public class InitWebClientConfig {

    private static final String HASHICORP_AUTH_URL = "https://auth.hashicorp.com/oauth/token";
    private static final String HASHICORP_AUDIENCE = "https://api.hashicorp.cloud";
    private static final String INIT_REGISTRATION_ID = "init";

    private final HashiCorpProperties hashiCorpProps;

    @Bean
    public WebClient initWebClient(ReactiveClientRegistrationRepository clientRegistrations) {
        var clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
        var authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider());

        var oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId(INIT_REGISTRATION_ID);

        return WebClient.builder()
                .filter(oauth)
                .build();
    }

    @Bean
    public ReactiveClientRegistrationRepository reactiveClientRegistrationRepository() {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId(INIT_REGISTRATION_ID)
                .clientId(hashiCorpProps.getClientId())
                .clientSecret(hashiCorpProps.getClientSecret())
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenUri(HASHICORP_AUTH_URL)
                .build();

        return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
    }

    private ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider() {
        var responseClient = new WebClientReactiveClientCredentialsTokenResponseClient();

        responseClient.addParametersConverter(
                source -> {
                    LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                    map.add("audience", HASHICORP_AUDIENCE);
                    return map;
                }
        );

        return ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials(clientCredentialsGrantBuilder ->
                        clientCredentialsGrantBuilder.accessTokenResponseClient(responseClient))
                .build();
    }
}
