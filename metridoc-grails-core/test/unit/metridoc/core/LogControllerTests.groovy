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

import grails.test.mixin.TestFor
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/**
 * Created with IntelliJ IDEA on 6/11/13
 * @author Tommy Barker
 */
@TestFor(LogController)
class LogControllerTests {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

    @Before
    void "add log files to temporary folder"() {
        folder.newFile("log1")
        folder.newFile("log2")
    }

    @SuppressWarnings("GroovyAccessibility")
    @Test
    void "basic testing of folder"() {
        def fileNames = controller.getLogFilesFrom(folder.root)
        assert 2 == fileNames.size()
        assert fileNames.contains("log1")
        assert fileNames.contains("log2")
    }
}
