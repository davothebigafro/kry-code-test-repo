# Back end of web polling service

The backend of this web polling service was written in Java (JDK 16, you may need to be using this version) using Vert.x, with Gradle as a build automation tool.

# Building

You can run from cmd with

```
gradlew clean run
```

You can run the unit test suite with

```
gradlew test
```

IntelliJ IDEA was my IDE of choice

You can import this project into IntelliJ as follows

```
New - New from existing source - select this directory - select Gradle
```
## Containerisation

Inside this project is a Dockerfile which can be used to build a Docker image for the backend of the web polling service.
