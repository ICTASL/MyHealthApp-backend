package lk.gov.govtech.covid19.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@Service
public class ImageCompressionService {
    public byte[] compressImage(MultipartFile file) throws IOException {
            BufferedImage image = ImageIO.read(file.getInputStream());
            File compressedFile = new File("compressed_"+file.getOriginalFilename());
//            OutputStream os = new FileOutputStream(compressedFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            float imageQ = 0.5f;
            Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("jpg");
            if (!imageWriters.hasNext()){
                throw new IllegalStateException("Writers not found");
            }
            ImageWriter iw = (ImageWriter)imageWriters.next();
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(baos);
            iw.setOutput(imageOutputStream);
            ImageWriteParam imageWriteParam = iw.getDefaultWriteParam();
            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(imageQ);
            iw.write(null, new IIOImage(image, null, null), imageWriteParam);
            baos.close();
            imageOutputStream.close();
            iw.dispose();
            return baos.toByteArray();
    }
}
