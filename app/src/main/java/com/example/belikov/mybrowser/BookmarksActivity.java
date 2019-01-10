package com.example.belikov.mybrowser;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class BookmarksActivity extends AppCompatActivity {
    private String[] data;
    public static String BOOKMARK = "bookmark";
    private String chooseBookmark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarks_activity);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        MyParcel parcel = getIntent().getParcelableExtra(MainActivity.BOOKMARKS);
        data = parcel.getBookmarksArr();
        BookmarksAdapter adapter = new BookmarksAdapter(data);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new BookmarksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView textView = (TextView)view;
                chooseBookmark = textView.getText().toString();
                Toast.makeText(BookmarksActivity.this, chooseBookmark, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BookmarksActivity.this, MainActivity.class);
                intent.putExtra(BOOKMARK, chooseBookmark);
                startActivity(intent);
            }
        });
    }
    }

