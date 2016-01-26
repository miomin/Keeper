package scu.miomin.com.keeper.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import scu.miomin.com.keeper.R;


/**
 * 描述：关于我们对话框 创建日期：2015/5/1
 *
 * @author 莫绪旻
 */
public class AboutUsDialog extends Activity {

    public static AboutUsDialog instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_about_us);
        instance = this;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AboutUsDialog.class);
        context.startActivity(intent);
    }

    public void close(View view) {
        finish();
    }
}
