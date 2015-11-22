package scu.miomin.com.keeper.application;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:管理所有的活动 创建日期:2015/10/21
 *
 * @author 莫绪旻
 */
public class ActivityCollector {

    // 用来管理所有的Activity
    public static List<Activity> activities = new ArrayList<Activity>();

    // 添加一个Activity
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    // 释放一个Activity
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    // 退出程序，释放所有的Activity
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
