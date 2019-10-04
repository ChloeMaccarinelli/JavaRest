package grailstp


class BootStrap {


    def init = { servletContext ->



        def userInstance = new User(username: "Perfection",
                                    password: "password",
                                    thumbnail: new Illustration(filename: 'Mongolito.png')).save(flush: true, failOnError: true)

        (1..5).each {
            userInstance.addToAnnonces(
                new Annonce(
                        title: "title" +it,
                        description: "description",
                        validTill: new Date(),
                        state: Boolean.TRUE,
                        illustration: new Illustration(filename: 'toto.png'),
                        illustrations: [new Illustration(filename: "Mongolito.png"), new Illustration(filename: "toto.png")]
                )

            )
        }

        userInstance.save(flush: true,failOnError: true)

    }
    def destroy = {
    }
}
