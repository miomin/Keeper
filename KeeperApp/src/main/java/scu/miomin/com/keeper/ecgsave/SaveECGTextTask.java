package scu.miomin.com.keeper.ecgsave;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.text.TextUtils;

/**
 * 描述:SaveECGTextTask 创建日期:2015/6/21
 *
 * @author 莫绪旻
 */
public class SaveECGTextTask implements Runnable {
    private Context mContext;
    private String mMsg;

    public SaveECGTextTask(Context context, String msg) {
        mContext = context;
        mMsg = msg;
    }

    @Override
    public void run() {
        if (mContext == null || TextUtils.isEmpty(mMsg)) {
            return;
        }

        log2File(mMsg);

    }

    private void log2File(String msg) {
        if (SaveECGUtils.sdAvailible()) {
            try {
                File file = new File(SaveECGUtils.getLogFileName(mContext));

                if (!file.exists() || file.isDirectory()) {

                    SaveECGUtils.delete(file);
                    file.createNewFile();

                }

                // Encode and encrypt the message.
                FileOutputStream trace = new FileOutputStream(file, true);
                OutputStreamWriter writer = new OutputStreamWriter(trace,
                        "utf-8");
                writer.write(msg + "\n");
                writer.flush();

                trace.flush();
                trace.close();
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}
