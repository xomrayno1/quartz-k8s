FROM maven:3.8.3-openjdk-17 as build
WORKDIR ./src
COPY . .
RUN mvn install -DskipTests=true

CMD [ "executable" ]
COPY /target dest

## run stage ##
FROM openjdk:17
COPY --from=build src/target/*.jar /run/quartzk8s.jar

EXPOSE 8080                                                                                                                                      
ENTRYPOINT java -jar /run/quartzk8s.jar