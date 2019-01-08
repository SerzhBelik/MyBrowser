package com.example.belikov.mybrowser;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    private String homePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homePage = "https://google.ru";


        // Получим наш элемент WebView
        final WebView page = findViewById(R.id.browse);

        // создадим запрос при помощи нашего класса Requester, параметром зададим анонимный класс
        // с обратным вызовом по завершении работы (этот вариант хоть и находится в потоке UI,
        // но все равно сделаем через обратный вызов, потому что нам в дальнейшем придется
        // запускать эту задачу в фоне)
        OkHttpRequester requester = new OkHttpRequester(new OkHttpRequester.OnResponseCompleted() {
            // Этот метод будет вызываться по завершении закачки страницы
            @Override
            public void onCompleted(String content) {
                page.loadData(content, "text/html; charset=utf-8", "utf-8");
            }
        });

        // Сделать запрос
        requester.run(homePage); // загрузим нашу страницу
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
