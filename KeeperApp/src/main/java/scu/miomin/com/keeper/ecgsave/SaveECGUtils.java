package scu.miomin.com.keeper.ecgsave;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

/**
 * 描述:SaveECGUtils 创建日期:2015/6/21
 *
 * @author 莫绪旻
 */
public class SaveECGUtils {
	public static boolean MULTITHREAD = false;

	public final static String LOG_CACHE_DIRECTORY_NAME = "ECG";
	private static String sLogFileName = null;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	public static File getLogDir(Context context) {

		File folder = new File(
				SaveECGUtils.sdAvailible() ? Environment.getExternalStorageDirectory()
						: context.getFilesDir(), LOG_CACHE_DIRECTORY_NAME);

		if (!folder.exists()) {

			folder.mkdirs();
		}

		return folder;
	}

	public static String getLogFileName(Context context) {
		if (TextUtils.isEmpty(sLogFileName)) {
			File sub = new File(getLogDir(context), "data_"
					+ dateFormat.format(new Date(System.currentTimeMillis()))
					+ ".txt");
			sLogFileName = sub.getAbsolutePath();
		}

		return sLogFileName;
	}

	public static void setLogFileName(Context context) {
		File sub = new File(getLogDir(context), "data_"
				+ dateFormat.format(new Date(System.currentTimeMillis()))
				+ ".txt");
		sLogFileName = sub.getAbsolutePath();

	}

	public static boolean sdAvailible() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Delete file(file or folder).
	 *
	 * @param file
	 */
	public static void delete(File file) {
		if (file == null) {
			return;
		}
		if (file.isFile()) {
			file.delete();
			return;
		}

		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		for (File f : files) {
			if (f.isDirectory()) {
				delete(f);
			} else {
				f.delete();
			}
		}
		file.delete();
	}
}
