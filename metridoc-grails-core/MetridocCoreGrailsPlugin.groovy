import metridoc.core.RememberCookieAge
import metridoc.utils.BootupUtils
import org.apache.shiro.crypto.AesCipherService
import org.apache.shiro.mgt.RememberMeManager
import org.apache.shiro.web.mgt.CookieRememberMeManager
import org.apache.shiro.web.servlet.SimpleCookie

/*
* Copyright 2010 Trustees of the University of Pennsylvania Licensed under the
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
class MetridocCoreGrailsPlugin {
    static DEFAULT_MAX_REMEMER_ME = 60 * 60 //one hour
    def version = "0.8.5"
    def grailsVersion = "2.0.4 > *"
    def loadAfter = ["rest-client-builder", "release", "hibernate", "resources"]

    def pluginExcludes = [
            "grails-app/services/metridoc/test/**/*",
            "grails-app/controllers/metridoc/test/**/*",
            "grails-app/domain/metridoc/test/**/*",
    ]
    def title = "Metridoc Core Plugin" // Headline display name of the plugin
    def author = "Thomas Barker"
    def authorEmail = "tbarker@pobox.upenn.edu"
    def description = '''\

    Provides core functionality for all metridoc views
'''
    def documentation = "https://github.com/metridoc/metridoc-grails.git"
    def license = "ECL2"
    def organization = [name: "University of Pennsylvania", url: "http://www.upenn.edu/"]
    def issueManagement = [system: "GitHuB", url: "https://github.com/metridoc/metridoc-grails.git"]
    def scm = [url: "https://github.com/metridoc/metridoc-grails.git"]
    def doWithSpring = {
        def shiroConfig = application.config.security.shiro
        //have to do it in here instead of using the plugin config plugin since the shiro plugin does not use the
        //plugin config plugin
        BootupUtils.addDefaultShiroConfig(shiroConfig)
    }

    def doWithApplicationContext = { applicationContext ->
        RememberMeManager manager = applicationContext.getBean("shiroRememberMeManager", RememberMeManager)
        if (manager instanceof CookieRememberMeManager) {
            manager.cookie.setMaxAge(RememberCookieAge.instance.ageInSeconds)
            manager.cookie.setPath(SimpleCookie.ROOT_PATH)
            AesCipherService aes = new AesCipherService()
            byte[] key = aes.generateNewKey().getEncoded()

            manager.cipherKey = key
        }
    }
}
