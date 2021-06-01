package jp.co.futureantiques.stopwatchapp2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //スタート時間
    long startTime;
    //表示時間
    long time;
    //経過時間（一時停止計算用）
    long elapsedTime;
    TextView timeText;
    Button start;
    Button pause;
    Button reset;
    Handler handler = new Handler();
    Runnable runnable;
    SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss.SSS", Locale.JAPAN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        start = findViewById(R.id.start_button);
        pause = findViewById(R.id.pause_button);
        reset = findViewById(R.id.reset_button);

        timeText = findViewById(R.id.time_text);
        timeText.setText(dateFormat.format(0));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(v);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer(v);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer(v);
            }
        });
    }

    public void startTimer(View v) {
        startTime = System.currentTimeMillis();
        runnable = new Runnable() {
            @Override
            public void run() {
                time = System.currentTimeMillis() - startTime + elapsedTime;
                timeText.setText(dateFormat.format(time));
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 10);
            }
        };
        handler.postDelayed(runnable, 10);
    }

    public void pauseTimer(View v) {
        elapsedTime += System.currentTimeMillis() - startTime;
        handler.removeCallbacks(runnable);
    }

    public void resetTimer(View v) {
        elapsedTime = 0;
        time = 0;
        timeText.setText(dateFormat.format(0));
    }
}