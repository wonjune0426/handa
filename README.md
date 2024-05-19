# 한다

> 한솔 임직원들의 목표 달성 동기부여를 위한 챌린지 서비스
> 

## Handa
http://172.27.0.174:8080

## 💬프로젝트 개요

- 개발 동기
    - 혼자서 시작하기 힘든 자기 계발을 회사의 동료들과 같이 목표를 달성하는 것이 어떨까?
- 기대 효과
    - 취미 측면, 자기 계발 측면을 통해서 성취감을 얻을 수 있다.
    - 공동의 목표를 통해서 유대감을 형성할 수 있다.
    - 자율적이고 창의적인 조직 문화 형성이 가능하다.
    

## 🧩와이어프레임

![image](https://github.com/wonjune0426/handa/assets/97424544/ed6d6247-a9d7-4276-ab32-c38dfb7a6132)

## 📃ERD

[HansolHanda (erdcloud.com)](https://www.erdcloud.com/d/mmLojnftsdFF4n3SR)
![image](https://github.com/wonjune0426/handa/assets/97424544/793b97e7-8a4e-41cc-862a-aa1be55627a3)

## 🧾구현기능

### **회원 관련 기능**

- 회원 가입/회원 정보 수정/로그인
    - 회원 정보 (ID, 비밀번호, 이름)
        - ID는 최대 10자로 제한
        - Password는 영문+숫자 조합으로 8~13자 제한
        - 프로필 이미지를 제외한 모든 항목은 필수 입력
        - 이미지 파일만 업로드 가능 (png, jpeg, jpg, gif )
    - e-mail (이메일 인증 기능)
    - 전화번호, 성별, 직급, 한솔 계열사
- 아이디/비밀번호 찾기
    - 비밀번호 찾기 시 입력한 이메일 주소로 비밀번호 재발급 메일 발송

---

### **챌린지 CRUD**

- 챌린지 이름
    - 12자로 제한
- 썸네일 이미지
    - 이미지 검색 api를 통해 구현
- 카테고리
    - 취미
        - 영화, 모임, 등산
    - 자기 계발
        - 독서, 자격증, 외국어, 운동
- 챌린지 설명
- 챌린지 기간 설정
- 챌린지 생성 날짜
- 챌린지 상태 (모집중, 진행중, 마감)
- 챌린지 D-Day
- 챌린지 검색
    - 챌린지 제목으로 검색
- 카테고리 별 시간 순, 참여 인원 순 정렬
- 챌린지 타입, 상태 별 챌린지 리스트 조회

---

### **댓글 CRUD**

- 댓글은 150자 이내로 제한하며 대댓글 기능은 없음
- 댓글은 해당 챌린지에 참여한 사람만 달 수 있음
- 챌린지 탈퇴해도 댓글은 남아있음

---

### **마이페이지**

- 회원 프로필 정보
- 생성한 챌린지
- 참여한 챌린지
- 회원 정보 수정
- 챌린지 일정 확인

## 🙎 역할분담

- **이고은**
    - 로그인, 회원 가입 (프로필 이미지 파일 업로드)
    - 회원 정보 수정
    - 공통 화면 디자인 및 구현
- **전현정**
    - 챌린지 리스트 페이지 (검색, 정렬 포함)
    - 마이페이지 (내 정보, 챌린지 종류 별 리스트, 일정 기능)
    - 서버 관리
- **최원준**
    - 챌린지 CUD (이미지 검색 api)
    - 챌린지 상세
    - 댓글 CRUD
    - DB관련 설정

## ⚒  개발환경

![기술_스택.PNG](/uploads/afbcbabd14d5c88bb02ee5981396a249/기술_스택.PNG.png)

## 📆 WBS

[https://docs.google.com/spreadsheets/d/1pTb7TWnkoVZI2LFhVo_q-zKbTDVSNS_8CK5rVLtv7k0/edit#gid=0](https://docs.google.com/spreadsheets/d/1pTb7TWnkoVZI2LFhVo_q-zKbTDVSNS_8CK5rVLtv7k0/edit#gid=0)

[한다 WBS](https://docs.google.com/spreadsheets/d/1pTb7TWnkoVZI2LFhVo_q-zKbTDVSNS_8CK5rVLtv7k0/edit?usp=sharing)

## 📖  작업일지

[api 명세서](https://www.notion.so/af9ee1eb69984c82ae3a3ac692e86cc7)

[일정관리](https://www.notion.so/9cdb3d4617b74e5085b4a5ca322544a7)

[Commit message Guide](https://www.notion.so/Commit-message-Guide-b1d836d6698b4d86807b13d30257c6e9)

[회의록](https://www.notion.so/100cde9d6ba940eca06cf1357c9afc5a)
