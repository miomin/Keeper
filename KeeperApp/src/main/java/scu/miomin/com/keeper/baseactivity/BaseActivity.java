package scu.miomin.com.keeper.baseactivity;

import android.os.Bundle;
import android.util.Log;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.application.ActivityCollector;

/**
 * 描述:所有活动的基类 创建日期:2015/10/21
 *
 * @author 莫绪旻
 */
public class BaseActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("keeper", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }
}
