# PLICE  [![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FCerdure%2FPlice&count_bg=%238AB5FF&title_bg=%23555555&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

<p align="left">
<img width="600" alt="banner" src="https://user-images.githubusercontent.com/110950838/223059029-6d6f1f17-f652-4d85-8d0b-0acc549643fa.png">
</p>

**PLICE(플라이스)는** 공공데이터 기반의 **아파트 정보 플랫폼**입니다.</br>
아파트의 실거래가를 비롯한 각종 정보들을 얻을 수 있으면서 관심있는 아파트에 대해 이야기도 나눌 수 있는 공간을 테마로 만들었습니다. <br/> 

> 개발기간: 2022.12 ~ 2023.01 (4주) </br>
> 개발인원: 4명 

| 최원석 | 윤수호 | 이현규 | 문태웅 |                                                                                                             
| :-: | :-: | :-: | :-: |
| ![110950838](https://user-images.githubusercontent.com/110950838/223305940-0a52c72e-18a9-4956-9c6d-a2964eff0fd7.png) | ![114133589](https://user-images.githubusercontent.com/110950838/223305874-3078d5e5-9dfb-4c36-81cc-a6ce7caa5408.png) | ![114133291](https://user-images.githubusercontent.com/110950838/223305773-2105b126-9e73-4c67-a31a-0530c40be323.png) | ![83555445](https://user-images.githubusercontent.com/110950838/223305674-272752a2-cb7f-418c-b583-bcb8f85a7abb.png) |
| [@Cerdure](https://github.com/Cerdure) | [@yoonsuho2](https://github.com/yoonsuho2) | [@HQ9999Lee](https://github.com/HQ9999Lee) | [@taewoong3](https://github.com/taewoong3) |
| PM, FrontEnd, BackEnd | BackEnd(부동산 컨텐츠 크롤링, 마이페이지) | BackEnd(게시판 CRUD) | BackEnd(회원가입, 로그인) |

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
- **[트러블슈팅 및 리팩토링](#트러블슈팅-및-리팩토링)**
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


## 트러블슈팅 및 리팩토링

<!---------------------------------------------------------------------------->
<details>
<summary><h4>실거래가 API의 일일 트래픽 제한</h4></summary>   

> 지도 페이지를 처음 로딩할 때 전국의 실거래 데이터 1만 건 정도를 한꺼번에 불러오기 때문에 API의 트래픽을 금방 초과해버리는 문제가 발생했습니다. 처음에는 API의 트래픽 허용치를 늘려보았지만 여전히 부족해서 두 가지 방안을 생각해냈습니다. 하나는 요청하는 범위를 전국에서 현재 위치 중심으로 축소시키는 것이고, 다른 하나는 실거래 데이터를 DB에 입력해서 서버 측에서 가져오는 것입니다.<br/><br/>
첫 번째 방안은 페이지의 처음 로딩되는 시간도 단축시킬 수 있는 장점이 있지만, 사용자가 많아진다고 가정하면 임시방편에 불과했습니다. 반면, 두 번째 방안은 직접 데이터를 넣어줘야하는 번거로움이 있지만, 트래픽 제한이 없고 요청 파라미터가 지역코드 밖에 없는 API와는 달리 다양한 조건으로 필터링을 할 수 있으므로 두 번째 방안을 적용하기로 했습니다.<br/><br/>
동일한 데이터를 엑셀 파일로 받아서 주소를 정제하고 DB에 입력하기 위한 CSV 형태로 변환하는 준비를 마치고, MySQL Workbench의 import 기능을 사용해서 DB에 인서트했지만 인서트 과정이 5시간 정도가 소요되었습니다. <br/>
이보다 훨씬 많은 데이터를 다루는 실무를 생각해보면 더 나은 방법이 있을 것이라는 확신에 개선책을 찾아보았고, LOAD DATA INFILE을 통한 대용량 데이터 인서트를 알게되었습니다. 이를 적용한 결과 데이터 입력 시간이 30초로 감소하여 데이터를 DB로 손쉽게 옮길 수 있었고 당초 문제였던 트래픽 제한을 해결할 수 있었습니다.<br/>
</details>

<!---------------------------------------------------------------------------->

<details>
<summary><h4>아파트 상세정보 API의 응답 지연</h4></summary>   

> 아파트 검색 결과 목록에 표시하기 위해 상세 정보를 API로 호출하는 과정에서, 요청량이 많아짐에 따라 응답이 지연되는 문제가 발생했습니다. 원인이 되는 API의 응답시간이 일정하지 않았으며, 검색 결과가 50건만 초과해도 평균 30초 이상이 소요되었습니다. <br/><br/>
API 자체의 응답 시간을 개선할 수는 없기에 요청을 줄이는 쪽으로 생각을 해서 1회 요청수를 제한하기로 했습니다. 일정한 크기로 데이터를 제한해서 가져오는 것이 결국 페이징이라는 생각이 들었고 스크롤을 통한 페이징이 적합하겠다는 결론을 내렸습니다.<br/><br/>
최종적으로 검색 결과 목록에 스크롤 페이징을 적용하여 최대 12개로 1회 요청을 제한함으로써 결과적으로 첫 응답 시간을 평균 3초 정도로 감소시킬 수 있었으며 불필요하게 데이터 전체를 읽어오는 것 또한 방지할 수 있었습니다.<br/>

```javascript
  let update = true
  let beforeLoad = true
  let loadComplete = false
    
 $('#items').scroll(function () {  // 아파트 검색 결과 목록을 스크롤
    let st = $(this).scrollTop(),
      ih = $(this).innerHeight(),
      sh = dom('#items').scrollHeight

    if (st + ih >= sh - 1 && beforeLoad && !loadComplete) {  // 스크롤이 바닥이면서 로딩 중이 아니고 전부 불러오지 않았으면
      update = false  // 새로 검색하는 것이 아님을 설정
      appendApartList()  // 상세정보 요청 및 지도와 목록에 아파트 표시
    }
  })
  
  async function appendApartList() {
    const maxSize = 12  // 한번에 불러오는 수를 12개로 설정
    let endNum = currentItemsIdx + maxSize <= apartDataList.length ? 
      currentItemsIdx + maxSize : apartDataList.length  // 총 개수가 불러오는 개수보다 적을 경우 상정
    $('.item-wrapper .loading').stop().show()  // 진행률을 알려주는 목록 로딩 화면 표시
    
    if (update) {  // 새로 검색하는 경우 초기화
      $('.item').remove()
      apartDetailXmls = []
      apartCoords = []
    }
    beforeLoad = false  // 연속으로 실행되는 것을 방지

    for (let i = currentItemsIdx; i < endNum; i++) {
      try {
      const apartData = apartDataList[i]
      const apartDetail = await apartDetailData(apartData.complexCode)  // 상세정보 요청
      
      // ...  아파트를 목록 및 지도에 표시
       
      } catch (e) {  // 결함 있는 데이터 처리
        console.log(e)
        continue  
      }
      
      // 진행률 반영
      $('.item-wrapper .loading').text((((i - currentItemsIdx + 1) / 12) * 100).toFixed(1) + '%') 

      if (i >= endNum - 1) {  // 로딩 완료 시
        currentItemsIdx = i + 1  // 현재 아파트 목록 인덱스 변경
        beforeLoad = true  // 로딩 중이 아님을 설정
        loadComplete = i >= apartDataList.length - 1 ? true : false  // 모든 데이터 로딩 여부
        $('.item-wrapper .loading').fadeOut(300, function () {  // 로딩 화면 해제
          $('.item-wrapper .loading').text('0%')
        })
      }
    }
  }
```
</details>

<!---------------------------------------------------------------------------->

<details>
<summary><h4>채팅방 변경 시 subscribe 문제</h4></summary>   

> 동시에 여러개의 채팅방에 참여할 수 있도록 구현하는 과정에서 채팅방을 변경할 때 채팅을 송신이 중첩되는 문제가 발생했습니다.<br/><br/>
처음에는 단순히 unsubscribe로 구독을 끊어주는 방식을 시도했지만 목록에 있는 채팅방이 지속적으로 업데이트 되기 위해서는 구독이 끊어지면 안된다는 것을 깨닫고 subscribe를 설정할 때 수신 조건을 추가해주는 방식으로 해결하였습니다.<br/>

```javascript

function subscribe(_roomId) {
    let messageInput = $('.chat-input')
    let sendBtn = $('.send-btn-' + _roomId)
    let roomId = _roomId

    const sock = new SockJS("/ws")
    const client = Stomp.over(sock)

    if (currentSubscribe.get(roomId) != null) {  // 중복 subscribe 방지
        currentSubscribe.get(roomId).unsubscribe()
    }

    client.connect({}, () => {
        if (newJoin) {
            client.send('/publish/chat/in-message', {}, JSON.stringify({ chatRoomId: roomId }))
        }
        currentSubscribe.set(roomId,  // 채팅방 id를 key로 subscribe 저장
            client.subscribe('/subscribe/chat/room/' + roomId, chat => {  // subscribe 설정
                const content = JSON.parse(chat.body)
                let messagebox
                if (content.chatRoomId == currentRoomId) {  // 현재 보고있는 채팅방과 일치하면 채팅 표시
                    if (content.type == 'INFO') { 
                        // ... 입장, 퇴장 메시지 표시 
                    } else { 
                        // ... 채팅 표시
                    }
                    ih = $("#chat-box").innerHeight()
                    sh = dom("#chat-box").scrollHeight
                    document.getElementById("chat-box").appendChild(messagebox)
                    newMessage(content.type)  // 채팅방 내부에서 스크롤 중일 경우 새로운 채팅 미리보기 표시
                }
                myRoomUpdate()  // 채팅방 목록 동기화
            })
        )
```
</details>

<!---------------------------------------------------------------------------->

<details>
<summary><h4>사이트 통계 방문 추이 코드 개선</h4></summary>   

> 기존의 코드는 통계 옵션인 전체와 페이지별이 둘 다 동일한 엔티티를 카운트하기 때문에 중복되는 부분이 많았습니다.<br/><br/>
이를 해결하기 위해 동일한 기능을 메서드로 추출하여 재사용하는 방법을 사용하였고 기간과 페이지만 매개변수로 주입해서 활용할 수 있도록 만들었습니다. 특히 전체 페이지를 카운트할 때도 재사용하기 위해서 페이지 일치여부를 equals가 아닌 contains로 비교하고 전체 페이지일 때는 ""을 넣어서 모든 페이지를 카운트하도록 만들었습니다.<br/><br/>
그 결과 6번 중복되는 코드를 하나의 메서드로 줄일 수 있었고 그로 인해 코드의 가독성과 유지보수성이 크게 향상되었습니다. <br/>


```java
    @Override  // 추출된 카운트 메서드
    public Map<LocalDate, Long> logCountByDay(int range, String page) {  // 기간과 페이지를 매개변수로 설정
        LocalDateTime startDateTime = LocalDateTime.of(  // 시작점 생성
                LocalDateTime.now().minusDays(range).toLocalDate(),  
                LocalTime.of(0, 0, 0));
        List<AccessLog> accessLogs = accessLogRepository.findByRegDateAfter(startDateTime);  // 시작점 이후로 조회
        Map<LocalDate, Long> result = new TreeMap<>();
        List<LocalDate> days = new ArrayList<>();  // key값이 될 day 단위 List 생성

        accessLogs.forEach(accessLog -> {  
            LocalDate regDateDay = accessLog.getRegDate().toLocalDate();  
            if (!days.contains(regDateDay)) days.add(regDateDay);  // 조회한 AccessLog들의 날짜 입력
        });

        days.forEach(day -> {
            result.put(day, accessLogs.stream()
                    .filter(accessLog ->  
                            accessLog.getRegDate().toLocalDate().equals(day)  // day 일치 여부
                                    && accessLog.getUri().contains(page))  // contains이므로 ""를 넣어줄 경우 모든 페이지 카운트
                    .count()  
            );
        });
        return result;
    }
    
    
    @GetMapping("/admin/logCount/day")
    @ResponseBody
    public Object logCountDay(@RequestParam("countType") String type) {
        switch (type) {
            case "total":
                return adminService.logCountByDay(6, "");  // 전체 방문 수, 추출한 메서드 사용
            case "page":
                return adminService.pageCountByDay(6);  // 페이지별 방문 수
            default:
                return null;
        }
    }
    
    @Override
    public Map<String, Map<LocalDate, Long>> pageCountByDay(int range) {  // key가 2개이므로 2중 Map으로 반환
        Map<String, Map<LocalDate, Long>> result = new HashMap<>();
        result.put("home", logCountByDay(range, "home"));  // 추출한 메서드 사용
        result.put("map", logCountByDay(range, "map"));
        result.put("chat", logCountByDay(range, "chat"));
        result.put("post", logCountByDay(range, "post"));
        result.put("contents", logCountByDay(range, "contents"));
        return result;
    }
    
```
</details>

<!---------------------------------------------------------------------------->

<br/>

## 보완 사항
#### 효율적인 버전 관리를 위한 CI/CD 적용
#### 코드의 가독성을 위한 주석 처리
#### 지도페이지 첫 로딩 시간 개선


<br/>

## 회고
처음 진행한 팀프로젝트에서 팀장을 맡게 되어 책임감을 느끼며, 4주라는 기간 동안 완성을 가장 큰 목표로 열심히 달렸습니다.<br/><br/> 
감사하게도 팀원들이 스스럼없이 모르는 것을 의논하고 생각하는 바를 잘 알려주어서 원만하고도 즐겁게 프로젝트를 진행할 수 있었습니다. 덕분에 자신있게 최선을 다 했다고 말할 수 있을 정도로 몰입했고, 결과적으로 목표했던 완성도로 팀프로젝트 1등이라는 기분 좋은 결실을 맺을 수 있었습니다.<br/><br/> 
지금에 와서 되돌아 보면 문서화를 소홀히 한 것이 못내 아쉽긴 합니다. 이를 반성하면서 앞으로 있을 프로젝트는 최대한 꼼꼼하게 문서화하고자 다짐했습니다. 또한 이전에는 잘 손이 가지않았던 Git이 이래서 중요하구나 라는 깨달음을 얻고, 적극적으로 활용하는 계기가 되었습니다.<br/>
웃는 얼굴로 발표를 마치던 팀원들을 생각하면 앞으로 있을 협업에 대해서 자신감이 샘솟으면서도, 어떻게 하면 좀 더 실무에서도 원활하게 소통하고 협력을 할 수 있을지 진지하게 고민도 하게 되는 좋은 경험이었습니다.<br/><br/>
마지막까지 읽어주셔서 감사합니다.
