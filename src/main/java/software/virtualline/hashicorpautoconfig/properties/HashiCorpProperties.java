package software.virtualline.hashicorpautoconfig.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("vls.hashicorp")
@PropertySource(value = "file:${user.home}/.vls/credentials", ignoreResourceNotFound = true)
public class HashiCorpProperties {

//    @Value("#{systemEnvironment['HASHICORP_CLIENT_ID'] ?: '${hashicorp_client_id}'}")
//    private String clientId;
//
//    @Value("#{systemEnvironment['HASHICORP_CLIENT_SECRET'] ?: '${hashicorp_client_secret}'}")
//    private String clientSecret;

//    @Value("${HASHICORP_CLIENT_ID:${hashicorp_client_id}}")
//    private String clientId;
//
//    @Value("${HASHICORP_CLIENT_SECRET:${hashicorp_client_secret}}")
//    private String clientSecret;

    @Value("#{systemEnvironment['HASHICORP_CLIENT_ID'] ?: '${hashicorp_client_id}'}")
    private String clientId;
    @Value("#{systemEnvironment['HASHICORP_CLIENT_SECRET'] ?: '${hashicorp_client_secret}'}")
    private String clientSecret;

//    @Value("${hashicorp_client_id}")
//    private String clientId;
//
//    @Value("${hashicorp_client_secret}")
//    private String clientSecret;

    private String organisationId;

    private String projectId;

    private String secretName;

    private String appName;
}
