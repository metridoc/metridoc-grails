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

import java.lang.management.ManagementFactory

class StatusController {

    def dataSource
    def grailsApplication

    static accessControl = {
        role(name: "ROLE_ADMIN")
    }

    def index() {
        redirect(controller: "manageConfig", action: "index")
    }

    private String javaCommand() {
        def slash = SystemUtils.FILE_SEPARATOR
        System.getProperty("java.home") + "${slash}bin${slash}java"
    }

    private String javaVmArguments() {
        def vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments()
        def vmArgsOneLine = new StringBuffer();
        vmArguments.each {
            if (!it.contains("-agentlib")) {
                vmArgsOneLine.append(it);
                vmArgsOneLine.append(" ");
            }
        }
        return vmArgsOneLine
    }

    private String mainCommand() {
        System.getProperty("sun.java.command")
    }
}
