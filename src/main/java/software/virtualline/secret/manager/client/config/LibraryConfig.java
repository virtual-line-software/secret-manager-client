package software.virtualline.secret.manager.client.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.secretmanager.v1.AccessSecretVersionRequest;
import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretManagerServiceSettings;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import lombok.Lombok;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import software.virtualline.secret.manager.client.data.CredentialsDtoList;
import software.virtualline.secret.manager.client.properties.SecretManagerClientProperties;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ComponentScan("software.virtualline.secret.manager.client")
public class LibraryConfig {

    private final ObjectMapper objectMapper;
    private final SecretManagerClientProperties properties;

    @Bean
    @Lazy
    @SneakyThrows
    public CredentialsDtoList credentialsProperties() {
        var projectId = Lombok.checkNotNull(properties.getProjectId(), "the projectId is not specified");
        var secretName = Lombok.checkNotNull(properties.getSecretName(), "the secretName is not specified");

        SecretVersionName secretVersionName = SecretVersionName.of(projectId, secretName, "latest");

        String secretValueJson;
        try (SecretManagerServiceClient client = create()) {
            AccessSecretVersionRequest request = AccessSecretVersionRequest.newBuilder()
                    .setName(secretVersionName.toString()).build();

            AccessSecretVersionResponse response = client.accessSecretVersion(request);
            secretValueJson = response.getPayload().getData().toStringUtf8();
        }

        return new CredentialsDtoList(objectMapper.readValue(secretValueJson, new TypeReference<>() {}));
    }

    private SecretManagerServiceClient create() throws IOException {
        try {
            return createClientViaServiceAccount();
        } catch (Exception e) {
            log.warn(e.getMessage());
        }

        return SecretManagerServiceClient.create();
    }

    private SecretManagerServiceClient createClientViaServiceAccount() throws Exception {
        String json = System.getenv("GCP_SA_KEY_JSON");
        if (json == null || json.isBlank()) {
            throw new IllegalStateException("GCP_SA_KEY_JSON is not set");
        }

        GoogleCredentials creds = GoogleCredentials.fromStream(
                new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));

        SecretManagerServiceSettings settings = SecretManagerServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(creds)).build();

        return SecretManagerServiceClient.create(settings);
    }
}
