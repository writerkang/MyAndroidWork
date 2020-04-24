package com.lec.android.a017_location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

/**  지오코딩(GeoCoding) : 주소,지명 => 위도(latitude),경도(longitude) 좌표로 변환하는 구글 서비스
 *    위치정보를 얻기위한 권한을 획득 필요, AndroidManifest.xml
 *      ACCESS_FINE_LOCATION : 현재 나의 위치를 얻기 위해서 필요함
 *      INTERNET : 구글서버에 접근하기위해서 필요함
 */

public class Main3Activity extends AppCompatActivity {

    TextView tvResult;

    EditText etLatitude, etLongitude;
    EditText etAddress;
    Button btnGeoCoder1, btnGeoCoder2;
    Button btnMap1, btnMap2;

    //지오코딩
    Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tvResult = findViewById(R.id.tvResult); // 결과창
        btnGeoCoder1 = findViewById(R.id.btnGeoCoder1);
        btnGeoCoder2 = findViewById(R.id.btnGeoCoder2);
        btnMap1 = findViewById(R.id.btnMap1);
        btnMap2 = findViewById(R.id.btnMap2);

        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        etAddress = findViewById(R.id.etAddress);

        geocoder = new Geocoder(this);

        btnGeoCoder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //위도, 경도 입력후 주소정보 변환

                double lat = Double.parseDouble(etLatitude.getText().toString());
                double lng = Double.parseDouble(etLongitude.getText().toString());

                List<Address> list = null;

                try {
                    list = geocoder.getFromLocation(lat, lng, 10); //얻어올 결과값의 최대 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("myapp", "입출력 오류 - 서버에서 주소 변환시 에러 발생");
                }

                if(list != null){
                    if(list.size() == 0){
                        tvResult.setText("해당되는 주소 정보가 없습니다");
                    } else{
                        StringBuffer result = new StringBuffer(list.size() + "개의 결과\n");
                        for(Address adr : list){
                            result.append("---------------------------\n");
                            result.append(adr.toString() + "\n");
                        }
                        tvResult.setText(result);
                    }

                }

            }
        });

        btnGeoCoder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //지명, 주소
                String str = etAddress.getText().toString();

                List<Address> list = null;

                try {
                    list = geocoder.getFromLocationName(str,10); //얻어올 결과값의 최대 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("myapp", "입출력 오류 - 서버에서 주소 변환시 에러 발생");
                }

                if(list != null){
                    if(list.size() == 0){
                        tvResult.setText("해당되는 주소 정보가 없습니다");
                    } else{
                        StringBuffer result = new StringBuffer(list.size() + "개의 결과\n");
                        for(Address adr : list){
                            result.append("---------------------------\n");
                            result.append(adr.getCountryName() + ", "
                                    + adr.getFeatureName() + ", "
                                    + adr.getLocality() + ", "
                                    + adr.getLatitude() + ", "
                                    + adr.getLongitude());
                        }
                        tvResult.setText(result);
                    }

                }

            }
        });

        btnMap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //위도, 경도 입력 후 지도 버튼 클릭 -> 묵시적 인텐트 날리기
                double lat = Double.parseDouble(etLatitude.getText().toString());
                double lng = Double.parseDouble(etLongitude.getText().toString());

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + "," + lng));
                startActivity(intent);
            }
        });

        btnMap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //지명, 주소
                String str = etAddress.getText().toString();

                List<Address> list = null;

                try {
                    list = geocoder.getFromLocationName(str,10); //얻어올 결과값의 최대 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("myapp", "입출력 오류 - 서버에서 주소 변환시 에러 발생");
                }

                if(list != null){
                    if(list.size() == 0){
                        tvResult.setText("해당되는 주소 정보가 없습니다");
                    } else{

                        Address addr = list.get(0);
                        double lat = addr.getLatitude();
                        double lng = addr.getLatitude();

                        String uri = String.format("geo:%f,%f", lat, lng);

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    }

                }
            }
        });
    }
}
