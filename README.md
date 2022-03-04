# 백엔드 과제📕

## 중점 사항✅

* JWT 토큰을 이용한 로그인 유지
* 본인인증 유효 시간 적용 (10분)

## 설치 및 빌드⚠️
### Maria DB 설정
1. 로컬 PC MariaDB에 데이터베이스 생성
   * DB명 : ably_task
   * 포트 : 3306

### 어플리케이션 실행
#### ItelliJ 통하여 실행
1. 프로젝트 root -> src -> main -> java -> com.ably.task.member 경로 이동
2. **MemberApplication.java** 에서 class 또는 main 함수 좌측에 실행 버튼 클릭

#### build 실행
1. 콘솔창에서 프로젝트 root 경로 진입
2. ./gradlew bootjar 실행
3. build -> libs 경로 이동
4. 생성된 jar 파일 확인 후 'java -jar (jar파일명-member-0.0.1-SNAPSHOT.jar)' 실행

#### querydsl 사용 시 설정 필요(IntelliJ 기준)
1. Preferences 실행
2. 빌드, 실행, 배포에서 gradle 선택
3. 빌드 및 실행을 **IntelliJ IDEA** 로 변경

## 테스트 시나리오
### 1. OTP 인증
1. OTP 발송 API 이용하여 OTP 시퀀스 번호 및 OTP 인증번호 발급
   * OTP 발송 API(/auth/otp, method : POST)
   * 인증 타입은 'JOIN'
2. OTP 인증 API를 이용하여 OTP 인증 완료
   * OTP 인증 API(/auth/otp, method : PUT)
   * OTP 시퀀스 번호와 OTP 인증번호를 전달
   
### 2. 회원가입
1. 회원가입 정보를 입력 후 회원가입 API(/auth/join, method : POST) 호출
   * OTP 인증 시 사용하였던 OTP 인증 시퀀스 번호 전달

### 3. 비밀번호 재설정
1. 다시 <ins>**1. OTP 인증**</ins> 단계를 진행
   * 인증 타입은 'PASSWORD'
2. OTP 인증 시퀀스 번호와 함께 비밀번호 재설정 API(/auth/password, method : PUT) 호출

### 4. 로그인
1. 이메일과 회원가입 시 저장하였던 비밀번호로 로그인 API(/auth/login) 호출
2. 로그인 API에서 응답 받은 'token' 정보를 저장

### 6. 내 정보 보기
1. 로그인 API(/auth/login)에서 받은 token 정보를 헤더에 설정
   * 헤더명 : X-AUTH-TOKEN
2. 내 정보 보기 API(/members, method : GET) 호출

## API 명세서
* json 포맷으로 데이터 전송/응답

### 공통 헤더
* Content-Type : application/json
* X-AUTH-TOKEN : 내 정보 보기 기능(/members) 호출 시 로그인에서 받은 토큰 값 헤더에 설정 필요 
    * Member API(/members) 호출 경우에만
    * 로그인이 필요 없는 인증(/auth)에서는 해당 토큰 불필요

### 회원가입 (/auth/join)
* method : POST
#### 요청 파라미터
<table>
    <thead>
        <tr>
            <th>파라미터명</th>
            <th>타입</th>
            <th>설명</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>person_name</td>
            <td>string</td>
            <td>사용자 이름</td>
        </tr>
        <tr>
            <td>email</td>
            <td>string</td>
            <td>사용자 이메일</td>
        </tr>
        <tr>
            <td>nick_name</td>
            <td>string</td>
            <td>사용자 닉네임</td>
        </tr>
        <tr>
            <td>password</td>
            <td>stirng</td>
            <td>비밀번호</td>
        </tr>
        <tr>
            <td>otp_auth_id</td>
            <td>number</td>
            <td>OTP 인증 시퀀스 번호</td>
        </tr>
    </tbody>
</table>

