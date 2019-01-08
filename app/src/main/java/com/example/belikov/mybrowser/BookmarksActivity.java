package com.example.belikov.mybrowser;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


public class BookmarksActivity extends AppCompatActivity {
    private String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarks_activity);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        MyParcel parcel = getIntent().getParcelableExtra(MainActivity.BOOKMARKS);
        data = parcel.getBookmarksArr();
        BookmarksAdapter adapter = new BookmarksAdapter(data);
        recyclerView.setAdapter(adapter);
    }
    }

