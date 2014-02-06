class MetridocBdGrailsPlugin {
    def version = "0.7.14-SNAPSHOT"
    def grailsVersion = "2.0.4 > *"

    def loadAfter = ['metridocCore']

    def title = "Metridoc Borrow Direct Plugin" // Headline display name of the plugin
    def author = "Thomas Barker"
    def authorEmail = "tbarker@pobox.upenn.edu"
    def description = '''\

    Provides Borrow Direct plugin for metridoc
'''
    def documentation = "https://github.com/metridoc/metridoc-grails.git"
    def license = "ECL2"
    def organization = [name: "University of Pennsylvania", url: "http://www.upenn.edu/"]
    def issueManagement = [system: "GitHuB", url: "https://github.com/metridoc/metridoc-grails.git"]
    def scm = [url: "https://github.com/metridoc/metridoc-grails.git"]
    def doWithSpring = {
        def baseConfig = new ConfigObject()
        baseConfig.datafarm.title.ext = " - BETA"
        baseConfig.datafarm.minFiscalYear=2011
        baseConfig.datafarm.minCalYear=2010
        def slurper = new ConfigSlurper()
        baseConfig.merge(slurper.parse(Queries))
        baseConfig.merge(application.config)
        //by doing this, we make sure the main config is overwritten by the application config
        application.config = baseConfig

        if(baseConfig.grails.validateable.packages) {
            baseConfig.grails.validateable.packages.add 'metridoc.penn.bd'
        }
        else {
            baseConfig.grails.validateable.packages = ["metridoc.penn.bd"]
        }
    }
}