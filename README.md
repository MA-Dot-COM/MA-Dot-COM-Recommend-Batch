# 추천 배치 서버

## 추천 서버를 분리한 이유?
1. 처음에 사용자가 접속을 하면 팔로우 한 인원들 중에서 24시간 내에 라이핑을 올렸는지 
    <br>
   여부와 AI서버에서 받아온 추천 정렬을 합쳐야 해서 시간이 오래 걸렸다.
2. 사용자가 적을 때는 메인서버에 부답이 적지만 사용자가 많아질수록 부하가 올 가능성이 높아서 분리를 할 필요가 있다.

<br>

    즉, 스프링 배치가 필요한 조건 2가지를 충족했다.
        1. 일정 주기로 실행이 필요하다.(추천 정렬을 미리 받아오기)
        2. 실시간 처리가 어려운 대량의 데이터들을 처리할 필요가 있다.(추천 정렬 데이터)

## 스프링 배치를 선택한 이유?
    1. 기존에 스프링 서버를 만들어서 사용을 하고 있어서 빠르게 적용이 가능했다.
    2. 오픈소스 프레임워크이다
    3. Spring Batch는 로깅 /추적, 트랜잭션 관리 , 작업 처리 통계, 작업 재시작, 건너뛰기, 리소스 관리 등 
       대용량 레코드 처리에 필수적인 재사용 가능한 기능을 제공한다.
    4. 단순하거나 복잡한 대용량 배치 작업은 확장성이 뛰어난 방식으로 프레임워크를 활용하여 상당한 양의 정보를 처리할 수 있다.
    
## [그 외 프로젝트 자세한 소개 깃허브](https://github.com/MA-Dot-COM/Intro)
