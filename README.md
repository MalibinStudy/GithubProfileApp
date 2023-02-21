# Week2
## STEP 1 : sample 코드 Unit Test
- [x] : TestViewModel  
- [x] : TestViewModelWithCoroutine
- [x] : TodoManager
## Step 2 - 1 : GithubProfileRepository Unit Test
- [x] : githubProfile은 내부 저장소에 저장된다
- [x] : local저장소에 userName에 해당하는 profile이 있으면 서버 통신 안한다.
- [x] : local저장소에 userName에 해당하는 profile이 없을 때 서버 통신 한다.
## Step 2 - 2 : MainViewModel Unit Test
- [x] : github 프로필을 가져올 수 있다
- [x] : github 프로필을 가져올 수 없으면 Error가 발생한다
# Week3
## Step 3 - 1 : ExampleInstrumentedTest class의 테스트를 Junit5, Truth를 활용해서 변경한다.
- [x] : application Context로 패키지 이름을 가져올 수 있다
## Step 3 - 2 : GithubService Unit Test
- [x] : userName에 해당하는 사람의 깃허브 프로필 정보를 불러올 수 있다
- [x] : userName에 해당하는 사람이 없으면 404 Error가 뜬다
- [x] : token 만료 시 401 Error가 뜬다