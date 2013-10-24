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

/**
 * Created with IntelliJ IDEA.
 * User: tbarker
 * Date: 3/6/13
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
class ConfigObjectUtilsTest {

    @Test
    void "make sure the cloned version doesn't override values from the original"() {
        def originalConfig = new ConfigObject()
        originalConfig.foo.bar = "baz"
        def cloned = ConfigObjectUtils.clone(originalConfig)
        def override = new ConfigObject()
        override.foo.bar = "notbaz"
        cloned.merge(override)
        assert "baz" == originalConfig.foo.bar
        assert 1 == originalConfig.size()
        assert 1 == cloned.size()
    }
}
