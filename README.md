spring JPA 게시판 만들기

1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요?
   @PathVariable로 id값을 받고 @RequestBody로 수정이나 삭제에 맏는 body값을 받아서 사용
2. 어떤 상황에 어떤 방식의 request를 써야하나요?
@RequestParam : 파라미터를 1:1로 받아올때 사용하고 required 는 파라미터의 필수 여부로 기본값은 true이다.
defaultValue로 required = "false"면서 만약 해달 파라미터를 받지 않았다면 파라미터의 기본값을 설정해줄 수 있다.
required = "true" 면서 파라미터가 넘어오지 않게되면 4xx에러가 발생한다.
파라미터값이 많을 때는 Map을 사용하여 받아오거나 객체를 사용하여 받아올 수 있지만, 자신이 아닌 다른사람이 이해하기 힘들 수 있다.

@PathVariable : body에서 지정해 주지 않은 변수값을 url에서 파라미터의 값으로 받아올때 사용된다.

@RequestBody : 파라미터를 객체로 받고 MessageConverter 를 이용해서 자바의 객체로 변환하여 받아온다.
MessageConverter 는 HTTP요청의 body내용을 자바의 객체로 변환시킨다. GET방식의 메소드는 body가 존재하지 않기때문에 에러를 발생시키므로
POST, PUT, DELETE와 같은 body내용이 있는 요청으로 사용한다.
JSON 데이터를 받을 때 줄 사용하고 MessageConverter 를 통한 자바의 객체로 변환이기 때문에 Setter가 없어도 괜찮다.

@ModelAttribute : 여러 파라미터를 자바의 객체로 한번에 바인딩해서 받아온다. 변수들이 Setter함수가 없다면 저장되지 않는다.

3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
확실하게 restful하게 설계했는지는 아직 잘 모르겠다. URL자체에 board를 넣어서 이게 게시판(board)에 대한것이라는 것을 보여주긴 했지만
URL에 들어가는 주소값이 board하나인것도 그렇고 수정, 삭제, 선택 조회에서 id값이 들어가긴하지만 그 부분이 restful한 것인지
아닌 것인지 아직은 확실하게 적립되지 않은것 같다.

4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
    Controller, Repository, Service, Entity, Dto 로 분리하여 적용
5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!
    API이름, 처리 데이터 유형, 교환 데이터 포맷, 클라이언트 코드 사례 등등 많은것이 부족