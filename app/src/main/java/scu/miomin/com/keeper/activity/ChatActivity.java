package scu.miomin.com.keeper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import scu.miomin.com.keeper.Enum.ChatMsgTypeEnum;
import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.adapter.ChatListAdapter;
import scu.miomin.com.keeper.baseactivity.BaseActivity;
import scu.miomin.com.keeper.bean.ChatMessageBean;
import scu.miomin.com.keeper.bean.Userbean;
import scu.miomin.com.keeper.controller.Controller;
import scu.miomin.com.keeper.doctor.activity.DoctorCheckPatientActivity;

public class ChatActivity extends BaseActivity {

    private ImageView btn_showVoiceBtn;
    private Button btn_recordVoice;
    private Button btn_sendText;

    private EditText edit_msg;

    public static ListView list_chat;

    private TextView tv_friendName;

    private LinearLayout layout_otherMenu;
    private LinearLayout btn_showEdit;
    private LinearLayout btn_showOtherMenu;

    public static ArrayList<ChatMessageBean> chat_msg_list;

    private ChatListAdapter chatListAdapter;

    private String friendphone;
    private String textContent;
    private Userbean ChatFriend;

    public static ChatActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);

        instance = this;
        //进入Activity时不打开软件盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        friendphone = getIntent().getStringExtra("phonenumber");
        ChatFriend = Controller.getFriendByID(friendphone);
        if (friendphone == null || ChatFriend == null) {
            Toast.makeText(this, "信息加载失败", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        chatListAdapter = Controller.getChatAdapter(friendphone);
        initView();
    }

    public static void addMsg(String userid, ChatMessageBean msg) {
        Controller.getChatAdapter(userid).addMsg(msg);
        list_chat.setSelection(chat_msg_list.size());
    }

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }

    public static void actionStart(Context context, String phonenumber) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("phonenumber", phonenumber);
        context.startActivity(intent);
    }

    private void initView() {

        btn_showVoiceBtn = (ImageView) findViewById(R.id.btn_showVoiceBtn);
        btn_recordVoice = (Button) findViewById(R.id.btn_recordVoice);
        btn_sendText = (Button) findViewById(R.id.btn_sendText);
        edit_msg = (EditText) findViewById(R.id.edit_msg);

        edit_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (edit_msg.getText().length() > 0) {
                    btn_showVoiceBtn.setVisibility(View.INVISIBLE);
                    btn_showVoiceBtn.setEnabled(false);
                    btn_sendText.setEnabled(true);
                    btn_sendText.setVisibility(View.VISIBLE);
                } else {
                    btn_showVoiceBtn.setVisibility(View.VISIBLE);
                    btn_sendText.setVisibility(View.INVISIBLE);
                    btn_showVoiceBtn.setEnabled(true);
                    btn_sendText.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_msg.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //打开软键盘
                    pushInputView();
                    //关闭其他菜单
                    layout_otherMenu.setVisibility(View.GONE);

                    //将显示输入文本框的按钮变化为显示其他菜单的按钮
                    btn_showOtherMenu.setVisibility(View.VISIBLE);
                    btn_showOtherMenu.setEnabled(true);
                    btn_showEdit.setVisibility(View.INVISIBLE);
                    btn_showEdit.setEnabled(false);
                }
            }
        });

        chat_msg_list = new ArrayList<ChatMessageBean>();
        list_chat = (ListView) findViewById(R.id.list_chat);
        list_chat.setAdapter(chatListAdapter);

        tv_friendName = (TextView) findViewById(R.id.tv_friendName);
        tv_friendName.setText(ChatFriend.getName());
        btn_showEdit = (LinearLayout) findViewById(R.id.btn_showEdit);
        btn_showOtherMenu = (LinearLayout) findViewById(R.id.btn_showOtherMenu);
        layout_otherMenu = (LinearLayout) findViewById(R.id.layout_otherMenu);
    }

    //显示其他发送内容菜单
    public void showOtherMenu(View view) {

        //关闭软键盘
        hideSoftInputView();
        //清除edit的焦点
        edit_msg.clearFocus();
        //将弹出其他菜单的按钮转变为显示输入文本框的按钮
        btn_showEdit.setVisibility(View.VISIBLE);
        btn_showEdit.setEnabled(true);
        btn_showOtherMenu.setVisibility(View.INVISIBLE);
        btn_showOtherMenu.setEnabled(false);

        //显示其他菜单
        layout_otherMenu.setVisibility(View.VISIBLE);
    }

    //显示输入文本框
    public void showEdit(View view) {

        //隐藏其他菜单栏
        layout_otherMenu.setVisibility(View.GONE);

        //将显示输入文本框的按钮变换为显示其他菜单的按钮
        btn_showOtherMenu.setVisibility(View.VISIBLE);
        btn_showOtherMenu.setEnabled(true);
        btn_showEdit.setVisibility(View.INVISIBLE);
        btn_showEdit.setEnabled(false);

        //隐藏录制语音的按钮
        btn_recordVoice.setVisibility(View.INVISIBLE);
        btn_recordVoice.setEnabled(false);

        //显示输入文本框
        edit_msg.setVisibility(View.VISIBLE);

        //弹出软键盘
        pushInputView();
    }

    //发送文字消息
    public void sendText(View view) {

        textContent = edit_msg.getText().toString();
        if (!TextUtils.isEmpty(textContent)) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date currentData = new Date(System.currentTimeMillis());
            String time = format.format(currentData);
            ChatMessageBean textMsg = new ChatMessageBean(Controller.getCurrentUser().getPhonenumber(),
                    friendphone, textContent, time, ChatMsgTypeEnum.SEND_MSG);
            //把terxtMsg发送到服务器
            chatListAdapter.addMsg(textMsg);
            edit_msg.setText("");
            list_chat.setSelection(chat_msg_list.size());
        }

        IMMessage message = MessageBuilder.createTextMessage(
                "18084803926", // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                SessionTypeEnum.P2P, // 聊天类型，单聊或群组
                textContent // 文本内容
        );

        // 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
        NIMClient.getService(MsgService.class).sendMessage(message, true);
    }

    //开始录音
    public void recordVoice(View view) {

    }

    //显示发送语音的按钮，隐藏输入文本的文本框,
    public void showVoiceBtn(View view) {

        //收起软键盘
        hideSoftInputView();

        //清空输入文本框的焦点
        edit_msg.clearFocus();

        //将显示其他菜单的按钮变换为显示输入文本框的按钮
        btn_showOtherMenu.setVisibility(View.INVISIBLE);
        btn_showOtherMenu.setEnabled(false);
        btn_showEdit.setVisibility(View.VISIBLE);
        btn_showEdit.setEnabled(true);

        //隐藏输入文本框
        edit_msg.setVisibility(View.GONE);

        //收起其他菜单栏
        layout_otherMenu.setVisibility(View.GONE);

        //显示录音按键
        btn_recordVoice.setVisibility(View.VISIBLE);
        btn_recordVoice.setEnabled(true);
    }

    // 隐藏软键盘
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this
                .getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // 打开软键盘
    private void pushInputView() {
        edit_msg.setFocusable(true);
        edit_msg.setFocusableInTouchMode(true);
        edit_msg.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) edit_msg.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);

        inputManager.showSoftInput(edit_msg, 0);
    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void checkUserinfo(View view) {
        DoctorCheckPatientActivity.actionStart(this);
    }
}
