class MetridocGateGrailsPlugin {
    def version = "0.9.0"
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
}
