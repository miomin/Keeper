package scu.miomin.com.keeper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;

/**
 * 描述:绘制心电图的界面 创建日期:2015/5/23
 *
 * @author 莫绪旻
 */
public class FollowupInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followup_info);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FollowupInfoActivity.class);
        context.startActivity(intent);
    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void checkFollowupLineChart(View view) {
        TrendLineChartActivity.actionStart(this);
    }
}
