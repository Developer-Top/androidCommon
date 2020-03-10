package com.newing.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.exifinterface.media.ExifInterface;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Android中Bitmap、Drawable、bytes数组之间相互转换
 * @author linlingrong
 * @date 2015/10/3
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class BitmapUtils {
    static int imgHeight, imgWidth;

    private BitmapUtils() {

    }

    /**
     * bitmap -> bytes
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        if (null == bitmap) {
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat compressFormat) {
        if (null == bitmap) {
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, 80, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    /**
     * bytes -> bitmap
     */
    public static Bitmap bytes2Bitmap(byte[] bytes) {
        if (null == bytes || bytes.length == 0) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    public static Bitmap yuvBytes2Bitmap(byte[] bytes, int width, int height) {
        if (null == bytes || bytes.length == 0) {
            return null;
        }
        YuvImage yuvimage = new YuvImage(bytes, ImageFormat.NV21, width, height, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, width, height), 80, baos);
        byte[] jdata = baos.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.ARGB_8888;
        return BitmapFactory.decodeByteArray(jdata, 0, jdata.length, options);
    }


    /**
     * bytes -> bitmap
     */
    public static Bitmap bytesToBitmapGoods(byte[] bytes) {
        if (null == bytes || bytes.length == 0) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPurgeable = true;
        options.inTempStorage = new byte[60 * 90];
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * bytes -> bitmap
     */
    public static Bitmap bytesToBitmap(byte[] bytes) {
        if (null == bytes || bytes.length == 0) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.ARGB_8888;
        options.inDither = false;
        options.inPurgeable = true;
        options.inTempStorage = new byte[150 * 200];
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * bitmap -> drawable
     */
    public static Drawable bitmap2Drawable(Context context, Bitmap bitmap) {
        if (null == context || null == bitmap) {
            return null;
        }

        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * drawable -> bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (null == drawable) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * @param bitmap 原图
     * @param width  截取的宽
     * @param height 截取的高
     * @return 缩放截取正中部分后的位图。
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, int width, int height) {
        if (null == bitmap || width <= 0 || height <= 0) {
            return null;
        }

        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        int xTopLeft = (widthOrg - width) / 2;
        int yTopLeft = (heightOrg - height) / 2;

        return Bitmap.createBitmap(bitmap, xTopLeft, yTopLeft, width, height);

    }

    //设置饱和度
    public static Bitmap saturation(Bitmap map, float s) {//s的值在0-1

        imgHeight = map.getHeight();
        imgWidth = map.getWidth();
        Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight,
                Config.ARGB_8888);
        ColorMatrix cMatrix = new ColorMatrix();
        // 设置饱和度  .
        cMatrix.setSaturation(s);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        Canvas canvas = new Canvas(bmp);
        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
        canvas.drawBitmap(map, 0, 0, paint);
        return bmp;
    }

    //设置对比度
    public static Bitmap contrast(Bitmap map, float contrast) {//c的值0-
        imgHeight = map.getHeight();
        imgWidth = map.getWidth();
        Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight,
                Config.ARGB_8888);
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{contrast, 0, 0, 0, 0, 0,
                contrast, 0, 0, 0,// 改变对比度
                0, 0, contrast, 0, 0, 0, 0, 0, 1, 0});

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

        Canvas canvas = new Canvas(bmp);
        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
        canvas.drawBitmap(map, 0, 0, paint);
        return bmp;
    }

    //设置亮度
    public static Bitmap Brightness(Bitmap map, int b) {//b的值正数变亮，负数变暗

        imgHeight = map.getHeight();
        imgWidth = map.getWidth();
        Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight,
                Config.ARGB_8888);
        int brightness = b;
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1,
                0, 0, brightness,// 改变亮度
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

        Canvas canvas = new Canvas(bmp);
        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
        canvas.drawBitmap(map, 0, 0, paint);
        return bmp;
    }

    public static Bitmap reviewPicRotate(Bitmap bitmap, String path) {
        int degree = getPicRotate(path);
        if (degree != 0) {
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(degree); // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
        }
        return bitmap;
    }

    /**
     * 读取图片文件旋转的角度
     * @param path 图片绝对路径
     * @return 图片旋转的角度
     */
    public static int getPicRotate(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap getBitmapFromFile(File dst, int width, int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                //设置inJustDecodeBounds为true后，decodeFile并不分配空间，此时计算原始图片的长度和宽度
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getAbsolutePath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength, width * height);
                //这里一定要将其设置回false，因为之前我们将其设置成了true
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getAbsolutePath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                Log.e("TEST", "BitmapFactory.decodeFile出错：" + e.getMessage(), e);
            }
        }
        return null;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math
                .floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 质量压缩方法
     * @param image bitmap
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;
        // 循环判断如果压缩后图片是否大于1.5M,大于继续压缩
        while (baos.toByteArray().length / 1024 > 190) {
            baos.reset(); // 重置baos即清空baos
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
        }
        long lengh = baos.toByteArray().length / 1024;
        Log.d("lm", "压缩后的大小:" + lengh);

        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * 图片按比例大小压缩方法
     * @param srcPath （根据路径获取图片并压缩）
     */
    public static Bitmap getimage(String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        // 此时返回bm为空
        Bitmap bitmap;
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        // 这里设置高度为1280f
        float hh = 1280f;
        // 这里设置宽度为720f
        float ww = 720f;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        // be=1表示不缩放
        int be = 1;
        // 如果宽度大的话根据宽度固定大小缩放
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        // 如果高度高的话根据宽度固定大小缩放
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        // 设置缩放比例
        newOpts.inSampleSize = be;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
        return compressImage(bitmap);
    }

    /**
     * 图片按比例大小压缩方法
     * @param image （根据Bitmap图片压缩）
     */
    public static Bitmap compressScale(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 判断如果图片大于1.5M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().length / 1024 > 1542) {
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 70, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap;
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        // float hh = 800f;// 这里设置高度为800f
        // float ww = 480f;// 这里设置宽度为480f
        float hh = 1280f;
        float ww = 720f;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        // 设置缩放比例
        newOpts.inSampleSize = be;
        // newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        // 压缩好比例大小后再进行质量压缩
        return compressImage(bitmap);
        //return bitmap;
    }

    public static void saveBitmap(Bitmap mBitmap, String fileName) {
        if (mBitmap == null) {
            return;
        }
        File f = new File(fileName);
        try {
            File fileParent = f.getParentFile();
            if (fileParent != null && !fileParent.exists()) {
                fileParent.mkdirs();
            }
            if (f.createNewFile()) {
                FileOutputStream fOut = new FileOutputStream(f);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush();
                fOut.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上下组合图片
     * @param topBitmapBytes 上面的图片
     * @param bottomBitmap   下面的图片
     * @return 结果
     */
    public static Bitmap composeBitmap(byte[] topBitmapBytes, Bitmap bottomBitmap) {
        Bitmap topBitmap = bytesToBitmap(topBitmapBytes);
        int height = topBitmap.getHeight() + bottomBitmap.getHeight();
        int width = Math.max(topBitmap.getWidth(), bottomBitmap.getWidth());
        Bitmap resultBitmap = Bitmap.createBitmap(width, height, Config.ARGB_4444);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(topBitmap, 0, 0, null);
        canvas.drawBitmap(bottomBitmap, 0, topBitmap.getHeight(), null);
        canvas.save();
        canvas.restore();
        topBitmap.recycle();
        return resultBitmap;
    }
}
