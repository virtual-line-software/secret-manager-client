Library which is reposnible to fetch secret and create object which has all credentials. All credentials is on same secret
which reduce cost of storing secret by secret manager providers like AWS, GCP.

Instead of creating many individual secrets (and paying per secret), all credentials are stored together in a single JSON document inside the secret manager.
The library retrieves that JSON, deserializes it into a CredentialsDtoList, and makes it available for injection in your application.

🔹 Current support: Google Cloud Secret Manager

🔹 Future-ready: designed to be extended with other providers (AWS, Vault, etc.)

🔹 Cost-efficient: one secret = lower monthly costs with providers that charge per secret/version

🔹 Spring integration: autoconfigures a CredentialsDtoList bean from your secret

--- 

Here’s a refined **README.md description block** with the required properties and authentication notes added:

---

## secret-manager-client

A small Java/Spring library for **fetching application credentials from a cloud secret manager** and exposing them as a simple bean.
Instead of creating many individual secrets (and paying per secret), all credentials are stored together in a **single JSON document** inside the secret manager.
The library retrieves that JSON, deserializes it into a `CredentialsDtoList`, and makes it available for injection in your application.

* 🔹 **Current support**: Google Cloud Secret Manager
* 🔹 **Future-ready**: designed to be extended with other providers (AWS, Vault, etc.)
* 🔹 **Cost-efficient**: one secret = lower monthly costs with providers that charge per secret/version
* 🔹 **Spring integration**: autoconfigures a `CredentialsDtoList` bean from your secret

### Required properties

You must configure these properties (e.g. in `application.yml` or `application.properties`):

```yaml
vls:
  secretmanager:
    project-id: your-gcp-project-id   # required, or app will fail to start
    secret-name: your-secret-id       # required, or app will fail to start
```

If either `projectId` or `secretName` is missing, the library throws an error at startup.

### Authentication

The library supports two authentication modes:

1. **Service account key via environment variable** (recommended for Heroku, on-prem, or other non-GCP environments)

    * Create a GCP service account with the role `roles/secretmanager.secretAccessor`.
    * Download the service account JSON key.
    * Set it in an environment variable, e.g.

      ```bash
      export GCP_SA_KEY_JSON="$(cat service-account.json)"
      export GOOGLE_CLOUD_PROJECT=your-gcp-project-id
      ```
    * The library will read `GCP_SA_KEY_JSON` and authenticate in-memory (no file written).

2. **Application Default Credentials (ADC)** (recommended for local dev and when running inside GCP)

    * Locally: run

      ```bash
      gcloud auth application-default login
      export GOOGLE_CLOUD_PROJECT=your-gcp-project-id
      ```
    * On GCP (Cloud Run, GKE, GCE): just attach a service account with `roles/secretmanager.secretAccessor`. The library will automatically use it.

---

How to publish it correctly:

1. **change** library version in build.gradle file: `org.gradle.api.Project#setVersion` 
2. push your changes to repository
3. run custom gradle command: `./gradlew publishing`