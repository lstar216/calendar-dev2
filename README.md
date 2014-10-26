## 프로젝트: calendar-dev
본 프로젝트는 한국기술교육대학교 컴퓨터공학부 4학년 "웹서비스컴퓨팅및실습" 수업에서 Assignment로 활용하는 프로젝트입니다. 

### Assignment 2
#### 제목: Dao를 JdbcTemplate를 활용하여 작성하고 JUnit 프레임워크로 테스트 하기
#### 기한: 2014년 10월 04일 (토요일) 23시 59분 
- calendar-dev를 pull 하여 최신 버전으로 업데이트하기
- calendar-dev 오른쪽 클릭 > Maven > Update Project 실행하여 라이브러리 재정리하기
  - [참고] 새로 추가된 Maven 라이브러리
    - spring-test
    - junit
- 요구 사항 1 - 다음 두 개의 Dao 클래스를 JdbcTemplate를 활용하여 리펙토링하기
  - JdbcCalendarUserDao.java
  - JdbcEventDao.java
  - [주의 1] 각 클래스 마다 deleteAll() 메소드가 추가되었고 구현 필요
  - [주의 2] EventDao 및 JdbcEventDao에 있던 이전 findForUser(int userId)는 findForOwner(int ownerUserId)로 메소드 이름과 인자 이름이 변경되었음, 역시 구현 필요
- 요구 사항 2 - 다음 테스트 클래스 구현하기 
  - 주석으로 설명해 놓은 7가지 사항 코딩하기 
  - [주의 1] @Before 1개 + @Test 6개
  - [주의 2] 테스트는 모두 성공해야 함
  - [주의 3] 비교 테스트시에는 반드시 JUnit 테스트 방법을 활용한다.
  

### Assignment 1
#### 제목: Dao를 이전 스타일로 작성하여 테스트 하기
#### 기한: 2014년 9월 17일 (수요일) 23시 59분 
- 본 프로젝트를 각자 자신의 github 내로 fork 하여 가져가기
- 자신의 로컬 하드로 프로젝트 내려받기 
- STS에서 해당 프로젝트를 import 하기 (Exsiting Maven Project)
- 로컬에 설치된 MySQL에 DB 구축하기
  - MySQL 서버 및 MySQL Workbench 실행
  - calendar-dev/src/main/resources/database/calendar-schema.sql 파일 내용을 MySQL Workbech에서 실행하기
  - 사용자는 반드시 ID: spring, PW: book으로 추가
- 다음의 DAO 파일 구현하기
  - JdbcCalendarUserDao.java
  - JdbcEventDao.java
- DaoTest.java 파일 구현하기
  - 주석으로 설명해 놓은 8가지 사항 코딩하기
  - 실행 모습 캡쳐
- 보고서 작성 및 제출
  - 프로그램 내에 작성한 특정한 부분 코드에 대한 설명을 코드와 함께 적을 것
  - 프로그램 수행 화면을 캡쳐하여 넣을 것
  - 작성 방법
    - 보고서 파일(HWP 또는 MS-Word) 작성
    - 표지, 서론, 본론, 결론의 구조를 가진 보고서
    - 서론: Homework의 내용 및 목적
    - 본론: 각각의 문제를 코딩한 이후 앞에 있는 요구사항에 따라 작성
    - 결론: 숙제를 한 이후의 느낀점, 하고싶은 말, 또는 불평~~
  - 과제를 제출 할 곳: http://el.koreatech.ac.kr 
  - 파일명: 웹서비스컴퓨팅및실습-1차-홍길동-2010111222.hwp
  - 게시물 제목 및 내용: 웹서비스컴퓨팅및실습-1차-홍길동-2010111222
    - 반드시 내용에 본인 코드가 올려져 있는 github repository url을 기재할 것
  - 질문은 jyjin989@koreatech.ac.kr로 메일을 보내면서 yhhan@koreatech.ac.kr를 CC 할 것


