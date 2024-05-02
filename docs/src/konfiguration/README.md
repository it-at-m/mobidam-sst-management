# Konfiguration
## Backend

```json
realm: mobidam
spring:
    security:
        oauth2:
            resourceserver:
                jwt:
                    jwk-set-uri: https://myidp/auth/realms/${realm}/protocol/openid-connect/certs
                    issuer-uri: https://myidp/auth/realms/${realm}

security:
    oauth2:
        resource:
            user-info-uri: https://myidp/auth/realms/${realm}/protocol/openid-connect/userinfo
```

## Api-Gateway

```json
server:
  port: 8082
spring:
  # spring cloud gateway config
  cloud:
    gateway:
      actuator:
        verbose:
          enabled: false
  mvc:
    log-request-details: on

  # security config
  security:
    oauth2:
      client:
        provider:
          keycloak:
            issuer-uri: https://myidp/auth/realms/${realm}
        registration:
          keycloak:
            client-id: myclient
            client-secret: mysecret
      resourceserver:
        jwt:
          jwk-set-uri: https://myidp/auth/realms/${realm}/protocol/openid-connect/certs

      resource:
        user-info-uri: https://myidp/auth/realms/${realm}/protocol/openid-connect/userinfo

realm: mobidam

app:
  gateway:
    routing:
      backend:
        uri: http://localhost:39146/
      sso:
        uri: https://myidp

    security:
      keycloak:
        realm: ${realm}
```