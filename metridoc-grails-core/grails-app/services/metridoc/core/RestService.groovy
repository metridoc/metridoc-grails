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

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder

import java.util.concurrent.TimeUnit

class RestService {

    Cache<String, String> restCache = CacheBuilder.newBuilder()
            .expireAfterAccess(30, TimeUnit.SECONDS)
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build()

    def addToRestCache(key, name) {
        restCache.put(key, name)
    }

    def getFromRestCache(key) {
        if (key) return restCache.getIfPresent(key)
        else return null
    }

    def hasCommonRoles(userRoles, controllerRoles) {
        for (ur in userRoles) {
            for (cr in controllerRoles) {
                if (ur == cr) return true
            }
        }
        return false
    }
}
