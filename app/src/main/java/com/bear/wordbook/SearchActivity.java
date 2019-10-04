package com.bear.wordbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import com.bear.wordbook.model.Word;
import java.util.ArrayList;
import java.util.List;

import static com.bear.wordbook.WordListFragment.words;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;

    private ListView listView;

    private ImageButton microphone;     // 麦克风

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.list_view);
        final ArrayAdapter<String> mAdapter = new ArrayAdapter<>(SearchActivity.this,
                android.R.layout.simple_list_item_1, mList);
        listView.setAdapter(mAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 搜索
                mList.clear();
                if (!query.isEmpty()){
                    for (Word word : words){
                        if (word.getEnglish().equals(query)){
                            mList.add(word.getEnglish() + "  " + word.getChinese());
                            mAdapter.notifyDataSetChanged();
                            searchView.clearFocus();
                            return true;
                        }
                    }
                }
                return false;
            }

            // 输入发生变化
            @Override
            public boolean onQueryTextChange(String newText) {
                mList.clear();
                String regex = "^" + newText + ".*";
                for (Word word : words){
                    if(word.getEnglish().matches(regex)){
                        mList.add(word.getEnglish() + "  " + word.getChinese());
                    }
                }
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                Intent intent = new Intent(SearchActivity.this, WordActivity.class);
                intent.putExtra("word", word);
                startActivity(intent);
            }
        });

    }


}
