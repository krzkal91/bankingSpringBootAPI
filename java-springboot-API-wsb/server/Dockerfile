#
# Build stage
#

FROM maven:3.8.1-jdk-11 as build

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package -DskipTests=true


#
# Package stage
#

FROM openjdk:11-jre

COPY --from=build /home/app/target/wsbSpringBootAPI-0.0.1-SNAPSHOT.jar /usr/local/lib/api.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "/usr/local/lib/api.jar"]