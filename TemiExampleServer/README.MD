School Project의 참고용 Firbase implemented Server

사전 설치 권장사항
 - IntelliJ
   - 서버를 외부에 올릴 경우 : Maven, Docker
** 해당 프로젝트는 DB를 사용하지 않습니다. 
DB 사용은 https://github.com/Midnev/StartingSpringBoot 에서 9번 항목부터 참조 바람니다.

해당 프로젝트는 현재 실행이되지 않는 것이 정상이다 
(firebase adminsdk 파일 내용이 비어있다.)

IntelliJ를 사용할때에 코딩을 할때 import를 직접 쓰지 말고 검색을 활용하자 
예시 : @Spring까지 입력하고 @SpringBootApplication을 방향키로 찾아서 엔터를 치면 완성된다.
오타를 줄일 수 있고 import를 자동으로 해준다. 혹시 안될 경우 라이브러리가 로딩이 안된것이니 라이브러리 설정을 확인하자. 혹은 ctr+spacebar 나오는 경우도 있다.


1. TemiExampleServer & Spring-boot 프로젝트 구조
	- pom.xml : 라이브러리 및 플러그인 설정
	- src > main > java > com.example.temi의 패키지들
		- application : 어플리케이션 선언파일이 있는 곳
		- controller : url을 수용하는 객체들이 정의된 곳
		- service : 외부에 노출되지 않고 기타 기능들을 해결해주는 곳
		- config : 서버의 기타 설정들
	- src > main > resources
		- application.yaml : 어플리케이션 기타 설정
		- static : 공용 파일을 넣는 곳
		- templates : (thymeleaf를 사용할 경우)  html파일을 넣는 곳


3. Spring-boot 설정
	- pom.xml 라이브러리 설정 
	---
		<?xml version="1.0" encoding="UTF-8"?>
		<project xmlns="http://maven.apache.org/POM/4.0.0"
		         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
		    <modelVersion>4.0.0</modelVersion>
		    <groupId>TemiExampleServer</groupId>
		    <artifactId>temiexampleserver</artifactId>
		    <version>1.0-SNAPSHOT</version>
		    <dependencies><!--라이브러리들을 선언하는 태그-->
	        	<!--스프링 기본 라이브러리 전언-->
		        <dependency>
		            <groupId>org.springframework.boot</groupId>
		            <artifactId>spring-boot-starter-web</artifactId>
		            <version>2.1.6.RELEASE</version>
		        </dependency>
		        <!--스프링 자동 스캔을 위한 라이브러리 전언-->
		        <dependency>
		            <groupId>org.springframework.boot</groupId>
		            <artifactId>spring-boot-autoconfigure</artifactId>
		            <version>2.1.6.RELEASE</version>
		        </dependency>
		        <!-- 추가되는 라이브러리는 이 공간에-->
		    </dependencies>
		</project>
	---

3. Application의 생성
	- Spring Framework의 경우 @Application등의 코드를 사용하는 어노테이션 위주의 프레임워크이다.
	- @를 사용하는 것은 전부 어노테이션이며 기능들이 내부에 프로그래밍되어있다.
	- application 패키지 내에 Application 이라는 객체를 생성한다.
	---
		@SpringBootApplication//스프링 부트 어플리케이션 선언
		@ComponentScan(basePackages = {"com.example.temi"})//스캔할 폴더 선언
		public class Application {
		    public static void main(String[] args) {//main 선언
		        SpringApplication.run(Application.class,args);//스프링 어플리케이션 선언
		    }
		}
	---
	
	- @ComponentScan 해당 폴더 내에서 @Component 혹은 컴포넌트를 상속한 @Controller, @Service, @Repository등의 객체를 전부 자동을 찾아서 등록한다. *(중요!!!)* 스프링은 컴포넌트를 찾은뒤 알아서 객체를 생성한다. 따라서 컴포넌트를 절대로 new로 생성하지 말아야한다. (이후 Service에서 추가 설명)

	- application.yaml (yaml 파일은 indent로 속성을 구문하니 주의하자)
	(예저 파일 그대로 들여쓰기를 할 것)
	---
		server:
  		 port: 8080
	---

	- 이 시점에서 main을 실행하면 서버가 올라온다.

4. index 페이지 만들기 
	spring-boot에서는 static 폴더(단어 틀리면 작동 안함)의 index.html 파일을 메인페이지로 자동 매핑을 해준다.	따라서 특별한 설정없이 메인페이지가 올라온다.
	---
		<!DOCTYPE html>
		<html lang="en">
		<head>
		    <meta charset="UTF-8">
		    <title>페이지 제목</title>
		</head>
		<body>
			인덱스 페이지 입니다
		</body>
		</html>
	---

	접근하기: 'http://localhost:8080/', 
		(혹은 네트워크 내의 기기에서 localhost 대신 실행중인 pc의 IP로도 접근 가능하다.)

