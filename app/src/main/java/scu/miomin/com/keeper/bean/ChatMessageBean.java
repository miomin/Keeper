package scu.miomin.com.keeper.bean;

import scu.miomin.com.keeper.Enum.ChatMsgTypeEnum;

/**
 * Created by 847912006 on 2015/11/12.
 * <p/>
 * 聊天内容的实体类
 */
public class ChatMessageBean {

    private String senderID;
    private String reciverID;
    private String time;

    private int MsgType;
    private int ContentType;

    private String text;
//  private Pic picture;
//  private Voice voice;
//  private Location location;
//  private ECGRecord ecg_record;

    public ChatMessageBean(String senderID, String reciverID, String text, String time, int MsgType) {
        this.senderID = senderID;
        this.reciverID = reciverID;
        this.text = text;
        this.time = time;
        this.ContentType = ChatMsgTypeEnum.TEXT_MSG;
        this.MsgType = MsgType;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getReciverID() {
        return reciverID;
    }

    public int getContentType() {
        return ContentType;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public int getMsgType() {
        return MsgType;
    }
}
