package scu.miomin.com.keeper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

import scu.miomin.com.keeper.R;

/**
 * 描述:修改系统设置的界面 创建日期:2015/5/10
 *
 * @author 应均康
 */
public class SetRemindActivity extends Activity {

    private ToggleButton Btn_reciveMsg = null;
    private ToggleButton Btn_vibrate = null;
    private ToggleButton Btn_voice = null;

    private final int TOGGLEBTN_ON = 1;
    private final int TOGGLEBTN_OFF = 0;

    private int isReciveMsg = TOGGLEBTN_ON;
    private int isVoice = TOGGLEBTN_ON;
    private int isVibrate = TOGGLEBTN_ON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_remind);

        initView();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SetRemindActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        Btn_reciveMsg = (ToggleButton) findViewById(R.id.btn_reciveMessage);
        Btn_reciveMsg.setOnToggleChanged(new OnToggleChanged() {

            @Override
            public void onToggle(boolean on) {
                if (on) {
                    isReciveMsg = TOGGLEBTN_ON;
                } else {
                    isReciveMsg = TOGGLEBTN_OFF;
                }
            }
        });

        Btn_vibrate = (ToggleButton) findViewById(R.id.btn_vibrate);
        Btn_vibrate.setOnToggleChanged(new OnToggleChanged() {

            @Override
            public void onToggle(boolean on) {
                if (on) {
                    isVibrate = TOGGLEBTN_ON;
                } else {
                    isVibrate = TOGGLEBTN_OFF;
                }
            }
        });
        Btn_voice = (ToggleButton) findViewById(R.id.btn_voice);
        Btn_voice.setOnToggleChanged(new OnToggleChanged() {

            @Override
            public void onToggle(boolean on) {
                if (on) {
                    isVoice = TOGGLEBTN_ON;
                } else {
                    isVoice = TOGGLEBTN_OFF;
                }
            }
        });

        SharedPreferences sp = this.getSharedPreferences("setting",
                MODE_PRIVATE);
        isReciveMsg = sp.getInt("reciveMsg", 1);
        isVoice = sp.getInt("voice", 1);
        isVibrate = sp.getInt("vibrate", 1);
        if (isReciveMsg == TOGGLEBTN_ON) {
            Btn_reciveMsg.setToggleOn();
        } else {
            Btn_reciveMsg.setToggleOff();
        }
        if (isVoice == TOGGLEBTN_ON) {
            Btn_voice.setToggleOn();
        } else {
            Btn_voice.setToggleOff();
        }
        if (isVibrate == TOGGLEBTN_ON) {
            Btn_vibrate.setToggleOn();
        } else {
            Btn_vibrate.setToggleOff();
        }
    }

    public void back(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences sp = this.getSharedPreferences("setting",
                MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        editor.putInt("reciveMsg", isReciveMsg);
        editor.putInt("voice", isVoice);
        editor.putInt("vibrate", isVibrate);
        super.onDestroy();
    }

    // 清空APP缓存
    public void clearBuffer(View view) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
