package com.lec.android.a017_location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/** 현재 위치 정보 가져오기 : GPS (Global Positioning System)
 *  1. AndroidManifest.xml 위치정보 권한획득 선언  (API23 이전 버젼은 이걸로 충분)
 *          android.permission.ACCESS_FINE_LOCATION
 *          android.permission.ACCESS_COARSE_LOCATION
 *
 *    1-2 API23+ 에서는 (마시멜로): 새로운 권한 획득 방법 사용.
 *          Run-time 에 onRequestPermissionsResult() 사용 해야 한다!!!
 *          https://developer.android.com/training/permissions/requesting.html
 *  2. LocationManager 획득
 *  3. LocationProvider 지정
 *     이전에 사용한 Provider 있는지 여부, 없으면 새로
 *
 *     ※ GPS 는 에뮬레이터에서는 기본적으로 동작하지 않는다
 *     ※ 실내에서는 GPS_PROVIDER 를 요청해도 응답이 없거나 느리다. 특별한 처리를 안하면 아무리 시간이 지나도 응답이 없으면
 *         해결방법은
 *          ① 타이머를 설정하여 GPS_PROVIDER 에서
 *              일정시간 응답이 없는 경우 NETWORK_PROVIDER로 전환
 *         ② 혹은, 둘다 한꺼번헤 호출하여 우선 들어오는 값을 사용하는 방식.
 *
 *      ※ 폰에 GPS 를 꺼놔도, LocationManager 가 최초 위치 정보 확인할때 GPS 켜지면서 초기화됨.
 *
 *  4. LocationListener 생성 : onLocationChanged() 에서 위치정보 (Location) 확인
 *
 *
 *  안드로이드 좌표계:  경도(longitude), 위도(latitude) 사용
 *        대한민국은 위도 36~38도,   경도 126~128도 사이 위치
 *      ex) 서울시청 : 37.566767, 126.978370       <-- 구글맵 등에서 위치 좌표 제공
 * */
public class Main2Activity extends AppCompatActivity {

    TextView tvResult;
    ToggleButton btnLocate;   // 토글버튼!

    String [] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
    final int REQUEST_CODE = 101;

    LocationManager manager = null;
    MyListener mLocationListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvResult = findViewById(R.id.tvResult);
        btnLocate = findViewById(R.id.btnLocate);

        // 위험권한 획득
        if(Build.VERSION.SDK_INT >= 23){
            if(checkSelfPermission(String.valueOf(permissions)) == PackageManager.PERMISSION_DENIED){
                requestPermissions(permissions, REQUEST_CODE);
            }
        }

        // LocationManager
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // LocationListener
        mLocationListener = new MyListener();

        btnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnLocate.isChecked()){ // 토글버튼
                    // 위치정보 수신 시작

                    try{
                        // 주어진 위치제공자가 기억하는 가장 최근의 위치정보 획득, 없으면 null 리턴
                        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if(location != null){
                            tvResult.setText("최근 위치\n" + locationInfo(location));
                        }

                        manager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                                100,    // 통지사이의 최소 시간간격
                                1,   // 통지사이의 최소 변경거리 (m)
                                mLocationListener);   // LocationListener

                        manager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                                100,    // 통지사이의 최소 시간간격
                                1,   // 통지사이의 최소 변경거리 (m)
                                mLocationListener);   // LocationListener

                        Toast.makeText(getApplicationContext(), "내 위치 확인 요청함", Toast.LENGTH_SHORT).show();

                    } catch(SecurityException e){
                        e.printStackTrace();
                    }



                } else {
                    // 위치정보 수신 종료
                    manager.removeUpdates(mLocationListener);
                }
            }
        });


    } // end onCreate()


    // LocationListener 구현
    //    LocationManager 로부터 전달되는 위치정보를 받기위해 정의
    class MyListener implements LocationListener {

        // 위치가 변경(확인) 되었을때 호출
        // 위치값은 Location 형태로 전달.
        @Override
        public void onLocationChanged(Location location) {
            Log.d("myapp", "onLocationChanged, location: " + location);
            tvResult.setText("내위치\n" + locationInfo(location));
            Toast.makeText(getApplicationContext(), "위치정보갱신!", Toast.LENGTH_SHORT).show();
        }


        @Override  // 위치제공자 가용하게 될때
        public void onProviderEnabled(String provider) {
            Log.d("myapp", "onProvideEnabled, provider: " + provider);
        }

        @Override  // 위치제공자 가용하지 못하게 될때.
        public void onProviderDisabled(String provider) {
            Log.d("myapp", "onProviderDisabled, provider: " + provider);
        }

        // API29 부터 deprecated 됨..
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

    } // end MyListener

    protected String locationInfo(Location location){
        double longitude = location.getLongitude();  // 경도
        double latitude = location.getLatitude();  // 위도
        double altitude = location.getAltitude();   // 고도
        float accuracy = location.getAccuracy();   // 정확도
        String provider = location.getProvider();    // 위치 제공자

        return "위치정보 : " + provider + "\n위도 : " + longitude + "\n경도 : " + latitude
                + "\n고도 : " + altitude + "\n정확도 : " + accuracy;

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "위치권한 획득!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "권한획득 실패!", Toast.LENGTH_LONG).show();
                }
        }
    }




} // end Activity