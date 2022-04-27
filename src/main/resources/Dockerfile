FROM frolvlad/alpine-oraclejdk8
VOLUME /data/tmp/
ADD npc-java.jar app.jar
ENV VERSION 1.0
WORKDIR /app
RUN mkdir -p /data/tmp/
RUN touch /data/tmp/npc.conf

EXPOSE 8656
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/app.jar"]