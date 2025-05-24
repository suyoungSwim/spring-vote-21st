# ------------------------------
# 1단계: 빌드 단계 (build stage)
# ------------------------------

#컨테이너에서 gradle(8.7)과 jdk 17을 사용해서 빌드하겠다.
FROM gradle:8.7.0-jdk17 AS build

#컨테이너 내부에 /app 디렉토리를 만들어서 그 디렉토리에서 작업하겠다.
WORKDIR /app

#Dockerfile 기반으로 이미지 만들 때 docker build . 명령어를 실행함 (.는 현재 디렉토리 즉 내 로컬 디렉토리 = 소스코드 전체)
#현재 디렉토리(소스코드 전체)를 컨테이너의 /app으로 복사.
#도커파일 .과 ..은 각각 내 컴퓨터의 현재 디렉토리, 컨테이너의 현재 학업 디렉토리를 의미한다고 한다.
COPY . .

#gradle로 프로젝트 빌드 => JAR 파일 생성
RUN gradle clean build -x test

# ------------------------------
# 2단계: 런타임 단계 (runtime stage)
# ------------------------------

#컨테이너에서 사용할 JDK 버전
FROM eclipse-temurin:17.0.14_7-jdk

#1단계와 마찬가지로 작업 디렉토리 /app 설정
WORKDIR /app

#1단계 컨테이너에서 빌드 결과물인 jar만 복사해서 가져옴 => app.jar로 가져옴
COPY --from=build /app/build/libs/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=docker

#컨테이너가 8080포트를 공개
EXPOSE 8080

#컨테이너가 실행될 때(docker run) 이 명령어가 실행 => java -jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# ---------------------------------
# 참고:
##1단계와 2단계는 서로 다른 컨테이너에서 일어난다. 그래서 각 단계 처음에 jdk를 따로 명시함.