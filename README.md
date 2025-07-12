# 📖 주식 정보, 맞춤형 투자 기업 분석 플랫폼

![alt text](/readme_picture/1.png)

## 시작하기

[뉴스 토스 링크](https://news-toss.vercel.app/)

## 프로젝트 소개

- NewsToss는 인공지능이 분석한 핵심 뉴스와 과거 유사 뉴스를 제공하여 주식 투자자들이 더 현명한 투자 결정을 내릴 수 있도록 도와드립니다.
- 주요 뉴스 선별 : 주가와 연관있는 뉴스를 AI가 선별하여 보여드립니다.
- 유사 뉴스 검색 : 실시간으로 유입되는 뉴스와 유사한 과거 뉴스를 찾아주고, 당시 주가 흐름을 함께 제공합니다.
- 개인화 서비스 : 포트폴리오를 분석하여 개인의 투자 성향과 보유 종목에 맞는 맞춤형 뉴스를 제공합니다.
- 캘린더 & 투자 도우미 챗봇 : 증시 일정을 확인하고 유사한 과거 뉴스에 대해 챗봇에게 물어볼 수 있습니다.

## 시스템 아키텍처

### Full Stack Architecture

<br />

![alt text](/readme_picture/2.webp)

<br />

## 팀원 구성

|                                             조장                                              |                           조원                            |                                             조원                                              |                                            조원                                             |
| :-------------------------------------------------------------------------------------------: | :-------------------------------------------------------: | :-------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------: |
| ![View parkstar12's full-sized avatar](https://avatars.githubusercontent.com/u/175083996?v=4) | ![](https://avatars.githubusercontent.com/u/61596931?v=4) | ![View gudwns1812's full-sized avatar](https://avatars.githubusercontent.com/u/128285587?v=4) | ![View kwgon0212's full-sized avatar](https://avatars.githubusercontent.com/u/86401186?v=4) |
|                            [박준영](https://github.com/parkstar12)                            |         [전장우](https://github.com/zangwoo-jeon)         |                            [박형준](https://github.com/gudwns1812)                            |                           [김우곤](https://github.com/kwgon0212)                            |
|                                            BackEnd                                            |                          DevOps                           |                                            BackEnd                                            |                                          FrontEnd                                           |

---

## 개발 환경

- Frontend : Next.js, Vercel
  <br />
- Backend : Springboot, AWS EC2, Docker
  <br />
- 버전 및 이슈관리 : Github
  <br />
- CICD : Github Actions
  <br />
- 협업 툴 : Slack, Notion
  <br />
- 디자인 : Figma

## 프로젝트 구조

    📁 .github                   # github
     ㄴ 📁 workflows               # github actions
     ㄴ 📁 ISSUE_TEMPLATE          # github issue template
    📁 src\main
     ㄴ 📁 java\com\newstoss       # dvc
          ㄴ 📁 auth               # auth
          ㄴ 📁 calender           # IR calendar
          ㄴ 📁 favorite           # favorite group & stock
          ㄴ 📁 global             # config, errorcode, etc...
          ㄴ 📁 member             # member
          ㄴ 📁 news               # news, scrap, sse
          ㄴ 📁 portfolio          # portfolio
          ㄴ 📁 savenews           # news collect
          ㄴ 📁 stock              # stock
     ㄴ 📁 resource
          ㄴ 📄 logback-spring.xml # logback
    📄 Dockerfile                  # Dockerfile

<br />

## 내가 맡은 역할

---

### API

- calender : IR 일정 캘린더 관련 API

  - [GET] /api/calen : 캘린더 조회 API(Request Param으로 year와 month를 입력하면 해당 월의 IR 일정을, day까지 입력하면 해당 날짜의 IR 일정을 조회)


- favorite : 관심 그룹 & 관심 종목 관련 API

  - [GET] /api/favorite/{memberId} : 관심 그룹 조회 API
  - [POST] /api/favorite/{memberId} : 관심 그룹 추가 API
  - [DELETE] /api/favorite/{memberId}/{groupId} : 관심 그룹 삭제 API
  - [PUT] /api/favorite/{memberId}/{groupId} : 관심 그룹 이름 수정 API
  - [PUT] /api/favorite/{memberId}/{groupId}/main : 관심 그룹 메인 변경 API
  - [GET] /api/favorite/{memberId}/{groupId} : 관심 종목 조회 API
  - [POST] /api/favorite/{memberId}/{groupId} : 관심 종목 추가 API
  - [DELETE] /api/favorite/{memberId}/{groupId}/stock : 관심 종목 삭제 API


- news : 뉴스 스크랩, 뉴스 로그 관련 API

  - [GET] /api/scrap : 스크랩 뉴스 조회 api
  - [POST] /api/scrap : 스크랩 뉴스 추가 api
  - [DELETE] /api/scrap : 스크랩 뉴스 삭제 api
  - [POST] /api/newsLogs/record :뉴스 조회 로그 기록 api
  - [GET] /api/newsLogs : 뉴스 로그 조회 api


### CI/CD 파이프라인 과정

#### CI 단계 (ci.yml)

트리거 조건
  - pull Request 생성 / 수정 / 재오픈
  - main 브랜치에 push

CI 과정
  1. 빌드 단계
     - JDK 17 설정
     - Gradle 캐시 활성화로 빌드 속도 향상
     - ./gradlew clean build -x test 명령으로 애플리케이션 빌드
     - 환경 변수들을 통해 JWT, KIS(한국투자증권) API, 관리 엔드포인트 설정
  2. ECR 배포 단계
     - AWS OIDC 인증을 통한 안전한 자격 증명
     - Docker 이미지 빌드 및 ECR push
     - latest 태그와 커밋 SHA 태그로 이미지 버전 관리

#### CD 단계 (cd.yml)

트리거 조건
  - CI 워크플로우 성공적으로 완료
  - main 브랜치에 push

CD 과정
  1. EC2 배포 준비
  2. EC2 SSH 배포
     - 환경 설정 및 도커 네트워크 확인
     - 최신 이미지 정보 확인 및 Pull
     - 기존 컨테이너 중지 및 제거
     - 새로운 컨테이너 실행
     - Health Cehck
     - 리소스 정리(구버전 도커 이미지 삭제)


## 관심 종목 API 아키텍처 전환

![alt text](/readme_picture/4.png)

1. 아키텍처 분리 : 초기에는 주가 정보를 메인 서비스와 동일한 DB에서 관리하였으나, 한국투자증권 API의 초당 20회 호출 제한과 실시간 증권 데이터 수집 시 발생하는 초당 2,000회 DB 쓰기 요청으로 인한 과부화 문제함. 팀에서 이 문제를 해결하고자, 데이터 수집 및 제공을 전담하는 별도의 EC2 서버와 DB로 분리함
2. 데이터 조회 방식 변경 :관심 종목의 주가 정보도 메인 DB를 직접 조회하는 방식에서 분리된 서버의 API로 호출하는 방식으로 사용하게 됨
3. 기술 구현 : Spring의 RestTemplate를 사용해 수신한 JSON 형태의 응답을 DTO로 변환하여 서비스 로직에서 처리하도록 구현함
4. 기여 및 성과 : 관심 종목 API를 새로운 아키텍처에 성공적으로 통합. API가 DB가 아닌 외부 API에만 의존하도록 변경하여, 서비스 간의 결합도를 낮추는데 기여함

## 뉴스 로그 API 구축

![alt text](/readme_picture/5.png)

### 목적
- 사용자 행동 분석 : 어떤 뉴스를 언제 조회했는지 추적
- 인기 컨텐츠 파악 : 조회 빈도 기반 인기 뉴스 식별
- 개인화 서비스 : 사용자 맞춤형 뉴스 제공


### 로그 기록 API
- 로그인 / 비로그인 사용자 구분 기록
- newsId 기록
- logback을 사용해 로그 파일 저장

### 로그 조회 API
- 날짜 범위별 로그 조회
- 사용자별 조회
- 시간순 정렬

### Logback 설정
- 롤링 정책 : 일별 롤링 (news.YYYY-MM-DD.log)
- 보존 기간 : 30일
- 로그 패턴 : 2025-06-25 14:30:25 [http-nio-8080-exec-1] [INFO ] com.newstoss.news.application.news.v2.impl.ml - [memberId : 123e4567-e89b-12d3-a456-426614174000] [newsId : news_001]


## 트러블 슈팅

---

### SSE 연결 중 Mixed Content 문제 발생

![alt text](/readme_picture/6.png)

문제 상황
- 뉴스 챗봇 서비스에서 실시간 데이터 연동을 위해 SSE(Server-Sent Events) 기술을 사용하고자 함
- 하지만 HTTPS로 서비스되는 프론트엔드에서 HTTP로 노출된 백엔드 SSE 엔드포인트로 연결을 시도하자, 브라우저의 보안 정책으로 인해 'Mixed Content' 오류가 발생하며 연결이 차단됨

개선 방안
- EC2 인스턴스 앞에 Application Load Balancer (ALB)를 도입하고, ALB에 SSL 인증서를 적용
- 클라이언트(프론트엔드)와 ALB 사이의 모든 통신은 HTTPS로 암호화하고, ALB는 암호화된 트래픽을 복호화하여(SSL Offloading) 내부 EC2 인스턴스와는 기존의 HTTP로 통신하도록 아키텍처를 개선
- 리스너 규칙: HTTP(80) 요청은 HTTPS(443)로 자동 리디렉션되도록 설정했습니다.

핵심 성과
- Mixed Content 오류 해결: 프론트엔드와 백엔드 간 통신이 모두 HTTPS 기반으로 이루어져 SSE 기능이 완벽하게 정상화됨
- 운영 효율성 증대: SSL 인증서 관리를 EC2 인스턴스가 아닌 ALB에서 중앙 관리하게 되어 유지보수가 간편해짐
- 코드 변경 최소화: 백엔드 애플리케이션은 기존 HTTP 로직을 그대로 유지한 채 HTTPS 통신을 지원하게 되어, 코드 수정 없이 보안을 강화


### EC2 도커 서비스 보안 강화 : Tailscale VPN으로 안전한 접근 환경 구축

![alt text](/readme_picture/7.png)

문제 상황
- 주요 서비스(PostgreSQL, Prometheus 등)가 Docker 컨테이너로 운영되는 AWS EC2 환경에서, 보안 그룹이 0.0.0.0/0(모든 IP)로 설정되어 있음
- 이로 인해 DB와 모니터링 시스템 등 민감한 내부 서비스가 인터넷에 직접 노출되어 보안에 매우 취약
- 팀원들은 고정 IP가 아닌 유동 IP를 사용하므로, IP 주소 기반의 전통적인 접근 제어(화이트리스트) 방식은 적용하기 힘듬

개선 방안 및 결과
- IP가 아닌 사용자 인증을 기반으로 P2P 사설망을 구축하는 Tailscale VPN을 도입
- EC2 인스턴스와 모든 팀원의 기기에 Tailscale을 설치하여 하나의 가상 사설 네트워크로 연결
- 기존의 0.0.0.0/0 인바운드 규칙을 모두 삭제하고, 오직 Tailscale 네트워크를 통한 내부 접근만 허용하도록 변경

핵심 성과
- 보안 강화 : 외부 인터넷망에서의 서비스 직접 접근을 원천적으로 차단하여 보안 취약점 해결
- 관리 효율 증대 : Tailscale 관리 콘솔을 통해 사용자 및 기기별 접근 권한을 중앙에서 유연하게 관리

