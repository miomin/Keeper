package scu.miomin.com.keeper.basedialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;

/**
 * 描述:自定义dialog的基类 创建日期:2015/10/21
 *
 * @author 莫绪旻
 */
public class BaseDialog extends BaseActivity {

    private TextView tvTitle;
    private TextView tvContent;
    private Button button1;
    private Button button2;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_base);
        initView();
    }

    // 初始化对话框内容
    private void initView() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String buttontext1 = intent.getStringExtra("buttontext1");
        String buttontext2 = intent.getStringExtra("buttontext2");
        index = intent.getIntExtra("index", -1);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvContent = (TextView) findViewById(R.id.tvContent);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        tvTitle.setText(title);
        tvContent.setText(content);
        button1.setText(buttontext1);
        button2.setText(buttontext2);
    }

    public void button1(View view) {
        Intent intent = new Intent();
        intent.putExtra("returnedChoose", 1);
        intent.putExtra("index", index);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void button2(View view) {
        Intent intent = new Intent();
        intent.putExtra("returnedChoose", 2);
        intent.putExtra("index", index);
        setResult(RESULT_OK, intent);
        finish();
    }

    // 用于启动BaseDialog
    public static void actionStartActivity(int requestCode, int index, Activity activity, String title, String content, String buttontext1, String buttontext2) {
        Intent intent = new Intent(activity, BaseDialog.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("buttontext1", buttontext1);
        intent.putExtra("buttontext2", buttontext2);
        intent.putExtra("index", index);
        activity.startActivityForResult(intent, requestCode);
    }
}
