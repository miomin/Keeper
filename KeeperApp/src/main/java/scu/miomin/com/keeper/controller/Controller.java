package scu.miomin.com.keeper.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import scu.miomin.com.keeper.adapter.ChatListAdapter;
import scu.miomin.com.keeper.adapter.ConversationAdapter;
import scu.miomin.com.keeper.bean.ChatMessageBean;
import scu.miomin.com.keeper.bean.Userbean;

/**
 * Created by miomin on 15/11/21.
 */
public class Controller {

    public static ConversationAdapter conversationAdapter;

    private static Userbean currentUser;

    private static ArrayList<Userbean> friendList = new ArrayList<Userbean>();

    private static HashMap<String, ChatListAdapter> chatAdapterMap = new HashMap<String, ChatListAdapter>();

    // 根据ID获取好友
    public static Userbean getFriendByID(String phonenumber) {
        Userbean userbean = null;
        for (int i = 0; i < friendList.size(); i++) {
            if (friendList.get(i).getAccount().equals(phonenumber)) {
                userbean = friendList.get(i);
            }
        }
        return userbean;
    }

    // 返回用户类型
    public static int getUserType() {
        return currentUser.getUserType();
    }

    public static Userbean getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Userbean currentUser) {
        Controller.currentUser = currentUser;
    }

    public static void initChatAdapterList(Context context) {
        for (int i = 0; i < friendList.size(); i++) {
            chatAdapterMap.put(friendList.get(i).getAccount(),
                    new ChatListAdapter(context, new ArrayList<ChatMessageBean>(),
                            friendList.get(i).getAccount()));
        }
    }

    public static ChatListAdapter getChatAdapter(String userphone) {
        return chatAdapterMap.get(userphone);
    }

    public static ConversationAdapter getConversationAdapter() {
        return conversationAdapter;
    }

    public static void setConversationAdapter(ConversationAdapter conversationAdapter) {
        Controller.conversationAdapter = conversationAdapter;
    }

    public static void addFriend(Userbean userbean) {
        friendList.add(userbean);
    }

    public static ArrayList<Userbean> getFriendList() {
        return friendList;
    }

    public static void finish() {
        currentUser = null;
    }
}
