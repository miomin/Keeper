package scu.miomin.com.keeper.patient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.activity.TrendLineChartActivity;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.bean.TreatmentFollowupBean;
import scu.miomin.com.keeper.util.ToastUtils;

/**
 * 描述:绘制心电图的界面 创建日期:2015/5/23
 *
 * @author 莫绪旻
 */
public class FollowupInfoActivity extends BaseActivity {

    private TreatmentFollowupBean treatmentFollowupBean;

    private ArrayList<TreatmentFollowupBean> arrayTreatmentFollowup;

    private ArrayList<Integer> datas = new ArrayList<Integer>();

    TextView tvRusuantuoqingmei, tvgucaozhuananmei, tvgubingzhuananmei, tvlingsuanjisuanjimei, tvlingsuanjisuanjimeitonggongmei,
            tvjihongdanbai, tvjigaidanbai, tvWaizhouxuebaixibaozongshu, tvzhongxinglixibaobilv;

    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;

    TextView tvFllowupdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followup_info);
        treatmentFollowupBean = (TreatmentFollowupBean) getIntent().getSerializableExtra("treatmentFollowupBean");
        arrayTreatmentFollowup = (ArrayList<TreatmentFollowupBean>) getIntent().getSerializableExtra("arrayTreatmentFollowup");

        if (treatmentFollowupBean == null || arrayTreatmentFollowup == null) {
            ToastUtils.showToast(this, "数据加载失败");
            finish();
            return;
        }
        initView();
        initData();
    }

    private void initView() {
        tvRusuantuoqingmei = (TextView) findViewById(R.id.tvRusuantuoqingmei);
        tvgucaozhuananmei = (TextView) findViewById(R.id.tvgucaozhuananmei);
        tvgubingzhuananmei = (TextView) findViewById(R.id.tvgubingzhuananmei);
        tvlingsuanjisuanjimei = (TextView) findViewById(R.id.tvlingsuanjisuanjimei);
        tvlingsuanjisuanjimeitonggongmei = (TextView) findViewById(R.id.tvlingsuanjisuanjimeitonggongmei);
        tvjihongdanbai = (TextView) findViewById(R.id.tvjihongdanbai);
        tvjigaidanbai = (TextView) findViewById(R.id.tvjigaidanbai);
        tvWaizhouxuebaixibaozongshu = (TextView) findViewById(R.id.tvWaizhouxuebaixibaozongshu);
        tvzhongxinglixibaobilv = (TextView) findViewById(R.id.tvzhongxinglixibaobilv);

        tvFllowupdata = (TextView) findViewById(R.id.tvFllowupdata);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);
        tv9 = (TextView) findViewById(R.id.tv9);
    }

    private void initData() {
        tvRusuantuoqingmei.setText("" + treatmentFollowupBean.getRUSUANTUOQINGMEI());
        tvgucaozhuananmei.setText("" + treatmentFollowupBean.getGUCAOZHUANANMEI());
        tvgubingzhuananmei.setText("" + treatmentFollowupBean.getGUBINGZHUANANMEI());
        tvlingsuanjisuanjimei.setText("" + treatmentFollowupBean.getLINGSUANJISUANJIMEI());
        tvlingsuanjisuanjimeitonggongmei.setText("" + treatmentFollowupBean.getLINGSUANJISUANJIMEITONGGONGMEI());
        tvjihongdanbai.setText("" + treatmentFollowupBean.getJIHONGDANBAI());
        tvjigaidanbai.setText("" + treatmentFollowupBean.getJIGAIDANBAI());
        tvWaizhouxuebaixibaozongshu.setText("" + treatmentFollowupBean.getWAIZHOUXUEHONGXIBAOZONGSHU());
        tvzhongxinglixibaobilv.setText("" + treatmentFollowupBean.getZHONGXINGLIXIBAOBILV());

        tvFllowupdata.setText(treatmentFollowupBean.getDate());

        arrayTreatmentFollowup = (ArrayList<TreatmentFollowupBean>) getIntent().getSerializableExtra("arrayTreatmentFollowup");
    }

    public static void actionStart(Context context, TreatmentFollowupBean treatmentFollowupBean,
                                   ArrayList<TreatmentFollowupBean> arrayTreatmentFollowup) {
        Intent intent = new Intent(context, FollowupInfoActivity.class);
        intent.putExtra("treatmentFollowupBean", treatmentFollowupBean);
        intent.putExtra("arrayTreatmentFollowup", arrayTreatmentFollowup);

        context.startActivity(intent);
    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void checkRusuantuoqingmei(View view) {

        for (int i = arrayTreatmentFollowup.size() - 1; i >= 0; i--) {

            datas.add((int) arrayTreatmentFollowup.get(i).getRUSUANTUOQINGMEI());

            if (arrayTreatmentFollowup.size() - i == 7) {
                break;
            }
        }

        TrendLineChartActivity.actionStart(this, tv1.getText().toString(), datas);
        datas.clear();
    }

    public void checkGucaozhuananmei(View view) {
        for (int i = arrayTreatmentFollowup.size() - 1; i >= 0; i--) {

            datas.add((int) arrayTreatmentFollowup.get(i).getGUCAOZHUANANMEI());

            if (arrayTreatmentFollowup.size() - i == 7) {
                break;
            }
        }

        TrendLineChartActivity.actionStart(this, tv2.getText().toString(), datas);
        datas.clear();
    }

    public void checkGubingzhuananmei(View view) {
        for (int i = arrayTreatmentFollowup.size() - 1; i >= 0; i--) {

            datas.add((int) arrayTreatmentFollowup.get(i).getGUBINGZHUANANMEI());

            if (arrayTreatmentFollowup.size() - i == 7) {
                break;
            }
        }

        TrendLineChartActivity.actionStart(this, tv3.getText().toString(), datas);
        datas.clear();
    }

    public void checkLingsuanjisuanjimei(View view) {
        for (int i = arrayTreatmentFollowup.size() - 1; i >= 0; i--) {

            datas.add((int) arrayTreatmentFollowup.get(i).getLINGSUANJISUANJIMEI());

            if (arrayTreatmentFollowup.size() - i == 7) {
                break;
            }
        }

        TrendLineChartActivity.actionStart(this, tv4.getText().toString(), datas);
        datas.clear();
    }

    public void checkLingsuanjisuanjimeitonggongmei(View view) {
        for (int i = arrayTreatmentFollowup.size() - 1; i >= 0; i--) {

            datas.add((int) arrayTreatmentFollowup.get(i).getLINGSUANJISUANJIMEITONGGONGMEI());

            if (arrayTreatmentFollowup.size() - i == 7) {
                break;
            }
        }

        TrendLineChartActivity.actionStart(this, tv5.getText().toString(), datas);
        datas.clear();
    }

    public void checkJihongdanbai(View view) {
        for (int i = arrayTreatmentFollowup.size() - 1; i >= 0; i--) {

            datas.add((int) arrayTreatmentFollowup.get(i).getJIHONGDANBAI());

            if (arrayTreatmentFollowup.size() - i == 7) {
                break;
            }
        }

        TrendLineChartActivity.actionStart(this, tv6.getText().toString(), datas);
        datas.clear();
    }

    public void checkJigaidanbai(View view) {
        for (int i = arrayTreatmentFollowup.size() - 1; i >= 0; i--) {

            datas.add((int) arrayTreatmentFollowup.get(i).getJIGAIDANBAI());

            if (arrayTreatmentFollowup.size() - i == 7) {
                break;
            }
        }

        TrendLineChartActivity.actionStart(this, tv7.getText().toString(), datas);
        datas.clear();
    }

    public void checkWaizhouxuebaixibaozongshu(View view) {
        for (int i = arrayTreatmentFollowup.size() - 1; i >= 0; i--) {

            datas.add((int) arrayTreatmentFollowup.get(i).getWAIZHOUXUEHONGXIBAOZONGSHU());

            if (arrayTreatmentFollowup.size() - i == 7) {
                break;
            }
        }

        TrendLineChartActivity.actionStart(this, tv8.getText().toString(), datas);
        datas.clear();
    }

    public void checkZhongxinglixibaibilv(View view) {
        for (int i = arrayTreatmentFollowup.size() - 1; i >= 0; i--) {

            datas.add((int) arrayTreatmentFollowup.get(i).getZHONGXINGLIXIBAOBILV());

            if (arrayTreatmentFollowup.size() - i == 7) {
                break;
            }
        }

        TrendLineChartActivity.actionStart(this, tv9.getText().toString(), datas);
        datas.clear();
    }
}
