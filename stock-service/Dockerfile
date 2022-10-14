FROM maven:3-amazoncorretto-18 AS temp
RUN mkdir "stockservice"
COPY /src /stockservice/src
COPY pom.xml /stockservice/pom.xml
WORKDIR /stockservice
RUN mvn package

FROM amazoncorretto:17
RUN mkdir "stockservice"
EXPOSE 8081
COPY --from=temp /stockservice/target/stock-service.jar  /stockservice/stock-service.jar
ENTRYPOINT java -jar /stockservice/stock-service.jar
