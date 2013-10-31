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
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.repos.metridocRepo.url = "https://api.bintray.com/maven/upennlib/metridoc/metridoc-illiad"
grails.project.repos.default = "metridocRepo"

String coreVersion = new File(new File(basedir).parent, "VERSION").getText("utf-8").trim()
boolean coreVersionIsSnapshot = coreVersion.endsWith("SNAPSHOT")

if(coreVersionIsSnapshot) {
    grails.plugin.location."metridoc-core" = "../metridoc-grails-core"
}

grails.project.dependency.resolution = {

    if(coreVersionIsSnapshot) {
        //inline plugins require this for some reason
        legacyResolve true
    }

    inherits("global")
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
        mavenCentral()
        mavenRepo "http://dl.bintray.com/upennlib/metridoc"
        mavenRepo "http://dl.bintray.com/upennlib/maven"
    }

    dependencies {
        compile 'net.sf.opencsv:opencsv:2.3'
    }

    plugins {
        if (!coreVersionIsSnapshot) {
            compile ":metridoc-core:${coreVersion}"
        }
    }
}
