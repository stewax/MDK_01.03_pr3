package com.example.pr3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView tv_timer;
    int time = 0;
    Timer timer;
    TimerTask timerTask;
    boolean isRunning = false;  // Переименовал для ясности

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_timer = findViewById(R.id.tv_timer);
    }

    // Исправил название метода - должно быть с маленькой буквы
    public void onStart(View view) {
        if (!isRunning) {  // Если таймер не запущен
            // Запускаем таймер
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    time++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateTimerDisplay();
                        }
                    });
                }
            };
            timer.schedule(timerTask, 0, 1000);

            // Меняем текст кнопки
            Button button = findViewById(R.id.button);
            button.setText("Стоп");
            isRunning = true;
        } else {  // Если таймер запущен - останавливаем
            if (timer != null) {
                timer.cancel();
                timer = null;
                timerTask = null;
            }
            Button button = findViewById(R.id.button);
            button.setText("Продолжить");
            isRunning = false;
        }
    }

    public void onClear(View view) {
        // Останавливаем таймер
        if (timer != null) {
            timer.cancel();
            timer = null;
            timerTask = null;
        }

        // Сбрасываем значения
        time = 0;
        isRunning = false;
        updateTimerDisplay();

        // Возвращаем кнопку в исходное состояние
        Button button = findViewById(R.id.button);
        button.setText("Начать");
    }

    // Вынес обновление дисплея в отдельный метод
    private void updateTimerDisplay() {
        int hours = time / 3600;  // 60*60 = 3600
        int minutes = (time % 3600) / 60;
        int seconds = time % 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        tv_timer.setText(timeString);
    }
}