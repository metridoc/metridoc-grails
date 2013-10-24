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

import org.apache.commons.lang.SystemUtils
import org.slf4j.LoggerFactory

def rootLoader = Thread.currentThread().contextClassLoader.rootLoader

def driverDirectory = new File("${SystemUtils.USER_HOME}/.grails/drivers")
if (driverDirectory.exists() && driverDirectory.isDirectory()) {
    if (rootLoader) {
        driverDirectory.eachFile {
            if (it.name.endsWith(".jar")) {
                def url = it.toURI().toURL()
                LoggerFactory.getLogger("config.Config").info "adding driver ${url}" as String
                rootLoader.addURL(url)
            }
        }
    }
}


grails.converters.default.pretty.print = true
metridoc.home = "${userHome}/.metridoc"

grails.dbconsole.enabled = true
grails.dbconsole.urlRoot = '/admin/dbconsole'
grails.config.locations = []

if (new File("${metridoc.home}/MetridocConfig.groovy").exists()) {
    log.info "found MetridocConfig.groovy, will add to configuration"
}
grails.config.locations << "classpath:MetridocConfig.groovy"
grails.config.locations << "file:${metridoc.home}/MetridocConfig.groovy"


if (System.properties["${appName}.config.location"]) {
    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
}

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [html: ['text/html', 'application/xhtml+xml'],
        xml: ['text/xml', 'application/xml'],
        text: 'text/plain',
        js: 'text/javascript',
        rss: 'application/rss+xml',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        all: '*/*',
        json: ['application/json', 'text/json'],
        form: 'application/x-www-form-urlencoded',
        multipartForm: 'multipart/form-data',
        xlsx: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        xls: "application/vnd.ms-excel"
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

//For jquery
grails.views.javascript.library = "jquery"

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

metridoc.app.name = appName

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
        grails.gsp.reload.enable = true
        grails.resources.processing.enabled = true
        grails.resources.debug = true

    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

//sets the layout for all pages
metridoc.style.layout = "main"

// log4j configuration
//TEMPLATE_LOG_4J
log4j = {

    appenders {
        rollingFile name: "file",
                maxBackupIndex: 10,
                maxFileSize: "1MB",
                file: "${config.metridoc.home}/logs/${config.metridoc.app.name ?: 'metridoc'}.log"

        rollingFile name: "stacktrace",
                maxFileSize: "1MB",
                maxBackupIndex: 10,
                file: "${config.metridoc.home}/logs/${config.metridoc.app.name ?: 'metridoc'}-stacktrace.log"

        //not used yet... this will be where we log cli jobs
        rollingFile name: "jobLog",
                maxFileSize: "1MB",
                maxBackupIndex: 10,
                file: "${config.metridoc.home}/logs/metridoc-job.log"
    }


    error 'org.codehaus.groovy',
            'grails.app.resourceMappers',
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate',
            'metridoc.camel',
            'grails.plugin',
            'org.grails',
            'org.quartz',
            'ShiroGrailsPlugin',
            'grails.util',
            'org.grails.plugin.resource.BundleResourceMapper',
            'org.apache',
            'net.sf' //ehcache

    //since it it running via commandline, it is assumed that standard out is only needed
    if ("true" == System.getProperty("metridoc.job.cliOnly")) {
        root {
            info 'stdout'
        }
    } else {
        if ("false" == System.getProperty("metridoc.job.loggedLogLocation", "false")) {
            println "INFO: logs will be stored at ${config.metridoc.home}/logs/${config.metridoc.app.name ?: 'metridoc'}.log"
            //avoids duplicate logging
            System.setProperty("metridoc.job.loggedLogLocation", "true")
        }
        root {
            info 'stdout', 'file'
        }
    }
}
//TEMPLATE_LOG_4J




