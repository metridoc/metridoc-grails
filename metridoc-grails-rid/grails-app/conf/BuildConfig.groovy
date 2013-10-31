grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

//location of the release repository
grails.project.repos.metridocRepo.url = "https://api.bintray.com/maven/upennlib/metridoc/metridoc-rid"
//name of the repository
grails.project.repos.default = "metridocRepo"

grails.project.target.level = 1.6
grails.project.source.level = 1.6

String coreVersion = new File(new File(basedir).parent, "VERSION").getText("utf-8").trim()
boolean coreVersionIsSnapshot = coreVersion.endsWith("SNAPSHOT")

if (coreVersionIsSnapshot) {
    grails.plugin.location."metridoc-core" = "../metridoc-grails-core"
}

grails.project.dependency.resolution = {
    inherits("global")
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'

    if(coreVersionIsSnapshot) {
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
        build(":rest-client-builder:1.0.3")


        if (!coreVersionIsSnapshot) {
            compile ":metridoc-core:${coreVersion}"
        }

        build(":release:2.2.1", ":bintray-upload:0.2", ":tomcat:$grailsVersion") {
            export = false
        }
    }
}