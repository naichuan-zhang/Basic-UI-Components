package com.example.horizontalprogressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressBar horizonP;
    private int mProgressStatus = 0;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horizonP = findViewById(R.id.progress1);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x111)
                    horizonP.setProgress(mProgressStatus);
                else {
                    Toast.makeText(MainActivity.this, "进度条已完成", Toast.LENGTH_SHORT).show();
                    horizonP.setVisibility(View.GONE);
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mProgressStatus = doWork();
                    Message msg = new Message();
                    if (mProgressStatus < 100) {
                        msg.what = 0x111;
                        mHandler.sendMessage(msg);
                    } else {
                        msg.what = 0x110;
                        mHandler.sendMessage(msg);
                        break;
                    }
                }
            }

            private int doWork() {
                mProgressStatus += Math.random() * 10;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return mProgressStatus;
            }
        }).start();
    }
}
