FROM openjdk:8-jre-alpine
# Install prerequisites
RUN apk --no-cache add curl
RUN mkdir -p /opt/app
WORKDIR /opt/app
COPY target/scala-2.12/app-assembly.jar ./app-assembly.jar
RUN ls -la
RUN java -version
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app-assembly.jar"]