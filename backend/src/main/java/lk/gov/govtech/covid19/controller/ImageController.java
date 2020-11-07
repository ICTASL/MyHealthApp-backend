package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.dto.StoredImage;
import lk.gov.govtech.covid19.dto.StoredImageResponse;
import lk.gov.govtech.covid19.exceptions.ImageHandlingException;
import lk.gov.govtech.covid19.service.ImageService;
import lk.gov.govtech.covid19.util.Constants;
import lk.gov.govtech.covid19.validation.AcceptableImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping(Constants.IMAGE_API_CONTEXT)
public class ImageController {

    @Autowired
    ImageService imageService;
    private MultipartFile image;

    @PostMapping
    public ResponseEntity uploadImage(@RequestParam("image") @AcceptableImage MultipartFile image)
            throws ImageHandlingException {
        StoredImageResponse response = imageService.addImage(image);
        log.info("Image {} of size:{} added", image.getOriginalFilename(), image.getSize());
        System.gc();
        return ResponseEntity.accepted().body(response);
    }

    @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getFiles(@PathVariable("imageId") int imageId){
        StoredImage storedImage = imageService.getImage(imageId);
        return storedImage.getImage();
    }
}
