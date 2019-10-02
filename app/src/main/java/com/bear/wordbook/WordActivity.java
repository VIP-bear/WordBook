package com.bear.wordbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bear.wordbook.model.Word;

import org.litepal.LitePal;

public class WordActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText wordEnglish;   // 单词的英文

    private EditText wordChinese;   // 单词的中文

    private EditText enExample;     // 单词英文例句

    private EditText zhExample;     // 单词中文例句

    private ImageButton back;       // 返回

    private Button edit;            // 编辑

    private Button change;          // 提交修改

    private int orientation;        // 记录横竖屏的值

    private Word word;

    private static final String TAG = "WordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        // 获取单词
        word = (Word) getIntent().getSerializableExtra("word");
        // 隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        orientation = getResources().getConfiguration().orientation;    // 获取横竖屏值
        initLayout(orientation);
        setListerner();
        showWord(word);

        // 设置文本不可编辑和点击
        editText(false);
    }

    // 寻找各个控件
    private void initLayout(int orientation){
        if (orientation == 1){
            edit = findViewById(R.id.edit_button);
            change = findViewById(R.id.change);
            change.setVisibility(View.GONE);
        }
        wordChinese = findViewById(R.id.word_chinese);
        wordEnglish = findViewById(R.id.word_english);
        enExample = findViewById(R.id.en_example);
        zhExample = findViewById(R.id.zh_example);
        back = findViewById(R.id.back);

    }

    // 设置监听器
    private void setListerner(){
        edit.setOnClickListener(this);
        change.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    // 显示单词
    private void showWord(Word word){
        wordEnglish.setText(word.getEnglish());
        wordChinese.setText(word.getChinese());
        enExample.setText(word.getEnglishExample());
        zhExample.setText(word.getChineseExample());
    }

    private void editText(boolean t){
        wordEnglish.setFocusable(t);
        wordEnglish.setFocusableInTouchMode(t);
        wordChinese.setFocusable(t);
        wordChinese.setFocusableInTouchMode(t);
        enExample.setFocusable(t);
        enExample.setFocusableInTouchMode(t);
        zhExample.setFocusable(t);
        zhExample.setFocusableInTouchMode(t);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                Intent intent = new Intent(WordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.edit_button:
                Log.d(TAG, "onClick: "+"hello");
                if (edit.getText().toString().equals("编辑")){
                    editText(true);
                    edit.setText("取消");
                    change.setVisibility(View.VISIBLE);
                }else {
                    edit.setText("编辑");
                    change.setVisibility(View.GONE);
                }
                break;
            case R.id.change:
                if (!wordEnglish.equals("") && !wordChinese.equals("") && !enExample.equals("")
                        && !zhExample.equals("")) {
                    editText(false);
                    // 删除原来的单词
                    LitePal.deleteAll(Word.class, "english = ?", word.getEnglish());
                    // 加入修改后的单词
                    Word word = new Word();
                    word.setEnglish(wordEnglish.getText().toString());
                    word.setChinese(wordChinese.getText().toString());
                    word.setEnglishExample(enExample.getText().toString());
                    word.setChineseExample(zhExample.getText().toString());
                    word.save();
                    edit.setText("编辑");
                    change.setVisibility(View.GONE);
                    Toast.makeText(WordActivity.this, "修改成功",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(WordActivity.this, "修改失败,不能有空",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
