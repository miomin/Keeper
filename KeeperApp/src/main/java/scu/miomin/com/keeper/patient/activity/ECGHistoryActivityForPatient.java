package scu.miomin.com.keeper.patient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.myview.ECGSurfaceView;

/**
 * 描述:绘制历史心电图的界面 创建日期:2015/5/23
 *
 * @author 莫绪旻
 */
public class ECGHistoryActivityForPatient extends BaseActivity {

    private ECGSurfaceView ecg_view;

    private List<Integer> list; // 存储原始数据
    private boolean isStop = false;

    public static void actionStart(Context context, File file) {
        Intent intent = new Intent(context, ECGHistoryActivityForPatient.class);
        intent.putExtra("file", file);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecg_history_patient);

        ecg_view = (ECGSurfaceView) findViewById(R.id.ecg_view);

        File file = (File) getIntent().getSerializableExtra("file");

        if (file.exists() && file.isFile()) {

            list = Collections.synchronizedList(new LinkedList<Integer>());// 初始化ArrayList
            new Thread(new DrawHistory(file)).start();
        }

    }

    class DrawHistory implements Runnable {

        private BufferedReader bufferedReader;
        private File file;

        public DrawHistory(File file) {
            this.file = file;
        }

        @Override
        public void run() {

            try {
                InputStreamReader inputStreamReader = new InputStreamReader(
                        new FileInputStream(file));
                bufferedReader = new BufferedReader(inputStreamReader);
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {

                    if (isStop) {
                        break;
                    }

                    int value = Integer.parseInt(text);
                    Log.i("miomin", value + "");
                    list.add(value);
                }

                if (isStop) {
                    return;
                }

                ecg_view.timeDelayedDraw(list);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        isStop = false;
    }

    public void back(View view) {
        super.onBackPressed();
    }

}
