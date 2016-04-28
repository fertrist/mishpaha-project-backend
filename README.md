# people-project 

1. It uses Jetty, but can also use Tomcat. To switch between them we just need
to use either
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
or
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
</dependency>

for Jetty we also need to add this stuff
<properties>
    <java.version>1.8</java.version>
    <jetty.version>9.1.0.v20131115</jetty.version>
    <servlet-api.version>3.1.0</servlet-api.version>
</properties>

For development/testing purposes we use spring boot to imitate real server

2. As database we use H2 embedded DB which imitates real DB. It is reinitialized every time bean is created.
