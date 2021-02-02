FROM maven:3.6-openjdk-11

COPY ./category-service/target/*.jar /home/app/

WORKDIR /home/app


EXPOSE 8087

ENTRYPOINT java -jar *.jar

