package com.example.belikov.mybrowser;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// Здесь будем вызывать запросы
public class OkHttpRequester {

    private final OnResponseCompleted listener;   // интерфейс с обратным вызовом

    public OkHttpRequester(OnResponseCompleted listener) {
        this.listener = listener;
    }

    // Синхронный запрос страницы
    public void run(String url) {
        OkHttpClient client = new OkHttpClient();        // Клиент
        Request.Builder builder = new Request.Builder(); // создадим строителя
        builder.url(url);                                // укажем адрес сервера
        Request request = builder.build();               // построим запрос

        Call call = client.newCall(request);            // Ставим запрос в очередь
        call.enqueue(new Callback() {
            final Handler handler = new Handler();

            // Этот метод срабатывает по приходу ответа от сервера
            public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                final String answer = response.body().string();
                // передаем данные в поток UI
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onCompleted(answer);
                    }
                });
            }

            // Этот метод срабатывает при сбое
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("OkHttpRequester", "failed", e);
            }
        });
    }

    // интерфейс обратного вызова, метод onCompleted вызывается по окончании загрузки страницы
    public interface OnResponseCompleted {
        void onCompleted(String content);
    }
}
