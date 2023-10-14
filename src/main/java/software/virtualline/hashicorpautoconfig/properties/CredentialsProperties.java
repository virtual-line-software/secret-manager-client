package software.virtualline.hashicorpautoconfig.properties;

import software.virtualline.hashicorpautoconfig.data.PasswordDTO;

import java.util.Map;

public record CredentialsProperties(
        Map<String, PasswordDTO> passwords
) {
}
