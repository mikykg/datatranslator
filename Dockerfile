FROM openjdk:11.0.1-jdk-slim
VOLUME /tmp
COPY ./build/libs/datatranslator*.jar datatranslator.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /datatranslator.jar" ]