package com.bear.wordbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bear.wordbook.translate.demo.TransApi;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TranslationFragment extends Fragment implements View.OnClickListener{

    private EditText inputText;     // 输入文本

    private TextView transResult;   // 翻译后的文本

    private Button trans;       // 翻译

    private static final String APP_ID = "20190911000333778";
    private static final String SECURITY_KEY = "J7AI5yk2B65NrqwTzb9o";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.translation_fragment, container, false);
        initLayout(view);
        trans.setOnClickListener(this);
        return view;
    }

    private void initLayout(View view){
        inputText = view.findViewById(R.id.input_text);
        transResult = view.findViewById(R.id.trans_result);
        trans = view.findViewById(R.id.trans);
    }

    // 百度翻译
    private void search(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                TransApi api = new TransApi(APP_ID, SECURITY_KEY);
                parseJSONWithGSON(api.getTransResult(inputText.getText().toString(),
                        "auto", "en"));
            }
        }).start();
    }

    // 解析json
    private void parseJSONWithGSON(String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String transResult = jsonObject.getString("trans_result").split(",")[1]
                                            .split(":")[1];
            String dst = transResult.substring(1, transResult.length()-3);
            updateUI(dst);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // 更新UI
    private void updateUI(final String str){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                transResult.setText(str);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.trans:
                search();
                break;
            default:
                break;
        }
    }
}
