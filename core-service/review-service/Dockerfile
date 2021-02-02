FROM maven:3.6-openjdk-11

COPY ./review-service/target/*.jar /home/app/

WORKDIR /home/app


EXPOSE 8085

ENTRYPOINT java -jar *.jar

