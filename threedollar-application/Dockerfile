# Step1: Build
FROM openjdk:17-alpine AS build
ENV HOME=/app
WORKDIR $HOME
COPY . $HOME
RUN ./gradlew clean :threedollar-application:bootJar

# Step2: Install Pinpoint agent
FROM openjdk:17-alpine AS pinpoint
WORKDIR /app/pinpoint-agent
ENV PINPOINT_VERSION=2.5.4
RUN apk add --no-cache curl unzip && \
    wget https://github.com/pinpoint-apm/pinpoint/releases/download/v${PINPOINT_VERSION}/pinpoint-agent-${PINPOINT_VERSION}.tar.gz && \
    tar xzf pinpoint-agent-${PINPOINT_VERSION}.tar.gz && \
    rm pinpoint-agent-${PINPOINT_VERSION}.tar.gz

# Step3: Run
FROM openjdk:17-alpine
WORKDIR /app
ARG PROFILE
ARG COLLECTOR_IP

COPY --from=build /app/threedollar-application/build/libs/threedollar-application.jar /app.jar
COPY --from=pinpoint /app/pinpoint-agent /app/pinpoint-agent

ENV PINPOINT_VERSION=2.5.4
#ENV JAVA_TOOL_OPTIONS="-javaagent:/app/pinpoint-agent/pinpoint-agent-${PINPOINT_VERSION}/pinpoint-bootstrap-${PINPOINT_VERSION}.jar \
#                       -Dpinpoint.applicationName=COMMUNITY-API-${PROFILE} \
#                       -Dprofiler.sampling.type=COUNTING \
#                       -Dprofiler.sampling.counting.sampling-rate=1 \
#                       -Dprofiler.tomcat.excludeurl=/*/health/liveness,/*/health/readiness \
#                       -Dprofiler.transport.grpc.collector.ip=${COLLECTOR_IP:-127.0.0.1}"

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/app.jar"]

EXPOSE 4000
