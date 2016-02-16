package scu.miomin.com.keeper.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by miomin on 16/1/23.
 */
public class FileUploadUtil {

    public static void uploadFile(String filename, String requestUrl, Context context) {

        FileInputStream in = null;
        BufferedReader reader = null;

        String ecgData = "";

        try {
            in = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";

            while ((line = reader.readLine()) != null) {
                ecgData = ecgData + line.replaceAll("\r", "") + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        uploadString(filename, ecgData, requestUrl);
    }

    public static void uploadString(String filename, String ecgdata, String requestUrl) {
        String str = "http://121.41.93.19:5000/upload";

        URL url = null;
        try {
            url = new URL(str);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// 提交模式
            conn.setDoOutput(true);// 是否输入参数
            StringBuffer params = new StringBuffer();
            params.append("data=");
            params.append(ecgdata);
            params.append("&file_name=");
            params.append(filename);

            byte[] bypes = params.toString().getBytes();
            OutputStream out = conn.getOutputStream();// 输入参数
            out.write(bypes);
            out.flush();
            out.close();
            InputStream inStream = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
