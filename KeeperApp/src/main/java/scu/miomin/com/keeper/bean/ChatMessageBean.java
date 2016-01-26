package scu.miomin.com.keeper.bean;

import java.io.Serializable;

import scu.miomin.com.keeper.Enum.ChatMsgTypeEnum;

/**
 * 描述:聊天消息实体类 创建日期:2015/11/12
 *
 * @author 莫绪旻
 */
public class ChatMessageBean implements Serializable {

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
