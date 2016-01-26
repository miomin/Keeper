package scu.miomin.com.keeper.doctor.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import scu.miomin.com.keeper.Enum.ChatMsgTypeEnum;
import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.activity.ChatActivity;
import scu.miomin.com.keeper.activity.LoginActivity;
import scu.miomin.com.keeper.adapter.ConversationAdapter;
import scu.miomin.com.keeper.application.ActivityCollector;
import scu.miomin.com.keeper.bean.ChatMessageBean;
import scu.miomin.com.keeper.bean.ConversationBean;
import scu.miomin.com.keeper.bean.DoctorBean;
import scu.miomin.com.keeper.bean.PatientBean;
import scu.miomin.com.keeper.controller.Controller;
import scu.miomin.com.keeper.doctor.controller.DoctorController;
import scu.miomin.com.keeper.patient.controller.PatientController;
import scu.miomin.com.keeper.resource.UserResource;

/**
 * 描述:主界面 创建日期:2015/7/23
 *
 * @author 莫绪旻
 */
public class MainKeeperForDoctor extends Activity {

    // 第一个界面的控件
    private PullToRefreshListView lvConversation;
    // 第三个界面的控件
    private TextView tvPhonenumber_top;
    private TextView tvPhonenumber;
    private TextView tvPatientname;

    // 三个界面的viewpager
    private ViewPager mTabPager;
    View view1;
    View view3;
    private ImageView mTab1, mTab3;
    private LinearLayout layout1, layout3;
    // 当前页卡编号
    private int currIndex = 0;

