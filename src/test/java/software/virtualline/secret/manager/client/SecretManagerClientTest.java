package software.virtualline.secret.manager.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.NestedExceptionUtils;
import software.virtualline.secret.manager.client.config.LibraryConfig;
import software.virtualline.secret.manager.client.exception.SecretManagerAccessException;

@SpringBootTest(classes = LibraryConfig.class)
@EnableAutoConfiguration
public class SecretManagerClientTest {

    @Test
    public void shouldReturnSecretManagerAccessException(@Autowired LibraryConfig libraryConfig) {
        BeanCreationException ex = Assertions.assertThrows(
                BeanCreationException.class,
                libraryConfig::credentialsProperties
        );

        Throwable root = NestedExceptionUtils.getMostSpecificCause(ex);
        Assertions.assertInstanceOf(SecretManagerAccessException.class, root);
    }
}
