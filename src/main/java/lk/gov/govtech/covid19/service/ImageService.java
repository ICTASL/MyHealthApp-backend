package lk.gov.govtech.covid19.service;

import lk.gov.govtech.covid19.dto.StoredImage;
import lk.gov.govtech.covid19.repository.CovidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    @Autowired
    CovidRepository repository;

    @Autowired
    ImageCompressionService imageCompressionService;

    public void addImage(MultipartFile file){
        try {
            byte[] bArray = imageCompressionService.compressImage(file);
            InputStream is = new ByteArrayInputStream(bArray);
            int id = repository.addImage(is,file.getOriginalFilename(),file.getSize());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StoredImage getImage(int id)
    {
        return repository.getImage(id);
    }
}
