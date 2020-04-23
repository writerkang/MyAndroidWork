package com.lec.android.a015_web;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
/* XML, JSON 파싱 연습
 *
 * ■서울시 지하철 역사 정보
http://data.seoul.go.kr/dataList/datasetView.do?infId=OA-12753&srvType=A&serviceKind=1&currentPageNo=1

샘플url

XML 버젼
http://swopenAPI.seoul.go.kr/api/subway/4d46796d7366726f3833774a774955/xml/stationInfo/1/5/서울

JSON 버젼
http://swopenAPI.seoul.go.kr/api/subway/4d46796d7366726f3833774a774955/json/stationInfo/1/5/서울

 */

// 32강 com.lec.java.crawl11 참조


public class Main3Activity extends AppCompatActivity {

    private TextView tvResult;
    private EditText et;
    private Button btnXML;
    private Button btnJSON;
    private Button btnParse;

    // 웹사이트 주소를 저장할 변수
    String reqType = "xml";
    String reqService = "stationInfo";
    int reqStartIndex = 0;
    int reqEndIndex = 5;
    String reqSearchStr = "";

    String api_key = "";

    String urlAddress;
    Handler handler = new Handler(); // 화면에 그려주기 위한 객체
    HttpURLConnection conn;
    StringBuffer sb;

    // XML 파싱
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // 웹에서 html 읽어오기
        // 1. 인터넷 권한 얻어오기 AndroidManifest.xml
        // 2. 쓰레드를 작성
        // 3. Handler 객체를 통해야만 화면을 그릴수 있다

        tvResult = findViewById(R.id.tvResult);
        btnXML = findViewById(R.id.btnXML);
        btnJSON = findViewById(R.id.btnJSON);
        btnParse = findViewById(R.id.btnParse);
        et = findViewById(R.id.editText);

        api_key = getResources().getString(R.string.api_key);

        try {
            // DOM parser 객체 생성
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


        btnXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqType = "xml";
                // EditText 에 입력된 역 이름의 공백제거.
                reqSearchStr = et.getText().toString().trim().replaceAll(" ", "");

                // url 에 한글이 들어가는 경우 URLEncode 를 해야 한다.
                try {
                    reqSearchStr = URLEncoder.encode(reqSearchStr, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if( reqSearchStr.length() > 0) {
                    urlAddress = "http://swopenAPI.seoul.go.kr/api/subway/" +
                            api_key + "/" + reqType + "/" + reqService + "/" +
                            reqStartIndex + "/" + reqEndIndex + "/" + reqSearchStr;

                    sendRequest(); // 웹에서 html 읽어오기
                } // end if
            } // end onClick()
        });

        btnJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqType = "json";
                // EditText 에 입력된 역 이름의 공백제거.
                reqSearchStr = et.getText().toString().trim().replaceAll(" ", "");

                // url 에 한글이 들어가는 경우 URLEncode 를 해야 한다.
                try {
                    reqSearchStr = URLEncoder.encode(reqSearchStr, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if( reqSearchStr.length() > 0) {
                    urlAddress = "http://swopenAPI.seoul.go.kr/api/subway/" +
                            api_key + "/" + reqType + "/" + reqService + "/" +
                            reqStartIndex + "/" + reqEndIndex + "/" + reqSearchStr;

                    sendRequest(); // 웹에서 html 읽어오기
                } // end if
            }
        });

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText("");

                switch(reqType){
                    case "xml":
                        parseXML();
                        break;
                    case "json":
                        parseJSON();
                        break;
                }
            }
        });


    } // end onCreate()

    void sendRequest() { // 웹에서 html 읽어오기
        tvResult.setText("");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                sb = new StringBuffer();

                try {
                    URL url = new URL(urlAddress);

                    conn = (HttpURLConnection)url.openConnection();// 접속
                    if (conn != null) {
                        conn.setConnectTimeout(2000);
                        conn.setRequestMethod("GET");
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                        conn.setUseCaches(false);
                        conn.connect();

                        if (conn.getResponseCode()
                                == HttpURLConnection.HTTP_OK){  // 200
                            // 데이터 읽기
                            BufferedReader br
                                    = new BufferedReader(new InputStreamReader
                                    (conn.getInputStream(),"utf-8"));//"utf-8"
                            while(true) {
                                String line = br.readLine();
                                if (line == null) break;
                                sb.append(line+"\n");
                            }
                            br.close(); // 스트림 해제
                        }else{
                            Log.d("text", "getResponseCode(): " + conn.getResponseCode());
                        }
                        conn.disconnect(); // 연결 끊기
                    }else{
                        Log.d("test", "conn NULL!");
                    }

                    // 값을 출력하기
                    Log.d("test", sb.toString());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvResult.setText(sb.toString());
                        } // end run()
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start(); // 쓰레드 시작
    } // end sendRequest()

    void parseXML()
    {
        InputSource is = new InputSource(new StringReader(sb.toString()));

        // DOM parser 객체 생성
        Document doc;  // 곧바로 InputStream 으로부터 받아 파싱

        try {
            doc = dBuilder.parse(is);
            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("row");  // 서울시 지하철은 <row>~</row> 로 구성됨

            Log.d("myapp", "nList.getLength() = " + nList.getLength());

            for(int i = 0; i < nList.getLength(); i++){
                Node node = nList.item(i);
                Element rowElement = (Element)node;   // 원래는 node.getNodeType() == Node.ELEMENT_NODE 체크해봐야 한다
                final String subwayId =
                        rowElement.getElementsByTagName("subwayId").item(0).getChildNodes().item(0).getNodeValue();
                final String subwayNm =
                        rowElement.getElementsByTagName("subwayNm").item(0).getChildNodes().item(0).getNodeValue();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText(tvResult.getText().toString() +
                                "--------------\nId:" + subwayId + "\n호선:" + subwayNm + "\n");
                    }
                }); // end post
            } // end for
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    } // end parseXML()

    void parseJSON(){

        try {
            JSONObject obj = new JSONObject(sb.toString());
            JSONArray stationArr = obj.getJSONArray("stationList");

            for(int i = 0; i < stationArr.length(); i++){
                JSONObject station = (JSONObject)stationArr.get(i);
                final String subwayId = station.getString("subwayId");
                final String subwayNm = station.getString("subwayNm");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText(tvResult.getText().toString() +
                                "--------------\nId:" + subwayId + "\n호선:" + subwayNm + "\n");
                    }
                }); // end post

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    } // end parseJSON()

} // end class