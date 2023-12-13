package ecommerce.project.backend.utils.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import ecommerce.project.backend.entities.Product;

import java.awt.image.BufferedImage;
import java.io.File;

public class QRCodeServiceImpl implements QRCodeService {


    private BufferedImage generateQRCodeImage(Product details) throws WriterException {
        StringBuilder str = new StringBuilder();
        str = str.append("Product ID: ").append(details.getId()).append(" || ").append("Product Name: ").append(details.getName()).append(" - ").append("From AGRISHOP");
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(str.toString(), BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
