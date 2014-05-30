class MetridocRidGrailsPlugin {
    def version = "0.7.20"
    def grailsVersion = "2.1 > *"
    def loadAfter = ['metridocCore']
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def title = "Metridoc Rid Plugin" // Headline display name of the plugin
    def author = "Thomas Apicella"
    def authorEmail = ""
    def description = '''\
Plugin to help collect reference instructional data
'''

    def documentation = "http://grails.org/metridoc/metridoc-grails"
}
