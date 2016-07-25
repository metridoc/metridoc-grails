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

import org.junit.Test

import static metridoc.utils.ScriptUtils.SNAPSHOT

/**
 * Created with IntelliJ IDEA on 4/23/13
 * @author Tommy Barker
 */
class ScriptUtilsTest {

/*
    @Test
    void "do a basic integrated test"() {
        String version = ScriptUtils.getMostRecentCoreVersion()
        //assert version
        assert !version.endsWith(SNAPSHOT)
    }
*/

    @Test
    void "basic test on available metridoc-core versions"() {
        assert ScriptUtils.availableMetridocCoreVersions().size() > 0
    }

    @Test
    void "basic testing for retrieving most recent version"() {
        assert "0.52" == ScriptUtils.getMostRecentCoreVersion(["0.51", "0.52", "0.52-SNAPSHOT"], true)
        assert "0.52-SNAPSHOT" == ScriptUtils.getMostRecentCoreVersion(["0.51", "0.52-SNAPSHOT"], true)
        assert "0.51" == ScriptUtils.getMostRecentCoreVersion(["0.51", "0.52-SNAPSHOT"], false)
        assert "0.52-SNAPSHOT" == ScriptUtils.getMostRecentCoreVersion(["0.52-SNAPSHOT"], false)

        try {
            ScriptUtils.getMostRecentCoreVersion([], false)
            assert false: "exception should have occurred"
        } catch (IllegalArgumentException ex) {

        }

        try {
            ScriptUtils.getMostRecentCoreVersion(null, false)
            assert false: "exception should have occurred"
        } catch (IllegalArgumentException ex) {

        }
    }

    @Test
    void "update template throws IllegalArgument if either path does not exist"() {
        try {
            ScriptUtils.updateWithTemplate("blah", "blah", "blah")
            assert false: "exception should have occurred"
        } catch (IllegalArgumentException e) {
        }

        try {
            def tmpFile = File.createTempFile("blah", null)
            tmpFile.deleteOnExit()
            ScriptUtils.updateWithTemplate("blah", tmpFile as String, "blah")
            assert false: "exception should have occurred"
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void "update template returns IllegalArgumentException if any arguments are blank or null"() {
        try {
            ScriptUtils.updateWithTemplate(null, null, "blah")
            assert false: "exception should have occurred"
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void "test basic templating text updates"() {
        def configText = """
            foofam
            //TEMPLATE_FOO
            foobar
            //TEMPLATE_FOO
        """

        def expectedResult = """
            foofam
            //TEMPLATE_FOO
            barfoo
            //TEMPLATE_FOO
        """

        def templateText = """
            farfam
            //TEMPLATE_FOO
            barfoo
            //TEMPLATE_FOO
        """

        assert expectedResult == ScriptUtils.getUpdatedTemplateText("foo", templateText, configText)
        assert expectedResult == ScriptUtils.getUpdatedTemplateText("template_foo", templateText, configText)
    }
}
