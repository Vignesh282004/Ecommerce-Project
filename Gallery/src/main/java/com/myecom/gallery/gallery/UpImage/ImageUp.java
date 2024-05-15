package com.myecom.gallery.gallery.UpImage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUp {

    private final String image_path = "E:\\main_images";

    // fist we wiil upload any image of any image is uploaded it wiill go oto the second mehtod--> checkExists....

    public boolean uploadNew(MultipartFile multipartFile)
    {
        boolean flag = false;
        try {
            Files.copy(multipartFile.getInputStream(), Paths.get(image_path + File.separator + multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                flag = true;
         }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean if_image_exists(MultipartFile multipartFile)
    {
        boolean img_exist = false;
        try {
            File file = new File(image_path + "\\" + multipartFile.getOriginalFilename());
            img_exist = file.exists();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return img_exist;
    }
}
