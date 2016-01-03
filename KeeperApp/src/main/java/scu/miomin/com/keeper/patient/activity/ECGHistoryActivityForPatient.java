package scu.miomin.com.keeper.patient.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.application.KeeperApplication;
import scu.miomin.com.keeper.baseactivity.BaseActivity;

/**
 * 描述:绘制历史心电图的界面 创建日期:2015/5/23
 *
 * @author 莫绪旻
 */
public class ECGHistoryActivityForPatient extends BaseActivity implements OnChartValueSelectedListener {

//    private ECGSurfaceView ecg_view;

    private LineChart mChart;
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

//        ecg_view = (ECGSurfaceView) findViewById(R.id.ecg_view);

        File file = (File) getIntent().getSerializableExtra("file");

        if (file.exists() && file.isFile()) {

            list = Collections.synchronizedList(new LinkedList<Integer>());// 初始化ArrayList
            new Thread(new DrawHistory(file)).start();
        }

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundResource(R.color.white);

        // add data
        setData(50, 50);

        mChart.animateY(3000);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // get the legend (only possible after setting data)
//        Legend l = mChart.getLegend();
//
//        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
//        l.setForm(Legend.LegendForm.LINE);
//        l.setTypeface(tf);
//        l.setTextSize(11f);
//        l.setTextColor(R.color.qianhei);
//        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        l.setYOffset(11f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(R.color.qianhei);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setSpaceBetweenLabels(1);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaxValue(350);
        leftAxis.setDrawGridLines(true);
        leftAxis.setStartAtZero(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTypeface(tf);
        rightAxis.setTextColor(Color.WHITE);
        rightAxis.setAxisMaxValue(350);
        rightAxis.setDrawGridLines(false);
        leftAxis.setStartAtZero(true);
    }

    private void setData(int count, int range) {

//        ArrayList<String> xVals = new ArrayList<String>();
//        for (int i = 0; i < count; i++) {
//            xVals.add((i) + "");
//            float mult = (100 + 1);
//            float val = (float) (Math.random() * mult) + 3;// + (float)
//            // ((mult *
//            // 0.1) / 10);
//            xVals.add(new Entry(val, i));
//        }
//
//        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
//
//        yVals2.add(new Entry(280, 0));
//        yVals2.add(new Entry(260, 1));
//        yVals2.add(new Entry(200, 2));
//        yVals2.add(new Entry(220, 3));
//        yVals2.add(new Entry(180, 4));
//        yVals2.add(new Entry(200, 5));
//        yVals2.add(new Entry(190, 6));
//
//        // create a dataset and give it a type
//        LineDataSet set2 = new LineDataSet(yVals2, "乳酸脱氢酶");
//        set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
//        set2.setColor(Color.RED);
//        set2.setCircleColor(Color.RED);
//        set2.setLineWidth(2f);
//        set2.setCircleSize(3f);
//        set2.setFillAlpha(65);
//        set2.setFillColor(Color.RED);
//        set2.setDrawCircleHole(false);
//        set2.setHighLightColor(Color.rgb(244, 117, 117));
//        set2.setDrawFilled(true);
//
//        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
//        dataSets.add(set2);
//
//        // create a data object with the datasets
//        LineData data = new LineData(xVals, dataSets);
//        data.setValueTextColor(R.color.qianhei);
//        data.setValueTextSize(9f);
//
//        // set data
//        mChart.setData(data);

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult) + range;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals1.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, "DataSet 1");
//        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set1.setColor(ColorTemplate.getHoloBlue());
//        set1.setCircleColor(Color.WHITE);
//        set1.setLineWidth(2f);
//        set1.setCircleSize(3f);
//        set1.setFillAlpha(65);
//        set1.setFillColor(ColorTemplate.getHoloBlue());
//        set1.setHighLightColor(Color.rgb(244, 117, 117));
//        set1.setDrawCircleHole(false);
        //set1.setFillFormatter(new MyFillFormatter(0f));
//        set1.setDrawHorizontalHighlightIndicator(false);
//        set1.setVisible(false);
//        set1.setCircleHoleColor(Color.WHITE);

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(Color.RED);
        set1.setCircleColor(Color.RED);
        set1.setLineWidth(2f);
        set1.setCircleSize(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.RED);
        set1.setDrawCircleHole(false);
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawFilled(true);

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + range + 100;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals2.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set2 = new LineDataSet(yVals2, "DataSet 2");
//        set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
//        set2.setColor(Color.RED);
//        set2.setCircleColor(Color.WHITE);
//        set2.setLineWidth(2f);
//        set2.setCircleSize(3f);
//        set2.setFillAlpha(65);
//        set2.setFillColor(Color.RED);
//        set2.setDrawCircleHole(false);
//        set2.setHighLightColor(Color.rgb(244, 117, 117));
        //set2.setFillFormatter(new MyFillFormatter(900f));

        set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set2.setColor(Color.RED);
        set2.setCircleColor(Color.RED);
        set2.setLineWidth(2f);
        set2.setCircleSize(3f);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.RED);
        set2.setDrawCircleHole(false);
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        set2.setDrawFilled(true);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set2);
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
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
                    //list.add(value);

                    for (int i = 0; i < list.size(); i++) {

                        ArrayList<Integer> templist = new ArrayList<>();
                        templist.add(list.get(i));

                        if (templist.size() == 2000) {
                            int sum = 0;
                            for (int j = 0; j < templist.size(); j++) {
                                sum += templist.get(i);
                                int result = (int) (sum / 2000);
                                new KeeperApplication().addValue(result % 300);
                            }
                        }
                    }
                }

                if (isStop) {
                    return;
                }

//                ecg_view.timeDelayedDraw(list);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    bufferedReader.close();
                } catch (Exception  e) {
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

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

}
