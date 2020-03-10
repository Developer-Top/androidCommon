package com.newing.core.helper;


import com.newing.core.base.BaseDialogInterface;


/**
 * @author ：LiMing
 * @date ：2019-10-23
 * @desc ：配置类
 */
public class CoreConfigure {

    private static CoreConfigure mCoreConfigures = null;

    private Class<BaseDialogInterface> mBaseDialogClass = null;

    private int mEmptylayout = 0;

    public static CoreConfigure getInstance() {
        if (mCoreConfigures == null) {
            mCoreConfigures = new CoreConfigure();
        }
        return mCoreConfigures;
    }

    /**
     * 设置等待框
     */
    public <T extends BaseDialogInterface> void setWaitDialog(Class<T> dialogClass) {
        this.mBaseDialogClass = (Class<BaseDialogInterface>) dialogClass;
    }

    /**
     * 创建等待框
     *
     * @return
     */
    public BaseDialogInterface createDialogInterface() {
        if (this.mBaseDialogClass == null) {
            return null;
        }
        try {
            return mBaseDialogClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置通用适配器默认加载的无数据状态布局
     *
     * @param emptylayout
     */
    public void setEmptylayout(int emptylayout) {
        this.mEmptylayout = emptylayout;
    }

    public int getEmptylayout() {
        return mEmptylayout;
    }
}
