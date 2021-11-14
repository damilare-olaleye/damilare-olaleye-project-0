FROM openjdk:8-jre-alpine AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY project-0/pom.xml /workspace
COPY src /workspace/src
RUN mvn -B package --file project-0/pom.xml -DskipTests

FROM openjdk:14-slim
COPY --from=build /workspace/target/*jar-with-dependencies.jar app.jar
EXPOSE 6379
ENTRYPOINT ["java","-jar","app.jar"]
