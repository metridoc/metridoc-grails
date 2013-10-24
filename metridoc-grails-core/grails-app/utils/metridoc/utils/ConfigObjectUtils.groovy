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

import org.apache.commons.lang.SerializationException
import org.apache.commons.lang.SerializationUtils

/**
 * Created with IntelliJ IDEA.
 * User: tbarker
 * Date: 3/6/13
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
class ConfigObjectUtils {

    /**
     * Best effort to deep clone a config object.
     * @param configObject
     * @return
     */
    static ConfigObject clone(ConfigObject configObject) {
        def copy = new ConfigObject()
        cloneHelper(configObject, copy)
        return copy
    }

    private static cloneHelper(Map original, Map copy) {
        original.each {key, value ->
            if (value instanceof Map) {
                copy[key] = new LinkedHashMap()
                cloneHelper(value, copy[key] as Map)
            } else if (value instanceof Serializable) {
                try {
                    copy[key] = SerializationUtils.clone(value)
                } catch (SerializationException e) {
                    copy[key] = value
                }
            } else if (value instanceof Cloneable) {
                copy[key] = value.clone()
            } else {
                copy[key] = value
            }
        }
    }
}
