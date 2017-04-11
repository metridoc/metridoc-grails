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
boolean useInlinePlugin = false
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

grails.project.fork = [
        // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
        //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

        // configure settings for the test-app JVM, uses the daemon by default
        test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
        // configure settings for the run-app JVM
        run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
        // configure settings for the run-war JVM
        war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
        // configure settings for the Console UI JVM
        console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    inherits("global")
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'

    if (useInlinePlugin) {
        //inline plugins require this for some reason
        legacyResolve true
    }

    repositories {

        mavenCentral()
        mavenRepo "http://dl.bintray.com/upennlib/metridoc"
        mavenRepo "http://dl.bintray.com/upennlib/maven"
        mavenLocal()
        grailsCentral()
        mavenRepo "http://repo.grails.org/grails/core"
        mavenRepo "http://repo.grails.org/grails/plugins"
        mavenRepo "https://mvnrepository.com/artifact/org.liquibase/liquibase-core"

    }
    dependencies {
        compile("org.apache.poi:poi:3.8-beta3")
        compile("org.apache.poi:poi-ooxml:3.8-beta3") {
            excludes 'poi'
            excludes 'dom4j'
        }
        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }

    plugins {
        runtime ':database-migration:1.3.6'
        compile ":google-visualization:0.6.2"
        build ":tomcat:7.0.47"

        build(":codenarc:0.18") {
            excludes "log4j", "groovy-all", "ant", "junit"
        }
        if (!useInlinePlugin) {
            compile ":metridoc-core:${coreVersion}"
        }
        test(":spock:0.7") {
            exclude "spock-grails-support"
        }
        runtime ":hibernate:3.6.10.6"
        build(":release:3.0.1", ":bintray-upload:0.2")
    }
}
