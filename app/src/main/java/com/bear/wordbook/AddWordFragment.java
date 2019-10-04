package com.bear.wordbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bear.wordbook.model.Word;

import org.litepal.LitePal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddWordFragment extends Fragment implements View.OnClickListener{

    private EditText english;   //英文

    private EditText chinese;   // 中文

    private EditText englishExample;    // 英文例句

    private EditText chineseExample;    // 中文例句

    private Button clear;       // 清空

    private Button commit;      // 提交

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_word_fragment, container, false);

        initLayout(view);
        setListener();

        return view;
    }

    // 找到各个控件
    private void initLayout(View view){
        english = view.findViewById(R.id.english);
        chinese = view.findViewById(R.id.chinese);
        englishExample = view.findViewById(R.id.english_example);
        chineseExample = view.findViewById(R.id.chinese_example);
        clear = view.findViewById(R.id.clear);
        commit = view.findViewById(R.id.commit);
    }

    // 设置监听器
    private void setListener(){
        clear.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clear:
                clearText();
                break;
            case R.id.commit:
                String en = english.getText().toString();
                String ch = chinese.getText().toString();
                String enEx = englishExample.getText().toString();
                String chEx = chineseExample.getText().toString();

                if (!en.equals("") && !ch.equals("") && !enEx.equals("")
                        && !chEx.equals("") && !LitePal.isExist(Word.class, "english = ?", en)) {
                    // 输入都不为空且单词不存在则保存单词
                    Word word = new Word();
                    word.setEnglish(en);
                    word.setChinese(ch);
                    word.setEnglishExample(enEx);
                    word.setChineseExample(chEx);
                    word.setFlag(0);
                    word.save();
                    clearText();
                    Toast.makeText(getContext(), "保存成功",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "不能有空",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    // 清空
    private void clearText(){
        english.setText("");
        chinese.setText("");
        englishExample.setText("");
        chineseExample.setText("");
    }

}
