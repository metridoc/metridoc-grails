/*
  *Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
  *	Educational Community License, Version 2.0 (the "License"); you may
  *	not use this file except in compliance with the License. You may
  *	obtain a copy of the License at
  *
  *http://www.osedu.org/licenses/ECL-2.0
  *
  *	Unless required by applicable law or agreed to in writing,
  *	software distributed under the License is distributed on an "AS IS"
  *	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
  *	or implied. See the License for the specific language governing
  *	permissions and limitations under the License.
  */

package metridoc.rid

import grails.converters.JSON
import org.apache.poi.openxml4j.exceptions.InvalidFormatException
import org.apache.poi.ss.usermodel.Workbook
import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartFile
import java.util.regex.Matcher
import java.util.regex.Pattern

import java.text.SimpleDateFormat

class RidTransactionController {

    static homePage = [title      : "Research Consultation & Instruction Database",
                       description: "Add/Update/Review data on consultation and instructional activity"]

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static boolean isProtected = true

    def ajaxChooseType = {
        def response = ridTransactionService.getFieldsByLibraryUnit(params, session.getAttribute("transType"))
        render response as JSON
    }

    def ridTransactionService
    def spreadsheetService
    def ridManageLibraryUnitSpreadsheetsService
    def ridStatisticsService

    /**
     * This controller handles both consultation and instruction transactions
     * The type is controlled by a session attribute, and is set to consultation by default
     * @return
     */
    def index() {
        session.setAttribute("transType", new String("consultation"))//Sets default mode to consultation
        redirect(action: "create")
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        if (session.getAttribute("transType") == "consultation") {
            [ridTransactionInstanceList : RidConsTransaction.list(params),
             ridTransactionInstanceTotal: RidConsTransaction.count(),
             ridTransactionAllList      : RidConsTransaction.list()]
        } else {
            [ridTransactionInstanceList : RidInsTransaction.list(params),
             ridTransactionInstanceTotal: RidInsTransaction.count(),
             ridTransactionAllList      : RidInsTransaction.list()]
        }
    }

    //TODO: Refactor using an abstract getTransactionClass like the admin controllers do to remove duplicated code

    def create() {

        //Generating a list of departments here, rather than using AJAX once the page is loaded
        def depts = RidDepartment.where { name != "" }.sort('name')
        def ridDepartmentInstanceList = depts.list()
        def ridDepartmentInstanceTotal = RidDepartment.count()

        if (session?.getAttribute("transType") == null) {
            session.setAttribute("transType", new String("consultation"))//Sets default mode to consultation
        }
        session.setAttribute("prev", new String("create"))

        if (session.getAttribute("transType") == "consultation") {
            try {
                RidConsTransactionBase ridTransactionInstance = new RidConsTransaction(params)
                if (params.tmp != null && RidConsTransactionTemplate.get(Long.valueOf(params.tmp))) {
                    ridTransactionInstance = RidConsTransactionTemplate.get(Long.valueOf(params.tmp))
                }
                [ridTransactionInstance    : ridTransactionInstance,
                 ridDepartmentInstanceList : ridDepartmentInstanceList,
                 ridDepartmentInstanceTotal: ridDepartmentInstanceTotal]
            } catch (Exception e) {
                flash.alerts << e.message
                if (params.tmp.equals("templateList"))
                    redirect(action: "templateList")
                else
                    [ridTransactionInstance    : new RidConsTransaction(params),
                     ridDepartmentInstanceList : ridDepartmentInstanceList,
                     ridDepartmentInstanceTotal: ridDepartmentInstanceTotal]
            }
        } else {
            try {
                RidInsTransactionBase ridTransactionInstance = new RidInsTransaction(params)
                if (params.tmp != null && RidInsTransactionTemplate.get(Long.valueOf(params.tmp))) {
                    ridTransactionInstance = RidInsTransactionTemplate.get(Long.valueOf(params.tmp))
                }
                [ridTransactionInstance    : ridTransactionInstance,
                 ridDepartmentInstanceList : ridDepartmentInstanceList,
                 ridDepartmentInstanceTotal: ridDepartmentInstanceTotal]
            } catch (Exception e) {
                flash.alerts << e.message
                if (params.tmp.equals("templateList"))
                    redirect(action: "templateList")
                else
                    [ridTransactionInstance    : new RidInsTransaction(params),
                     ridDepartmentInstanceList : ridDepartmentInstanceList,
                     ridDepartmentInstanceTotal: ridDepartmentInstanceTotal]
            }
        }
    }