#### 응답 파라미터
<table>
    <thead>
        <tr>
            <th colspan="2">파라미터명</th>
            <th>타입</th>
            <th>설명</th>
        </tr>
    </thead>
    <tbody>
        <tr>
          <td>header</td>
          <td></td>
          <td>object</td>
          <td>헤더 영역</td>
        </tr>
        <tr>
          <td></td>
          <td>code</td>
          <td>number</td>
          <td>응답 코드(http status code와 동일)</td>
        </tr>
        <tr>
          <td></td>
          <td>message</td>
          <td>string</td>
          <td>응답 메시지</td>
        </tr>
        <tr>
          <td>body</td>
          <td></td>
          <td>object</td>
          <td>응답 데이터 영역</td>
        </tr>
        <tr>
          <td></td>
          <td>user_id</td>
          <td>number</td>
          <td>회원 시퀀스 번호</td>
        </tr>
    </tbody>
</table>

### 로그인 (/auth/login)
* method : POST
#### 요청 파라미터
<table>
    <thead>
        <tr>
            <th>파라미터명</th>
            <th>타입</th>
            <th>설명</th>
        </tr>
    </thead>
    <tbody>
      <tr>
        <td>email</td>
        <td>string</td>
        <td>회원가입시 저장한 이메일</td>
      </tr>
      <tr>
        <td>password</td>
        <td>string</td>
        <td>로그인 비밀번호(숫자/영문자/특수문자 8자리 이상)</td>
      </tr>
    </tbody>
</table>

#### 응답 파라미터
<table>
    <thead>
        <tr>
            <th colspan="2">파라미터명</th>
            <th>타입</th>
            <th>설명</th>
        </tr>
    </thead>
    <tbody>
        <tr>
          <td>header</td>
          <td></td>
          <td>object</td>
          <td>헤더 영역</td>
        </tr>
        <tr>
          <td></td>
          <td>code</td>
          <td>number</td>
          <td>응답 코드(http status code와 동일)</td>
        </tr>
        <tr>
          <td></td>
          <td>message</td>
          <td>string</td>
          <td>응답 메시지</td>
        </tr>
        <tr>
          <td>body</td>
          <td></td>
          <td>object</td>
          <td>응답 데이터 영역</td>
        </tr>
        <tr>
          <td></td>
          <td>token</td>
          <td>string</td>
          <td>내 정보 보기에 필요한 토큰(X-AUTH-TOKEN)</td>
        </tr>
    </tbody>
</table>

### OTP 번호 발송 (/auth/otp)
* method : POST
#### 요청 파라미터
<table>
    <thead>
        <tr>
            <th>파라미터명</th>
            <th>타입</th>
            <th>설명</th>
        </tr>
    </thead>
    <tbody>
      <tr>
        <td>phone_number</td>
        <td>string</td>
        <td>OTP 인증을 위한 휴대폰 번호</td>
      </tr>
      <tr>
        <td>auth_type</td>
        <td>String</td>
        <td>
            인증 유형 (JOIN : 회원가입 시 인증 / PASSWORD : 비밀번호 변경 시 인증)
        </td>
      </tr>
    </tbody>
</table>

#### 응답 파라미터
<table>
    <thead>
        <tr>
            <th colspan="3">파라미터명</th>
            <th>타입</th>
            <th>설명</th>
        </tr>
    </thead>
    <tbody>
        <tr>
          <td>header</td>
          <td></td>
          <td></td>   
          <td>object</td>
          <td>헤더 영역</td>
        </tr>
        <tr>
          <td></td>
          <td>code</td>
          <td></td>
          <td>number</td>
          <td>응답 코드(http status code와 동일)</td>
        </tr>
        <tr>
          <td></td>
          <td>message</td>
          <td></td>
          <td>string</td>
          <td>응답 메시지</td>
        </tr>
        <tr>
          <td>body</td>
          <td></td>
          <td></td>
          <td>object</td>
          <td>응답 데이터 영역</td>
        </tr>
        <tr>
          <td></td>
          <td>auth_info</td>
          <td></td>
          <td>object</td>
          <td>인증정보 object</td>
        </tr>
        <tr>
          <td></td>
          <td></td>
          <td>id</td>
          <td>number</td>
          <td>인증번호 시퀀스 번호</td>
        </tr>
        <tr>
          <td></td>
          <td></td>
          <td>otp</td>
          <td>string</td>
          <td>OTP 번호</td>
        </tr>
    </tbody>
</table>

### OTP 인증 (/auth/otp)
* method : PUT
#### 요청 파라미터
<table>
    <thead>
        <tr>
            <th>파라미터명</th>
            <th>타입</th>
            <th>설명</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>otp_auth_id</td>
            <td>number</td>
            <td>OTP 발송 단계에서 사용된 시퀀스 번호</td>
        </tr>
        <tr>
            <td>otp_number</td>
            <td>string</td>
            <td>OTP 인증번호 4자리</td>
        </tr>
    </tbody>
