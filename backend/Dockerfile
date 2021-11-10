FROM openjdk:16
COPY . /app
WORKDIR /app
RUN chmod +x gradlew
RUN ./gradlew --version
RUN ./gradlew clean build
EXPOSE 8080
ENTRYPOINT ./gradlew clean run
