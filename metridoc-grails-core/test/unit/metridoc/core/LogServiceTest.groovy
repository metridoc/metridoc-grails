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

import org.junit.Test

import static metridoc.core.LogService.DATE_FORMAT

/**
 * Created with IntelliJ IDEA.
 * User: tbarker
 * Date: 8/30/12
 * Time: 9:38 AM
 * To change this template use File | Settings | File Templates.
 */
class LogServiceTest {

    @Test
    void "test date class rendering"() {
        def now = Date.parse(DATE_FORMAT, "2012-08-29 14:48:31")
        //noinspection GroovyAccessibility
        def htmlClazz = LogService.getDateClass("2012-08-29 12:48:31,548", null, now)
        assert "all sixHours twelveHours day" == htmlClazz
    }

    @Test
    void "render log between dates"() {
        def out = new ByteArrayOutputStream()
        long start = getTime("2012-08-29 14:48:31")
        LogService.renderLogStartingAt(out, sampleLog, start)
        def lines = []
        new ByteArrayInputStream(out.toByteArray()).eachLine {
            lines << it
        }

        assert 6 == lines.size()
        ["bar", "bam", "biz", "baz"].each { item ->
            assert 1 == lines.findAll { it.contains(item) }.size()
        }
    }

    private static long getTime(String time) {
        Date.parse(DATE_FORMAT, time).time
    }

    def sampleLog = """
2012-08-29 13:48:31 foo
2012-08-29 14:48:31 bar
blah
blah
2012-08-29 14:50:31 bam
2012-08-29 14:55:31 biz
2012-08-29 15:48:31 baz
"""
}
