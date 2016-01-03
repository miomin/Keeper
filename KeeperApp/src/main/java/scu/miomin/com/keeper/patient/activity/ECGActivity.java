package scu.miomin.com.keeper.patient.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.fly2think.btlib.BluetoothService;
import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.application.KeeperApplication;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.ecgsave.SaveECGManager;
import scu.miomin.com.keeper.ecgsave.SaveECGUtils;
import scu.miomin.com.keeper.myview.ECGSurfaceView;
import scu.miomin.com.keeper.util.ToastUtils;

/**
 * 描述:绘制心电图的界面 创建日期:2015/5/23
 *
 * @author 莫绪旻
 */
public class ECGActivity extends BaseActivity implements OnClickListener {

    private SeekBar xSeekBar;

    private final int count = 10;// 每次画图推的数目
    private final int time = 10;// time随着时间增大而增大则比较好

    // Intent 请求类型
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // 连接的蓝牙设备名
    private String mConnectedDeviceName = null;

    TextView tvxinlv = null;

    private int xinlvcount = 0;

    // 蓝牙管理器
    private BluetoothAdapter mBluetoothAdapter = null;
    // 蓝牙连接服务
    private BluetoothService mBTService = null;

    private Button btn_connect, btn_record, btn_find;
    private ECGSurfaceView ecg_view;

    private int[] dataCount1;
    private int[] dataCount;// 存储要推的数据
    private int counterAll = 0;
    private int counterAllPre = 0;
    private int indexTemp = 0;
    private int maxnum = 512; // 绘画的最大点数
    private int flag = 0;

    private boolean isConnect = false;// 蓝牙设否断开的标志位
    private boolean isDraw = false;

    private MyTimerTask task = null;
    private Timer timer = null;

    private boolean isRecord = false;

    private MyCount mc;

    // 蓝牙连接服务通过Handle传递的数据
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        // 连接成功
                        case BluetoothService.STATE_CONNECTED:

