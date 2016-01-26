package scu.miomin.com.keeper.Enum;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.application.KeeperApplication;

/**
 * Created by miomin on 15/11/13.
 */
public class ProfessionalEnum {
    public static final String ZHURENYISHI = KeeperApplication.getInstance().getResources().getString(R.string.ZHURENYISHI); //主任医师/教授
    public static final String FUZHURENYISHI = KeeperApplication.getInstance().getResources().getString(R.string.FUZHURENYISHI); //副主任医师/副教授
    public static final String ZHUZHIYISHI = KeeperApplication.getInstance().getResources().getString(R.string.ZHUZHIYISHI); //主治医师/讲师
    public static final String FUZHUZHIYISHI = KeeperApplication.getInstance().getResources().getString(R.string.FUZHUZHIYISHI); //副主治医师
    public static final String SHIXIYISHI = KeeperApplication.getInstance().getResources().getString(R.string.SHIXIYISHI); //实习医师
}
