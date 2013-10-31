/**
 * Created with IntelliJ IDEA.
 * User: xiaofant
 * Date: 11/20/12
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
class MetridocRidBootStrap {

    def ridBootStrapService
    def ridManageLibraryUnitSpreadsheetsService
    def init = { servletContext ->
        ridBootStrapService.bootStrapContents()
        ridManageLibraryUnitSpreadsheetsService.transferSpreadsheets()
    }

    def destroy = {}
}