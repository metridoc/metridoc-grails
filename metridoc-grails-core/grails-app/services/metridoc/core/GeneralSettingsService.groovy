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

class GeneralSettingsService {

    def grailsApplication

    File getStartFile() {
        getMetridocHomeFile("start.sh")
    }

    File getWorkDirectoryFile() {
        getMetridocHomeFile("workDir.txt")
    }

    File getMetridocHomeFile(String fileName) {
        def metridocHome = grailsApplication.config.metridoc.home
        return new File("$metridocHome${SystemUtils.FILE_SEPARATOR}${fileName}")
    }

    static boolean fileExists(File file) {
        file.exists() && file.text.trim()
    }

    String javaCommand() {

        def slash = SystemUtils.FILE_SEPARATOR
        System.getProperty("java.home") + "${slash}bin${slash}java"
    }

    String javaVmArguments() {
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

    String mainCommand() {
        System.getProperty("sun.java.command")
    }
}
