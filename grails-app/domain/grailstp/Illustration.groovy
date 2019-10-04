package grailstp

import grails.validation.Validateable

import java.awt.image.BufferedImage

class Illustration implements Validateable{

    String filename

    static constraints = {
        filename blank: false, nullable: false
    }
}

/************   INFOS UTILES *******/
/**
 * acces distant aux fichiers
 * String filename = "thumb_123456678.png"
 * peut donc etre accédé par l'app
 */