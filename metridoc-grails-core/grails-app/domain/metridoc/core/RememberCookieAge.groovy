package metridoc.core

class RememberCookieAge {

    Integer ageInSeconds = 60 * 60 //one hour

    static constraints = {
        ageInSeconds(min: 0)
    }

    static RememberCookieAge getInstance() {
        if(RememberCookieAge.count == 0) {
            RememberCookieAge.withNewTransaction{ new RememberCookieAge().save(flush: true) }
        }

        RememberCookieAge.list().get(0)
    }
}
