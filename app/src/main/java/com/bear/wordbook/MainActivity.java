package com.bear.wordbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;

    private ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;      // 适配器

    private MenuItem menuItem;      // 底部菜单

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建数据库
        LitePal.getDatabase();


        // 隐藏标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        initLayout();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                menuItem = item;
                switch (menuItem.getItemId()){
                    case R.id.home:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.add:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.translation:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.glossary:
                        viewPager.setCurrentItem(3);
                        return true;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                WordListFragment.update();
                NewWordsFragment.update();
                if (menuItem != null){
                    menuItem.setChecked(false);
                }else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        List<Fragment> list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new AddWordFragment());
        list.add(new TranslationFragment());
        list.add(new NewWordsFragment());
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.setList(list);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void initLayout(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.view_pager);

    }

}
