package scu.miomin.com.keeper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.adapter.TreatmentFollowupAdapter;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.bean.TreatmentFollowupBean;

/**
 * 描述:绘制心电图的界面 创建日期:2015/5/23
 *
 * @author 莫绪旻
 */
public class TreatmentFollowupActivity extends BaseActivity {

    private PullToRefreshListView lvTreatmentFollowup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_treatment_followup);
        initView();
        initAdapter();
        initListener();
    }

    private void initView() {
        lvTreatmentFollowup = (PullToRefreshListView) findViewById(R.id.lvTreatmentFollowup);
    }

    private void initAdapter() {
        // 创建适配器对象
        TreatmentFollowupAdapter treatmentFollowupAdapter = new TreatmentFollowupAdapter(new ArrayList<TreatmentFollowupBean>(), this);
        // 将ListView与适配器关联
        lvTreatmentFollowup.setAdapter(treatmentFollowupAdapter);

        TreatmentFollowupBean treatmentFollowupBean;

        treatmentFollowupBean = new TreatmentFollowupBean("2015-11-24");
        treatmentFollowupAdapter.add(treatmentFollowupBean);

        treatmentFollowupBean = new TreatmentFollowupBean("2015-11-24");
        treatmentFollowupAdapter.add(treatmentFollowupBean);

        treatmentFollowupBean = new TreatmentFollowupBean("2015-11-26");
        treatmentFollowupAdapter.add(treatmentFollowupBean);

        treatmentFollowupBean = new TreatmentFollowupBean("2015-11-27");
        treatmentFollowupAdapter.add(treatmentFollowupBean);
    }

    private void initListener() {
        lvTreatmentFollowup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("miomin", "" + position);

                if (position == 1)
                    return;

                FollowupActivity.actionStart(TreatmentFollowupActivity.this);
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

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TreatmentFollowupActivity.class);
        context.startActivity(intent);
    }


    public void back(View view) {
        super.onBackPressed();
    }

    public void openAddTreatmentActivity(View view) {
        AddFollowupActivity.actionStart(this);
    }
}
