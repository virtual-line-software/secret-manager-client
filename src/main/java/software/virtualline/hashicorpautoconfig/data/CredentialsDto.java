package software.virtualline.hashicorpautoconfig.data;

import java.util.Map;

public record CredentialsDto(
        String username,
        String password,
        Map<String, Object> otherProperties
) {}
