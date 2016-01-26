package scu.miomin.com.keeper.resource;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 描述：存放ImageLoader的加载器 创建日期：2015/5/1
 *
 * @author 莫绪旻
 */
public class MyLoader {

    public static ImageLoader loader = ImageLoader.getInstance();

    public static void displayFromDrawable(int imageId, ImageView imageView) {
        loader.displayImage("drawable://" + imageId,
                imageView);
    }

    public static void dispalyFromAssets(String imageName, ImageView imageView) {
        ImageLoader.getInstance().displayImage("assets://" + imageName,
                imageView);
    }
}
