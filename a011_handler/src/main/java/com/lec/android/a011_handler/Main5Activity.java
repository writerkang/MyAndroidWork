package com.lec.android.a011_handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/** AsyncTask
 *    백그라운드(background) 작업 스레드 수행,
 *    좀더 쉽게 스레드 만들고 운영,
 *    작업스레드 결과값까지 쉽게 받아볼수 있다.
 *    심지어 Handler 없이도 메인UI 접근 할수 있다!
 *
 *    AsyncTask 의 메소드
 *      onPreExecute() : 백그라운드 작업 시작하기 전에 호출
 *      doInBackground() : 백그라운드 작업, 시간이 많이 걸리는 '통신' 작업이나 복잡한 연산 작업등을 (비동기로)수행케 해야 한다.
 *      onProgressUpdate() : 백그라운즈 작업 도중 (여러번) 호출가능, 중간중간에 UI업데이트시 사용 가능!
 *      onPostExecute() : doInBackground() 완료되면 호출
 *
 *    AsyncTask<Params, Progress, Result>
 *      Params: doItBackground 에서 사용할 변수 타입
 *      Progress: onProgress 에서 사용할 변수의 타입
 *      Result : onPostExecute 에서 사용할 변수의 타입
 *
 */

public class Main5Activity extends AppCompatActivity {

    int mainValue = 0;
    int backValue1 = 0;
    int backValue2 = 0;
    TextView tvMainValue;
    TextView tvBackValue1, tvBackValue2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        tvMainValue = findViewById(R.id.tvMainValue);
        tvBackValue1 = findViewById(R.id.tvBackValue1);
        tvBackValue2 = findViewById(R.id.tvBackValue2);

        Log.d("myapp", "PRE!!");
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(100);

        Log.d("myapp", "POST!!"); //<--- 과연 언제찍힐까?

    }

    // AsyncTask<Params, Progress, Result>
//      Params: doItBackground 에서 사용할 변수 타입
//      Progress: onProgress 에서 사용할 변수의 타입
//      Result : onPostExecute 에서 사용할 변수의 타입
class BackgroundTask extends AsyncTask<Integer, Integer, Integer>{ // 가변매개변수, integers는 Integer[]

        //백그라운드 작업 시작하기 전에 호출


        @Override
        protected void onPreExecute() {
            Log.d("myapp", "onPreExecute");
            super.onPreExecute();
        }

        //백그라운드 작업, 반드시 구현!
        // ※ doInBackground() 에서 시간이 많이 걸리는 '통신' 작업이나 복잡한 연산 작업등을 (비동기로)수행케 해야 한다.
        @Override
        protected Integer doInBackground(Integer... integers) {
            for(backValue1 = 0; backValue1 < integers[0]; backValue1++){
                if(backValue1 % 10 == 0){
                    publishProgress(backValue1); //progress 상태를 update 뽑아냄 --> oProgressUpdate 호출되고 매개변수값 전달됨
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return backValue1;
        }

        //백그라운드 작업 도중(여러번) 호출 가능, 진행상황 업데이트, 중간중간 UI 업데이트시 사용
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("myapp", "Progress : " + values[0] + "%"); //publishProgress(i)가 보낸 값
            super.onProgressUpdate(values);

            tvBackValue1.setText("onProgressUpdate: " + values[0]); // Handler 없이도 메인 UI접근가능!
        }

        //doInBackground()완료되면 호출

        @Override
        protected void onPostExecute(Integer integer) {//doInBackground에서 return한 값을 매개변수로 받는다
            Log.d("myapp", "Result : " + integer);
            super.onPostExecute(integer);

            tvBackValue1.setText("onPostExecute:" + integer); //Handler 없이도 메인 UI접근 가능!
        }


    }
    public void mOnClick(View v){
        mainValue++;
        tvMainValue.setText("MainValue" + mainValue);
    }

}
