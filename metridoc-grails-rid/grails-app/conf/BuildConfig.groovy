grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

//location of the release repository
grails.project.repos.metridocRepo.url = "https://api.bintray.com/maven/upennlib/metridoc/metridoc-rid"
//name of the repository
grails.project.repos.default = "metridocRepo"

grails.project.target.level = 1.7
grails.project.source.level = 1.7

File versionFile = new File(new File(basedir).parent, "VERSION")

String coreVersion
boolean useInlinePlugin
if (versionFile.exists()) {
    coreVersion = versionFile.getText("utf-8").trim()
    useInlinePlugin = coreVersion.endsWith("SNAPSHOT")

    if (useInlinePlugin) {
        grails.plugin.location."metridoc-core" = "../metridoc-grails-core"
    }
}
else {
    def metadata = new XmlSlurper().parse("http://dl.bintray.com/upennlib/metridoc/org/grails/plugins/metridoc-core/maven-metadata.xml")
    coreVersion = metadata.versioning.latest.text().trim()
}

grails.project.dependency.resolution = {
    inherits("global")
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'

    if (useInlinePlugin) {
        //inline plugins require this for some reason
        legacyResolve true
    }

    repositories {
        grailsCentral()
        mavenCentral()
        mavenRepo "http://dl.bintray.com/upennlib/metridoc"
        mavenRepo "http://dl.bintray.com/upennlib/maven"
    }
    dependencies {
        compile("org.apache.poi:poi:3.8-beta3")
        compile("org.apache.poi:poi-ooxml:3.8-beta3") {
            excludes 'poi'
            excludes 'dom4j'
        }

    }

    plugins {
        compile ":google-visualization:0.6.2"

        if (!useInlinePlugin) {
            compile ":metridoc-core:${coreVersion}"
        }
    }
}