class MetridocGateGrailsPlugin {
    def version = "0.9.2"
    def grailsVersion = "2.1 > *"
    def loadAfter = ['metridocCore']
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def title = "Metridoc Gate Plugin" // Headline display name of the plugin
    def author = "Gavin Young"
    def authorEmail = "zhenghao@seas.upenn.edu"
    def description = '''\
        Plugin to help collect data of time and location of people entering libraries
        '''

    def documentation = "http://grails.org/metridoc/metridoc-grails"
    def doWithSpring = {
        def baseConfig = new ConfigObject()
        def slurper = new ConfigSlurper()
        baseConfig.merge(slurper.parse(GateSQLQueries))
        baseConfig.merge(application.config)
        //by doing this, we make sure the main config is overwritten by the application config
        application.config = baseConfig

        // if(baseConfig.grails.validateable.packages) {
        //     baseConfig.grails.validateable.packages.add 'metridoc.penn.gate'
        // }
        // else {
        //     baseConfig.grails.validateable.packages = ["metridoc.penn.gate"]
        // }
    }
}
