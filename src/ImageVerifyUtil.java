import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 图形验证码生成工具
 * 具体思路：
 * 1、生成画布，设置画布颜色大小
 * 2、绘制边框及颜色
 * 3、生成随机数和随机线条
 */
public class ImageVerifyUtil {
     // 图片的宽度。
     private int width = 160;
     // 图片的高度。
     private int height = 40;
      // 验证码字符个数
     private int codeCount = 5;
      // 验证码干扰线数
     private int lineCount = 150;
     // 验证码
     private String code = null;
     // 验证码图片Buffer
     private BufferedImage buffImg = null;
     //随机码字符
     private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
             'I', 'J', 'K', 'L', 'M', 'N',  'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',  '1', '2', '3', '4', '5', '6', '7', '8', '9' };

     public ImageVerifyUtil() {

    }

    /**
     * 根据长宽生成验证码
     * @param width
     * @param height
     */
    public ImageVerifyUtil(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 根据长宽、验证码字数和先提哦啊数量生成验证码
     * @param width
     * @param height
     * @param codeCount
     * @param lineCount
     */
    public ImageVerifyUtil(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
    }

    /**
     * 生成验证码
     */
    public void createCode(){
        //初始化图像原点坐标
        int x = 0, y = 0;
        //初始化字体大小
        int frontSize = 0;
        //初始化RGB
        int red = 0, green = 0, blue = 0;

        //生成图像imageBuff
        buffImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics = buffImg.createGraphics();

        //将图像填充设置白色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,width,height);

        //创建字体


        //生成随机数
        Random random = new Random();

        //生成干扰线
        for(int i=0;i<lineCount;i++){
            //直线起点
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            //直线终点
            int xe = xs+random.nextInt(width/8);
            int ye = ys+random.nextInt(height/8);
            red = random.nextInt(255);
            //设置直线的随机颜色
            green = random.nextInt(255);
            blue = random.nextInt(255);
            graphics.setColor(new Color(red, green, blue));
            graphics.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();

        //随机生成codeCount个随机数
        for(int i=0;i<codeCount;i++){
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            //每个数字设置随机颜色
            red = random.nextInt();
            green = random.nextInt();
            blue = random.nextInt();
            graphics.setColor(new Color(red, green, blue));
            //画数字
            graphics.drawString(strRand, (i + 1) * x, y);
            //连接生成的验证码
            randomCode.append(randomCode);
        }
        //将验证码存入Session
       if(randomCode!=null){
          code = randomCode.toString();
       }

    }

    //获取生成的
    public void write(OutputStream sos) throws IOException {
        ImageIO.write(buffImg, "png", sos);
        sos.close();
    }
    public BufferedImage getBuffImg() {
        return buffImg;
    }

    public String getCode() {
        return code;
    }
}