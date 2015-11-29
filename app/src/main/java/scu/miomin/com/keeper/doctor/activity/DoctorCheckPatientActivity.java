package scu.miomin.com.keeper.doctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.patient.activity.ECGRecordActivityForPatient;

/**
 * Created by miomin on 15/11/13.
 */
public class DoctorCheckPatientActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_patient_info);
    }

    public void back(View view) {
        super.onBackPressed();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DoctorCheckPatientActivity.class);
        context.startActivity(intent);
    }

    public void openECGRecordActivity(View view) {
        ECGRecordActivityForPatient.actionStart(this);
    }
}
