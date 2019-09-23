package com.bear.wordbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bear.wordbook.model.Word;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WordListFragment extends Fragment implements AdapterView.OnItemClickListener{

    public static ArrayAdapter<String> adapter;

    private List<Word> words = new ArrayList<>();   // 存储数据库中单词的信息

    private String[] wordList;   // 存储用于显示在listview上的信息

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_list_fragmentt, container, false);

        String[] wordList = getWordList();

        ListView listView = view.findViewById(R.id.word_list);
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, wordList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    // 获取单词列表
    private String[] getWordList(){
        LitePal.getDatabase();  // 创建数据库
        words = LitePal.findAll(Word.class);
        wordList = new String[words.size()];
        for (int i = 0; i < words.size(); i++){
            wordList[i] = words.get(i).getEnglish() + "  " + words.get(i).getChinese();
        }
        return wordList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Word word = words.get(position);
        Intent intent = new Intent(getContext(), WordActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }
}
