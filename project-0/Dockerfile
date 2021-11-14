FROM maven:3.8.3-jdk-11 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY . /workspace
COPY . /workspace/src
RUN mvn -B package --file project-0/pom.xml -DskipTests

FROM openjdk:11-jdk
COPY --from=build /workspace/target/*jar-with-dependencies.jar app.jar
EXPOSE 6379
ENTRYPOINT ["java","-jar","app.jar"]
