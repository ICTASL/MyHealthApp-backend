package lk.gov.govtech.covid19.service;

import lk.gov.govtech.covid19.dto.StoredImage;
import lk.gov.govtech.covid19.dto.StoredImageResponse;
import lk.gov.govtech.covid19.exceptions.ImageHandlingException;
import lk.gov.govtech.covid19.repository.CovidRepository;
import lk.gov.govtech.covid19.util.Constants;
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

    public StoredImageResponse addImage(MultipartFile file) throws ImageHandlingException {
        byte[] bArray = imageCompressionService.compressImage(file);
        int id = repository.addImage(bArray,file.getOriginalFilename(),file.getSize());

        StoredImageResponse storedImageResponse = new StoredImageResponse();
        storedImageResponse.setId(id);
        storedImageResponse.setUrl(Constants.BACKEND_CONTEXT
                + Constants.IMAGE_API_CONTEXT + "/image/" + id);
        storedImageResponse.setName(file.getOriginalFilename());
        return storedImageResponse;
    }

    public StoredImage getImage(int id)
    {
        return repository.getImage(id);
    }
}