5. 기타 html 파일 추가하기
	- pom.xml dependencies 추가
	---
		<!-- html templates를 사용하기 위한 설정 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
            <version>2.3.1.RELEASE</version>
        </dependency>
	---

	- thymeleaf 라이브러리에서 ViewResolver라는 객체가 templates 폴더로 설정이 되어있다. 단어를 틀리면 마찬가지로 접근이 되지 않는다.
	- templates 폴더에 원하는 이름의 html 파일을 만든다.
	---
		<!DOCTYPE html>
		<html lang="en">
		<head>
		    <meta charset="UTF-8">
		    <title>Title</title>
		</head>
		<body>
		    testpage입니다.
		</body>
		</html>
	---

	- controller 패키지에 WebController 클래스를 생성한다.
	- public 메서드(함수)를 생성한다. (메서드명은 당장은 하나도 상관 없지만이후 관점지향 프로그래밍을 할때는 중요할 수 있다.)
	- 메서드의 return 타입은 String(S 대문자: String은 객체기 때문에 대문자이다.)이다.
	---
		@Controller//html파일을 매핑해주는 컨트롤러이다.
		public class WebController {
		    @GetMapping("/t")// http://locahost:8080/t를 뜻한다.
		    public String tesPage(){ //리턴값은 무조건 String이다.
		        return "test";//test.html에서 .html은 suffix 설정으로 생략한다.
		    }
		}
	---

	- GetMapping의 Url 설정과 html페이지의 이름과는 아무 관계가 없어도 된다.
	- 여기서 서버를 실행하면 'http://locahost:8080/t'페이지에 test.html 페이지가 뜬다.

6. 변수를 html로 넘기기
	- thymeleaf에서는 데이터를 컨트롤러에서 매핑하는 기능을 지원한다.
	- Web Controller
	---
		@Controller
		public class WebController {
		    @GetMapping("/t")
		    public String testPage(){
		        return "test";
		    }
		    @GetMapping("/mapping")
		    public String testMapping(Model model){
		        String value = "value to send";
		        model.addAttribute("text",value);
		        model.addAttribute("text1",value+"1");
		        return "mapping";
		    }
		}
	---

	- Model : 스프링은 MVC(Model-View-Controller) 형태이다. 각각 (데이터, html, 컨트롤러)를 뜻한다. 
	-  addAttribute: key, val 형태로 key를 조회하면 val 값을 html에서 조회할 수 있다. 속성은 여러개 선언해도 상관이 없다.

	- mapping.html
	---
		<!DOCTYPE html>
		<html lang="en"  xmlns:th="http://www.thymeleaf.org">
		<head>
		    <meta charset="UTF-8">
		    <title>매핑 테스트</title>
		</head>
		    <body>
		    <span th:text="${text}" >여기 데이터는 지워진다.</span>
		    <span th:text="${text1}" >여기 데이터는 지워진다.</span>
		    </body>
		</html>
	---
	- xmlns:th="http://www.thymeleaf.org" 반드시 필요하다. 신류 태그 속성 생성
	- th: : thymeleaf 에서 지원하는 기능. 자세한건 https://www.thymeleaf.org/documentation.html , 기능들이 엄청 많다.

7. Service 
	- 서비스는 스프링에서 중요한 컴포넌트 중 하나다이다.
	- MVC 모델에서 컨트롤러의 과부하를 줄이기 위해 데이터 처리를 Service 컴포넌트에 넘기는 것이 좋다.
	- service 패키지에 StringService 클래스를 생성한다. (예시를 위해 적당히 생성하였다.)
	---
		@Service
		public class StringService {
		    private String val = "hidden value";
		    public String getVal(String str) {
		        return val+ str;
		    }
		    public String getVal(){//오버로딩: 동명 함수, 다른 변수
		        return val;
		    }
		    public void setVal(String val) {
		        this.val = val;
		    }
		}
	---

	- WebController 추가 변수 및 메서드
	---
		@Autowired
 		StringService service;

 		@GetMapping("/mapping2")
	    public String testMapping2(Model model){
	        String value = service.getVal();
	        model.addAttribute("text",value);
	        model.addAttribute("text1",service.getVal("1"));//다른 예시
	        return "mapping";
	    }
	---

	- @Autowired : 스프링은 컴포넌트들을 자동 스캔한다. 따라서 스캔된 컴포넌트들을 찾아달라고 요청하면 된다. 절대로 new로 컴포넌트를 생성하면 안된다.

8. 변수를 서버로 전달하기
	파라미터 사용 
	- WebController 
	---
		@GetMapping("/param")
	    public String getparam(@RequestParam("value1") String str1,
	                           @RequestParam(value = "value2", required = false, defaultValue = "nodata") String str2,
	                           Model m){
	        m.addAttribute("text",str1);
	        m.addAttribute("text1",str2);
	        return "mapping";
	    }
	---

	- 접근 방법 1: http://localhost:8080/param?value1=data1&value2=data2
	- 접근 방법 2: http://localhost:8080/param?value1=data1
	- 기본 설정은 required true이다. false로 바꾸면 데이터가 없을 경우의 기본 값을 설정해야한다.

	url path 변수
	- WebController
	---
		@GetMapping("/path/{value3}")
	    public String getPath(@PathVariable("value3")String value1,Model m){
	        m.addAttribute("text",value1);
	        m.addAttribute("text1", service.getVal(" "+value1));
	        return "mapping";
	    }
	---
	- 접근 방법 : http://localhost:8080/path/datahere
	- url상의 경로를 변수로 받는 방법이다.

