package com.pdb.common.google;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class QRUtils {

    private static final Logger logger = LoggerFactory.getLogger(QRUtils.class);

    /*默认宽度*/
    private final static int DEFAULT_WIDTH = 150;
    /*默认高度*/
    private final static int DEFAULT_HEIGHT = 150;


    /**
     * 生成二维码文件
     *
     * @param content
     * @param height
     * @param width
     * @param filePath
     * @param fileName
     * @throws WriterException
     * @throws IOException
     */
    public static void generateMatrixPic(String content, int height, int width, String filePath, String fileName) throws WriterException, IOException {
        BitMatrix bitMatrix = getBitMatrix(content, height, width);
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, "png", path);
    }

    /**
     * 生成二维码内存流
     *
     * @param content
     * @param height
     * @param width
     * @return
     * @throws WriterException
     */
    public static BufferedImage generateMatrixBufferedImage(String content, int height, int width) throws WriterException {
        BitMatrix bitMatrix = getBitMatrix(content, height, width);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    /**
     * 生成二维码内存流
     *
     * @param content
     * @return
     * @throws WriterException
     */
    public static BufferedImage generateMatrixBufferedImage(String content) throws WriterException {
        return generateMatrixBufferedImage(content, DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }


    /**
     * 生成矩阵
     *
     * @param content
     * @param height
     * @param width
     * @return
     * @throws WriterException
     */
    private static BitMatrix getBitMatrix(String content, int height, int width) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        return new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);
    }

    public static String parseQRCode(String filePath) {
        String content = "";
        File file = new File(filePath);
        try {
            BufferedImage image = ImageIO.read(file);
            content = getTextFromImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String parseQRCode(InputStream inputStream) {
        try {
            BufferedImage image = ImageIO.read(inputStream);
            return getTextFromImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从URL图片中解析二维码，返回字符串
     *
     * @param url
     * @return
     */
    public static String parseQRCodeFromUrl(String url) {
        return parseQRCode(getImageStream(url));
    }

    private static String getTextFromImage(BufferedImage image) {
        if (image == null) {
            return "";
        }
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        MultiFormatReader formatReader = new MultiFormatReader();
        Result result = null;
        try {
            result = formatReader.decode(binaryBitmap, hints);
        } catch (NotFoundException e) {
            logger.error("该图片中没有二维码，解析失败====>", e);
        }

        logger.info("getTextFromImage ===> result 为：{}", result);
        //设置返回值
        return result == null ? "" : result.getText();
    }

    /**
     * 获取网络图片流
     *
     * @param url
     * @return
     */
    private static InputStream getImageStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return connection.getInputStream();
            }
        } catch (IOException e) {
            logger.error("获取网络图片出现异常，图片路径为：{}", url);
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(parseQRCode("D:\\1.png"));
       // logger.info(parseQRCodeFromUrl("http://test-payment.s3-ap-northeast-1.amazonaws.com/20191029/65284771f3a84649bd6c70b6991a9bc7.jpg"));
    }


}
