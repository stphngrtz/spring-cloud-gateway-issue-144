# Issue 144

[Issue #144: Spring Security integration, for user authentication and authorization in gateway](https://github.com/spring-cloud/spring-cloud-gateway/issues/144)

```bash
mvn clean package
java -jar target/issue144-1.0-SNAPSHOT.jar
curl -u user:pw http://localhost:8080/get
```