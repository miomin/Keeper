package scu.miomin.com.keeper.patient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;

/**
 * Created by miomin on 15/11/12.
 */
public class HealthyDescribeByMyselfActivityForPatient extends BaseActivity {

    ImageView ivshifouxiongmen, ivshifouxinhuang,
            ivshifouhuxikunnan, ivshifoutouyun,
            ivshifouquanshenfali, ivshuijiaoshishifoubiemen;

    boolean xiongmen = false, xinhuang = false,
            huxikunnan = false, touyun = false,
            quanshenfali = false, shuijiaobiemen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_describe_myself_patient);
        initView();
    }

    private void initView() {
        ivshifouxiongmen = (ImageView) findViewById(R.id.ivshifouxiongmen);
        ivshifouxinhuang = (ImageView) findViewById(R.id.ivshifouxinhuang);
        ivshifouhuxikunnan = (ImageView) findViewById(R.id.ivshifouhuxikunnan);
        ivshifoutouyun = (ImageView) findViewById(R.id.ivshifoutouyun);
        ivshifouquanshenfali = (ImageView) findViewById(R.id.ivshifouquanshenfali);
        ivshuijiaoshishifoubiemen = (ImageView) findViewById(R.id.ivshuijiaoshishifoubiemen);
    }

    public static void actionStart(Context context) {

        Intent intent = new Intent(context, HealthyDescribeByMyselfActivityForPatient.class);
        context.startActivity(intent);
    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void openECGActivity(View view) {
        finish();
        ECGActivity.actionStart(this);
    }

    private void refreshCheckBox() {
        if (xiongmen)
            ivshifouxiongmen.setImageResource(R.drawable.check_box_red_select);
        else
            ivshifouxiongmen.setImageResource(R.drawable.check_box_red_normal);

        if (xinhuang)
            ivshifouxinhuang.setImageResource(R.drawable.check_box_red_select);
        else
            ivshifouxinhuang.setImageResource(R.drawable.check_box_red_normal);

        if (huxikunnan)
            ivshifouhuxikunnan.setImageResource(R.drawable.check_box_red_select);
        else
            ivshifouhuxikunnan.setImageResource(R.drawable.check_box_red_normal);

        if (touyun)
            ivshifoutouyun.setImageResource(R.drawable.check_box_red_select);
        else
            ivshifoutouyun.setImageResource(R.drawable.check_box_red_normal);

        if (quanshenfali)
            ivshifouquanshenfali.setImageResource(R.drawable.check_box_red_select);
        else
            ivshifouquanshenfali.setImageResource(R.drawable.check_box_red_normal);

        if (shuijiaobiemen)
            ivshuijiaoshishifoubiemen.setImageResource(R.drawable.check_box_red_select);
        else
            ivshuijiaoshishifoubiemen.setImageResource(R.drawable.check_box_red_normal);
    }

    public void xiongmen(View view) {
        if (xiongmen)
            xiongmen = false;
        else
            xiongmen = true;

        refreshCheckBox();
    }

    public void xinhuang(View view) {
        if (xinhuang)
            xinhuang = false;
        else
            xinhuang = true;

        refreshCheckBox();
    }

    public void huxikunnan(View view) {
        if (huxikunnan)
            huxikunnan = false;
        else
            huxikunnan = true;

        refreshCheckBox();
    }

    public void touyun(View view) {
        if (touyun)
            touyun = false;
        else
            touyun = true;

        refreshCheckBox();
    }

    public void quanshenfali(View view) {
        if (quanshenfali)
            quanshenfali = false;
        else
            quanshenfali = true;

        refreshCheckBox();
    }

    public void shuijiaobiemen(View view) {
        if (shuijiaobiemen)
            shuijiaobiemen = false;
        else
            shuijiaobiemen = true;

        refreshCheckBox();
    }
}
