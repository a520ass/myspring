package com.hf.awt;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by krt on 2017/4/13.
 */
public class ImageUtils {

    /*
   * 图片缩放,w，h为缩放的目标宽度和高度
   * src为源文件目录，dest为缩放后保存目录
   */
    public static void zoomImage(String src,String dest,int w,int h) throws Exception {

        double wr=0,hr=0;
        File srcFile = new File(src);
        File destFile = new File(dest);

        BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
        Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板

        wr=w*1.0/bufImg.getWidth();     //获取缩放比例
        hr=h*1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile); //写入缩减后的图片
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void test(){
        try {
            ImageUtils.zoomImage("C:\\Users\\krt\\Desktop\\2.png","C:\\Users\\krt\\Desktop\\2-zoom.png",640,220);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
