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

class EncryptionService {
    static transactional = false

    def encryptString(LdapData data, String target) {
        try {
            StrongTextEncryptor textEncrypt = new StrongTextEncryptor()
            textEncrypt.setPassword(CryptKey.list().get(0).encryptKey)
            String encrypted = textEncrypt.encrypt(target)
            data.encryptedPassword = encrypted
            data.encryptStrong = true
        } catch (org.jasypt.exceptions.EncryptionOperationNotPossibleException ex) {
            BasicTextEncryptor textEncrypt = new BasicTextEncryptor()
            textEncrypt.setPassword(CryptKey.list().get(0).encryptKey)
            String encrypted = textEncrypt.encrypt(target)
            data.encryptedPassword = encrypted
            data.encryptStrong = false
        }
    }

    def decryptString(String target) {
        try {
            StrongTextEncryptor textEncrypt = new StrongTextEncryptor()
            textEncrypt.setPassword(CryptKey.list().get(0).encryptKey)
            String decrypted = textEncrypt.decrypt(target)
            return decrypted
        } catch (org.jasypt.exceptions.EncryptionOperationNotPossibleException ex) {
            BasicTextEncryptor textEncrypt = new BasicTextEncryptor()
            textEncrypt.setPassword(CryptKey.list().get(0).encryptKey)
            String decrypted = textEncrypt.decrypt(target)
            return decrypted
        }
    }
}
