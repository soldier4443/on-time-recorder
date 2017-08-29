package com.turastory.ontimerecorder;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int PERIOD_SECOND = 1000;
    private static final int PERIOD_MILLISECOND = 1;

    public static final int REQUEST_TAG = 100;

    public static final int SUCCESS = 200;
    public static final int FAILURE = 201;

    @BindView(R.id.container)
    ViewGroup container;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.timer_Text)
    TextView timerText;

    private Timer timer;
    private ValueAnimator backgroundAnimation;
    private ValueAnimator textAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        timer = new Timer( () -> update(timer.getInterval()) );

        container.setOnClickListener(v -> {
            Log.e("MainActivity", "onClick()");

            if (timer.isRunning()) {
                timer.stop();

                backgroundAnimation.reverse();
                textAnimation.reverse();

                startActivityForResult(
                    new Intent(this, TagActivity.class),
                    REQUEST_TAG);

            } else {
                timer.start(PERIOD_SECOND);
                backgroundAnimation.start();
                textAnimation.start();
            }
        });

        setupAnimation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAG) {
            if (resultCode == SUCCESS) {
                Tag tag = (Tag) data.getSerializableExtra("tag");

                TimePiece timePiece = new TimePiece(
                    timer.getStartTime(),
                    timer.getInterval(),
                    tag);

                Database.getInstance().addTimePiece(timePiece);

                Toast.makeText(this,
                    String.format(Locale.getDefault(), "Name: [%s] duration: [%d]s",
                        timePiece.getTag().getName(), timePiece.getInterval()),
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupAnimation() {
        backgroundAnimation = new ValueAnimator();
        backgroundAnimation.setIntValues(0xffffffff, 0xff000000);
        backgroundAnimation.setEvaluator(new ArgbEvaluator());
        backgroundAnimation.addUpdateListener( animation -> container.setBackgroundColor((int)animation.getAnimatedValue()) );
        backgroundAnimation.setDuration(250);

        textAnimation = new ValueAnimator();
        textAnimation.setIntValues(0xff000000, 0xffffffff);
        textAnimation.setEvaluator(new ArgbEvaluator());
        textAnimation.addUpdateListener(animation -> {
//            titleText.setTextColor((int)animation.getAnimatedValue());
            timerText.setTextColor((int)animation.getAnimatedValue());
        } );
        textAnimation.setDuration(250);
    }

    private void update(long interval) {
        int min = (int) interval / 60;
        int sec = (int) interval % 60;

        runOnUiThread(() -> timerText.setText(String.format(Locale.getDefault(), "%02d:%02d", min, sec)));
    }
}
