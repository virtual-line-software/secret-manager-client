package software.virtualline.hashicorpautoconfig.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Lombok;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import software.virtualline.hashicorpautoconfig.data.PasswordDTO;
import software.virtualline.hashicorpautoconfig.properties.HashiCorpProperties;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class HashiCorpApi {

    private static final String HASHICORP_SECRET_URL_PATTERN = "https://api.cloud.hashicorp.com/secrets/2023-06-13/organizations/%s/projects/%s/apps/%s/open/%s";

    private final WebClient initWebClient;
    private final ObjectMapper objectMapper;
    private final HashiCorpProperties hashiCorpProps;

    public Map<String, PasswordDTO> fetchPassword() {

        var appName = Lombok.checkNotNull(hashiCorpProps.getAppName(), "the appName is not specified");
        var secretName = Lombok.checkNotNull(hashiCorpProps.getSecretName(), "the secretName is not specified");

        return initWebClient.get()
                .uri(String.format(HASHICORP_SECRET_URL_PATTERN, hashiCorpProps.getOrgId(), hashiCorpProps.getProjId(),  appName, secretName))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMap(jsonNode -> Mono.just(jsonNode.at("/secret/version/value").asText()))
                .flatMap(json -> Mono.just(parseSecrets(json)))
                .block();
    }

    @SneakyThrows
    private Map<String, PasswordDTO> parseSecrets(String json) {
        return objectMapper.readValue(json, new TypeReference<>() {});
    }

}
