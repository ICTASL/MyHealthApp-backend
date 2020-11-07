package lk.gov.govtech.covid19.validation;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageValidator implements ConstraintValidator<AcceptableImage, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if(multipartFile.isEmpty()) {
                return false;
            } else if(multipartFile.getSize() > 200000) {
                return false;
            } else if (isInvalidFileName(multipartFile.getOriginalFilename())) {
                return false;
            } else if (isInvalidFileExtension(multipartFile.getOriginalFilename())) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isInvalidFileName(String filename) {
        if (filename == null) {
            return true;
        } else if(filename.length()>100) {
            return true;
        } else if(hasInvalidChars(filename)) {
            return true;
        } else if (StringUtils.countOccurrencesOf(filename, ".") != 1) {
            return true;
        } else  {
            return false;
        }
    }

    private boolean hasInvalidChars(String filename) {
        boolean isMatching = filename.matches("^[a-zA-Z0-9 ._-]+$");
        return !isMatching;
    }

    private boolean isInvalidFileExtension(String originalFilename) {
        String extension = originalFilename.split("\\.")[1]; //split by dot
        return !extension.equals("jpg");
    }
}
