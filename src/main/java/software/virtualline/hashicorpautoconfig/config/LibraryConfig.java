package software.virtualline.hashicorpautoconfig.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import software.virtualline.hashicorpautoconfig.api.HashiCorpApi;
import software.virtualline.hashicorpautoconfig.properties.CredentialsProperties;

@Configuration
@RequiredArgsConstructor
@ComponentScan("software.virtualline.hashicorpautoconfig")
public class LibraryConfig {

    private final HashiCorpApi hashiCorpApi;

    @Bean
    public CredentialsProperties credentialsProperties() {
        return new CredentialsProperties(hashiCorpApi.fetchPassword());
    }
}
