package software.virtualline.secret.manager.client.data;

import java.util.Map;

public record CredentialsDtoList(
        Map<String, CredentialsDto> passwords
) {
}
