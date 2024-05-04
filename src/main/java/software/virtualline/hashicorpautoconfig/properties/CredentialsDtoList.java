package software.virtualline.hashicorpautoconfig.properties;

import software.virtualline.hashicorpautoconfig.data.CredentialsDto;

import java.util.Map;

public record CredentialsDtoList(
        Map<String, CredentialsDto> passwords
) {
}
