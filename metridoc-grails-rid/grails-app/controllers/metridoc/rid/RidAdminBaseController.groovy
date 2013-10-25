package metridoc.rid

abstract class RidAdminBaseController {

    /**
     * This class provides the common methods for creating, editing, and deleting domain objects
     * Each of the AdminFooControllers extends this base class and has a domainClass field
     */

    abstract Class getDomainClass()

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static accessControl = {
        role(name: "ROLE_ADMIN")
    }

    /**
     * Index and List set a session attribute to ensure that after doing administrative actions, you are redirected properly
     */
    def index() {
        session.setAttribute("prev", new String(getClass().getSimpleName().minus("Controller")))
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        session.setAttribute("prev", new String(getClass().getSimpleName().minus("Controller")))
        params.max = Math.min(max ?: 10, 100)
        [ridInstanceList: domainClass.list(params), ridInstanceTotal: domainClass.count()]
    }

    def create() {
        [ridInstance: domainClass.newInstance(params)]
    }

    def save() {
        withForm {
            def ridInstance = domainClass.newInstance(params)
            if (!ridInstance.save(flush: true)) {
                chain(action: "list", model: [DomainClassError: ridInstance])
                return
            }

            flash.message = message(code: 'default.created.message', args: [message(code: 'domainClass.label',
                    default: 'domainClass'),
                    ridInstance.id])
            redirect(action: "list")
        }.invalidToken {
            flash.alerts << "Don't click the create button more than one time to make duplicated submission!"
            redirect(action: "list")
        }
    }

    def edit(Long id) {
        def ridInstance = domainClass.get(id)
        if (!ridInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'domainClass.label',
                    default: 'domainClass'), id])
            redirect(action: "list")
            return
        }

        [ridInstance: ridInstance]
    }

    def update(Long id, Long version) {
        withForm {
            def ridInstance = domainClass.get(id)
            def oldname = ridInstance.name
            if (!ridInstance) {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'domainClass.label',
                        default: 'domainClass'), id])
                redirect(action: "list")
                return
            }

            if (version != null) {
                if (ridInstance.version > version) {
                    ridInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                            [message(code: 'domainClass.label', default: 'domainClass')] as Object[],
                            "Another rank has updated this domainClass while you were editing")
                    render(view: "list", model: [DomainClassError: ridInstance])
                    return
                }
            }

            ridInstance.properties = params

            if (!ridInstance.save(flush: true)) {
                chain(action: "list", model: [DomainClassError: ridInstance])
                return
            }

            flash.message = message(code: 'default.updated.message', args: [message(code: 'domainClass.label',
                    default: 'domainClass'),
                    ridInstance.id])
            redirect(action: "list")
        }.invalidToken {
            flash.alerts << "Don't click the update button more than one time to make duplicated submission!"
            redirect(action: "list")
        }
    }
}
