FROM ubuntu:18.04 as prod

ENV CLASSPATH "/dist/conf/:/dist/apps/*:/dist/lib/*"
ENV JAVA_OPTS " -server -Dfile.encoding=UTF-8 -Xmx256m -Xms256m -Xmn128m -Xss512k -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/log/heap_error.log  -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+UseContainerSupport"
ENV APP_MAIN "com.webank.oracle.Application"
ENV LANG en_US.UTF-8

RUN apt-get update \
    && apt-get -y install openjdk-8-jdk \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /dist/

COPY lib                  /dist/lib
COPY conf                 /dist/conf
#COPY script/deploy        /dist/script/deploy
COPY apps                 /dist/apps

EXPOSE 5012

# start commond
ENTRYPOINT java ${JAVA_OPTS} -Djdk.tls.namedGroups="secp256k1", -Duser.timezone="Asia/Shanghai" -Djava.security.egd=file:/dev/./urandom, -Djava.library.path=/dist/conf -cp ${CLASSPATH}  ${APP_MAIN}
