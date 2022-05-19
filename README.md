# React.js, 스프링 부트, AWS로 배우는 웹 개발 101
---
### 레이어드 아키텍쳐
>이 책에서는 model(entity), dto, persistence(repository), service, controller 로 나누고 있다
- 퍼시스턴스레이어: 데이터베이스와 통신하며 필요한 쿼리를 보내고 해석해 엔티티 오브젝트로 변환 해주는 역할
- 서비스레이어: HTTP나 데이터베이스같은 외부 컴포넌트로부터 추상화돼 우리가 온전히 비즈니스 로직에만 집중할 수 있다
- 컨트롤러레이어: 주로 HTTP 요청과 응답을 어떻게 넘겨받고 리턴하느냐, 즉 외부 세계와 통신하는 규약을 정의했다

순서는 보통 퍼시스턴스 -> 서비스 -> 컨트롤러


### 컨트롤러에서 요청을 받는 법
- DTO로 받아서 Entity로 변환해 JPA가 실행되도록, 또 Entity를 DTO로 변환해 사용자에게 리턴하도록 짜야한다.

### 기본 CRUD
- Create: service에서 밸리데이션 후 repository.save(entity)
- Retrieve: service에서 repository.findByUserId()
- Update: service에서 밸리데이션 후 Optional<Entity> entities = repository.findByUserId(entity.getId())
    - entity.ifPresent(data -> {... 업데이트 값 넣어주고 repository.save(entity)})
    - 즉, findByUserId -> save 2개로 update 구현하는거 정석 방법 맞았음