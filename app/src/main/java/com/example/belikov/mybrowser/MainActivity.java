package com.example.belikov.mybrowser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String homePage = "https://google.ru";
    private Button go;
    private EditText editText;
    private OkHttpRequester requester;
    private SharedPreferences sharedPref;
    private String currentPage;

    private static String HOME_PAGE = "home page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        // Получим наш элемент WebView
        final WebView page = findViewById(R.id.browse);

        // создадим запрос при помощи нашего класса Requester, параметром зададим анонимный класс
        // с обратным вызовом по завершении работы (этот вариант хоть и находится в потоке UI,
        // но все равно сделаем через обратный вызов, потому что нам в дальнейшем придется
        // запускать эту задачу в фоне)
        requester = new OkHttpRequester(new OkHttpRequester.OnResponseCompleted() {
            // Этот метод будет вызываться по завершении закачки страницы
            @Override
            public void onCompleted(String content) {
                page.loadData(content, "text/html; charset=utf-8", "utf-8");
            }
        });

        // Сделать запрос
        requester.run(homePage); // загрузим нашу страницу


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nextPage = "http://"  + editText.getText().toString();
                requester.run(nextPage);
                currentPage = nextPage;
                Toast.makeText(MainActivity.this, "GO!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initialize() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        go = findViewById(R.id.go);
        editText = findViewById(R.id.uri);
        sharedPref = getSharedPreferences(HOME_PAGE, Context.MODE_PRIVATE);

        if (sharedPref.contains(HOME_PAGE)){
            homePage = sharedPref.getString(HOME_PAGE, "https://");
        }
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
        if (id == R.id.open_bookmark) {
            return true;
        }

        if (id == R.id.add_bookmark) {
            return true;
        }

        if (id == R.id.make_home_page) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(HOME_PAGE, currentPage);
            editor.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
