/*
  - Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
//grails.project.war.file = "target/${appName}-${appVersion}.war"

//location of the release repository
grails.project.repos.metridocRepo.url = "https://api.bintray.com/maven/upennlib/metridoc/metridoc-core"
//name of the repository
grails.project.repos.default = "metridocRepo"

codenarc.properties = {
    // Each property definition is of the form:  RULE.PROPERTY-NAME = PROPERTY-VALUE
    GrailsPublicControllerMethod.enabled = false
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
    // inherit Grails' default dependencies, this has to be here
    inherits("global")
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

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

    //standard jar maven dependencies
    dependencies {
        compile("mysql:mysql-connector-java:5.1.20")
        compile('org.jasypt:jasypt:1.9.0')
        compile('com.google.guava:guava:13.0.1')
        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }

    plugins {
        runtime ":twitter-bootstrap:2.3.2"
        runtime ":mail:1.0.1"
        runtime ":hibernate:3.6.10.6"
        runtime ":resources:1.1.6"
        runtime ":jquery:1.10.2.2"
        runtime ":font-awesome-resources:3.2.1.1"
        runtime ":jquery-validation:1.9"
        runtime(":shiro:1.2.0") {
            excludes(
                    [name: "shiro-quartz", group: "org.apache.shiro"]
            )
        }
        compile ":scaffolding:2.0.1"
        build ":tomcat:7.0.47"
        compile(":rest-client-builder:1.0.3")
        build(":release:3.0.1", ":bintray-upload:0.2")
        build(":codenarc:0.18") {
            excludes "log4j", "groovy-all", "ant", "junit"
        }
        build(':squeaky-clean:0.2')
        test(":code-coverage:1.2.5")
        test(":spock:0.7") {
            exclude "spock-grails-support"
        }
    }
}
