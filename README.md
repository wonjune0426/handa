# 한다

> 한솔 임직원들의 목표 달성 동기부여를 위한 챌린지 서비스
> 

## 프로젝트 개요

- 개발 동기
    - 혼자서 시작하기 힘든 자기 계발을 회사의 동료들과 같이 목표를 달성하는 것이 어떨까?
- 기대 효과
    - 취미 측면, 자기 계발 측면을 통해서 성취감을 얻을 수 있다.
    - 공동의 목표를 통해서 유대감을 형성할 수 있다.
    - 자율적이고 창의적인 조직 문화 형성이 가능하다.
    

## 와이어프레임

![와이어프레임.PNG](/uploads/7c58b7d1d693148fc19f34442140cf23/와이어프레임.PNG.png)

## ERD

[HansolHanda (erdcloud.com)](https://www.erdcloud.com/d/mmLojnftsdFF4n3SR)

![ERD.PNG](/uploads/fe0b1324216606ab2527d308588b1cb2/ERD.PNG.png)

## 구현기능

- 회원가입 , 로그인
    - 회원정보 (ID, 비밀번호, 이름)
        - ID는 10자로 제한
        - Password는 영문+숫자 조합으로 13자 제한
        - 프로필 이미지를 제외한 모든 항목은 필수 입력
    - 한솔 계열사
        - 한솔 인티큐브
        - 한솔 제지
        - 한솔 테크닉스
        - 한솔페이퍼텍
        - 한솔 홀딩스
        - 한솔문화재단
        - 한솔케미칼
        - 한솔홈데코
        - 한솔코에버
        - 한솔 BS
        - 한솔 PNS
        - 한솔 로지스틱스
    - 프로필 이미지
        - 이미지 파일업로드
        - 이미지파일 확장자가 아니면 업로드 할 수 없음
    - 전화번호
    - e-mail
    - 성별
    - 직급
        - 선임
        - 수석
        - 책임
- 챌린지 CRUD
    - 챌린지 이름
        - 15자로 제한
    - 썸네일 이미지
        - 이미지 검색 api를 통해 구현
    - 카테고리
        - 취미
            - 영화, 모임, 등산
        - 자기계발
            - 독서, 자격증, 외국어
    - 챌린지 설명
    - 챌린지 기간 설정
    - 챌린지 생성 날짜
    - 각 카테고리별 시간순, 참여인원수 정렬
- 댓글 CRUD
    - 댓글은 50자 이내로 제한하며 대댓글 기능은 없음
- 마이페이지
    - 간단한 프로필 정보
    - 참여한 챌린지
    - 개설한 챌린지
        - 모집을 제한할 수 있음
    - 회원정보 수정
    - 챌린지 일정 확인

## 역할분담

- 이고은
    - 로그인, 회원가입 (프로필 이미지 파일업로드)
    - 회원정보 수정
    - 공통 화면 디자인 및 구현
- 전현정
    - 메인 페이지 (검색, 정렬 포함)
    - 마이페이지 (내 정보, 챌린지 종류별 리스트, 일정 기능)
    - 서버 관리
- 최원준
    - 챌린지 상세
    - 챌린지 참여 제한 설정 여부
    - 챌린지 CRUD
    - DB관련 설정
    

## 개발환경

(상세 버전 미정)

- **Back-End**
    - Spring Boot 2.6.8
    - Java 8
    - Apache Tomcat
    - Gradle
    - mariaDB 10.6.8
    - Spring Security

- **Front-End**
    - HTML, CSS, JS
    - Bootstrap
    - JQuery
    - Thymeleaf

- **APIs**
    - 네이버 이미지 검색 API
    - 댓글 API
    - 달력 API
    - 에디터 API
    

- **Co-op**
    - Notion
    - Slack
    - Gitlab
    

## WBS

[https://docs.google.com/spreadsheets/d/1pTb7TWnkoVZI2LFhVo_q-zKbTDVSNS_8CK5rVLtv7k0/edit#gid=0](https://docs.google.com/spreadsheets/d/1pTb7TWnkoVZI2LFhVo_q-zKbTDVSNS_8CK5rVLtv7k0/edit#gid=0)
