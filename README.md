# 2주차 미션
## 🎲  1단계
### GithubProfileRepository
1. 입력된 이름에 대한 Github Profile이 반환된다.
2. Github Profile을 Local DB에 저장한다.

### DefaultGithubProfileRepository
1. GithubProfile이 Local DB에 없으면, Remote의 getGithubProfile()과 Local의 saveGithubProfile이 호출된다.
2. userName이 Local DB에 존재하면 GithubProfile을 반환한다.
3. GithubProfile을 LocalDB에 저장할 때 LocalGithubProfileSource의 saveGithubProfile 메소드가 호출된다.

## 🎲  2단계
1. loadGithubProfile 메소드 호출 성공 시 GithubProfile을 가져온다.
2. loadGithubProfile 메소드 호출 실패 시 isError의 value 가 True가 된다.

## 🎲  3단계
1. 올바른 패키지 명을 반환한다.