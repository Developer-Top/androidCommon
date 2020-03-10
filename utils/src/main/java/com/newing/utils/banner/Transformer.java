package com.newing.utils.banner;


import com.newing.utils.banner.transformer.AccordionTransformer;
import com.newing.utils.banner.transformer.BackgroundToForegroundTransformer;
import com.newing.utils.banner.transformer.CubeInTransformer;
import com.newing.utils.banner.transformer.CubeOutTransformer;
import com.newing.utils.banner.transformer.DefaultTransformer;
import com.newing.utils.banner.transformer.DepthPageTransformer;
import com.newing.utils.banner.transformer.FlipHorizontalTransformer;
import com.newing.utils.banner.transformer.FlipVerticalTransformer;
import com.newing.utils.banner.transformer.ForegroundToBackgroundTransformer;
import com.newing.utils.banner.transformer.RotateDownTransformer;
import com.newing.utils.banner.transformer.RotateUpTransformer;
import com.newing.utils.banner.transformer.ScaleInOutTransformer;
import com.newing.utils.banner.transformer.StackTransformer;
import com.newing.utils.banner.transformer.TabletTransformer;
import com.newing.utils.banner.transformer.ZoomInTransformer;
import com.newing.utils.banner.transformer.ZoomOutSlideTransformer;
import com.newing.utils.banner.transformer.ZoomOutTranformer;

import androidx.viewpager.widget.ViewPager;

public class Transformer {
    public static Class<? extends ViewPager.PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
