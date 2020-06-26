사전 설치 권장사항
 - AndroidStudio

 안드로이드 프로젝트이지만, 
 테미/Firebase 라이브러리가 있어 테미 이외에 실행되지 않는 것이 정상이다.

 해당 설명서는 파이어베이스와 HTTP 통신관련 정보만 들어있다.
 안드로이드 기초 사용법은 없다.

 1. 프로젝트 구조
 	- MainActivity.java :
 	- activity_main.xml 
 	- manifest > AndroidManifest.xml
 	- res > values > strings
 	- temi > TemiManager : 예제에서 테미 코드만 분리하여 개별 객체로 정리
 	- http > HttpManager : http 요청을 할 수 있도록 코드 정리

2. Temi & Firebase 설정
	- Gradle Scripts 정리 파일에 build.gradle(Module: app) 파일이 있다.
	- 추가해야할 것1 : 파이어베이스용 구글 서비스
	---
		apply plugin: 'com.android.application' < 여기 바로 아래:1라인  >
		apply plugin: 'com.google.gms.google-services'
	---

	-추가해야할 것2 : dependencies : ',' 콤마 구분이 아니니 다른 라이브러리랑 모양을 맞춰서 넣자
	---
	implementation 'com.google.firebase:firebase-analytics:17.2.0'
    implementation 'com.google.firebase:firebase-messaging:20.0.0'
    implementation 'com.robotemi:sdk:0.10.53'
    implementation 'com.loopj.android:android-async-http:1.4.9'
	---

	- 각각 : 파이어베이스 분석, 파이어베이스 메세징, 테미, http 요청 라이브러리 이다.

	- AndroidManifest.xml permission
	---
		<?xml version="1.0" encoding="utf-8"?>
		<manifest xmlns:android="http://schemas.android.com/apk/res/android"
		    package="com.app.temiexampleapp"> 
		    <!-- 여기 아래--> 
		    <uses-permission android:name="android.permission.INTERNET" />
	---

	- AndroidManifest.xml service 추가 
	---
		<application
	        android:allowBackup="true"
	        android:icon="@mipmap/ic_launcher"
	        android:label="@string/app_name"
	        android:roundIcon="@mipmap/ic_launcher_round"
	        android:supportsRtl="true"
	        android:theme="@style/AppTheme">
	        <activity android:name=".MainActivity">
	            <intent-filter>
	                <action android:name="android.intent.action.MAIN" />
	                <category android:name="android.intent.category.LAUNCHER" />
	            </intent-filter>
	        </activity>
	        <!-- 여기부터--> 
	        <service
	            android:name=".firebase.FirebaseMessagingService"
	            android:exported="false">
	            <intent-filter>
	                <action android:name="com.google.firebase.MESSAGING_EVENT" />
	            </intent-filter>
	        </service>
	        <!-- 여기까지--> 
	    </application>
	---

3. 각 기능 사용하기 
	- MainActivity
	--- 
		public class MainActivity extends AppCompatActivity {
		    private String topic;
		    private HttpManager httpManager;
		    private TemiManager temiManager;
		    @Override
		    protected void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.activity_main);
		        //테미 초기화
		        temiManager = new TemiManager();
		        temiManager.init();
		        //Firebase 구독하기
		        topic = "";// 구독할 토픽 서버에서 보내는것과 일치해야함
		        FirebaseMessaging.getInstance().subscribeToTopic(topic)
		                .addOnCompleteListener(new OnCompleteListener<Void>() {
		                       @Override
		                       public void onComplete(@NonNull Task<Void> task) {
		                           //완료했을 경우 실행할 내용
		                       }
		                   });
		        //서버 주소 등록, 서버 요청 보내기 예시
		        httpManager = new HttpManager("http://192.168.0.0:8080");
		        httpManager.postHttpRequest("/url","?val=param?val2=param");
		        httpManager.getHttpRequest("/url","?val=param?val=param");
		    }
		}
	---

	- TemiManager는 자기가 사용하려는 기능을 추가하거나 제거하면 된다.

	- MyFirebaseMessagingService onMessageReceived 메소드아래에 하고싶은것을 추가하면 된다.
