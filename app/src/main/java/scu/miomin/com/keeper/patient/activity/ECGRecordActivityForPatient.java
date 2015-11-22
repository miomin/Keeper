package scu.miomin.com.keeper.patient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.bean.DateBean;
import scu.miomin.com.keeper.bean.ECGRecordBean;
import scu.miomin.com.keeper.bean.HealthyDescribeByMyselfBean;
import scu.miomin.com.keeper.ecgsave.SaveECGUtils;
import scu.miomin.com.keeper.patient.adapter.ECGRecordAdapterForPatient;
import scu.miomin.com.keeper.patient.controller.PatientController;


/**
 * 描述:病人端的健康档案 创建日期:2015/11/11
 *
 * @author 莫绪旻
 */
public class ECGRecordActivityForPatient extends BaseActivity {

    private List<File> datas = new ArrayList<File>();
    File[] files = null;
    private PullToRefreshListView lvecgrecord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_healthyrecord_patient);

        files = SaveECGUtils.getLogDir(this).listFiles();
        Collections.addAll(datas, files);

        // 初始化控件
        initView();
        // 设置监听器
        setListener();

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ECGRecordActivityForPatient.class);
        context.startActivity(intent);
    }

    private void initView() {
        lvecgrecord = (PullToRefreshListView) findViewById(R.id.lvecgrecord);
    }

    private void setListener() {
        // 创建适配器对象
        PatientController.setEcgRecordAdapterForPatient(new ECGRecordAdapterForPatient(ECGRecordActivityForPatient.this));
        // 将ListView与适配器关联
        lvecgrecord.setAdapter(PatientController.getEcgRecordAdapterForPatient());

        ECGRecordBean ecgRecord = null;

        for (int i = 0; i < files.length; i++) {

            String filename = files[i].getName().substring(5, 19);

            String yearStr = filename.substring(0, 4);

            String monthStr = filename.substring(4, 6);
            if (monthStr.substring(0, 1).equals(0)) {
                monthStr = monthStr.substring(1, 2);
            }

            String dayStr = filename.substring(6, 8);
            if (dayStr.substring(0, 1).equals(0)) {
                dayStr = dayStr.substring(1, 2);
            }

            String hourStr = filename.substring(8, 10);
            if (hourStr.substring(0, 1).equals(0)) {
                hourStr = hourStr.substring(1, 2);
            }

            String minuteStr = filename.substring(10, 12);
            if (minuteStr.substring(0, 1).equals(0)) {
                minuteStr = minuteStr.substring(1, 2);
            }

            String secondStr = filename.substring(12, 14);
            if (monthStr.substring(0, 1).equals(0)) {
                secondStr = secondStr.substring(1, 2);
            }

            int year = Integer.parseInt(yearStr);
            int month = Integer.parseInt(monthStr);
            int day = Integer.parseInt(dayStr);
            int hour = Integer.parseInt(hourStr);
            int minute = Integer.parseInt(minuteStr);
            int second = Integer.parseInt(secondStr);

            boolean isAtPhone;
            if (i % 2 == 0)
                isAtPhone = true;
            else
                isAtPhone = false;

            ecgRecord = new ECGRecordBean(new DateBean(year, month, day, hour, minute, second),
                    files[i].getName(), new HealthyDescribeByMyselfBean(), isAtPhone);
            PatientController.getEcgRecordAdapterForPatient().add(ecgRecord);
        }

        // 设置下拉刷新监听器
        lvecgrecord.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
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
                        lvecgrecord.onRefreshComplete();
                    }
                }.execute();
            }
        });

        // 设置点击事件监听器
        lvecgrecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ECGHistoryActivityForPatient.actionStart(ECGRecordActivityForPatient.this, datas.get(position - 1));
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, Menu.FIRST, Menu.NONE, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 得到当前被选中的item信息
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        switch (item.getItemId()) {
            case Menu.FIRST:
                File file = datas.get(menuInfo.position);
                if (file.exists()) {
                    file.delete();
                }

                datas.remove(file);
                PatientController.getEcgRecordAdapterForPatient().delete(menuInfo.position);

                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
}