    //  收发消息更新对话列表的观察者对象
    Observer<List<RecentContact>> conversationObserver;
    // 消息接受的观察者
    Observer<List<IMMessage>> incomingMessageObserver = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_keeper_doctor);
        // 启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ActivityCollector.addActivity(this);
        // 初始化viewPager
        initViewPager();
        // 初始化第一个界面的控件
        initView1();
        // 设置第一个界面的监听器
        setListener1();
        // 初始化第三个界面的控件
        initView3();
        // 设置第三个界面的监听器
        initListener3();
        //  创建收发消息更新对话列表的观察者对象
        initConversationObserver();
        // 初始化好友列表
        initFriendList();
        // 初始化chat适配器
        Controller.initChatAdapterList(this);
        // 初始化接受消息的观察者对象
        initMsgReciverObserver();
    }

    // 初始化好友列表
    private void initFriendList() {
        DoctorBean doctorBean;

        doctorBean = (DoctorBean) UserResource.getUserByID("2013141463040");
        Controller.addFriend(doctorBean);

        doctorBean = (DoctorBean) UserResource.getUserByID("2013141463002");
        Controller.addFriend(doctorBean);

        doctorBean = (DoctorBean) UserResource.getUserByID("2013141463001");
        Controller.addFriend(doctorBean);

        doctorBean = (DoctorBean) UserResource.getUserByID("2013141463003");
        Controller.addFriend(doctorBean);

        PatientBean patientBean;

        patientBean = (PatientBean) UserResource.getUserByID("18000000000");
        Controller.addFriend(patientBean);

        patientBean = (PatientBean) UserResource.getUserByID("18000000001");
        Controller.addFriend(patientBean);

        patientBean = (PatientBean) UserResource.getUserByID("18084803926");
        Controller.addFriend(patientBean);

        patientBean = (PatientBean) UserResource.getUserByID("13558868295");
        Controller.addFriend(patientBean);

        Controller.addFriend(Controller.getCurrentUser());
    }

    // 初始化对话监听的观察者
    private void initConversationObserver() {
        conversationObserver =
                new Observer<List<RecentContact>>() {
                    @Override
                    public void onEvent(List<RecentContact> messages) {
                        // recents参数即为最近联系人列表（最近会话列表）
                        for (int i = 0; i < messages.size(); i++) {
                            String lastMsg = messages.get(i).getContent();
                            long time = messages.get(i).getTime();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/dd/MM HH:mm:ss");
                            String lastTime = sdf.format(new Date(time));
                            boolean isNewMsg = true;
                            String userphone = messages.get(i).getContactId();
                            String username = Controller.getFriendByID(userphone).getName();

                            ConversationBean conversation = new ConversationBean(lastMsg, lastTime,
                                    isNewMsg, userphone, username);
                            Controller.getConversationAdapter().add(conversation);
                        }
                    }
                };
        //  注册观察者
        NIMClient.getService(MsgServiceObserve.class)
                .observeRecentContact(conversationObserver, true);
    }

    // 初始化消息接受的观察者
    private void initMsgReciverObserver() {
        incomingMessageObserver =
                new Observer<List<IMMessage>>() {
                    @Override
                    public void onEvent(List<IMMessage> messages) {
                        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                        for (int i = 0; i < messages.size(); i++) {
                            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                            Date currentData = new Date(System.currentTimeMillis());
                            String time = format.format(currentData);
                            ChatMessageBean textMsg = new ChatMessageBean(messages.get(i).getSessionId(),
                                    Controller.getCurrentUser().getAccount(),
                                    messages.get(i).getContent(), time, ChatMsgTypeEnum.RECIVE_MSG);
                            // 显示最后一行
                            if (ChatActivity.instance != null)
                                ChatActivity.addMsg(messages.get(i).getSessionId(), textMsg);
                            else
                                Controller.getChatAdapter(messages.get(i).getSessionId()).addMsg(textMsg);
                        }
                    }
                };
        // 注册观察者对象
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
    }

    // 初始化控件
    private void initViewPager() {
        // 分页
        mTabPager = (ViewPager) findViewById(R.id.tabpager);
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mTab1 = (ImageView) findViewById(R.id.tab_one);
        mTab3 = (ImageView) findViewById(R.id.tab_three);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout1.setOnClickListener(new MyOnClickListener(0));
        layout3.setOnClickListener(new MyOnClickListener(2));

        // 将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        view1 = mLi.inflate(R.layout.main_tab_one_doctor, null);
        view3 = mLi.inflate(R.layout.main_tab_three_doctor, null);

        // 每个页面的view数据
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view3);

        // 填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager) container).removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ((ViewPager) container).addView(views.get(position));
                return views.get(position);
            }
        };
        mTabPager.setAdapter(mPagerAdapter);
    }

    // 初始化第一个界面的控件
    private void initView1() {
        lvConversation = (PullToRefreshListView) view1.findViewById(R.id.lvConversation);
        // 初始化对话列表的适配器
        initConversationAdapter();
    }

    // 设置第一个界面的监听器
    private void setListener1() {
        initConversationListener();
    }

    private void initConversationListener() {
        lvConversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatActivity.actionStart(MainKeeperForDoctor.this,
                        Controller.getConversationAdapter().getConversation(position - 1).getPhonenumber());
            }
        });

        // 设置下拉刷新监听器
        lvConversation.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
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
                        lvConversation.onRefreshComplete();
                    }
                }.execute();
            }
        });
    }

    // 初始化对话列表的适配器
    private void initConversationAdapter() {
        // 创建适配器对象
        Controller.setConversationAdapter(new ConversationAdapter(MainKeeperForDoctor.this));
        // 将ListView与适配器关联
        lvConversation.setAdapter(Controller.getConversationAdapter());

        NIMClient.getService(MsgService.class).queryRecentContacts()
                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable exception) {
                        // recents参数即为最近联系人列表（最近会话列表）
                        for (int i = 0; i < recents.size(); i++) {
                            String lastMsg = recents.get(i).getContent();
                            long time = recents.get(i).getTime();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/dd/MM HH:mm:ss");
                            String lastTime = sdf.format(new Date(time));
                            boolean isNewMsg = true;
                            // 有问题要查API文档
                            // 有问题要查API文档
                            // 有问题要查API文档
                            // 有问题要查API文档
                            String userphone = recents.get(i).getContactId();
                            String username = Controller.getFriendByID(userphone).getName();

                            ConversationBean conversation = new ConversationBean(lastMsg, lastTime,
                                    isNewMsg, userphone, username);
                            Controller.getConversationAdapter().add(conversation);
                        }
                    }
                });
    }

    // 初始化第三个界面的控件
    private void initView3() {

        tvPatientname = (TextView) view3.findViewById(R.id.tvUsername);
        tvPhonenumber_top = (TextView) view3.findViewById(R.id.tvUserphone_top);
        tvPhonenumber = (TextView) view3.findViewById(R.id.tvUserphone);

        String phonenumber = Controller.getCurrentUser().getAccount().substring(0, 3)
                + "****"
                + Controller.getCurrentUser().getAccount().substring(9, 13);

        tvPatientname.setText(Controller.getCurrentUser().getName());
        tvPhonenumber_top.setText(phonenumber);
        tvPhonenumber.setText(phonenumber);
    }

    // 设置第三个界面的监听器
    private void initListener3() {

    }


    // 最下面的头标点击的监听
    public class MyOnClickListener implements OnClickListener {

        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mTabPager.setCurrentItem(index);
        }
    }

    // 页卡切换监听
    public class MyOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    mTab1.setImageDrawable(getResources().getDrawable(
                            R.drawable.tab_main_msg_press));
                    if (currIndex == 1) {
                        mTab3.setImageDrawable(getResources().getDrawable(
                                R.drawable.tab_main_me_def));
                    }
                    break;
                case 1:
                    mTab3.setImageDrawable(getResources().getDrawable(
                            R.drawable.tab_main_me_press));
                    if (currIndex == 0) {
                        mTab1.setImageDrawable(getResources().getDrawable(
                                R.drawable.tab_main_msg_def));
                    }
                    break;
            }
            currIndex = arg0;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    }

    public void openTreatmentFollowupActivity(View view) {
        TreatmentListActivityForDoctor.actionStart(this);
    }

    // 监听到返回键时，退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            NIMClient.getService(AuthService.class).logout();
            ActivityCollector.finishAll();
            PatientController.finish();
            DoctorController.finish();
            LoginActivity.actionStart(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PatientController.finish();
        DoctorController.finish();
        //  注销观察者
        NIMClient.getService(MsgServiceObserve.class)
                .observeRecentContact(conversationObserver, false);
        // 注销观察者对象
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
        ActivityCollector.removeActivity(this);
    }
}
