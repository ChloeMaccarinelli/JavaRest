package grailstp

import grails.gorm.transactions.Transactional
import org.springframework.web.multipart.MultipartFile

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

@Transactional
class FeaturedImageService {

    def serviceMethod() {

    }

    void upload(String filename, MultipartFile imageToUpload) {

        String extension = ""

        switch(imageToUpload.getContentType()) {
            case "image/png":
                extension = "png"
                break
            case "image/jpg":
                extension = "jpg"
                break
            case "image/jpeg":
                extension = "jpeg"
                break
        }

        File dirToUploadTo = new File("/Applications/XAMPP/xamppfiles/htdocs/GrailsTP/grails-app/assets/images/"+ filename + "." + extension)

        imageToUpload.transferTo(dirToUploadTo)
    }

}
