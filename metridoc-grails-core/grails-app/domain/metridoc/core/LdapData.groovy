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

import org.jasypt.util.text.BasicTextEncryptor
import org.jasypt.util.text.StrongTextEncryptor

class LdapData {
    String server
    String rootDN
    String userSearchBase
    String userSearchFilter
    String managerDN
    String groupSearchBase = "NOT USED YET"
    Boolean encryptStrong = true
    String encryptedPassword

    Boolean skipAuthentication = false
    Boolean skipCredentialsCheck = false
    Boolean allowEmptyPasswords = true

    static transients = ['unencryptedPassword']

    static constraints = {
        server(blank: false)
        rootDN(blank: false)
        userSearchBase(blank: false)
        userSearchFilter(blank: false)
        managerDN(nullable: true, blank: true)
        encryptedPassword(nullable: true, blank: true)
        groupSearchBase(nullable: true)
    }

    @SuppressWarnings("UnnecessaryQualifiedReference")
    String getUnencryptedPassword() {
        //helps with testing
        if(!encryptedPassword) return null

        if (encryptStrong) {
            StrongTextEncryptor textEncrypt = new StrongTextEncryptor()
            textEncrypt.setPassword(CryptKey.list().get(0).encryptKey)
            String decrypted = textEncrypt.decrypt(encryptedPassword)
            return decrypted
        } else {
            BasicTextEncryptor textEncrypt = new BasicTextEncryptor()
            textEncrypt.setPassword(CryptKey.list().get(0).encryptKey)
            String decrypted = textEncrypt.decrypt(encryptedPassword)
            return decrypted
        }
    }
}
