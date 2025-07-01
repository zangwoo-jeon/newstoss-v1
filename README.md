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

## API

- auth : 인증 관련 api

  - [POST] /api/auth/login : 로그인 API
  - [POST] /api/auth/logout : 로그아웃 API
  - [GET] /api/auth/refresh : 토큰 재발급 API

- calender : IR 일정 캘린더 관련 API

  - [GET] /api/calen : 캘린더 조회 API

- favorite : 관심 그룹 & 관심 종목 관련 API

  - [GET] /api/favorite/{memberId} : 관심 그룹 조회 API
  - [POST] /api/favorite/{memberId} : 관심 그룹 추가 API
  - [DELETE] /api/favorite/{memberId}/{groupId} : 관심 그룹 삭제 API
  - [PUT] /api/favorite/{memberId}/{groupId} : 관심 그룹 이름 수정 API
  - [PUT] /api/favorite/{memberId}/{groupId}/main : 관심 그룹 메인 변경 API
  - [GET] /api/favorite/{memberId}/{groupId} : 관심 종목 조회 API
  - [POST] /api/favorite/{memberId}/{groupId} : 관심 종목 추가 API
  - [DELETE] /api/favorite/{memberId}/{groupId}/stock : 관심 종목 삭제 API

- member : 회원가입, 탈퇴 등 멤버 정보 관련 API

  - [POST] /api/auth/register : 회원 가입 api
  - [POST] /api/auth/invest : 투자 성향 설정 api
  - [POST] /api/auth/deplicate : 중복 ID 체크 api
  - [DELETE] /api/auth/withdraw : 회원 탈퇴 api

- news : 뉴스, 뉴스 스크랩, 뉴스 로그 관련 API

  - [GET] /api/news/v2/stocknews : 종목과 관련된 뉴스 조회 api
  - [GET] /api/news/v2/search : 뉴스 검색 api
  - [GET] /api/news/v2/related/news : 유사 뉴스 조회 api
  - [GET] /api/news/v2/recommend : 맞춤 뉴스 조회 api
  - [GET] /api/news/v2/meta : 뉴스 메타데이터 조회 api
  - [GET] /api/news/v2/highlight/redis : 하이라이트 뉴스 with redis 조회 api
  - [GET] /api/news/v2/external : 뉴스 external 조회 api
  - [GET] /api/news/v2/detail : 뉴스 상세 조회 api
  - [GET] /api/news/v2/count : 뉴스 갯수 조회 api
  - [GET] /api/news/v2/all : 전체 뉴스 조회 api
  - [GET] /api/scrap : 스크랩 뉴스 조회 api
  - [POST] /api/scrap : 스크랩 뉴스 추가 api
  - [DELETE] /api/scrap : 스크랩 뉴스 삭제 api
  - [POST] /api/newsLogs/record :뉴스 조회 로그 기록 api
  - [GET] /api/newsLogs : 뉴스 로그 조회 api

- userinfo : 유저 정보 조회 API

  - [GET] /api/v1/userinfo/{memberId} : 유저 정보 조회 api
  - [GET] /api/v1/userinfo/all : 모든 유저 정보 조회 api

- portfolio : 포트폴리오 관련 API

  - [GET] /api/v1/portfolios/{memberId} : 포트폴리오 조회 api
  - [POST] /api/v1/portfolios/{memberId} : 포트폴리오 주식 추가 api
  - [POST] /api/v1/portfolios/{memberId}/{stockCode} : 포트폴리오 주식 변경 api
  - [GET] /api/v1/portfolios/asset/{memberId} : 포트폴리오 기간별 자산 조회 api
  - [GET] /api/v1/portfolios/asset/pnl/{memberid} : 포트폴리오 기간별 손익 조회 api

  - stock : 주식 관련 API

    - [GET] /api/v2/stocks/search : 주식 검색 api
    - [POST] /api/v2/stocks/search : 주식 검색 카운트 증가 api
    - [GET] /api/v2/stocks/categories : 카테고리 조회 api
    - [GET] /api/v2/stocks/categories/{categoryName} : 카테고리별 종목 조회 api
    - [GET] /api/v2/stocks/{stockCode} : 주식 기간별 과거 데이터 조회 api
    - [GET] /api/v2/stocks/popular : 상위 6개 인기 종목 조회 api
    - [GET] /api/v2/stocks/info/{stockCode} : 주식 실시간 정보 조회 api
    - [GET] /api/v2/stocks/indices/{market} : 주요 지수 일자별 조회 api
    - [GET] /api/v2/stocks/FX : FX, 원자재, 채권 조회 api

  - sse : Server Sent Event 연결 API
    - [GET] /api/sse/stream/v2 : 챗봇 연결 api
    - [GET] /api/sse/realtime : 실시간 뉴스 연결 api

## Type 종류

```
feat : 새로운 기능 추가 (이미지추가 포함)
fix : 버그 수정
refactor : 코드 리팩토링
test : 테스트 코드 추가/수정
ci   : CI/CD 관련 변경
```
