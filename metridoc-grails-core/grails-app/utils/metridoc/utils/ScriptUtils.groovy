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

package metridoc.utils

import org.springframework.util.Assert

import java.util.regex.Matcher

/**
 * Created with IntelliJ IDEA on 4/23/13
 *
 * Utilities for the various metridoc scripts
 *
 * @author Tommy Barker
 */
class ScriptUtils {

    public static final String SNAPSHOT = "-SNAPSHOT"
    public static final String VERSIONS_MUST_BE_PROVIDED = "a version list must be provided"
    public static final String MAVEN_XML_DATA = "http://dl.bintray.com/upennlib/metridoc/org/grails/plugins/metridoc-core/maven-metadata.xml" //"http://metridoc.googlecode.com/svn/maven/repository/org/grails/plugins/metridoc-core/maven-metadata.xml"
    public static final String TEMPLATE = "TEMPLATE_"
    public static final String ENCODING = "utf-8"

    static List<String> availableMetridocCoreVersions() {
        def mavenXmlMeta = MAVEN_XML_DATA
        def slurper = new XmlSlurper()
        def result = slurper.parse(mavenXmlMeta)
        def allVersions = []
        result.versioning.versions.version.each {
            allVersions << it.text()
        }

        return allVersions
    }

    static String getMostRecentCoreVersion(boolean includeSnapshots = false) {
        getMostRecentCoreVersion(availableMetridocCoreVersions(), includeSnapshots)
    }

    static String getMostRecentCoreVersion(List<String> versions, boolean includeSnapshots = false) {
        Assert.notNull(versions, VERSIONS_MUST_BE_PROVIDED)
        Assert.notEmpty(versions, VERSIONS_MUST_BE_PROVIDED)
        def sortedList = versions.sort().reverse()
        String chosenItem = sortedList.get(0)
        def isSnapshot = isSnapshotVersion(chosenItem)
        if (isSnapshot) {
            def hasMoreThanOneItem = sortedList.size() > 1
            if (hasMoreThanOneItem) {
                def index = chosenItem.indexOf(SNAPSHOT)
                def releaseVersion = chosenItem.substring(0, index)
                def hasReleaseVersion = sortedList.get(1) == releaseVersion

                if (hasReleaseVersion) {
                    return releaseVersion
                } else if (!includeSnapshots) {
                    def subList = sortedList.subList(1, sortedList.size())
                    subList.each {
                        if (!isSnapshotVersion(it)) {
                            if (isSnapshotVersion(chosenItem)) {
                                chosenItem = it
                            }
                        }
                    }
                }
            }

            return chosenItem
        }
    }

    static void updateWithTemplate(String templateName, String templateText, File configFile) {
        def textUpdate = getUpdatedTemplateText(templateName, templateText, configFile.text)
        configFile.delete()
        configFile.write(textUpdate, ENCODING)
    }

    static void updateWithTemplate(String templateName, String pathToTemplateFile, String pathToConfigFile) {

        [templateName, pathToConfigFile, pathToTemplateFile].each {
            Assert.hasText(it, "$it cannot be null or empty")
        }

        def configFile = new File(pathToConfigFile)
        def templateFile = new File(pathToTemplateFile)

        [templateFile, configFile].each {
            Assert.isTrue(it.exists(), "template file $it does not exist")
        }

        updateWithTemplate(templateName, templateFile.text, configFile)
    }

    protected static String getUpdatedTemplateText(String templateName, String templateText, String configText) {
        def upperCaseTemplateName = templateName.toUpperCase()
        String normalizedTemplateName = upperCaseTemplateName.startsWith(TEMPLATE) ? upperCaseTemplateName : "${TEMPLATE}${upperCaseTemplateName}"
        def regex = "(?s)${normalizedTemplateName}.*?${normalizedTemplateName}"
        def m = templateText =~ regex

        Assert.isTrue(m.find(), "template text $templateText does not include the template")
        String replacementText = m.group(0)
        return configText.replaceAll(regex, Matcher.quoteReplacement(replacementText))
    }

    private static boolean isSnapshotVersion(String version) {
        version.endsWith(SNAPSHOT)
    }
}
