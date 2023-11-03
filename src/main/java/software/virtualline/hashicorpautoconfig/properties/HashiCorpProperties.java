package software.virtualline.hashicorpautoconfig.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("vls.hashicorp")
@PropertySource("file:${user.home}/.vls/credentials")
public class HashiCorpProperties {

    private String clientId;

    private String clientSecret;

    private String organisationId;

    private String projectId;

    private String secretName;

    private String appName;
}
