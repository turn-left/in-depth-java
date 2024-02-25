package com.ethen.feature.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * java图片处理demo01：读写支持的图片格式
 *
 * @author ethenyang@126.com
 * @since 2024/02/24
 */
public class ImageFormats {
    public static void main(String[] args) throws IOException {
        // JDK支持的图片格式
        String[] readerFormatNames = ImageIO.getReaderFormatNames();
        String[] writerFormatNames = ImageIO.getWriterFormatNames();
        System.out.println("readerFormatNames:"+ Arrays.asList(readerFormatNames));
        System.out.println("writerFormatNames:"+ Arrays.asList(writerFormatNames));

        // 读取图片
        File file = new File("C:\\Users\\ethen\\Pictures\\联想锁屏壁纸\\welcome.png");
        BufferedImage bufferedImage = ImageIO.read(file);
        System.err.println(bufferedImage.getMinX());
    }
}
