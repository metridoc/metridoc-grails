/*
  *Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
  *	Educational Community License, Version 2.0 (the "License"); you may
  *	not use this file except in compliance with the License. You may
  *	obtain a copy of the License at
  *
  *http://www.osedu.org/licenses/ECL-2.0
  *
  *	Unless required by applicable law or agreed to in writing,
  *	software distributed under the License is distributed on an "AS IS"
  *	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
  *	or implied. See the License for the specific language governing
  *	permissions and limitations under the License.  */

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
grails.project.repos.metridocRepo.url = "https://api.bintray.com/maven/upennlib/metridoc/metridoc-illiad"
grails.project.repos.default = "metridocRepo"


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

grails.project.dependency.resolution = {

    if(coreVersionIsSnapshot) {
        //inline plugins require this for some reason
        legacyResolve true
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

    grails.project.dependency.resolver = "maven"

    inherits("global")
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        inherits true // Whether to inherit repository definitions from plugins
        mavenLocal()
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        mavenRepo "https://oss.sonatype.org/content/repositories/snapshots"
        mavenRepo "http://dl.bintray.com/upennlib/metridoc"
        mavenRepo "http://dl.bintray.com/upennlib/maven"
        mavenRepo "http://jcenter.bintray.com/"
    }

    dependencies {
        compile 'net.sf.opencsv:opencsv:2.3'
        //not sure why I need this, but the resolution step could not find it
        test 'org.springframework:spring-test:3.2.5.RELEASE'
    }

    plugins {
        if (!useInlinePlugin) {
            compile ":metridoc-core:${coreVersion}"
        }
        build(":tomcat:$grailsVersion")
        build(":release:2.2.1", ":bintray-upload:0.2")
        build(":codenarc:0.18") {
            excludes "log4j", "groovy-all", "ant", "junit"
        }
        build(':squeaky-clean:0.2')
    }
}
