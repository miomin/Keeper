package scu.miomin.com.keeper.ecgsave;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import android.content.Context;

/**
 * 描述:SaveECGManager 创建日期:2015/6/21
 *
 * @author 莫绪旻
 */
public class SaveECGManager {
	private static SaveECGManager sManager;

	private static final String TAG = "SaveManagerImpl";
	private final static int EXECUTOR_HANDLE_THREAD_PRIORITY = Thread.NORM_PRIORITY - 1;

	private Context mContext;
	private ExecutorService mExecutorService = null;
	// The available numbers of threads in executor service.
	private int mAvailableProcessors = 1;

	SaveECGManager(Context context) {
		mContext = context;
		mAvailableProcessors = Runtime.getRuntime().availableProcessors();
	}

	private void checkExecutor() {
		if (mExecutorService == null || mExecutorService.isShutdown()) {
			if (mAvailableProcessors < 0) {
				mAvailableProcessors = 1;
			}
			mExecutorService = Executors.newFixedThreadPool(
					SaveECGUtils.MULTITHREAD ? mAvailableProcessors : 1,
					new ThreadFactory() {
						@Override
						public Thread newThread(Runnable r) {
							Thread t = new Thread(r);
							t.setPriority(EXECUTOR_HANDLE_THREAD_PRIORITY);
							t.setName(TAG);
							return t;
						}
					});
		}
	}

	private void submitTask(final Runnable runnable) {
		checkExecutor();
		mExecutorService.submit(runnable);
	}

	public boolean log(String msg) {

		submitTask(new SaveECGTextTask(mContext, msg));

		return true;
	}

	/**
	 * Get singleton manager. DO NOT GET MANAGER BY THE CONTEXT OF ACTIVITY.
	 *
	 * @param context
	 *            The context of application.
	 * @return
	 */
	public synchronized static SaveECGManager getManager(Context context) {
		if (context == null) {
			return null;
		}

		if (sManager == null) {
			sManager = new SaveECGManager(context);
		}

		return sManager;
	}
}
