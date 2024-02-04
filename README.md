Library which make easy to fetch sensitive. You just need specify `vls.hashicorp.app-name`, `vls.hashicorp.secret-name`
and then you can use `software.virtualline.hashicorpautoconfig.properties.CredentialsProperties` to get your needed
passwords which are stored on HashiCorp service


How to publish it correctly:

1. **change** library version in build.gradle file: `org.gradle.api.Project#setVersion` 
2. push your changes to repository
3. run custom gradle command: `./gradlew publishing`