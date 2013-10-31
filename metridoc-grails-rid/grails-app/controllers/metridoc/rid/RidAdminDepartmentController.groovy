package metridoc.rid

class RidAdminDepartmentController extends RidAdminBaseController {
    Class domainClass = RidDepartment

    /**
     * For use in department list modals
     */
    def departmentList() {
        def instances = RidDepartment.where { name != "" }.sort('name')
        [ridDepartmentInstanceList: instances.list(), ridDepartmentInstanceTotal: instances.count()]
    }
}
