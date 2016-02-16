package scu.miomin.com.keeper.patient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.patient.adapter.TreatmentListAdapterForPatient;
import scu.miomin.com.keeper.resource.UserResource;

/**
 * 描述:病人端诊后随访界面 创建日期:2015/11/22
 *
 * @author 莫绪旻
 */
public class TreatmentListActivityForPatient extends BaseActivity {

    private PullToRefreshListView lvTreatmentFollowupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_list_patient);

        initView();
        initAdapter();
        setListener();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TreatmentListActivityForPatient.class);
        context.startActivity(intent);
    }

    private void initView() {
        lvTreatmentFollowupList = (PullToRefreshListView) findViewById(R.id.lvTreatment);
    }

    private void initAdapter() {
        // 创建适配器对象
        TreatmentListAdapterForPatient treatmentFollowupAdapterForPatient =
                new TreatmentListAdapterForPatient(this, UserResource.getTreatmentArray());
        // 将ListView与适配器关联
        lvTreatmentFollowupList.setAdapter(treatmentFollowupAdapterForPatient);
    }

    private void setListener() {
        // 设置下拉刷新监听器
        lvTreatmentFollowupList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
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
                        lvTreatmentFollowupList.onRefreshComplete();
                    }
                }.execute();
            }
        });

        lvTreatmentFollowupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TreatmentInfoActivityForPatient.actionStart(TreatmentListActivityForPatient.this,
                        UserResource.getTreatmentArray().get(position - 1));
            }
        });
    }

    public void back(View view) {
        super.onBackPressed();
    }

}