9. RestController
	- WebController는 html을 매핑하는 컨트롤러이다.
	- RestController는 데이터를 돌려주는 컨트롤러이다.
	- controller 패키지에 APIRestController 클래스 생성
	---
		@RestController
		@RequestMapping("/api")
		public class APIRestController {
		    @Autowired
		    private StringService service;
		    @GetMapping("/{val}")
		    public String getRest(@PathVariable("val")String value1){
		        return service.getVal(value1);
		    }
		    @PostMapping("/post")
		    public void getPost(@RequestParam("value1") String str1){
		        service.setVal(str1);
		    }
		}
	---

	- @RestController : Rest(Reprisentational State Transfer) 간단하게 html을 사용하지 않는 컨트롤러. html파일을 받아오지 않기 때문에 데이터 처리에 도움이 된다.
	- @RequestMapping : 해당 url을 먼저 받는다. ex) localhost:8080/api/val . RequestMapping을 지우면 /api를 지우면 된다. 하나의 컨트롤러에 많은 코드를 넣으면 보기 힘들기 때문에 컨트롤러 분할할때 많은 도움이 된다.
	- localhost:8080/api/data 조회하기
	- PostMapping : 통상적으로 브라우저에서 접근할수 없고 파라미터를 숨길수 있다. 브라우저는 기본 Get 방식이다. (Postman을 쓰거나 httpie로 post request를 날려야한다.)

10. Firebase Admin 설정
	- firebase 콘솔페이지 > 좌측 상단 프로젝트 개요 옆 설정 페이지> 서비스 계정 탭
	- 새 비공개키 생성 : 해당 파일은 서버의 resources 폴더에 넣어야하고, 공개되면 제3자가 파이어베이스 서비스 사용이 가능해진.
	- Admin SDK의 database Url을 잘 복사해두자 

	- pom.xml dependencies
	---
		 <dependency>
            <groupId>com.google.firebase</groupId>
            <artifactId>firebase-admin</artifactId>
            <version>6.12.1</version>
        </dependency>
	---

	- config 패키지 내에 FirebaseConfig 클래스를 생성한다.
	---
		@Component
		public class FirebaseConfig {
		    @Bean
		    public DatabaseReference firebaseDatabase(){
		        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
		        return firebase;
		    }
		}
	---
	- 이미 설정된거니 아래만 잘 따라하면 된다.

11. FirebaseService
	- service 패키지에 ServersideFirebaseService 클래스를 생성한다.
	---
		@Service
		public class ServersideFireBaseService {
		    private String dbUrl = "복사하라고 했던 database url";
		    private String configPath ="src/main/resources/firebase-adminsdk.json";
		    public ServersideFireBaseService() {
		        try {
		            FileInputStream serviceAccount =
		                    new FileInputStream(configPath);
		            FirebaseOptions options = new FirebaseOptions.Builder()
		                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		                    .setDatabaseUrl(dbUrl)
		                    .build();
		            FirebaseApp.initializeApp(options);
		        }catch (Exception e){
		            System.out.println(e.getMessage());
		        }
		    }
		    private static final String ANDROID_NEWS_ICON_RESOURCE = "news_alert_icon";
		    public String sendNotification(String title, String body, String topic) throws Exception {
		        Message message = Message.builder()
		                .setNotification(new Notification(title, body))
		                .setAndroidConfig(AndroidConfig.builder()
		                        .setNotification(AndroidNotification.builder()
		                                .setIcon(ANDROID_NEWS_ICON_RESOURCE)
		                                .build())
		                        .build())
		                .setTopic(topic)
		                .build();
		        return FirebaseMessaging.getInstance().sendAsync(message).get();
		    }
		}
	---

	- constructor는 설정이 된 것이니 건들지 말자
	- dbUrl : 비공개키 발급 받을 때 있었던 dburl 
	- configPath: 비공개키 json 파일 
	- sendNotification(제목, 내용, 토픽)
		- 제목: 메시지의 제목
		- 내용: 메시지의 내용
		- 토픽: 어느 토픽에게 메시지를 보낼것인가. 해당 토픽을 구독중인 사람만 수신이 가능하다.
		- return : 메기자 잘 보내졌을 경우 메시지 코드가 돌아온다.
	- RestController에서
	---
		@GetMapping("/firebase")
	    public String sendFireBaseMsg(@RequestParam(value = "title", required = false, defaultValue = "empty title") String title,
	                                  @RequestParam(value = "content",required = false,defaultValue = "empty content") String content){
	        try {
	            return service2.sendNotification(title, content, "topic");
	        }catch (Exception e){// 메시지 송신을 실패할 경우
	            return "fail to send";
	        }
	    }
	---
