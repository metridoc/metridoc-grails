class MetridocFundsGrailsPlugin {
    def version = "0.8.9"
    def grailsVersion = "2.0 > *"

    def title = "Metridoc Illiad Plugin" // Headline display name of the plugin
    def author = "Thomas Barker"
    def authorEmail = "tbarker@pobox.upenn.edu"
    def description = '''\
Simple dashboard is provided
to display some simple stats for the current fiscal year and to download all available data
'''

    def documentation = "https://github.com/metridoc/metridoc-grails"
    def doWithSpring = {
        def baseConfig = new ConfigObject()
        def slurper = new ConfigSlurper()
        baseConfig.merge(slurper.parse(FundsQueries))
        baseConfig.merge(slurper.parse(FundsAppConfig))
        baseConfig.merge(application.config)
        //by doing this, we make sure the main config is overwritten by the application config
        application.config = baseConfig
    }
}
