package software.virtualline.secret.manager.client.data;

import java.util.Map;

public record CredentialsDto(
        String username,
        String password,
        Map<String, Object> otherProperties
) {}
