package scu.miomin.com.keeper.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import scu.miomin.com.keeper.R;
import scu.miomin.com.keeper.activity.WelcomeActivity;
import scu.miomin.com.keeper.ecgsave.ECGDirSaveUtil;
import scu.miomin.com.keeper.resource.UserResource;

/**
 * 描述:Application 创建日期:2015/11/4
 *
 * @author 莫绪旻
 */
public class KeeperApplication extends Application {

    private static Context context;
    public static KeeperApplication mInstance;
    private List<Integer> list; // 存储原始心电数据

    public static int size = 1;
    public static List<Integer> tempList = new ArrayList<Integer>(10);

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        context = getApplicationContext();
        initImageLoader(this);
        NIMClient.init(this, getLoginInfo(), options()); // SDK初始化（启动后台服务，若已经存在用户登录信息，SDK 将完成自动登录）
        mInstance = this;
        list = Collections.synchronizedList(new LinkedList<Integer>());// 初始化ArrayList
        // 创建心电图目录文件
        ECGDirSaveUtil.creatDirFile(this);
        UserResource.initData();
    }

    // 获取全局的context
    public static Context getContext() {
        return context;
    }

    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = WelcomeActivity.class;
        config.notificationSmallIconId = R.drawable.ic_launcher;
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小，该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = 480 / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String s, String s1, SessionTypeEnum sessionTypeEnum) {
                return null;
            }

            @Override
            public int getDefaultIconResId() {
                return R.drawable.ic_launcher;
            }

            @Override
            public Bitmap getTeamIcon(String tid) {
                return null;
            }
        };

        return options;
    }

    // 获取之前的登录信息
    private LoginInfo getLoginInfo() {

        SharedPreferences sp = getSharedPreferences("logininfo", MODE_PRIVATE);

        // 从本地读取上次登录成功时保存的用户登录信息
        String phonenumber = sp.getString("phonenumber", null);
        String password = sp.getString("password", null);

        if (!TextUtils.isEmpty(phonenumber) && !TextUtils.isEmpty(password)) {
            return null;
        } else {
            return null;
        }
    }

    public static KeeperApplication getInstance() {

        return mInstance;
    }

    public void addValue(int value) {

        tempList.add(value);

        if (tempList.size() == size) {

            int sum = 0;
            for (int i = 0; i < tempList.size(); i++) {
                sum += tempList.get(i);
            }

            list.add(sum / size);
            tempList.clear();
        }
    }

    public void clearValue() {

        list.clear();

    }

    public int getValue() {
        int value = list.get(0);
        list.remove(0);

        return value;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int getSize() {
        return list.size();
    }

    // 获取ecg数组数据
    public int[] getECG() {

        int[] ecg = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ecg[i] = list.get(i).intValue();
        }

        return ecg;

    }


    public void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                        // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                        // You can pass your own memory cache
                        // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                        // 缓存的文件数量
                        // .discCache(
                        // new UnlimitedDiscCache(new File(Environment
                        // .getExternalStorageDirectory()
                        // + "/myApp/imgCache")))
                        // 自定义缓存路径
                .defaultDisplayImageOptions(getDisplayOptions())
                .imageDownloader(
                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
                .writeDebugLogs() // Remove for release app
                .build();// 开始构建
        ImageLoader.getInstance().init(config);
    }

    private DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                // .showImageForEmptyUri(R.drawable.defaul_head)// 设置图片Uri为空或是错误的时候显示的图片
                // .showImageOnFail(R.drawable.defaul_head) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                        // .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
                        // 设置图片以如何的编码方式显示
                        // .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                        // .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                        // .displayer(new RoundedBitmapDisplayer(10))// 是否设置为圆角，弧度为多少
                        // .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .build();// 构建完成
        return options;
    }
}
