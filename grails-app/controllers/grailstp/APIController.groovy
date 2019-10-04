package grailstp

import grails.converters.JSON
import grails.converters.XML

class APIController {

    /**
     {
     "annonce": {
     "title": "TestPost",
     "description": "TestDescPost",
     "validTill": "10-10-2020",
     "illustration": "toto.png"
     }
     }
     */

    def annonceService

    /**
     * TEST API REST DANS LE CAS D'UNE COLLECTION D'ANNONCE
     */

    def getAllAnnonces() {
        switch(request.getMethod()){
            case "GET" :
                def annoncesInstance = Annonce.getAll()
                if (!annoncesInstance)
                    return response.status = 404
                response.withFormat {
                    xml { render annoncesInstance as XML}
                    json { render annoncesInstance as JSON }
                }
                break
            case "POST" :
                if(request.getJSON().annonce != null) {
                    println("G ANNONSS")

                    def usr = new User(username: "Perfection",
                            password: "password",
                            thumbnail: new Illustration(filename: 'Mongolito.png')).save(flush: true, failOnError: true)

                    Annonce annonce = new Annonce(
                            title: request.getJSON().annonce.title,
                            description: request.getJSON().annonce.description,
                            validTill: new Date(),
                            state: Boolean.TRUE,
                            illustration: new Illustration(filename: request.getJSON().annonce.illustration),
                            author : usr
                    )

                    if(annonce.title == null || annonce.description == null || annonce.validTill == null || annonce.illustration == null) {
                        return response.status = 406
                    }
                    annonceService.save(annonce)

                    return response.status = 201
                } else {
                    println("G PA ANNONSS")
                    return response.status = 400
                }
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }


    /**
     * TEST API REST DANS LE CAS  D'UNE ANNONCE
     */

    def getAnnonce() {
        switch(request.getMethod()){


        /**
         * TEST API REST DANS LE CAS D'UNE RECUPERATION DES DONNEES D'ANNONCE
         */

            case "GET" :
                if(!params.id) {
                    return response.status = 400
                }
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404
                response.withFormat {
                    xml { render annonceInstance as XML}
                    json { render annonceInstance as JSON }
                }
                break

        /**
         * TEST API REST DANS LE CAS D'UNE MODIFICATION D'ANNONCE
         */

            case "PUT" :
                if(request.getJSON().annonce.id != null) {
                    println("G PUT ANNONSS")

                    def annonce = null

                    try {
                        annonce = Annonce.get(request.getJSON().annonce.id)
                    }
                    catch (Exception e){
                        return response.status = 404
                    }

                    if(annonce == null) return response.status = 404

                    def prop = annonce.properties

                    if(request.getJSON().annonce.title != null) {
                        prop.title = request.getJSON().annonce.title
                    } else {
                        prop.title = annonceService.get(request.getJSON().annonce.id).title
                    }

                    if(request.getJSON().annonce.description != null) {
                        prop.description = request.getJSON().annonce.description
                    } else {
                        prop.description = annonceService.get(request.getJSON().annonce.id).description
                    }

                    if(request.getJSON().annonce.state != null) {
                        prop.state = request.getJSON().annonce.state
                    } else {
                        prop.state = annonceService.get(request.getJSON().annonce.id).state
                    }

                    if(request.getJSON().annonce.illustration != null) {
                        prop.illustration = new Illustration(filename: request.getJSON().annonce.illustration)
                    } else {
                        prop.illustration = annonceService.get(request.getJSON().annonce.id).illustration
                    }

                    /**
                     * Dois-t-on autorise la modification de plusieurs illustrations ?
                     *
                    if(request.getJSON().annonce.illustrations != null) {
                        annonce.illustrations = new Illustration(filename : request.getJSON().annonce.illustrations)
                    } else {
                    } */

                    /**
                     * Dois-t-on autoriser le changement de user ? Je ne pense pas personellement
                     *
                    if(request.getJSON().annonce.author != null) {
                        annonce.author = request.getJSON().annonce.author
                    } else {

                    } */

                    annonceService.save(annonce)

                    return response.status = 200
                } else {
                    println("G PA PUT ANNONSS")
                    return response.status = 400
                }
                break
        /**
         * TEST API REST DANS LE CAS D'UNE MODIFICATION PARTIELLE D'ANNONCE
         */

            case "PATCH" :
                if(params.id != null) {
                    println("G PATCH ANNONSS")


                    def annonce = Annonce.get(params.id)

                    if(annonce == null) {
                        return response.status = 406
                    }

                    def prop = annonce.properties

                    if(params.get("title") != null) {
                        prop.title = params.get("title")
                        println("G TROUVE TITLE")
                    } else {
                        prop.title = annonceService.get(params.id).title
                    }

                    if(params.get("description") != null) {
                        prop.description = params.get("description")
                    } else {
                        prop.description = annonceService.get(params.id).description
                    }

                    if(params.get("state") != null) {
                        prop.state = params.get("state")
                    } else {
                        prop.state = annonceService.get(params.id).state
                    }

                    annonceService.save(annonce)

                    return response.status = 200
                } else {
                    println("G PA PUT ANNONSS")
                    return response.status = 400
                }
                break

        /**
         * TEST API REST DANS LE CAS D'UNE SUPPRESSION D'ANNONCE
         */
            case "DELETE":

                if(annonceService.get(request.getJSON().annonce.id) == null) {
                    return response.status = 406
                }

                try {
                    annonceService.delete(request.getJSON().annonce.id)
                } catch (Exception e) {
                    return response.status = 406
                }

                return response.status = 200
                break

            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

}


