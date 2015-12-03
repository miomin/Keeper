package scu.miomin.com.keeper.activity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.resource.MyString;

/**
 * 描述:欢迎页 创建日期:2015/7/23
 *
 * @author 莫绪旻
 */
public class WelcomeActivity extends BaseActivity {

    private int welcome;
    private static final int TIME = 2000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    private void init() {
        SharedPreferences perPreferences = getSharedPreferences(MyString.WELCOME,
                MODE_PRIVATE);
        welcome = perPreferences.getInt(MyString.WELCOME, 0);
        if (welcome == 0) {
            Editor editor = perPreferences.edit();
            editor.clear();
            editor.putInt(MyString.WELCOME, 1);
            editor.commit();
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
        }
    }

    private void goHome() {
        LoginActivity.actionStart(this);
        overridePendingTransition(R.anim.push_right_in, R.anim.hold_long);
        finish();
    }

    private void goGuide() {
        LoginActivity.actionStart(this);
        overridePendingTransition(R.anim.push_right_in, R.anim.hold_long);
        finish();
    }
}
