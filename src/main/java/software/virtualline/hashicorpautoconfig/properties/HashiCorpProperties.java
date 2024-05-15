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

    @Value("${HASHICORP_CLIENT_ID:${vls.hashicorp.client-id:defaultClientId}}")
    private String clientId;

    @Value("${HASHICORP_CLIENT_SECRET:${vls.hashicorp.client-secret:defaultClientSecret}}")
    private String clientSecret;

//    @Value("${HASHICORP_CLIENT_ID:${vls.hashicorp.hashicorp_client_id}}")
//    private String clientId;
//
//    @Value("${HASHICORP_CLIENT_SECRET:${vls.hashicorp.hashicorp_client_secret}}")
//    private String clientSecret;

    private String organisationId;

    private String projectId;

    private String secretName;

    private String appName;
}
