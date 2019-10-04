package grailstp

import grails.validation.ValidationException
import grailstp.uploadImage.FeaturedImageCommand
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*

class IllustrationController{

    IllustrationService illustrationService
    FeaturedImageService featuredImageService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond illustrationService.list(params), model: [illustrationCount: illustrationService.count()]
    }

    def show(Long id) {
        respond illustrationService.get(id)
    }

    def create() {
        respond new Illustration(params)
    }

    def save(Illustration illustration) {
        if (illustration == null) {
            notFound()
            return
        }

        MultipartFile imageToUpload = params.fileToUpload


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
        featuredImageService.upload(illustration.filename, imageToUpload)

        illustration.filename = illustration.filename + "." + extension


        try {
            illustrationService.save(illustration)
        } catch (ValidationException e) {
            respond illustration.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'illustration.label', default: 'Illustration'), illustration.id])
                redirect illustration
            }
            '*' { respond illustration, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond illustrationService.get(id)
    }

    def update(Illustration illustration) {
        if (illustration == null) {
            notFound()
            return
        }

        try {
            illustrationService.save(illustration)
        } catch (ValidationException e) {
            respond illustration.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'illustration.label', default: 'Illustration'), illustration.id])
                redirect illustration
            }
            '*' { respond illustration, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        illustrationService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'illustration.label', default: 'Illustration'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'illustration.label', default: 'Illustration'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def editFeaturedImage(Long id) {
        Illustration illustration = illustrationService.get(id)
        if (!illustration) {
            notFound()
        }
        [illustration: illustration]
    }
}