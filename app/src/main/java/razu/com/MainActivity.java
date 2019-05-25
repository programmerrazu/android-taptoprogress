package razu.com;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    private Circle circleCurve;
    private LinearLayout llTap;
    private boolean tapStatus = false;
    private CircleAnimation circleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleCurve = findViewById(R.id.circle_curve);
        llTap = findViewById(R.id.ll_tap);
        initTapAnimation();
    }

    private void initTapAnimation() {
        circleAnimation = new CircleAnimation(circleCurve, 127.0f);
        this.circleCurve.setCurveColor(ContextCompat.getColor(this, R.color.tap_light), ContextCompat.getColor(this, R.color.tap_dark));
        circleAnimation.setDuration(2000);
        circleAnimation.setAnimationListener(this);
        this.llTap.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        circleCurve.startAnimation(circleAnimation);
                        tapStatus = false;
                        return true;
                    case 1:
                    case 3:
                        circleAnimation.cancel();
                        tapStatus = true;
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void onAnimationStart(Animation animation) {
        Log.d("anim", "anim Animation started");
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (tapStatus) {
            Log.d("anim", "anim Incomplete");
        } else {
            // circleAnimation.cancel();
            Toasty.success(this, "Tap Completed", Toast.LENGTH_SHORT, true).show();
            Log.d("anim", "anim Complete");
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        Log.d("anim", "anim Animation Repeat");
    }
}