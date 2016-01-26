package scu.miomin.com.keeper.patient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import scu.miomin.com.keeper.Enum.UserTypeEnum;
import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.basedialog.BaseDialog;
import scu.miomin.com.keeper.bean.ECGRecordBean;
import scu.miomin.com.keeper.bean.HealthyDescribeByPatientBean;
import scu.miomin.com.keeper.controller.Controller;
import scu.miomin.com.keeper.dialog.LoadDialog;
import scu.miomin.com.keeper.ecgsave.ECGDirSaveUtil;
import scu.miomin.com.keeper.patient.adapter.ECGRecordAdapterForPatient;
import scu.miomin.com.keeper.patient.controller.PatientController;
import scu.miomin.com.keeper.util.FileUploadUtil;


/**
 * 描述:病人端的健康档案 创建日期:2015/11/11
 *
 * @author 莫绪旻
 */
public class ECGRecordActivityForPatient extends BaseActivity {

    private PullToRefreshListView lvecgrecord;

    private ArrayList<String> ecgfileNameList;

    private static final int PUSHSUCCEED = 1;
    private static final int PUSHFAILD = -1;

    private static String uploadFilename = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthyrecord_patient);
        // 初始化数据
        initData();
        // 初始化控件
        initView();
        // 设置监听器
        setListener();
    }

    // 初始化数据
    private void initData() {
        ecgfileNameList = ECGDirSaveUtil.readRecordDir(this);
    }

    // 监听上传心电数据状态的handler
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == PUSHSUCCEED) {
                // 上传的项目的下标
                int index = msg.arg1;

                if (LoadDialog.instance != null) {
                    LoadDialog.instance.finish();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.pullsucceed), Toast.LENGTH_SHORT).show();
                }

                if (index >= 0) {
                    PatientController.getEcgRecordAdapterForPatient().pushSucceed(index);
                }
            }
        }
    };

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

        for (int i = 0; i < ecgfileNameList.size(); i++) {

            String temp1[] = ecgfileNameList.get(i).split("_");

            String recordDate = temp1[2];

            String yearStr = recordDate.substring(0, 4);

            String monthStr = recordDate.substring(4, 6);
            if (monthStr.substring(0, 1).equals(0)) {
                monthStr = monthStr.substring(1, 2);
            }

            String dayStr = recordDate.substring(6, 8);
            if (dayStr.substring(0, 1).equals(0)) {
                dayStr = dayStr.substring(1, 2);
            }

            String hourStr = recordDate.substring(8, 10);
            if (hourStr.substring(0, 1).equals(0)) {
                hourStr = hourStr.substring(1, 2);
            }

            String minuteStr = recordDate.substring(10, 12);
            if (minuteStr.substring(0, 1).equals(0)) {
                minuteStr = minuteStr.substring(1, 2);
            }

            int year = Integer.parseInt(yearStr);
            int month = Integer.parseInt(monthStr);
            int day = Integer.parseInt(dayStr);
            int hour = Integer.parseInt(hourStr);
            int minute = Integer.parseInt(minuteStr);

            boolean isAtPhone = false;

            if (Controller.getCurrentUser().getUserType() == UserTypeEnum.PATIENT) {
                if (i < 2)
                    isAtPhone = false;
                else
                    isAtPhone = true;
            } else if (Controller.getCurrentUser().getUserType() == UserTypeEnum.DOCTOR) {
                isAtPhone = true;
            }

            ecgRecord = new ECGRecordBean(year + "-" + month + "-" + day + " " + hour + ":" + minute,
                    ecgfileNameList.get(i), new HealthyDescribeByPatientBean(false, false, false, false, false, false), isAtPhone);
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
                uploadFilename = ecgfileNameList.get(position - 1);
                BaseDialog.actionStartActivity(1, position, ECGRecordActivityForPatient.this,
                        "提示", "是否要将本次心电监测数据上传到云端？上传后可供医生查看",
                        "否", "是");
            }
        });

        lvecgrecord.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openOptionsMenu();
                return false;
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
//        // 得到当前被选中的item信息
//        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
//                .getMenuInfo();
//
//        switch (item.getItemId()) {
//            case Menu.FIRST:
//                File file = datas.get(menuInfo.position);
//                if (file.exists()) {
//                    file.delete();
//                }
//
//                datas.remove(file);
//                PatientController.getEcgRecordAdapterForPatient().delete(menuInfo.position);
//
//                break;
//            default:
//                return super.onContextItemSelected(item);
//        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    int returnedChoose = data.getIntExtra("returnedChoose", -1);
                    final int index = data.getIntExtra("index", -1);

                    if (returnedChoose == 1) {

                    } else if (returnedChoose == 2) {
                        LoadDialog.actionStart(this);

                        new Thread() {
                            public void run() {
                                try {
                                    sleep(500);

                                    if (uploadFilename == null) {
                                        return;
                                    }
                                    FileUploadUtil.uploadFile(uploadFilename, "http://192.168.252.1:5000/upload",
                                            ECGRecordActivityForPatient.this);
                                    Message message;
                                    message = Message.obtain();
                                    message.what = PUSHSUCCEED;
                                    message.arg1 = index;
                                    handler.sendMessage(message);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                    } else {
                        Toast.makeText(this, "返回值异常", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
        }
    }

    public void back(View view) {
        super.onBackPressed();
    }
}
