package com.bear.wordbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bear.wordbook.model.Word;

public class WordActivity extends AppCompatActivity {

    private TextView wordEnglish;   // 单词的英文

    private TextView wordChinese;   // 单词的中文

    private TextView enExample;     // 单词英文例句

    private TextView zhExample;     // 单词中文例句

    private ImageButton back;       // 返回

    private int orientation;        // 记录横竖屏的值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        // 获取单词
        Word word = (Word) getIntent().getSerializableExtra("word");
        // 隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        orientation = getResources().getConfiguration().orientation;    // 获取横竖屏值
        initLayout();
        showWord(word);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 寻找各个控件
    private void initLayout(){
        wordChinese = findViewById(R.id.word_chinese);
        wordEnglish = findViewById(R.id.word_english);
        enExample = findViewById(R.id.en_example);
        zhExample = findViewById(R.id.zh_example);
        back = findViewById(R.id.back);

    }

    // 显示单词
    private void showWord(Word word){
        wordEnglish.setText(word.getEnglish());
        wordChinese.setText(word.getChinese());
        enExample.setText(word.getEnglishExample());
        zhExample.setText(word.getChineseExample());
    }

}
