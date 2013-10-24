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

package metridoc.core

import org.apache.commons.lang.SystemUtils

import static org.apache.commons.lang.SystemUtils.*

/**
 * service for commons operations accross services and controllers
 */
class CommonService {

    private static final String DEFAULT_METRIDOC_HOME = "${USER_HOME}${FILE_SEPARATOR}.metridoc"
    public static final String METRIDOC_CONFIG = "MetridocConfig.groovy"
    public static final String DEFAULT_ENCODING = "utf-8"
    def grailsApplication

    def emailIsConfigured() {
        return doEmailIsConfigured(grailsApplication.config)
    }

    private static doEmailIsConfigured(Map configObject) {
        configObject.grails?.mail ? true : false
    }

    File getMetridocConfig() {

        new File("${metridocHome}${FILE_SEPARATOR}${METRIDOC_CONFIG}")
    }

    String getMetridocHome() {
        def metridocHome = grailsApplication.config.metridoc.home
        metridocHome ?: DEFAULT_METRIDOC_HOME
    }
}
