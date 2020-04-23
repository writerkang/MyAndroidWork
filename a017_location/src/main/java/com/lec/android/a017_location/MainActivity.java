package com.lec.android.a017_location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

/**  위치기반 앱 : 사용자의 위치를 활용한 어플리케이션
 *
 *  LocationManager (위치 관리자): 안드로이드에서 위치제공자(LocationProvider)를 얻어오기 위한 객체
 *
 *  안드로이드에서 사용가능한 위치제공자(LocationProvider)들  (즉, 사용자 위치 알아내는 방법들)
 *
 *    1. GPS : 위성에서 정보를 받아 삼각측량으로 위치를 계산,
 *             정확하다(accuracy), 실내에서는 잘 안된다.  실외에서도 고층건물등 장애물에 따라 오차발생
 *             배터리 소모 크다,
 *             위치 정보 판독에 시간 걸린다.  특히 초기위치 결정시간 (TTFF : Time To First Fix) 가 많이 걸림
 *
 *    2. Network : 전화 기지국 이용 (셀룰러)
 *             WiFi 네트워크, Cell ID 위치 사용
 *             GPS 에 비해 부정확,   TTFF 는 매우 빠름, 실내에서도 사용 가능
 *             GPS 에 비해 배터리 소모 적다
 *
 *    3. Passive : WiFi AP 사용
 *             실내 위치 추적용, AP위치, AP와의 수신신호강도등을 계산하여 위치 계산
 *             만약 다른 앱이 이미 위치서비스를 사용하고 있다면 그 위치 정보를 받아올수 있다.
 *             추가적인 배터리 소모가 가장 적다.
 *
 */

public class MainActivity extends AppCompatActivity {

    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        //위치관리자 객체  얻어오기
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        List<String> list = lm.getAllProviders(); //모든 가용한 위치 제공자 가져오기

        String str = "[위치제공자] : 사용가능여부\n-------------------------\n";
        for(String provider : list){
            str += "[" + provider + "] : " + lm.isProviderEnabled(provider) + "\n";
        }
        tvResult.setText(str);
    }
}
