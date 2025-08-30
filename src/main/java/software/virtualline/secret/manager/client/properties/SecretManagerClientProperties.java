package software.virtualline.secret.manager.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("vls.secret.manager.client")
public class SecretManagerClientProperties {

    private String projectId;
    private String secretName;
}
