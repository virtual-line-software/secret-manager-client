package software.virtualline.hashicorpautoconfig.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${organisation.id}")
    private String OrgId;

    @Value("${project.id}")
    private String projId;

    private String secretName;

    private String appName;
}
