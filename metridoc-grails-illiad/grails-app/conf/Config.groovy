/*
  *Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
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

grails.plugins.squeakyclean.cleandirs = true

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

grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
grails.views.gsp.sitemesh.preprocess = true
grails.json.legacy.builder = false
grails.enable.native2ascii = true
grails.web.disable.multipart = false
grails.exceptionresolver.params.exclude = ['password']
metridoc.app.name = appName
grails.resources.debug = true


log4j = {
    error 'org.codehaus.groovy.grails.web.servlet',        // controllers
            'org.codehaus.groovy.grails.web.pages',          // GSP
            'org.codehaus.groovy.grails.web.sitemesh',       // layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping',        // URL mapping
            'org.codehaus.groovy.grails.commons',            // core / classloading
            'org.codehaus.groovy.grails.plugins',            // plugins
            'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'
    info 'metridoc'
}

//TEMPLATE_LOG_4J
//change the document parameters if creating a user manual for a plugin
grails.doc.authors = "Thomas Barker, Weizhuo Wu"

grails.doc.subtitle = " "

grails.doc.title = "Illiad User Manual"

//sets the layout for all pages
metridoc.style.layout = "main"



