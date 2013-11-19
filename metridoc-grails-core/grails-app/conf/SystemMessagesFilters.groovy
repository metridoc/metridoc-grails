
import grails.util.Environment

class SystemMessagesFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                if(Environment.current!=Environment.PRODUCTION){
                    flash.message = "This server is in ${Environment.current.getName()} mode. Data may be deleted!"
                    if(System.getProperty("shutdownDate")!="")
                        flash.alert = "The server will be shut down for maintenance on ${System.getProperty("shutdownDate")}"
                }
            }

        }
    }
}
