package scu.miomin.com.keeper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.dialog.LoadDialog;

/**
 * 描述:注册界面 创建日期:2015/7/23
 *
 * @author 应俊康
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back_btn;

    private LinearLayout erro_layout;
    private TextView erro_inf;

    private EditText id_edit;
    private LinearLayout id_cancel;

    private EditText pw_edit;
    private LinearLayout pw_cancel;
    private LinearLayout btn_eye;

    private EditText code_edit;

    private Button get_Code_btn;

    private Button btn_register;

    private TextView user_protocol;
    private boolean isEye;

    public static RegisterActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        instance = this;
        initView();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    private void initView() {

        back_btn = (ImageView) findViewById(R.id.btn_back);
        back_btn.setOnClickListener(this);

        erro_layout = (LinearLayout) findViewById(R.id.erro_layout);
        erro_inf = (TextView) findViewById(R.id.erro_info);

        id_edit = (EditText) findViewById(R.id.id_edit);
        id_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(id_edit.getText())) {
                    id_cancel.setVisibility(View.VISIBLE);
                    get_Code_btn.setEnabled(true);
                    if (!TextUtils.isEmpty(pw_edit.getText()) && !TextUtils.isEmpty(code_edit.getText())) {
                        btn_register.setEnabled(true);
                    } else {
                        btn_register.setEnabled(false);
                    }
                } else {
                    id_cancel.setVisibility(View.INVISIBLE);
                    get_Code_btn.setEnabled(false);
                    btn_register.setEnabled(false);
                }
            }
        });

        id_cancel = (LinearLayout) findViewById(R.id.id_cancel);
        id_cancel.setOnClickListener(this);

        pw_edit = (EditText) findViewById(R.id.pw_edit);
        pw_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(pw_edit.getText())) {
                    pw_cancel.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(id_edit.getText()) && !TextUtils.isEmpty(code_edit.getText())) {
                        btn_register.setEnabled(true);
                    } else {
                        btn_register.setEnabled(false);
                    }
                } else {
                    pw_cancel.setVisibility(View.INVISIBLE);
                    btn_register.setEnabled(false);
                }
            }
        });

        pw_cancel = (LinearLayout) findViewById(R.id.pw_cancel);
        pw_cancel.setOnClickListener(this);

        btn_eye = (LinearLayout) findViewById(R.id.btn_eye);
        btn_eye.setOnClickListener(this);

        code_edit = (EditText) findViewById(R.id.code_edit);
        code_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(code_edit.getText())) {
                    if (!TextUtils.isEmpty(id_edit.getText()) && !TextUtils.isEmpty(pw_edit.getText())) {
                        btn_register.setEnabled(true);
                    } else {
                        btn_register.setEnabled(false);
                    }
                } else {
                    btn_register.setEnabled(false);
                }
            }
        });
        get_Code_btn = (Button) findViewById(R.id.btn_getCode);
        get_Code_btn.setOnClickListener(this);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        user_protocol = (TextView) findViewById(R.id.user_protocol);
        user_protocol.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                super.onBackPressed();
                break;

            case R.id.id_cancel:
                id_edit.setText("");
                break;

            case R.id.pw_cancel:
                pw_edit.setText("");
                break;

            case R.id.btn_eye:
                if (!isEye) {
                    pw_edit.getInputType();
                    pw_edit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    btn_eye.setBackgroundResource(R.drawable.icon_eye_f);
                    isEye = true;
                } else {
                    pw_edit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    btn_eye.setBackgroundResource(R.drawable.icon_eye_n);
                    isEye = false;
                }
                break;

            case R.id.btn_getCode:
                getCode();
                break;

            case R.id.btn_register:
                register();
                break;

            case R.id.user_protocol:
                break;
        }
    }

    private void register() {
        String code = code_edit.getText().toString();
        String userphone = id_edit.getText().toString();
        String password = pw_edit.getText().toString();
        String username = userphone;

        LoadDialog.actionStart(this);

        new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LoginActivity.actionStart(RegisterActivity.this);
            }
        }.start();
    }

    public void getCode() {
        //get_Code_btn.setEnabled(false);
        Toast.makeText(getApplicationContext(), "服务器连接异常", Toast.LENGTH_LONG).show();
    }
}
