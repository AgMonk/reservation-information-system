FROM openjdk:15
VOLUME /tmp
ADD target/reservation-information-system-0.0.1-SNAPSHOT.war app.war
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV JAVA_OPTS='--add-opens java.base/java.lang=ALL-UNNAMED'
ENTRYPOINT java ${JAVA_OPTS} -jar /app.war  --spring.profiles.active=prod -Ddruid.mysql.usePingMethod=false