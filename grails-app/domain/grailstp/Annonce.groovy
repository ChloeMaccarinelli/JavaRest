package grailstp

import org.springframework.web.multipart.MultipartFile

class Annonce {

    String title
    String description
    Date dateCreated
    Date validTill
    Boolean state = Boolean.FALSE
    Illustration illustration
    static hasOne = [author: User] // une annonce est issue d'UN seul auteur ( hasOne au lieu de belongTo )
    static hasMany = [illustrations: Illustration]



    static constraints = {
        title blank: false, nullable: false
        description blank: false, nullable: false
        validTill nullable: false
        illustration blank: false, nullable: false
        illustrations blank: false, display: true
    }


    @Override
    String toString(){
        return title
    }
}
