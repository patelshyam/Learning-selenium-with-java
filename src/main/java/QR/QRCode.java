package QR;


import java.awt.image.BufferedImage;
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.util.HashMap; 
import java.util.Map; 
  
import javax.imageio.ImageIO; 
  
import com.google.zxing.BarcodeFormat; 
import com.google.zxing.BinaryBitmap; 
import com.google.zxing.EncodeHintType; 
import com.google.zxing.MultiFormatReader; 
import com.google.zxing.MultiFormatWriter; 
import com.google.zxing.NotFoundException; 
import com.google.zxing.Result; 
import com.google.zxing.WriterException; 
import com.google.zxing.client.j2se.BufferedImageLuminanceSource; 
import com.google.zxing.client.j2se.MatrixToImageWriter; 
import com.google.zxing.common.BitMatrix; 
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel; 
  
public class QRCode { 
  
    public static void main(String[] args) 
        throws WriterException, 
               IOException, 
               NotFoundException 
    { 
  
        // The data that the QR code will contain 
        String data = "www.geeksforgeeks.org"; 
  
        // The path where the image will get saved 
        String path = "C:\\Users\\Shyam\\eclipse-workspace\\WhatsApp\\"; 
  
        BufferedImage bimage = createQR(data);
       
        File outputfile = new File(path + "image.jpg");
        ImageIO.write(bimage, "jpg", outputfile);
        System.out.println( 
            "QR Code Generated!!! "); 
    } 
  
    // Function to create the QR code 
    public static BufferedImage  createQR(String barcodeText) throws WriterException
    { 
  
    	 QRCodeWriter barcodeWriter = new QRCodeWriter();
    	    BitMatrix bitMatrix = 
    	      barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 232, 172);
    	 
    	    return MatrixToImageWriter.toBufferedImage(bitMatrix);
    } 
} 