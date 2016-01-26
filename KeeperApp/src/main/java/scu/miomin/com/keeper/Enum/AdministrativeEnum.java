package scu.miomin.com.keeper.Enum;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.application.KeeperApplication;

/**
 * Created by miomin on 15/11/13.
 */
public class AdministrativeEnum {
    public static final String NEIKE = KeeperApplication.getInstance().getResources().getString(R.string.NEIKE); //内科
    public static final String WAIKE = KeeperApplication.getInstance().getResources().getString(R.string.WAIKE); //外科
    public static final String ERKE = KeeperApplication.getInstance().getResources().getString(R.string.ERKE); //儿科
    public static final String JIZHENSHI = KeeperApplication.getInstance().getResources().getString(R.string.JIZHENSHI); //急诊室
    public static final String ZHONGYIKE = KeeperApplication.getInstance().getResources().getString(R.string.ZHONGYIKE); //中医科

    //还有很多暂时不列举
}
