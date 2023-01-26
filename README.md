[![](https://jitpack.io/v/TakuiasH/web-server-java.svg)](https://jitpack.io/#TakuiasH/web-server-java)

# web-server-java
 Simple web server for java API implementations

## Usage
Here is an example of a GET request
```java
private static WebServer server = new WebServer(3000);
	
public static void main(String[] args) {
    Router.get("/", (request) -> {
		return "Hello World!";
	});
		
	server.start();
}
```

## Setup
Before get started, make sure you have the [JitPack repository](https://jitpack.io/#TakuiasH/web-server-java) included in your build configuration.

Maven
```xml
<dependency>
    <groupId>com.github.TakuiasH</groupId>
    <artifactId>web-server-java</artifactId>
    <version>Tag</version>
</dependency>
```
