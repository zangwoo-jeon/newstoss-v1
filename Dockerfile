# 빌드 스테이지
FROM gradle:7.6.1-jdk17 AS build

# Gradle 캐시를 위한 볼륨 설정
VOLUME /home/gradle/.gradle
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# 실행 스테이지
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 환경 변수 설정
ENV TZ=Asia/Seoul
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 