</table>

#### 응답 파라미터
<table>
    <thead>
        <tr>
            <th colspan="2">파라미터명</th>
            <th>타입</th>
            <th>설명</th>
        </tr>
    </thead>
    <tbody>
        <tr>
          <td>header</td>
          <td></td>
          <td>object</td>
          <td>헤더 영역</td>
        </tr>
        <tr>
          <td></td>
          <td>code</td>
          <td>number</td>
          <td>응답 코드(http status code와 동일)</td>
        </tr>
        <tr>
          <td></td>
          <td>message</td>
          <td>string</td>
          <td>응답 메시지</td>
        </tr>
        <tr>
          <td>body</td>
          <td></td>
          <td>object</td>
          <td>응답 데이터 영역</td>
        </tr>
        <tr>
          <td></td>
          <td>otp_auth</td>
          <td>boolean</td>
          <td>OTP 인증 성공여부(true : 성공 / false : 실패)</td>
        </tr>
    </tbody>
</table>


### 비밀번호 변경 (/auth/password)
* method : PUT
#### 요청 파라미터
<table>
    <thead>
        <tr>
            <th>파라미터명</th>
            <th>타입</th>
            <th>설명</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>auth_otp_id</td>
            <td>number</td>
            <td>비밀번호 변경을 위한 OTP 인증 시퀀스 번호</td>
        </tr>
        <tr>
            <td>password</td>
            <td>string</td>
            <td>변경할 비밀번호</td>
        </tr>
    </tbody>
</table>


#### 응답 파라미터
<table>
    <thead>
        <tr>
            <th colspan="2">파라미터명</th>
            <th>타입</th>
            <th>설명</th>
        </tr>
    </thead>
    <tbody>
        <tr>
          <td>header</td>
          <td></td>
          <td>object</td>
          <td>헤더 영역</td>
        </tr>
        <tr>
          <td></td>
          <td>code</td>
          <td>number</td>
          <td>응답 코드(http status code와 동일)</td>
        </tr>
        <tr>
          <td></td>
          <td>message</td>
          <td>string</td>
          <td>응답 메시지</td>
        </tr>
        <tr>
          <td>body</td>
          <td></td>
          <td>object</td>
          <td>응답 데이터 영역</td>
        </tr>
        <tr>
          <td></td>
          <td>password_reset</td>
          <td>boolean</td>
          <td>비밀번호 변경 성공 여부(true : 성공, false : 실패)</td>
        </tr>
    </tbody>
</table>

### 내 정보 보기 (/members)
* method : GET
* header에 X-AUTH-TOKEN 필요(/auth/login)
#### 요청 파라미터
<table>
    <thead>
        <tr>
            <th>파라미터명</th>
            <th>타입</th>
            <th>설명</th>
        </tr>
    </thead>
</table>

#### 응답 파라미터
<table>
    <thead>
        <tr>
            <th colspan="3">파라미터명</th>
            <th>타입</th>
            <th>설명</th>
        </tr>
    </thead>
    <tbody>
        <tr>
          <td>header</td>
          <td></td>
          <td></td>
          <td>object</td>
          <td>헤더 영역</td>
        </tr>
        <tr>
          <td></td>
          <td>code</td>
          <td></td>
          <td>number</td>
          <td>응답 코드(http status code와 동일)</td>
        </tr>
        <tr>
          <td>body</td>
          <td></td>
          <td></td>
          <td>object</td>
          <td>응답 데이터 영역</td>
        </tr>
        <tr>
            <td></td>
            <td>member</td>
            <td></td>
            <td>object</td>
            <td>사용자 정보 object</td>
        </tr>
        <tr>
          <td></td>
          <td></td>
          <td>email</td>
          <td>string</td>
          <td>가입된 이메일 정보</td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>person_name</td>
            <td>string</td>
            <td>사용자 이름</td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>phone_number</td>
            <td>string</td>
            <td>휴대폰 번호</td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>nick_name</td>
            <td>string</td>
            <td>닉네임</td>
        </tr>
    </tbody>
</table>