                            new Thread() {
                                public void run() {
                                    while (true) {
                                        try {
                                            Thread.sleep(1000);
                                            Message msg = new Message();
                                            msg.what = 1;
                                            handler.sendMessage(msg);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();

                            isConnect = true;

                            btn_connect.setText(R.string.disconnect);
                            btn_connect.setEnabled(true);
                            // 连接成功后开始绘制心电图
                            startPaintTimer();
                            break;
                        // 连接中
                        case BluetoothService.STATE_CONNECTING:
                            btn_connect.setEnabled(false);
                            btn_connect.setText(getResources().getString(R.string.title_connecting));
                            break;
                        // 未连接
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            btn_connect.setEnabled(true);
                            btn_connect.setText(R.string.connect);
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_WRITE:

                    break;
                // 读取到的蓝牙数据
                case BluetoothService.MESSAGE_READ:

                    isDraw = true;

                    byte[] readBuf = (byte[]) msg.obj;
                    // 转换为整数
                    int value = ((0x0FF & readBuf[1]) << 8) + (0x0FF & readBuf[2]);
                    KeeperApplication.getInstance().addValue(value);

                    if (isRecord) {

                        SaveECGManager.getManager(getApplicationContext()).log(
                                Integer.toString(value));

                    }

                    break;
                case BluetoothService.MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(
                            BluetoothService.DEVICE_NAME);
                    break;
                case BluetoothService.MESSAGE_TOAST:

                    switch (msg.arg1) {

                        // 连接失败
                        case BluetoothService.MESSAGE_FAILED_CONNECTION:

                            ToastUtils.showToast(getApplicationContext(),
                                    R.string.unable_connect);

                            btn_connect.setText(R.string.connect);
                            btn_connect.setEnabled(true);

                            break;

                        // 连接断开
                        case BluetoothService.MESSAGE_LOST_CONNECTION:

                            btn_connect.setText(R.string.connect);

                            isConnect = false;
                            KeeperApplication.getInstance().clearValue();
                            stopPaintTimer();
                            ToastUtils.showToast(getApplicationContext(),
                                    R.string.lost_connection);

                            break;

                        default:
                            break;
                    }

                    break;

                case BluetoothService.MESSAGE_NOT_CONNECT:

                    ToastUtils.showToast(getApplicationContext(),
                            R.string.not_connected);

                    break;
            }
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

                if (xinlvcount % 2 == 0) {
                    tvxinlv.setText(" ");
                } else {
                    Random random = new Random();
                    int result = random.nextInt(100) % 10;
                    int xinlv = result + 80;
                    tvxinlv.setText("" + xinlv);
                }

                xinlvcount++;
            }
        }
    };

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ECGActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecg_patient);

        // 获取蓝牙连接管理器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 不支持蓝牙则退出App
        if (mBluetoothAdapter == null) {
            ToastUtils.showToast(this, R.string.not_support);
            finish();
            return;
        }

        tvxinlv = (TextView) findViewById(R.id.tvxinlv);
        btn_connect = (Button) findViewById(R.id.btn_connect);
        btn_connect.setOnClickListener(this);
        btn_record = (Button) findViewById(R.id.btn_record);
        btn_record.setOnClickListener(this);
        btn_find = (Button) findViewById(R.id.btn_find);
        btn_find.setOnClickListener(this);

        ecg_view = (ECGSurfaceView) findViewById(R.id.ecg_view);

        dataCount = new int[count];

        xSeekBar = (SeekBar) findViewById(R.id.xSeekBar);

        xSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                KeeperApplication.size = progress;
                KeeperApplication.tempList = new ArrayList<Integer>(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // 打开蓝牙开关
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            if (mBTService == null) {
                // 初始化蓝牙连接服务
                mBTService = new BluetoothService(this, mHandler);
            }
        }

    }

    @Override
    public synchronized void onResume() {
        super.onResume();

        if (mBTService != null) {
            if (mBTService.getState() == BluetoothService.STATE_NONE) {
                // 开始蓝牙连接服务
                mBTService.start();
            }
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

        maxnum = ecg_view.getWidth() / ECGSurfaceView.POINT_DIS;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 停止蓝牙连接服务
        if (mBTService != null)
            mBTService.stop();

        KeeperApplication.getInstance().clearValue();

        if (mc != null)
            mc.cancel();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_connect:

                // 点击连接按钮事件
                if (mBTService.getState() == BluetoothService.STATE_NONE
                        || mBTService.getState() == BluetoothService.STATE_LISTEN) {

                    Intent serverIntent = new Intent(this, DeviceListActivity.class);
                    startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);

                } else {

                    if (mBTService != null)
                        mBTService.stop();
                    btn_connect.setText(R.string.connect);
                }

                break;

            case R.id.btn_find:

                ECGRecordActivityForPatient.actionStart(this);
                break;

            case R.id.btn_record:

                if (isRecord) {

                    btn_record.setText(getResources().getString(R.string.startRecord));
                    isRecord = false;
                    if (mc != null) {
                        mc.cancel();
                        mc = null;
                    }

                } else {

                    // 点击连接按钮事件
                    if (mBTService.getState() == BluetoothService.STATE_NONE
                            || mBTService.getState() == BluetoothService.STATE_LISTEN) {

                        ToastUtils.showToast(this, getResources().getString(R.string.not_connected));

                    } else {

                        SaveECGUtils.setLogFileName(this);

                        btn_record.setText(getResources().getString(R.string.stopRecord));

                        if (mc == null) {
                            // 设置countDownInterval为100解决onTick不准确的问题
                            mc = new MyCount(60 * 1000, 100);
                        }

                        if (!mc.isRun) {

                            mc.start();
                        }

                        isRecord = true;

                    }

                }

                break;

            default:
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // 获取需要连接的蓝牙设备
                if (resultCode == Activity.RESULT_OK) {
                    // 获取蓝牙设备的MAC地址
                    String address = data.getExtras().getString(
                            DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // 获取蓝牙设备对象
                    BluetoothDevice device = mBluetoothAdapter
                            .getRemoteDevice(address);
                    // 开始连接
                    mBTService.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:

                if (resultCode == Activity.RESULT_OK) {
                    // 打开蓝牙开关后，初始化蓝牙连接服务
                    mBTService = new BluetoothService(this, mHandler);
                } else {

                    ToastUtils.showToast(this, R.string.not_connected);
                    finish();
                }
        }
    }

    /**
     * 注意事项：每次放定时任务前，确保之前任务已从定时器队列中移除 每次放任务都要新建一个对象，否则出现一下错误：
     * ERROR/AndroidRuntime(11761): java.lang.IllegalStateException: TimerTask
     * is scheduled already 所以同一个定时器任务只能被放置一次
     */
    // 如果list的长度大于count则执行的操作
    private void OperationMoreTcount() {

        if (flag == 1) {
            dataCount1 = new int[maxnum - counterAllPre];
            // 取出maxnum个中剩下的
            for (int i = 0; i < (maxnum - counterAllPre); i++) {
                try {
                    dataCount1[i] = KeeperApplication.getInstance().getValue();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            counterAll += (maxnum - counterAllPre);
            // 画图
            ecg_view.realTimeDraw(indexTemp, dataCount, counterAll,
                    counterAllPre);

            indexTemp = 0;
            counterAll = 0;
            counterAllPre = 0;
            flag = 0;
        } else {
            // 把list的count个数据取出来,dataCount为一个大小为count的数组
            for (int i = 0; i < count; i++) {
                try {
                    dataCount[i] = KeeperApplication.getInstance().getValue();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            // 统计总数
            counterAll += dataCount.length;

            ecg_view.realTimeDraw(indexTemp, dataCount, counterAll,
                    counterAllPre);

            // 取得最后一个元素
            indexTemp = dataCount[(dataCount.length - 1)];

            if (counterAll == maxnum) {
                counterAll = 0;
                counterAllPre = 0;
                flag = 0;
            } else {
                counterAllPre += dataCount.length;
                if (counterAllPre == maxnum) {
                    flag = 0;
                } else if ((counterAllPre + count > maxnum)
                        && (counterAllPre < maxnum)) {
                    flag = 1;
                }
            }

        }

    }

    /*------------------------------------------------------定时器操作---------------------------------------------------*/
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            while (task != null) {
                if (!KeeperApplication.getInstance().isEmpty()) {
                    if (!isConnect) {
                        if (KeeperApplication.getInstance().getSize() >= count)
                            OperationMoreTcount();
                        else {
                            stopPaintTimer();
                            KeeperApplication.getInstance().clearValue();
                            isConnect = true;
                        }

                    }

                    if (isDraw && isConnect) {
                        if (KeeperApplication.getInstance().getSize() >= count)
                            OperationMoreTcount();
                    }

                }
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    // 开启定时器
    public void startPaintTimer() {

        if (task != null) {
            task.cancel(); // 将原任务从队列中移除
        }

        reset();

        task = new MyTimerTask();
        timer = new Timer();
        // 周期执行task,从0时开始，每隔100ms进行一次
        try {
            // timer.schedule(task, 0, time);
            timer.schedule(task, time);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    // 停止定时器
    private void stopPaintTimer() {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if (task != null) {
            task.cancel();
            task = null;
        }

    }

    // 重置绘制心电图相关变量
    private void reset() {

        counterAll = 0;
        counterAllPre = 0;
        indexTemp = 0;
        flag = 0;
    }

    /* 定义一个倒计时的内部类 */
    class MyCount extends CountDownTimer {

        boolean isRun = false;
        private long millisInFuture;

        // 前者是倒计的时间数，后者是倒计每秒中间的间隔时间
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.millisInFuture = millisInFuture;
        }

        @Override
        public void onFinish() {
            // tv.setText("finish");
            isRun = false;
            btn_record.setText(getResources().getString(R.string.startRecord));
            mc = null;
            isRecord = false;

        }

        @Override
        public void onTick(long millisUntilFinished) {

            isRun = true;
            if (millisInFuture / 1000 != millisUntilFinished / 1000) {

                btn_record
                        .setText(getResources().getString(R.string.stopRecord) + "(" + (millisUntilFinished / 1000) + ")");
            }
        }
    }

    public void back(View view) {
        super.onBackPressed();
    }
}
