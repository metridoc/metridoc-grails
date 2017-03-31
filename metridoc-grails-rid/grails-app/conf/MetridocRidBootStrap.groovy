/**
 * Created with IntelliJ IDEA.
 * User: xiaofant
 * Date: 11/20/12
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
class MetridocRidBootStrap {

    def ridBootStrapService
    def testDataService //need to modify a function in this service and invoke that function to update database.
    def ridManageLibraryUnitSpreadsheetsService
    def init = { servletContext ->
        testDataService.populateTestFields()
        ridManageLibraryUnitSpreadsheetsService.transferSpreadsheets()
    }

    def destroy = {}
}