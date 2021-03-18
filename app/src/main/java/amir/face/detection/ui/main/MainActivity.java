// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package amir.face.detection.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback;
import androidx.core.content.ContextCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.gms.common.annotation.KeepName;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.io.IOException;

import amir.face.detection.R;
import amir.face.detection.ui.photo_viewer.PhotoViewerActivity;
import amir.face.detection.utils.base.BaseActivity;
import amir.face.detection.utils.base.Cons;
import amir.face.detection.utils.common.CameraSource;
import amir.face.detection.utils.common.CameraSourcePreview;
import amir.face.detection.utils.interfaces.FaceDetectStatus;
import amir.face.detection.utils.common.FrameMetadata;
import amir.face.detection.utils.interfaces.FrameReturn;
import amir.face.detection.utils.common.GraphicOverlay;
import amir.face.detection.utils.base.PublicMethods;
import amir.face.detection.utils.models.RectModel;
import amir.face.detection.utils.visions.FaceDetectionProcessor;

import com.github.barteksc.pdfviewer.listener.OnTapListener;

import static amir.face.detection.utils.base.Cons.IMG_EXTRA_KEY;

import android.view.MotionEvent;


@KeepName
public final class MainActivity extends BaseActivity
        implements OnRequestPermissionsResultCallback, FrameReturn {
    private static final String FACE_DETECTION = "Face Detection";
    private static final String TAG = "MLKitTAG";

    Bitmap originalImage = null;
    private CameraSource cameraSource = null;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;
    private ImageView test;
//    private Bitmap croppedImage = null;

    long d_start_time = 0;
    long d_end_time = 0;
    long l_start_time = 0;
    long l_end_time = 0;
    int p1 = 0;
    int p2 = 0;

    int swipeNext = 0;
    int swipeprev = 0;

    long downTime = SystemClock.uptimeMillis();
    long eventTime = SystemClock.uptimeMillis() + 100;
    float rightX = 900.0f;
    float rightY = 1100.0f;
    float leftX = 100.0f;
    float leftY = 1100.0f;
    int metaState = 0;

    // --------MOTION---------
    // -----1.Next Page-------
    MotionEvent NextEvent1 = MotionEvent.obtain(
            downTime,
            eventTime+1000,
            MotionEvent.ACTION_DOWN,
            rightX,
            rightY,
            metaState
    );
    MotionEvent NextEvent2 = MotionEvent.obtain(
            downTime+1000,
            eventTime+5000,
            MotionEvent.ACTION_MOVE,
            leftX,
            leftY,
            metaState
    );
    MotionEvent NextEvent3 = MotionEvent.obtain(
            downTime+5000,
            eventTime+5000,
            MotionEvent.ACTION_UP,
            leftX,
            leftY,
            metaState
    );

    // -----2.Prev Page-------
    MotionEvent PrevEvent1 = MotionEvent.obtain(
            downTime,
            eventTime+1000,
            MotionEvent.ACTION_DOWN,
            leftX,
            leftY,
            metaState
    );
    MotionEvent PrevEvent2 = MotionEvent.obtain(
            downTime+1000,
            eventTime+5000,
            MotionEvent.ACTION_MOVE,
            rightX,
            rightY,
            metaState
    );
    MotionEvent PrevEvent3 = MotionEvent.obtain(
            downTime+5000,
            eventTime+5000,
            MotionEvent.ACTION_UP,
            rightX,
            rightY,
            metaState
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = findViewById(R.id.test);
        preview = findViewById(R.id.firePreview);
        graphicOverlay = findViewById(R.id.fireFaceOverlay);
        Switch sw = (Switch)findViewById(R.id.cameraSwitch);

        // 카메라 화면 온/오프 스위치
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    findViewById(R.id.firePreview).setVisibility(View.INVISIBLE);
                    findViewById(R.id.fireFaceOverlay).setVisibility(View.INVISIBLE);
                } else {
                    findViewById(R.id.firePreview).setVisibility(View.VISIBLE);
                    findViewById(R.id.fireFaceOverlay).setVisibility(View.VISIBLE);
                }
            }
        });


        if (PublicMethods.allPermissionsGranted(this)) {
            createCameraSource();
        } else {
            PublicMethods.getRuntimePermissions(this);
        }

        // 책이름 get intent
        Intent intent = getIntent();
        String bookname = intent.getExtras().getString("bookname") + ".pdf";
        String assetName = bookname.replaceAll(" ", "");

        // PDF View
        PDFView pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset(assetName)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .defaultPage(0)
                // allows to draw something on the current page, usually visible in the middle of the screen
                // called on single tap, return true if handled, false to toggle scroll handle visibility
                //.onTap(onTapListener)
                //.onLongPress(onLongPressListener)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .autoSpacing(true) // add dynamic spacing to fit each page on its own on the screen
                .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
                .pageSnap(true) // snap pages to screen boundaries
                .pageFling(true) // make a fling change only a single page like ViewPager
                .nightMode(false) // toggle night mode
                .load();

    }

    private void createCameraSource() {
        if (cameraSource == null) {
            cameraSource = new CameraSource(this, graphicOverlay);
        }
        try {
            FaceDetectionProcessor processor = new FaceDetectionProcessor(getResources());
            processor.frameHandler = this;
            //processor.faceDetectStatus = this;
            cameraSource.setMachineLearningFrameProcessor(processor);
        } catch (Exception e) {
            Log.e(TAG, "Can not create image processor: " + FACE_DETECTION, e);
            Toast.makeText(
                    getApplicationContext(),
                    "Can not create image processor: " + e.getMessage(),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }


    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startCameraSource();
    }

    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PublicMethods.allPermissionsGranted(this)) {
            createCameraSource();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // 변수 check를 사용하여 한번만 인식하도록 조정
    int check_d = 0;
    int check_l = 0;
    //calls with each frame includes by face
    @Override
    public void onFrame(Bitmap image, FirebaseVisionFace face, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay) {
        originalImage = image;

        float leftState = face.getLeftEyeOpenProbability();
        float rightState = face.getRightEyeOpenProbability();

        // blink
        if (leftState < 0.1 & rightState < 0.1) {
            d_end_time = System.currentTimeMillis() / 1000 - d_start_time;
            p1 = 1;
            check_d = 1;
        } else {
            findViewById(R.id.doubleBlink).setVisibility(View.INVISIBLE);
            findViewById(R.id.longBlink).setVisibility(View.INVISIBLE);
        }

        // double blink
        if ((p1 == 1) && (leftState >= 0.3 & rightState >= 0.3)) {
            p1 = 0;
            d_start_time = System.currentTimeMillis() / 1000;
        }

        // long blink
        if (leftState < 0.1 & rightState < 0.1) {
            l_end_time = System.currentTimeMillis() / 1000 - l_start_time;
        }
        if (leftState >= 0.3 & rightState >= 0.3) {
            l_start_time = System.currentTimeMillis() / 1000;
            check_l = 1;
        }

        // start_time과 end_time 차이가 0.9초 이하면 double blink
        if (d_end_time <= 0.9 & check_d == 1) {
            // 페이지 넘기기
            findViewById(R.id.doubleBlink).setVisibility(View.VISIBLE);
            findViewById(R.id.pdfView).dispatchTouchEvent(NextEvent1);
            findViewById(R.id.pdfView).dispatchTouchEvent(NextEvent2);
            findViewById(R.id.pdfView).dispatchTouchEvent(NextEvent3);
            // 초기화
            check_d = 0;
            d_end_time = 0;
            d_start_time = 0;

            Toast.makeText(this, "다음 페이지", Toast.LENGTH_SHORT).show();
        }

        // 감은 상태로 1초 이상이면 long blink
        if (l_end_time >= 1.0 & l_end_time < 1.001 & check_l == 1) {
            findViewById(R.id.longBlink).setVisibility(View.VISIBLE);
            findViewById(R.id.pdfView).dispatchTouchEvent(PrevEvent1);
            findViewById(R.id.pdfView).dispatchTouchEvent(PrevEvent2);
            findViewById(R.id.pdfView).dispatchTouchEvent(PrevEvent3);
            check_l = 0;
            l_end_time = 0;
            l_start_time = 0;

            Toast.makeText(this, "이전 페이지", Toast.LENGTH_SHORT).show();
        }
    }

}
