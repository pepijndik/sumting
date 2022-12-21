package app.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@AllArgsConstructor
@Service
public class FileStore {
    @Autowired
    private final AmazonS3 amazonS3;


    public boolean isImage(MultipartFile file){
        if (file.isEmpty()) {
            return false;
        }
        //Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(), IMAGE_BMP.getMimeType(), IMAGE_GIF.getMimeType(), IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
                return false;
        }
        return true;
    }

    public Map<String,String> prepareUplaud(MultipartFile file){
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    public String upload(String path,
                       String fileName,
                       Optional<Map<String, String>> optionalMetaData,
                       InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
           return amazonS3.getUrl(path, fileName).toString(); // return url
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }
    public String upload(String path,
                         String fileName,
                         Optional<Map<String, String>> optionalMetaData,
                         InputStream inputStream,
                         Boolean isPublic) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
            if(isPublic){
                amazonS3.setObjectAcl(path, fileName, com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead);
            }
            return amazonS3.getUrl(path, fileName).toString(); // return url
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }




    public byte[] download(String path, String key) {
        try {
            S3Object object = amazonS3.getObject(path, key);

            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download the file", e);
        }
    }
}
