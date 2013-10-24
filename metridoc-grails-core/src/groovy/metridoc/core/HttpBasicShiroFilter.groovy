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

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import org.apache.shiro.web.util.WebUtils

import javax.servlet.ServletRequest

/**
 * Created with IntelliJ IDEA.
 * User: tbarker
 * Date: 11/24/12
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */
class HttpBasicShiroFilter extends BasicHttpAuthenticationFilter {

    private final static REMEMBER_ME = "rememberMe"

    @Override
    protected boolean isRememberMe(ServletRequest request) {
        def isRememberMe = WebUtils.isTrue(request, REMEMBER_ME);
        return isRememberMe
    }
}
