package software.virtualline.hashicorpautoconfig.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import software.virtualline.hashicorpautoconfig.api.HashiCorpApi;
import software.virtualline.hashicorpautoconfig.properties.CredentialsDtoList;

@Configuration
@RequiredArgsConstructor
@ComponentScan("software.virtualline.hashicorpautoconfig")
public class LibraryConfig {

    private final HashiCorpApi hashiCorpApi;

    @Bean
    @Lazy
    public CredentialsDtoList credentialsProperties() {
        return new CredentialsDtoList(hashiCorpApi.fetchPassword());
    }
}
