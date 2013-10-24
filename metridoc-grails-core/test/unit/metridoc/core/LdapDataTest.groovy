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

import grails.test.mixin.Mock
import org.junit.Test

/**
 * Created with IntelliJ IDEA on 9/4/13
 * @author Tommy Barker
 */
@Mock(LdapData)
class LdapDataTest {

    @Test
    void "make sure that LdapData is valid when groupSearch is not specified"() {
        new LdapData(
                userSearchBase: "foo",
                rootDN: "foo",
                userSearchFilter: "foo",
                server: "foo",
                managerDN: "foo"
        ).save(failOnError: true)
    }
}
