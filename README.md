# Food Delivery Monorepo (Gradle Groovy, REST-only, no DDD)

Generated skeleton with separate services and DB-per-service. Adjust versions in root `build.gradle` ext block if needed.

## How to open
1. Open the root folder in IntelliJ IDEA.
2. Gradle sync.
3. Start infra (optional): `docker compose up -d`.
4. Run services:
   - `./gradlew :services:menu-service:bootRun`
   - `./gradlew :services:order-service:bootRun`

## Ports
- gateway 8080, menu 8081, cart 8082, order 8083, payment 8084, delivery 8085, notification 8086, auth 8087.
