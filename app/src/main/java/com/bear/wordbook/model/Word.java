package com.bear.wordbook.model;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Word extends LitePalSupport implements Serializable {

    private String english; // 英文

    private String chinese;   // 中文

    private String englishExample;     // 英文例句

    private String chineseExample;     // 中文例句

    private int flag;       // 0表示不是生词，1是

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getEnglishExample() {
        return englishExample;
    }

    public void setEnglishExample(String englishExample) {
        this.englishExample = englishExample;
    }

    public String getChineseExample() {
        return chineseExample;
    }

    public void setChineseExample(String chineseExample) {
        this.chineseExample = chineseExample;
    }
}
