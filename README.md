# Issue #144 / #5013

- [Issue #144: Spring Security integration, for user authentication and authorization in gateway](https://github.com/spring-cloud/spring-cloud-gateway/issues/144)
- [Issue #5013: Reactive: Principal of ServerWebExchange is empty?](https://github.com/spring-projects/spring-security/issues/5013)

```bash
mvn clean package
java -jar target/issue144-1.0-SNAPSHOT.jar
curl -u user:pw http://localhost:8080/get
```