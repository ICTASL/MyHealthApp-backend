package lk.gov.govtech.covid19.service;

import lk.gov.govtech.covid19.exceptions.ImageHandlingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Slf4j
@Service
public class ImageCompressionService {
    public byte[] compressImage(MultipartFile file) throws ImageHandlingException {
            InputStream is = null;
            BufferedImage bufferedImage;
            ByteArrayOutputStream baos = null;
            ImageOutputStream imageOutputStream = null;
            ImageWriter imageWriter = null;

            try {
                    is = file.getInputStream();
                    baos = new ByteArrayOutputStream();
                    bufferedImage = ImageIO.read(is);

                    float imageQ = 0.5f;
                    Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("jpg");
                    if (!imageWriters.hasNext()) {
                            throw new IllegalStateException("Writers not found");
                    }
                    imageWriter = imageWriters.next();
                    imageOutputStream = ImageIO.createImageOutputStream(baos);
                    imageWriter.setOutput(imageOutputStream);
                    ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
                    imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    imageWriteParam.setCompressionQuality(imageQ);
                    imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
                    return baos.toByteArray();
            } catch (IOException e) {
                    log.warn("Error while loading or compressing image");
                    throw new ImageHandlingException("Error while loading or compressing image", e);
            } finally {
                    IOUtils.closeQuietly(is);
                    IOUtils.closeQuietly(baos);
                    IOUtils.closeQuietly(imageOutputStream);
                    if (imageWriter != null) {
                            imageWriter.dispose();
                    }
            }
    }
}
