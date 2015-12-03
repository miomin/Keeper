package scu.miomin.com.keeper.doctor.controller;

import scu.miomin.com.keeper.adapter.ConversationAdapter;

/**
 * Created by miomin on 15/11/13.
 */
public class DoctorController {

    private static ConversationAdapter conversationAdapter;

    public static ConversationAdapter getConversationAdapter() {
        return conversationAdapter;
    }

    public static void setConversationAdapter(ConversationAdapter conversationAdapter) {
        DoctorController.conversationAdapter = conversationAdapter;
    }

    // 释放资源
    public static void finish() {

    }
}
