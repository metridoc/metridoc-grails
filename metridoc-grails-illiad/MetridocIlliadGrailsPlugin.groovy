class MetridocIlliadGrailsPlugin {
    def version = "0.9.3"
    def grailsVersion = "2.0 > *"
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def title = "Metridoc Illiad Plugin" // Headline display name of the plugin
    def author = "Thomas Barker"
    def authorEmail = "tbarker@pobox.upenn.edu"
    def description = '''\
Simple dashboard is provided
to display some simple stats for the current fiscal year and to download all available data
'''

    def documentation = "https://github.com/metridoc/metridoc-grails"
}
