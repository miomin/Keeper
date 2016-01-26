package scu.miomin.com.keeper.patient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.activity.AddFollowupActivity;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.bean.TreatmentBean;
import scu.miomin.com.keeper.patient.adapter.TreatmentFollowupAdapter;
import scu.miomin.com.keeper.util.ToastUtils;

/**
 * 描述:绘制心电图的界面 创建日期:2015/5/23
 *
 * @author 莫绪旻
 */
public class TreatmentInfoActivityForPatient extends BaseActivity {

    private PullToRefreshListView lvTreatmentFollowup;

    private TreatmentBean treatmentBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_info);
        initView();
        initData();
        initAdapter();
        initListener();
    }

    private void initView() {
        lvTreatmentFollowup = (PullToRefreshListView) findViewById(R.id.lvTreatmentFollowup);
    }

    private void initData() {
        Intent intent = getIntent();
        treatmentBean = (TreatmentBean) intent.getSerializableExtra("treatmentBean");

        if (treatmentBean == null) {
            ToastUtils.showToast(this, "加载信息失败");
            return;
        }
    }

    private void initAdapter() {
        // 创建适配器对象
        TreatmentFollowupAdapter treatmentFollowupAdapter = new TreatmentFollowupAdapter(treatmentBean.getTreatmentFollowupList(),
                this, treatmentBean);
        // 将ListView与适配器关联
        lvTreatmentFollowup.setAdapter(treatmentFollowupAdapter);
    }

    private void initListener() {
        lvTreatmentFollowup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("miomin", "" + position);

                if (position == 1)
                    return;

                FollowupInfoActivity.actionStart(TreatmentInfoActivityForPatient.this,
                        treatmentBean.getTreatmentFollowupList().get(position - 2),
                        treatmentBean.getTreatmentFollowupList());
            }
        });

        // 设置下拉刷新监听器
        lvTreatmentFollowup.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {
                    // 异步加载数据
                    @Override
                    protected Void doInBackground(Void... arg0) {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    // 修改UI
                    protected void onPostExecute(Void result) {
                        lvTreatmentFollowup.onRefreshComplete();
                    }
                }.execute();
            }
        });
    }

    public static void actionStart(Context context, TreatmentBean treatmentBean) {
        Intent intent = new Intent(context, TreatmentInfoActivityForPatient.class);
        intent.putExtra("treatmentBean", treatmentBean);
        context.startActivity(intent);
    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void openAddTreatmentActivity(View view) {
        AddFollowupActivity.actionStart(this);
    }
}
