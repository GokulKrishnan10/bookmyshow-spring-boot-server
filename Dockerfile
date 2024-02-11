FROM amazoncorretto:17-alpine-jdk
COPY private.pem /home/
COPY public.pem /home/
COPY target/livedocs-server-0.0.1-SNAPSHOT.jar docker-spring-server-1.0.0.jar
ENTRYPOINT ["java","-jar","/docker-spring-server-1.0.0.jar"]