    def save() {

        if (session.getAttribute("transType") == "consultation") {
            withForm {
                def ridTransactionInstance


                if (!params.dateOfConsultation.empty)
                    params.dateOfConsultation = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfConsultation);
                ridTransactionInstance = new RidConsTransaction(params)
//                ridTransactionInstance.testField = params.testField;
                ridTransactionService.createNewConsInstanceMethod(params, ridTransactionInstance)
                if (!ridTransactionInstance.save(flush: true)) {
                    render(view: "create", model: [ridTransactionInstance: ridTransactionInstance])
                    return
                }

                flash.message = message(code: 'default.created.message',
                        args: [message(code: 'ridTransaction.label',
                                default: 'RidConsTransaction'), ridTransactionInstance.id])
                redirect(action: "show", id: ridTransactionInstance.id)
            }.invalidToken {
                flash.alerts << "Don't click the create button more than one time to make duplicated submission!"
                redirect(action: "list")
            }
        } else {
            withForm {
                def ridTransactionInstance

                if (!params.dateOfInstruction.empty)
                    params.dateOfInstruction = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfInstruction);
                ridTransactionInstance = new RidInsTransaction(params)
                ridTransactionService.createNewInsInstanceMethod(params, ridTransactionInstance)
                if (!ridTransactionInstance.save(flush: true)) {
                    render(view: "create", model: [ridTransactionInstance: ridTransactionInstance])
                    return
                }

                flash.message = message(code: 'default.created.message',
                        args: [message(code: 'ridTransaction.label',
                                default: 'RidInsTransaction'), ridTransactionInstance.id])
                redirect(action: "show", id: ridTransactionInstance.id)
            }.invalidToken {
                flash.alerts << "Don't click the create button more than one time to make duplicated submission!"
                redirect(action: "list")
            }
        }
    }


    def update(Long id, Long version) {

        if (session.getAttribute("transType") == "consultation") {
            withForm {
                def ridTransactionInstance = RidConsTransaction.get(id)
                if (!ridTransactionInstance) {
                    flash.message = message(code: 'default.not.found.message',
                            args: [message(code: 'ridTransaction.label',
                                    default: 'RidConsTransaction'), id])
                    redirect(action: "list")
                    return
                }

                if (version != null) {
                    if (ridTransactionInstance.version > version) {
                        ridTransactionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                                [message(code: 'ridTransaction.label', default: 'RidConsTransaction')] as Object[],
                                "Another rank has updated this RidConsTransaction while you were editing")
                        render(view: "edit", model: [ridTransactionInstance: ridTransactionInstance])
                        return
                    }
                }

                if (!params.dateOfConsultation.empty)
                    params.dateOfConsultation = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfConsultation);
                ridTransactionInstance.properties = params
                ridTransactionService.createNewConsInstanceMethod(params, ridTransactionInstance)
                if (!ridTransactionInstance.save(flush: true)) {
                    render(view: "edit", model: [ridTransactionInstance: ridTransactionInstance])
                    return
                }

                flash.message = message(code: 'default.updated.message',
                        args: [message(code: 'ridTransaction.label',
                                default: 'RidConsTransaction'), ridTransactionInstance.id])
                redirect(action: "show", id: ridTransactionInstance.id)
            }.invalidToken {
                flash.alerts << "Don't click the update button more than one time to make duplicated submission!"
                redirect(action: "list")
            }
        } else {
            withForm {
                def ridTransactionInstance = RidInsTransaction.get(id)
                if (!ridTransactionInstance) {
                    flash.message = message(code: 'default.not.found.message',
                            args: [message(code: 'ridTransaction.label',
                                    default: 'RidInsTransaction'), id])
                    redirect(action: "list")
                    return
                }

                if (version != null) {
                    if (ridTransactionInstance.version > version) {
                        ridTransactionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                                [message(code: 'ridTransaction.label', default: 'RidInsTransaction')] as Object[],
                                "Another rank has updated this RidInsTransaction while you were editing")
                        render(view: "edit", model: [ridTransactionInstance: ridTransactionInstance])
                        return
                    }
                }

                if (!params.dateOfInstruction.empty)
                    params.dateOfInstruction = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfInstruction);
                ridTransactionInstance.properties = params
                ridTransactionService.createNewInsInstanceMethod(params, ridTransactionInstance)
                if (!ridTransactionInstance.save(flush: true)) {
                    render(view: "edit", model: [ridTransactionInstance: ridTransactionInstance])
                    return
                }


                flash.message = message(code: 'default.updated.message',
                        args: [message(code: 'ridTransaction.label',
                                default: 'RidInsTransaction'), ridTransactionInstance.id])
                redirect(action: "show", id: ridTransactionInstance.id)
            }.invalidToken {
                flash.alerts << "Don't click the update button more than one time to make duplicated submission!"
                redirect(action: "list")
            }
        }
    }
