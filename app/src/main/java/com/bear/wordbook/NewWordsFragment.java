package com.bear.wordbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import static com.bear.wordbook.WordListFragment.words;

public class NewWordsFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView newWordsListView;

    public static List<Word> newWordList = new ArrayList<>();

    public static ArrayAdapter<String> newWordAdapter;

    public static List<String> dataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_words_fragment, container, false);
        newWordsListView = view.findViewById(R.id.new_words_listview);
        getDataList();
        newWordAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dataList);
        newWordsListView.setAdapter(newWordAdapter);
        newWordsListView.setOnItemClickListener(this);
        return view;
    }

    // 获取需要显示的数据
    public static void getDataList(){
        newWordList.clear();
        dataList.clear();
        for (int i = 0; i < words.size(); i++){
            if (words.get(i).getFlag() == 1) {
                newWordList.add(words.get(i));
                dataList.add(words.get(i).getEnglish() + "  " + words.get(i).getChinese());
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Word word = newWordList.get(position);
        Intent intent = new Intent(getContext(), WordActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }

    // 刷新界面
    public static void update(){
        getDataList();
        if (!dataList.isEmpty()) {
            newWordAdapter.notifyDataSetChanged();
        }
    }
}
