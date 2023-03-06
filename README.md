

# PLICE  [![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FCerdure%2FPlice&count_bg=%238AB5FF&title_bg=%23555555&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

<p align="left">
<img width="600" alt="banner" src="https://user-images.githubusercontent.com/110950838/223059029-6d6f1f17-f652-4d85-8d0b-0acc549643fa.png">
</p>

**PLICE(플라이스)는** 공공데이터 기반의 **아파트 정보 플랫폼 서비스**입니다.</br>
아파트의 실거래가를 비롯한 각종 정보들을 얻을 수 있으면서 관심있는 아파트에 대해 이야기도 나눌 수 있는 공간을 목적으로 만들어졌습니다. <br/> 
> 개발기간: 2022.12 ~ 2023.01

<br/>

## 배포 주소
| URL | **[https://plice.site](https://plice.site)** |
| :-: | :-: |


<br/>

## 목차
- **[사용 기술](#사용-기술)**
- **[구조 및 설계](#구조-및-설계)**
- **[서비스 화면](#서비스-화면)**
- **[주요 기능](#주요-기능)**
- **[트러블슈팅](#트러블슈팅)**
- **[리팩토링](#리팩토링)**
- **[보완 사항](#보완-사항)**
- **[회고](#회고)**


<br/>

## 사용 기술

#### Backend
- Java 11
- SpringBoot 2.7.7
- JPA(Spring Data JPA)
- Spring Security
- Gradle 7.6
- MySQL 8.0.31

#### Frontend
- HTML/CSS
- JavaScript ES6
- JQuery 3.3.1


<br/>

## 구조 및 설계

#### 디렉토리 구조

<details>
  
<summary>java</summary>   

```bash
java
 ┣ controller
 ┃ ┣ AdminController.java
 ┃ ┣ ChatController.java
 ┃ ┣ ContentsController.java
 ┃ ┣ HomeController.java
 ┃ ┣ JoinController.java
 ┃ ┣ LoginController.java
 ┃ ┣ MapController.java
 ┃ ┣ MyPageController.java
 ┃ ┗ PostController.java
 ┣ domain
 ┃ ┣ admin
 ┃ ┃ ┣ AccessLog.java
 ┃ ┃ ┣ AdminTeam.java
 ┃ ┃ ┣ Authority.java
 ┃ ┃ ┣ Blacklist.java
 ┃ ┃ ┣ IP.java
 ┃ ┃ ┣ Report.java
 ┃ ┃ ┗ SearchKeyword.java
 ┃ ┣ chat
 ┃ ┃ ┣ Chat.java
 ┃ ┃ ┣ ChatRoom.java
 ┃ ┃ ┗ MemberChatRoom.java
 ┃ ┣ contents
 ┃ ┃ ┗ Contents.java
 ┃ ┣ data
 ┃ ┃ ┣ AddressData.java
 ┃ ┃ ┣ ApartData.java
 ┃ ┃ ┗ TradeData.java
 ┃ ┣ enums
 ┃ ┃ ┗ MemberRole.java
 ┃ ┣ inquire
 ┃ ┃ ┣ Answer.java
 ┃ ┃ ┗ Inquire.java
 ┃ ┣ member
 ┃ ┃ ┣ Member.java
 ┃ ┃ ┗ PersistentLogins.java
 ┃ ┗ post
 ┃ ┃ ┣ Notice.java
 ┃ ┃ ┣ Post.java
 ┃ ┃ ┗ Reply.java
 ┣ dto
 ┃ ┣ admin
 ┃ ┃ ┗ BlockDto.java
 ┃ ┣ chat
 ┃ ┃ ┣ ChatDto.java
 ┃ ┃ ┣ ChatRoomDto.java
 ┃ ┃ ┗ MemberChatRoomDto.java
 ┃ ┣ contents
 ┃ ┃ ┣ ArticleDto.java
 ┃ ┃ ┣ ContentsDto.java
 ┃ ┃ ┣ NaverClient.java
 ┃ ┃ ┣ SearchNewsReq.java
 ┃ ┃ ┗ SearchNewsRes.java
 ┃ ┣ data
 ┃ ┃ ┣ AddressDataDto.java
 ┃ ┃ ┣ ApartDataDto.java
 ┃ ┃ ┗ TradeDataDto.java
 ┃ ┣ inquire
 ┃ ┃ ┣ AnswerDto.java
 ┃ ┃ ┗ InquireDto.java
 ┃ ┣ member
 ┃ ┃ ┗ MemberDto.java
 ┃ ┣ post
 ┃ ┃ ┣ NoticeDto.java
 ┃ ┃ ┣ PostDto.java
 ┃ ┃ ┗ ReplyDto.java
 ┃ ┗ utils
 ┃ ┃ ┣ DataUtils.java
 ┃ ┃ ┗ SearchUtils.java
 ┣ repository
 ┃ ┣ admin
 ┃ ┃ ┣ AccessLogRepository.java
 ┃ ┃ ┣ AdminTeamRepository.java
 ┃ ┃ ┣ AuthorityRepository.java
 ┃ ┃ ┣ BlacklistRepository.java
 ┃ ┃ ┣ IPRepository.java
 ┃ ┃ ┣ ReportRepository.java
 ┃ ┃ ┗ SearchKeywordRepository.java
 ┃ ┣ chat
 ┃ ┃ ┣ ChatRepository.java
 ┃ ┃ ┣ ChatRoomRepository.java
 ┃ ┃ ┗ MemberChatRoomRepository.java
 ┃ ┣ contents
 ┃ ┃ ┗ ContentsRepository.java
 ┃ ┣ data
 ┃ ┃ ┣ AddressDataRepository.java
 ┃ ┃ ┣ ApartDataRepository.java
 ┃ ┃ ┗ TradeDataRepository.java
 ┃ ┣ inquire
 ┃ ┃ ┣ AnswerRepository.java
 ┃ ┃ ┗ InquireRepository.java
 ┃ ┣ member
 ┃ ┃ ┗ MemberRepository.java
 ┃ ┗ post
 ┃ ┃ ┣ NoticeRepository.java
 ┃ ┃ ┣ PostRepository.java
 ┃ ┃ ┗ ReplyRepository.java
 ┣ service
 ┃ ┣ classes
 ┃ ┃ ┣ AdminServiceImpl.java
 ┃ ┃ ┣ ChatServiceImpl.java
 ┃ ┃ ┣ ContentsServiceImpl.java
 ┃ ┃ ┣ InquireServiceImpl.java
 ┃ ┃ ┣ LoginServiceImpl.java
 ┃ ┃ ┣ MapServiceImpl.java
 ┃ ┃ ┣ MemberServiceImpl.java
 ┃ ┃ ┣ PostServiceImpl.java
 ┃ ┃ ┗ ReplyServiceImpl.java
 ┃ ┗ interfaces
 ┃ ┃ ┣ AdminService.java
 ┃ ┃ ┣ ChatService.java
 ┃ ┃ ┣ ContentsService.java
 ┃ ┃ ┣ InquireService.java
 ┃ ┃ ┣ LoginService.java
 ┃ ┃ ┣ MapService.java
 ┃ ┃ ┣ MemberService.java
 ┃ ┃ ┣ PostService.java
 ┃ ┃ ┗ ReplyService.java
 ┣ web
 ┃ ┣ interceptor
 ┃ ┃ ┣ BlockCheckInterceptor.java
 ┃ ┃ ┗ LoggerInterceptor.java
 ┃ ┣ security
 ┃ ┃ ┣ LoginSuccessHandler.java
 ┃ ┃ ┣ MemberAdapter.java
 ┃ ┃ ┗ SecurityConfig.java
 ┃ ┗ webConfig
 ┃ ┃ ┣ WebMvcConfiguration.java
 ┃ ┃ ┗ WebSocketConfig.java
 ┣ InitDb.java
 ┗ PliceApplication.java
````

</details>

<details>
  
<summary>resources</summary>   

```bash
resources
 ┣ data
 ┃ ┣ address_data.csv
 ┃ ┣ apart_data.csv
 ┃ ┗ trade_data.csv
 ┣ ssl
 ┃ ┗ keystore.p12
 ┣ static
 ┃ ┣ css
 ┃ ┃ ┣ layout
 ┃ ┃ ┃ ┗ default.css
 ┃ ┃ ┗ layout-content
 ┃ ┃ ┃ ┣ admin
 ┃ ┃ ┃ ┃ ┣ access-mng.css
 ┃ ┃ ┃ ┃ ┣ admin-mng.css
 ┃ ┃ ┃ ┃ ┣ chat-mng.css
 ┃ ┃ ┃ ┃ ┣ inquiry-mng.css
 ┃ ┃ ┃ ┃ ┣ member-mng.css
 ┃ ┃ ┃ ┃ ┣ post-mng.css
 ┃ ┃ ┃ ┃ ┗ site-chart.css
 ┃ ┃ ┃ ┣ chat
 ┃ ┃ ┃ ┃ ┗ chat.css
 ┃ ┃ ┃ ┣ contents
 ┃ ┃ ┃ ┃ ┗ contents.css
 ┃ ┃ ┃ ┣ home
 ┃ ┃ ┃ ┃ ┗ home.css
 ┃ ┃ ┃ ┣ join
 ┃ ┃ ┃ ┃ ┣ join-success.css
 ┃ ┃ ┃ ┃ ┣ join.css
 ┃ ┃ ┃ ┃ ┣ marketing.css
 ┃ ┃ ┃ ┃ ┣ sign-up.css
 ┃ ┃ ┃ ┃ ┣ term-of-service.css
 ┃ ┃ ┃ ┃ ┣ term-service.css
 ┃ ┃ ┃ ┃ ┗ use-personal.css
 ┃ ┃ ┃ ┣ login
 ┃ ┃ ┃ ┃ ┣ login.css
 ┃ ┃ ┃ ┃ ┗ pw-reset.css
 ┃ ┃ ┃ ┣ map
 ┃ ┃ ┃ ┃ ┗ map.css
 ┃ ┃ ┃ ┣ my-page
 ┃ ┃ ┃ ┃ ┣ inquiry.css
 ┃ ┃ ┃ ┃ ┣ inquiry_write.css
 ┃ ┃ ┃ ┃ ┗ my-page.css
 ┃ ┃ ┃ ┗ post
 ┃ ┃ ┃ ┃ ┣ notice-detail.css
 ┃ ┃ ┃ ┃ ┣ post-notice.css
 ┃ ┃ ┃ ┃ ┣ post-story.css
 ┃ ┃ ┃ ┃ ┗ story-detail.css
 ┃ ┣ img
 ┃ ┃ ┣ icon
 ┃ ┃ ┣ images
 ┃ ┃ ┗ logo
 ┃ ┣ js
 ┃ ┃ ┣ layout
 ┃ ┃ ┃ ┗ default.js
 ┃ ┃ ┗ layout-content
 ┃ ┃ ┃ ┣ admin
 ┃ ┃ ┃ ┃ ┣ access-mng.js
 ┃ ┃ ┃ ┃ ┣ admin-mng.js
 ┃ ┃ ┃ ┃ ┣ chat-mng.js
 ┃ ┃ ┃ ┃ ┣ inquiry-mng.js
 ┃ ┃ ┃ ┃ ┣ member-mng.js
 ┃ ┃ ┃ ┃ ┣ post-mng.js
 ┃ ┃ ┃ ┃ ┗ site-chart.js
 ┃ ┃ ┃ ┣ chat
 ┃ ┃ ┃ ┃ ┗ chat.js
 ┃ ┃ ┃ ┣ contents
 ┃ ┃ ┃ ┃ ┗ contents.js
 ┃ ┃ ┃ ┣ home
 ┃ ┃ ┃ ┃ ┗ home.js
 ┃ ┃ ┃ ┣ join
 ┃ ┃ ┃ ┃ ┣ join.js
 ┃ ┃ ┃ ┃ ┗ sign-up.js
 ┃ ┃ ┃ ┣ login
 ┃ ┃ ┃ ┃ ┣ login.js
 ┃ ┃ ┃ ┃ ┗ pw-reset.js
 ┃ ┃ ┃ ┣ map
 ┃ ┃ ┃ ┃ ┗ map.js
 ┃ ┃ ┃ ┣ my-page
 ┃ ┃ ┃ ┃ ┣ inquiry.js
 ┃ ┃ ┃ ┃ ┗ my-page.js
 ┃ ┃ ┃ ┗ post
 ┃ ┃ ┃ ┃ ┣ notice-detail.js
 ┃ ┃ ┃ ┃ ┣ post-notice.js
 ┃ ┃ ┃ ┃ ┣ post-story.js
 ┃ ┃ ┃ ┃ ┗ story-detail.js
 ┃ ┗ upload-img
 ┣ templates
 ┃ ┣ layout
 ┃ ┃ ┣ fragments
 ┃ ┃ ┃ ┣ admin-nav.html
 ┃ ┃ ┃ ┣ footer.html
 ┃ ┃ ┃ ┗ header.html
 ┃ ┃ ┗ default.html
 ┃ ┗ layout-content
 ┃ ┃ ┣ admin
 ┃ ┃ ┃ ┣ access-mng-ip.html
 ┃ ┃ ┃ ┣ access-mng-log.html
 ┃ ┃ ┃ ┣ access-mng-member.html
 ┃ ┃ ┃ ┣ admin-mng.html
 ┃ ┃ ┃ ┣ chat-mng.html
 ┃ ┃ ┃ ┣ inquiry-mng.html
 ┃ ┃ ┃ ┣ member-mng.html
 ┃ ┃ ┃ ┣ post-mng-notice.html
 ┃ ┃ ┃ ┣ post-mng-story.html
 ┃ ┃ ┃ ┗ site-chart.html
 ┃ ┃ ┣ chat
 ┃ ┃ ┃ ┗ chat.html
 ┃ ┃ ┣ contents
 ┃ ┃ ┃ ┗ contents.html
 ┃ ┃ ┣ exeption
 ┃ ┃ ┃ ┣ 404.html
 ┃ ┃ ┃ ┗ chart.html
 ┃ ┃ ┣ home
 ┃ ┃ ┃ ┗ home.html
 ┃ ┃ ┣ join
 ┃ ┃ ┃ ┣ join-success.html
 ┃ ┃ ┃ ┣ join.html
 ┃ ┃ ┃ ┣ marketing.html
 ┃ ┃ ┃ ┣ sign-up.html
 ┃ ┃ ┃ ┣ term-of-service.html
 ┃ ┃ ┃ ┣ term-service.html
 ┃ ┃ ┃ ┗ use-personal.html
 ┃ ┃ ┣ login
 ┃ ┃ ┃ ┣ login.html
 ┃ ┃ ┃ ┗ pw-reset.html
 ┃ ┃ ┣ map
 ┃ ┃ ┃ ┗ map.html
 ┃ ┃ ┣ my-page
 ┃ ┃ ┃ ┣ inquiry.html
 ┃ ┃ ┃ ┣ inquiry_write.html
 ┃ ┃ ┃ ┗ my-page.html
 ┃ ┃ ┗ post
 ┃ ┃ ┃ ┣ notice-detail.html
 ┃ ┃ ┃ ┣ post-notice.html
 ┃ ┃ ┃ ┣ post-story.html
 ┃ ┃ ┃ ┗ story-detail.html
 ┗ application.yml
````

</details>

<br/>

#### DB 설계
![erd](https://user-images.githubusercontent.com/110950838/223092641-db9e8cc4-cf71-4dd2-8a8f-238d586e9660.png)


<br/>

## 서비스 화면
| 홈 | 지도 |
| :-: | :-: |
| <img width="949" alt="home" src="https://user-images.githubusercontent.com/110950838/223057681-ce68dbfd-6c2f-49b5-ab59-f2989beb18ff.PNG"> |  <img width="949" alt="map-1" src="https://user-images.githubusercontent.com/110950838/223057728-f88f6bf1-2340-4cec-9520-42a7939ef7d8.PNG">|  
| 채팅 | 이야기 |  
| <img width="949" alt="chat" src="https://user-images.githubusercontent.com/110950838/223057748-2c88c04f-07e3-4166-ad7e-07e4f16f8f75.png">   |  <img width="949" alt="post" src="https://user-images.githubusercontent.com/110950838/223057758-96541834-a2f3-457c-8581-8f2029bafa92.PNG">    |
| 컨텐츠 - 기사 | 컨텐츠 - 유튜브 |  
| <img width="949" alt="content-1" src="https://user-images.githubusercontent.com/110950838/223057787-405a969c-3c7d-4432-8c90-2d08bbaab509.PNG">   |  <img width="949" alt="content-2" src="https://user-images.githubusercontent.com/110950838/223057799-cf25fefc-6697-4e23-9ec4-0f3b5b9e35d5.PNG">    |
| 관리페이지 - 통계 | 관리페이지 - 접속관리 |  
| <img width="949" alt="admin-1" src="https://user-images.githubusercontent.com/110950838/223057849-aec401c9-7f80-42ee-9bec-9ff380cf065b.PNG">   |  <img width="949" alt="admin-2" src="https://user-images.githubusercontent.com/110950838/223057863-0eb62ea0-e7be-4dd7-a597-1b9150f2632f.PNG">    |



<br/>

## 주요 기능

#### 지도
 - Kakao Maps API를 이용한 지도상 아파트 데이터 표시 및 클러스터화
 - 공공데이터 및 Open API를 이용한 아파트 거래가 및 상세정보 조회 
 - 지역 또는 아파트명으로 전국 아파트 검색
 - 거래유형과 전용면적으로 거래 데이터 필터링
 - 원격으로 지역 모습을 확인할 수 있는 스카이뷰와 로드뷰 제공
 - 실시간 인기 검색어 표시
 
#### 채팅
 - STOMP 및 SockJS를 이용한 오픈 채팅 서비스
 - Subscribe 기능으로 동시에 5개까지 채팅방 참여 가능
 - 채팅방별 마지막 채팅 실시간 업데이트
 - 가독성을 위해 날짜별로 채팅 그룹화
 - 이전 채팅을 보고 있을 시 새로운 채팅은 미리보기로 표시
 - 부적절한 채팅에 대한 신고 기능 존재
 - 사이트에서 채팅방에 참여중인 인원수 표시
 - 참여 인원 기준 채팅방 Top3 표시

#### 컨텐츠
 - Naver Search API를 이용한 부동산 관련 인터넷 기사 크롤링
 - YouTube Data API를 통한 부동산 관련 영상 검색 및 재생
 
#### 사이트 관리
- 운영진 관리
  + Spring Security를 이용한 SuperAdmin과 Admin 설정
  + 관리자 계정 CRUD 및 부서 및 관리 권한 변경
- 회원 관리
  + 회원 정보 조회, 수정, 삭제, 차단
- 접속 관리
  + 회원의 접속 IP 및 페이지를 포함하는 Access Log 표시
  + Access Log의 IP 또는 계정 차단
  + 차단된 IP, 계정은 차단 목록에서 확인 가능
- 채팅 관리
  + 채팅 신고 내역 조회 및 처리
  + 신고된 채팅을 기준으로 채팅방 표시
  + 채팅을 작성한 계정을 차단 시 자동으로 완료 처리
- 게시판 관리
  + 게시글 및 공지사항 CRUD
- 1:1문의 관리
  + 1:1문의 내역 조회 및 처리
  + 답변 작성 시 자동으로 완료 처리
- 사이트 통계
  + Google Chart API를 이용한 통계 서비스
  + 일일/월간/연간, 전체/페이지별 방문 추이
  + 페이지별 일일 방문 분포 현황
  + 신규 가입자 추이 및 전일 대비 증감
  + 많이 검색되는 키워드를 시각적 비율로 표시 

<br/>

## 트러블슈팅
#### 실거래가 API의 일일 트래픽 제한
#### 아파트 좌표 변환 API의 응답 지연
#### 아파트 상세정보 데이터 파싱
#### 로드뷰 전환 시 마커 문제
#### 채팅방 변경 후 이전 채팅방 연결 잔존
#### 로그인 시 계정 차단 적용 시점


<br/>

## 리팩토링
#### 관리페이지 검색 코드 개선
#### 사이트 통계 방문 추이 코드 개선
#### 부동산 키워드 크롤링 코드 개선


<br/>

## 보완 사항
#### 효율적인 버전 관리를 위한 CI/CD 적용
#### 코드의 가독성을 위한 주석 처리
#### 지도페이지 첫 로딩 시간 개선


<br/>

## 회고
