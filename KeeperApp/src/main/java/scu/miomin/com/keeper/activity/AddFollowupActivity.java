package scu.miomin.com.keeper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.util.ToastUtils;

/**
 * 描述:绘制心电图的界面 创建日期:2015/5/23
 *
 * @author 莫绪旻
 */
public class AddFollowupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_followup);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AddFollowupActivity.class);
        context.startActivity(intent);
    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void add(View view) {
        ToastUtils.showToast(this, getResources().getString(R.string.addSucceed));
        super.onBackPressed();
    }
}