/**
 * Note that this method controls the "edit" view. Update is responsible for actually changing the transaction
 */
    def edit(Long id) {
        //Generating a list of departments here, rather than using AJAX once the page is loaded
        def depts = RidDepartment.where { name != "" }.sort('name')
        def ridDepartmentInstanceList = depts.list()
        def ridDepartmentInstanceTotal = RidDepartment.count()


        if (session.getAttribute("transType") == "consultation") {
            def ridTransactionInstance = RidConsTransaction.get(id)
            if (!ridTransactionInstance) {
                flash.message = message(code: 'default.not.found.message',
                        args: [message(code: 'ridTransaction.label',
                                default: 'RidConsTransaction'), id])
                redirect(action: "list")
                return
            }

            [ridTransactionInstance: ridTransactionInstance]
        } else {
            def ridTransactionInstance = RidInsTransaction.get(id)
            if (!ridTransactionInstance) {
                flash.message = message(code: 'default.not.found.message',
                        args: [message(code: 'ridTransaction.label',
                                default: 'RidInsTransaction'), id])
                redirect(action: "list")
                return
            }

            [ridTransactionInstance    : ridTransactionInstance,
             ridDepartmentInstanceList : ridDepartmentInstanceList,
             ridDepartmentInstanceTotal: ridDepartmentInstanceTotal]
        }
    }

    def show(Long id) {

        if (session.getAttribute("transType") == "consultation") {
            def ridTransactionInstance = RidConsTransaction.get(id)
            if (!ridTransactionInstance) {
                flash.message = message(code: 'default.not.found.message',
                        args: [message(code: 'ridTransaction.label',
                                default: 'RidConsTransaction'), id])
                redirect(action: "list")
                return
            }

            [ridTransactionInstance: ridTransactionInstance]
        } else {
            def ridTransactionInstance = RidInsTransaction.get(id)
            if (!ridTransactionInstance) {
                flash.message = message(code: 'default.not.found.message',
                        args: [message(code: 'ridTransaction.label',
                                default: 'RidInsTransaction'), id])
                redirect(action: "list")
                return
            }

            [ridTransactionInstance: ridTransactionInstance]
        }
    }

    def delete(Long id) {

        if (session.getAttribute("transType") == "consultation") {
            RidConsTransactionBase ridTransactionInstance = RidConsTransaction.get(id)
            if (params.isTemplate == 'true')
                ridTransactionInstance = RidConsTransactionTemplate.get(id)
            if (!ridTransactionInstance) {
                flash.message = message(code: 'default.not.found.message',
                        args: [message(code: 'ridTransaction.label',
                                default: 'RidConsTransaction'), id])
                redirect(action: "list")
                return
            }

            def msg = message(code: 'ridTransaction.label', default: 'RidConsTransaction')
            if (ridTransactionInstance.properties.containsKey('templateOwner'))
                msg = message(code: 'ridTransaction.label', default: 'RidConsTransaction Template')

            try {
                ridTransactionInstance.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [msg, id])
                redirect(action: "list")
            }
            catch (DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [msg, id])
                redirect(action: "show", id: id)
            }
        } else {
            RidInsTransactionBase ridTransactionInstance = RidInsTransaction.get(id)
            if (params.isTemplate == 'true')
                ridTransactionInstance = RidInsTransactionTemplate.get(id)
            if (!ridTransactionInstance) {
                flash.message = message(code: 'default.not.found.message',
                        args: [message(code: 'ridTransaction.label',
                                default: 'RidInsTransaction'), id])
                redirect(action: "list")
                return
            }

            def msg = message(code: 'ridTransaction.label', default: 'RidInsTransaction')
            if (ridTransactionInstance.properties.containsKey('templateOwner'))
                msg = message(code: 'ridTransaction.label', default: 'RidInsTransaction Template')
            try {
                ridTransactionInstance.delete(flush: true)
                flash.message = message(code: 'default.deleted.message', args: [msg, id])
                redirect(action: "list")
            }
            catch (DataIntegrityViolationException e) {
                flash.message = message(code: 'default.not.deleted.message', args: [msg, id])
                redirect(action: "show", id: id)
            }
        }

    }

    /**
     * Controls the search view. Query carries out the search itself
     */
    def search() {
        session.setAttribute("prev", new String("search"))
    }

    def query(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def queryResult = ridTransactionService.queryMethod(params, session.getAttribute("transType"))

        render(view: "list",
                model: [ridTransactionInstanceList : queryResult.list(params),
                        ridTransactionInstanceTotal: queryResult.count(),
                        ridTransactionAllList      : queryResult.list()])

        return
    }


    def spreadsheetUpload() {
        session.setAttribute("prev", new String("spreadsheetUpload"))
    }

    def upload() {

        withForm {
            MultipartFile uploadedFile = request.getFile("spreadsheetUpload")
            Workbook wb
            if (uploadedFile == null || uploadedFile.empty) {
                flash.alerts << "No file was provided"
                redirect(action: "spreadsheetUpload")
                return
            }

            if (!spreadsheetService.checkFileType(uploadedFile.getContentType())) {
                flash.alerts << "Invalid File Type. Only Excel Files are accepted!"
                redirect(action: "spreadsheetUpload")
                return
            }

            def valNameResponse = spreadsheetService.validateFilename(uploadedFile.originalFilename)
            if (valNameResponse != "good") {
                flash.alerts << valNameResponse
                redirect(action: "spreadsheetUpload")
                return
            }


            try {
                wb = spreadsheetService.convertToWorkbook(uploadedFile)
            } catch (InvalidFormatException e) {
                flash.message = message(code: "spreadsheet.illegal.argument")
                return
            }

            if (!spreadsheetService.checkSpreadsheetFormat(wb)) {
                flash.alerts << "Invalid Spreadsheet Format. Cannot Parse it."
                redirect(action: "spreadsheetUpload")
                return
            }
            def skipCons = false
            def skipIns = false

            if (RidConsTransaction.findBySpreadsheetName(uploadedFile.originalFilename)) {
                flash.alerts << "This spreadsheet has been uploaded before. Skipping consultation upload."
                skipCons = true
            }
            if (RidInsTransaction.findBySpreadsheetName(uploadedFile.originalFilename)) {
                flash.alerts << "This spreadsheet has been uploaded before. Skipping instructional upload"
                skipIns = true
            }

            TreeMap<String, ArrayList<ArrayList<String>>> allInstances = spreadsheetService.getAllInstances(wb, flash, skipCons, skipIns)
            if (!allInstances?.size()) {
                redirect(action: "spreadsheetUpload")
                return
            }
            def numCons = allInstances?.get("cons")?.size() ?: 0
            def numIns = allInstances?.get("ins")?.size() ?: 0
            log.warn "***********************"
            log.warn "${allInstances}"
            log.warn "${numCons}"
            log.warn "${numIns}"
            log.warn "***********************"

            if (spreadsheetService.saveToDatabase(allInstances, uploadedFile.originalFilename, flash)) {
                flash.infos << "Spreadsheet successfully uploaded. " +
                        String.valueOf(numCons) + " consultation instances and " + String.valueOf(numIns) + " instructional instances uploaded."
                redirect(action: "spreadsheetUpload")
            } else {
                redirect(action: "spreadsheetUpload")
                return
            }
        }.invalidToken {
            flash.alerts << "Don't click the uploading button more than one time to make duplicated submission!"
            redirect(action: "spreadsheetUpload")
        }
    }

    def export() {
        def queryResult = ridTransactionService.queryMethod(params, session.getAttribute("transType"))

        if (queryResult.count()) {
            Workbook wb = spreadsheetService.exportAsFile(queryResult.list(), session.getAttribute("transType"))

            try {
                response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
                response.setHeader("Content-disposition",
                        "filename=Transaction_List_" + new Date().format("MMddyyyy-HHmmss"))
                wb.write(response.outputStream) // Performing a binary stream copy
            } catch (Exception e) {
                flash.alerts << e.message
            }
        }
    }

    def download() {
        if (ridManageLibraryUnitSpreadsheetsService.download(response, flash, params) == false) {
            redirect(action: "index")
        }
    }

    /**
     * These two methods are used in the WIP form templates
     */
    def templateList() {

        if (SecurityUtils.getSubject().getPrincipal()) {
            if (session.getAttribute("transType") == "consultation") {
                def query = RidConsTransactionTemplate.where {
                    templateOwner == SecurityUtils.getSubject().getPrincipal().toString()
                }
                [ridTransactionInstanceList: query.list()]
            } else {
                def query = RidInsTransactionTemplate.where {
                    templateOwner == SecurityUtils.getSubject().getPrincipal().toString()
                }
                [ridTransactionInstanceList: query.list()]
            }
        } else {
            redirect(action: "create")
        }
    }

    def remember() {

        if (session.getAttribute("transType") == "consultation") {
            withForm {
                def ridTransactionInstance
                if (!params.dateOfConsultation.empty)
                    params.dateOfConsultation = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfConsultation);
                ridTransactionInstance = new RidConsTransactionTemplate(params)
                ridTransactionInstance.templateOwner = SecurityUtils.getSubject().getPrincipal().toString()
                ridTransactionService.createNewConsInstanceMethod(params, ridTransactionInstance)
                if (!ridTransactionInstance.save(flush: true)) {
                    render(view: "create", model: [ridTransactionInstance: ridTransactionInstance])
                    return
                }

                flash.message = message(code: 'default.created.message',
                        args: [message(code: 'ridTransaction.label',
                                default: 'RidConsTransaction Template'), ridTransactionInstance.id])
                redirect(action: "create")
            }.invalidToken {
                if (SecurityUtils.getSubject().getPrincipal())
                    flash.alerts << "Don't click the remember button more than one time to make duplicated submission!"
                redirect(action: "list")
            }
        } else {
            withForm {
                def ridTransactionInstance
                if (!params.dateOfInstruction.empty)
                    params.dateOfInstruction = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfInstruction);
                ridTransactionInstance = new RidInsTransactionTemplate(params)
                ridTransactionInstance.templateOwner = SecurityUtils.getSubject().getPrincipal().toString()
                ridTransactionService.createNewInsInstanceMethod(params, ridTransactionInstance)
                if (!ridTransactionInstance.save(flush: true)) {
                    render(view: "create", model: [ridTransactionInstance: ridTransactionInstance])
                    return
                }

                flash.message = message(code: 'default.created.message',
                        args: [message(code: 'ridTransaction.label',
                                default: 'RidInsTransaction Template'), ridTransactionInstance.id])
                redirect(action: "create")
            }.invalidToken {
                if (SecurityUtils.getSubject().getPrincipal())
                    flash.alerts << "Don't click the remember button more than one time to make duplicated submission!"
                redirect(action: "list")
            }
        }

    }

    /**
     * These methods are used in the WIP statistical analysis features
     */


    def stats() {
        def queryResult = ridStatisticsService.getStats(params, session.getAttribute("transType"))
        render(view: "/ridTransaction/stats",
                model: [statResults: queryResult])
        session.setAttribute("prev", "stats")
        return
    }

    def statSearch() {
        session.setAttribute("prev", new String("statSearch"))
    }

    def statGraph() {
        def queryResult = ridStatisticsService.getStats(params, session.getAttribute("transType"))
        render(view: "/ridTransaction/statGraph",
                model: [statResults: queryResult])
        session.setAttribute("prev", "statGraph")
        return
    }

    /*
    def statGraph() {
        def queryResult = ridStatisticsService.statGraph(params, session.getAttribute("transType"))
        render(view: "/ridTransaction/statGraph",
                model: [statResults: queryResult])
        session.setAttribute("prev", "statGraph")
        return
    }*/


    def statQuery(Integer max) {
        def queryResult = ridStatisticsService.searchStats(params, session.getAttribute("transType"))

        render(view: "/ridTransaction/searchStatResults",
                model: [statResults: queryResult])
        session.setAttribute("prev", new String("statSearch"))
        return
    }

    /**
     * Functions for switching between consultation and instructional, and between transaction and admin views
     */
    def consultation() {
        session.setAttribute("transType", new String("consultation"))
        redirect(action: session.getAttribute("prev"))
    }

    def instructional() {
        session.setAttribute("transType", new String("instructional"))
        redirect(action: session.getAttribute("prev"))
    }

    def switchMode() {
        redirect(controller: "RidAdminTransaction", action: "index")
    }
}