package com.bear.wordbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bear.wordbook.model.Voice;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;

public class SpeechActivity extends AppCompatActivity {

    private ImageButton microphone;     // 麦克风

    private TextView result;            // 识别结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        result = findViewById(R.id.result);

        // 初始化语言引擎
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5d955fd0");

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        // 麦克风设置
        microphone = findViewById(R.id.microphone);
        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 动态申请麦克风权限
                if (ContextCompat.checkSelfPermission(SpeechActivity.this, Manifest.
                        permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(SpeechActivity.this, new String[]{
                            Manifest.permission.RECORD_AUDIO
                    }, 1);
                }else {
                    initSpeech();
                }
            }
        });
    }

    // 初始化语言识别
    private void initSpeech(){
        // 创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(SpeechActivity.this, null);
        // 设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                   result.setText(parseVoice(recognizerResult.getResultString()));
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        mDialog.show();
    }

    // 解析json
    private String parseVoice(String resultString){
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString,Voice.class);
        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws){
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initSpeech();
                }else {
                    Toast.makeText(SpeechActivity.this, "You denied the permission",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
