package scu.miomin.com.keeper.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.constant.LoginSyncStatus;

import scu.miomin.com.keeper.Enum.AdministrativeEnum;
import scu.miomin.com.keeper.Enum.ProfessionalEnum;
import scu.miomin.com.keeper.Enum.SexEnum;
import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.application.ActivityCollector;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.bean.BirthdayBean;
import scu.miomin.com.keeper.bean.DoctorBean;
import scu.miomin.com.keeper.bean.HospitalBean;
import scu.miomin.com.keeper.bean.MyLocationBean;
import scu.miomin.com.keeper.bean.PatientBean;
import scu.miomin.com.keeper.controller.Controller;
import scu.miomin.com.keeper.dialog.LoadDialog;
import scu.miomin.com.keeper.doctor.activity.MainKeeperForDoctor;
import scu.miomin.com.keeper.doctor.controller.DoctorController;
import scu.miomin.com.keeper.patient.activity.MainKeeperForPatient;
import scu.miomin.com.keeper.patient.controller.PatientController;

/**
 * 描述:登录 创建日期:2015/7/23
 *
 * @author 应俊康
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btn_back;
    private TextView btn_register;

    private EditText id_edit;
    private EditText pw_edit;

    private LinearLayout id_cancel;
    private LinearLayout pw_cancel;
    private LinearLayout btn_eye;

    private Button login_btn;

    private TextView erro_info;
    private TextView forget_pw;

    private LinearLayout erro_layout;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initData();
    }

    private void initData() {
        SharedPreferences sp = getSharedPreferences("logininfo", MODE_PRIVATE);

        if (sp != null) {
            // 从本地读取上次登录成功时保存的用户登录信息
            String phonenumber = sp.getString("phonenumber", null);
            if (phonenumber != null) {
                id_edit.setText(phonenumber);
            }
        }
    }

    private void initView() {

        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        btn_register = (TextView) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

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
                    if (!TextUtils.isEmpty(pw_edit.getText())) {
                        login_btn.setEnabled(true);
                    } else {
                        login_btn.setEnabled(false);
                    }
                } else {
                    id_cancel.setVisibility(View.INVISIBLE);
                    login_btn.setEnabled(false);
                }
            }
        });
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
                    if (!TextUtils.isEmpty(id_edit.getText())) {
                        login_btn.setEnabled(true);
                    } else {
                        login_btn.setEnabled(false);
                    }
                } else {
                    pw_cancel.setVisibility(View.INVISIBLE);
                    login_btn.setEnabled(false);
                }
            }
        });

        id_cancel = (LinearLayout) findViewById(R.id.id_cancel);
        id_cancel.setOnClickListener(this);

        pw_cancel = (LinearLayout) findViewById(R.id.pw_cancel);
        pw_cancel.setOnClickListener(this);

        btn_eye = (LinearLayout) findViewById(R.id.btn_eye);
        btn_eye.setOnClickListener(this);

        login_btn = (Button) findViewById(R.id.btn_login);
        login_btn.setOnClickListener(this);

        login_btn.setEnabled(false);
        erro_info = (TextView) findViewById(R.id.erro_info);

        forget_pw = (TextView) findViewById(R.id.forget_pw);
        forget_pw.setOnClickListener(this);

        erro_layout = (LinearLayout) findViewById(R.id.erro_layout);
    }

    private boolean isEye = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                break;

            case R.id.btn_register:
                register();
                break;

            case R.id.id_cancel:
                id_edit.setText("");
                id_cancel.setVisibility(View.INVISIBLE);
                break;

            case R.id.pw_cancel:
                pw_edit.setText("");
                pw_cancel.setVisibility(View.INVISIBLE);
                break;

            case R.id.btn_login:
                login();
                break;

            case R.id.forget_pw:
                break;

            case R.id.btn_eye:
                if (!isEye) {
                    pw_edit.getInputType();
                    pw_edit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //btn_eye.setBackgroundResource(R.drawable.icon_eye_f);
                    isEye = true;
                } else {
                    pw_edit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //btn_eye.setBackgroundResource(R.drawable.icon_eye_n);
                    isEye = false;
                }
                break;
        }
    }

    private void login() {

        startActivity(new Intent(LoginActivity.this, LoadDialog.class));

        String phonenumber = id_edit.getText().toString().toLowerCase();
        String password = pw_edit.getText().toString().toLowerCase();
        LoginInfo loginInfo = new LoginInfo(phonenumber, password);

        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {

                        if (loginInfo.getAccount().length() == 11) {
                            startActivity(new Intent(LoginActivity.this, MainKeeperForPatient.class));
                            PatientBean patientBean = new PatientBean(loginInfo.getAccount(), loginInfo.getToken(),
                                    "莫绪旻", SexEnum.MAN, new BirthdayBean(1993, 8, 15), null, 171, 57);
                            Controller.setCurrentUser(patientBean);
                        } else {
                            startActivity(new Intent(LoginActivity.this, MainKeeperForDoctor.class));
                            DoctorBean doctorBean = new DoctorBean(loginInfo.getAccount(), loginInfo.getToken(),
                                    "王鹏", SexEnum.MAN, new BirthdayBean(1987, 1, 1), null, AdministrativeEnum.NEIKE,
                                    new HospitalBean("四川大学华西医院", "四川省", "成都市", "锦江区", new MyLocationBean(0, 0)),
                                    ProfessionalEnum.FUZHURENYISHI, "擅长心脑血管病及糖尿病的治疗，有丰富的临床经验", false);
                            Controller.setCurrentUser(doctorBean);
                        }

                        // 保存登录信息到SharedPerences
                        saveLoginInfo(loginInfo);

                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();

                        if (LoadDialog.instance != null)
                            LoadDialog.instance.finish();
                    }

                    @Override
                    public void onFailed(int i) {
                        Toast.makeText(getApplicationContext(), "登录失败" + " - " + i, Toast.LENGTH_LONG).show();

                        if (LoadDialog.instance != null)
                            LoadDialog.instance.finish();
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "登录异常", Toast.LENGTH_LONG).show();

                        if (LoadDialog.instance != null)
                            LoadDialog.instance.finish();
                    }
                };

        NIMClient.getService(AuthService.class).login(loginInfo)
                .setCallback(callback);

        NIMClient.getService(AuthServiceObserver.class).observeLoginSyncDataStatus(new Observer<LoginSyncStatus>() {
            @Override
            public void onEvent(LoginSyncStatus status) {
                if (status == LoginSyncStatus.BEGIN_SYNC) {
                    Log.i("miomin", "login sync data begin");
                } else if (status == LoginSyncStatus.SYNC_COMPLETED) {
                    Log.i("miomin", "login sync data completed");
                }
            }
        }, true);
    }

    private void register() {
        RegisterActivity.actionStart(this);
    }

    // 监听到返回键时，退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ActivityCollector.finishAll();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 保存登录信息到SharedPerences
    public void saveLoginInfo(LoginInfo info) {
        SharedPreferences.Editor editor = getSharedPreferences("logininfo", MODE_PRIVATE).edit();
        editor.putString("phonenumber", info.getAccount());
        editor.putString("password", info.getToken());
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PatientController.finish();
        DoctorController.finish();
    }

}
