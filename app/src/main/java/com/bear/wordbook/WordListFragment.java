package com.bear.wordbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bear.wordbook.model.Word;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.bear.wordbook.NewWordsFragment.newWordAdapter;
import static com.iflytek.cloud.VerifierResult.TAG;

public class WordListFragment extends Fragment implements AdapterView.OnItemClickListener{

    public static ArrayAdapter<String> adapter;

    public static List<Word> words = new ArrayList<>();   // 存储数据库中单词的信息

    public static List<String> wordList = new ArrayList<>();   // 存储用于显示在listview上的信息

    private ListView listView;          // 单词列表

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_list_fragmentt, container, false);

        getWordList();

        listView = view.findViewById(R.id.word_list);
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, wordList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        // 长按删除
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("警告")
                        .setMessage("确定删除该单词?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LitePal.deleteAll(Word.class, "english = ?", words.get(position).getEnglish());
                                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                                update();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });
        return view;
    }

    // 获取单词列表
    public static void getWordList(){
        wordList.clear();
        words.clear();
        words = LitePal.findAll(Word.class);
        for (int i = 0; i < words.size(); i++){
            wordList.add(words.get(i).getEnglish() + "  " + words.get(i).getChinese());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Word word = words.get(position);
        Intent intent = new Intent(getContext(), WordActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }

    // 刷新界面
    public static void update(){
        getWordList();
        if (!wordList.isEmpty()) {
            adapter.notifyDataSetChanged();
        }
    }
}
