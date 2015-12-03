package scu.miomin.com.keeper.dialog;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;

/**
 * 描述：正在登陆对话框 创建日期：2015/5/1
 *
 * @author 莫绪旻
 */
public class LoadDialog extends BaseActivity {

    public static LoadDialog instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        instance = this;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoadDialog.class);
        context.startActivity(intent);
    }
}
