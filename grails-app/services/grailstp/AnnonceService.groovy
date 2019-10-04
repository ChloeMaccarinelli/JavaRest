package grailstp

import grails.gorm.services.Service

@Service(Annonce)
interface AnnonceService {

    Annonce get(Serializable id)

    Annonce save(Annonce annonce)

    List<Annonce> list(Map args)


    Long count()

    void delete(Serializable id)
}