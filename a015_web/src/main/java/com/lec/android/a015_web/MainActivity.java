package com.lec.android.a015_web;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/* HTTP 요청하기
   - 메니페스트 설정 하기 : android.permission.INTERNET 권한
   - <application> 에 추가 usesCleartextTraffic="true"
       HTTP와 같은 cleartext 네트워크 트래픽을 사용할지 여부를 나타내는 flag로
       이 플래그가 flase 로 되어 있으면, 플랫폼 구성 요소 (예 : HTTP 및 FTP 스택, DownloadManager, MediaPlayer)는
       일반 텍스트 트래픽 사용에 대한 앱의 요청을 거부하게 됩니다. 이 flag를 설정하게 되면 모든 cleartext 트래픽은 허용처리가 됩니다

   - URL 객체 만들기 -> HttpURLConnection 객체 만들기
       setXXX() 메소르도 Conneciton 세팅
           ex) setRequestMethod(method) :  "GET" "POST " 등의 문자열
           ex) setRequestProperty(field, value) :

   - request 는 별도의 Thread 로 진행!
   - 위 Thread에서 화면 UI 접근하려면 (당연히) Handler 사용
*/
public class MainActivity extends AppCompatActivity {

    EditText etUrl;
    TextView tvResult;
    Button btnRequest, btnClear;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUrl = findViewById(R.id.etUrl);
        tvResult = findViewById(R.id.tvResult);

        btnRequest = findViewById(R.id.btnWebView);
        btnClear = findViewById(R.id.btnBrowser);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String urlStr = etUrl.getText().toString();

                //HTTP request는 별도의 Thread로 진행해야한다
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request(urlStr);
                    }
                }).start();


            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText(""); //내용 지우기
            }
        });
    }

    public void request(String urlStr){
        final StringBuilder sb = new StringBuilder();

        BufferedReader reader = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection();

            if(conn != null){
                conn.setConnectTimeout(5000); //timeout시간설정, 경과하면 SocketTimeoutException 발생
                conn.setUseCaches(false); //캐시사용안함
                conn.setRequestMethod("GET"); //GET 방식 request

                conn.setDoInput(true); //URLConnection을 입력으로사용 (true), (false) -> 출력용

                int responseCode = conn.getResponseCode(); //response code값 성공하면 200
                if(responseCode == HttpURLConnection.HTTP_OK){
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while(true){
                        line = reader.readLine();
                        if(line == null) break;
                        sb.append(line + "\n");
                    }
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if(reader != null) reader.close();
                if(conn != null) conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                tvResult.setText("응답결과 ->" + sb.toString());
            }
        });


    }
}
