package scu.miomin.com.keeper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.bean.DoctorBean;
import scu.miomin.com.keeper.patient.activity.ECGRecordActivityForPatient;
import scu.miomin.com.keeper.resource.MyLoader;

/**
 * Created by miomin on 15/11/13.
 */
public class DoctorInfoActivity extends BaseActivity {

    ImageView ivDoctorHead;
    TextView doctorName, tvProfessional, tvAdministrative, tvHospital, tvIntroduction;

    DoctorBean doctorBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);

        doctorBean = (DoctorBean) getIntent().getSerializableExtra("doctorBean");

        ivDoctorHead = (ImageView) findViewById(R.id.ivDoctorHead);
        doctorName = (TextView) findViewById(R.id.doctorName);
        tvProfessional = (TextView) findViewById(R.id.tvProfessional);
        tvAdministrative = (TextView) findViewById(R.id.tvAdministrative);
        tvHospital = (TextView) findViewById(R.id.tvHospital);
        tvIntroduction = (TextView) findViewById(R.id.tvIntroduction);

        MyLoader.dispalyFromAssets(doctorBean.getHeadUrl(), ivDoctorHead);
        doctorName.setText(doctorBean.getName());
        tvProfessional.setText(doctorBean.getProfessional());
        tvAdministrative.setText(doctorBean.getAdministrative());
        tvHospital.setText(doctorBean.getHospitalBean().getName());
        tvIntroduction.setText(doctorBean.getIntroduction());
    }


    public void back(View view) {
        super.onBackPressed();
    }

    public static void actionStart(Context context, DoctorBean doctorBean) {
        Intent intent = new Intent(context, DoctorInfoActivity.class);
        intent.putExtra("doctorBean", doctorBean);
        context.startActivity(intent);
    }

    public void openECGRecordActivity(View view) {
        ECGRecordActivityForPatient.actionStart(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
