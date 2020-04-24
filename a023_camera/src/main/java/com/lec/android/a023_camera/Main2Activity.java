package com.lec.android.a023_camera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;

/*  카메라 화면 보여주기 --> SurfaceView 사용
                                    1.프리뷰설정
                                     --->
    SurfaceView <-->  SurfaceHolder <---   카메라   2.프리뷰 시작
               3. 프리뷰표시        3. 프리뷰 디스플레이
    SuffaceView 는 SurfaceHolder 에 의해 제어되는 모습
                - setPreviewDisplay() 로 미리보기 설정해주어야 함
    초기화 작업후 카메라객체의 startPreview() 호출 --> 카메라 영상이 SurfaveView 로 보이게 된다
    주의!: Surface 타입은 반드시 SURFACE_TYPE_PUSH_BUFFERS)
    SurfaceView 가  SURFACE_TYPE_PUSH_BUFFERS 타입인 경우, 카메라 보여주기 외에 다른 그림 못 그림
    그 위에 다른 그림 (아이콤, 마커, 증강현실..) 그리려면 별도의 레이아웃을 위에 포개야 한다
 */

// API29 부터  android.hardware.Camera 는 deprecated 됨.
//        대신 android.hardware.camera2 의 객체 사용 권장

public class Main2Activity extends AppCompatActivity {

    String[] permissions = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    final int REQUEST_CODE = 101;

    CameraSurfaceView cameraSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // 권한 획득 하기
        if(Build.VERSION.SDK_INT >= 23 ){
            if(checkSelfPermission(String.valueOf(permissions)) == PackageManager.PERMISSION_DENIED){
                requestPermissions(permissions, REQUEST_CODE);  // 권한 요청하기
            }
        }

        FrameLayout previewFrame = findViewById(R.id.previewFrame);
        cameraSurfaceView = new CameraSurfaceView(this);
        previewFrame.addView(cameraSurfaceView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            // 사진 촬영
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

    } // end onCreate()

    //캡처한 이미지 데이터 --> data
    public void takePicture(){
        cameraSurfaceView.capture(new Camera.PictureCallback() {
            //사진 찍힐떄 호출되는 콜백
            //data : 전달받은 이미지 byte 배열
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //byte배열 => Bitmap 객체로 만들기
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                //미디어 앨범에추가, MediaStore.Image.Media 사용
                String outUriStr = MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        bitmap,
                        "Captured Image",
                        "captured Image using Camera"

                );

                if(outUriStr == null){
                    Log.d("myapp", "이미지 저장 실패, Image Insert Fail");
                    return;
                } else{
                    Uri outUri = Uri.parse(outUriStr);
                    sendBroadcast(new Intent(
                            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, outUri
                    ));
                }

                camera.startPreview(); //촬영후 프리뷰 다시 재개

            }
        });

    }


    // SurfaceView 상속  + SurfaceHolder.Callback 구현
    private class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

        private SurfaceHolder mHolder;
        private Camera camera = null;

        // 생성자에선 SurfaceHolder 객체 참조후 설정
        public CameraSurfaceView(Context context) {
            super(context);

            mHolder = getHolder();
            mHolder.addCallback(this);
        }

        // SurfaceView 가 만들어질때, 카메라 객체를 참조한 후 미리보기 화면으로
        // 홀더 객체 설정
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            camera = Camera.open();

            // 카메라 orientation 세팅
            setCameraOrientation();

            try {
                camera.setPreviewDisplay(mHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // SurfaceView 의 화면 크기가 변경될때 호출
        // --> 변경되는 시점에 미리보기 시작
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            camera.startPreview();
        }

        // SurfaceView 가 소멸될때 호출
        // --> 미리보기 중지
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }

        // 카메로 orientation 세팅
        public void setCameraOrientation(){
            if(camera == null) return;

            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(0, info);

            WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
            int rotation = manager.getDefaultDisplay().getRotation();  // 화면 회전값

            int degrees = 0;
            switch (rotation){
                case Surface.ROTATION_0: degrees = 0; break;
                case Surface.ROTATION_90: degrees = 90; break;
                case Surface.ROTATION_180: degrees = 180; break;
                case Surface.ROTATION_270: degrees = 270; break;
            }

            int result;
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }

            camera.setDisplayOrientation(result);
        } // end setCameraOrientation()


        // 사진 촬영!
        public boolean capture(Camera.PictureCallback handler){
            if(camera == null) return false;

            camera.takePicture(null, null, handler);
            return true;
        }


    } // end SurfaceView



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length <= 0){
                    Toast.makeText(this, "권한 획득 실패", Toast.LENGTH_SHORT).show();

                    // onDestroy() 혹은 finish()
                    return;
                }

                String result = "";
                for(int i = 0; i < grantResults.length; i++){
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        result += "권한 획득 성공:" + permissions[i] + "\n";
                    } else {
                        result += "권한 획득 실패:" + permissions[i] + "\n";
                    }
                }
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                Log.d("myapp", result);
                break;
        }
    }
} // end Activity
