package cn.rdp.common.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author gaoyuzhe
 * @date 2017/12/18.
 */
public class ImageUtils {
	
	private static Logger log = LoggerFactory.getLogger(ImageUtils.class);
	
	
	
    /***
     * 剪裁图片
     * @param file 图片
     * @param x  起点横坐标
     * @param y  纵坐标
     * @param w  长
     * @param h  高
     * x y w h 必须大于0， 页面裁剪必须在坐标范围
     * @throws IOException
     * @date
     */
    public static BufferedImage cutImage(MultipartFile file, int x, int y, int w, int h,String prefix) {
    	log.info("创建裁剪图片");
    	Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(prefix);
        try {
            ImageReader reader = iterator.next();
            //转换成输入流
            InputStream in = file.getInputStream();
            ImageInputStream iis = ImageIO.createImageInputStream(in);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            //坐标异常处理，高宽不会小于0
            if(x < 0) {x = 0;}
            if(y < 0) {y = 0;}
            Rectangle rect = new Rectangle(x, y, w,h);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0,param);
            return bi;
        } catch (Exception ignored) {
        	ignored.printStackTrace();
        	log.info("创建裁剪图片失败，" + ignored.getMessage());
        	System.out.println(ignored.getMessage());
        }
        return null;
    }
    /***
     * 图片旋转指定角度
     * @param bufferedimage 图像
     * @param degree      角度
     * @return
     * @date
     */
    public static BufferedImage rotateImage(BufferedImage bufferedimage, int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.setPaint(Color.WHITE);
        graphics2d.fillRect(0, 0, w, h);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0,Color.WHITE, null);
        graphics2d.dispose();
        return img;
    }
}
