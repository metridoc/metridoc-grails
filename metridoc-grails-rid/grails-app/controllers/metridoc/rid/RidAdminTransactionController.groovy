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
  *	permissions and limitations under the License.
  */

package metridoc.rid

class RidAdminTransactionController {


    static accessControl = { role(name: "ROLE_ADMIN") }

    /**
     * The index and list methods serve to set up the default values for the adminstration pane
     * Currently, this means consultation transactions, and RidLibraryUnit as the target domain
     */

    def index() {
        session.setAttribute("transType", new String("consultation"))//Sets default mode to consultation
        session.setAttribute("prev", new String("RidAdminLibraryUnit"))
        session.setAttribute("display", new String("dropdown"))
        redirect(controller: "RidAdminLibraryUnit", action: "index")
    }

    def list(Integer max) {
        session.setAttribute("transType", new String("consultation"))//Sets default mode to consultation
        redirect(controller: "RidAdminLibraryUnit", action: "index")
    }

    /**
     * Toggle for switching between transaction types.
     * If the current pane is of a domain class that transaction type doesn't have, it switches to Library Unit
     */

    def consultation() {
        session.setAttribute("transType", new String("consultation"))
        if (session.getAttribute("prev").equals("RidAdminExpertise") ||
                session.getAttribute("prev").equals("RidAdminInstructionalMaterials") ||
                session.getAttribute("prev").equals("RidAdminLocation") ||
                session.getAttribute("prev").equals("RidAdminSessionType")){
            redirect(controller: "RidAdminLibraryUnit", action: "index")
        } else {
            redirect(controller: session.getAttribute("prev"), action: "index")
        }
    }

    def instructional() {
        session.setAttribute("transType", new String("instructional"))

        if (session.getAttribute("prev").equals("RidAdminCourseSponsor") ||
                session.getAttribute("prev").equals("RidAdminModeOfConsultation") ||
                session.getAttribute("prev").equals("RidAdminServiceProvided") ||
                session.getAttribute("prev").equals("RidAdminUserGoal")||
                session.getAttribute("prev").equals("RidAdminRank")) {
            redirect(controller: "RidAdminLibraryUnit", action: "index")
        } else {
            redirect(controller: session.getAttribute("prev"), action: "index")
        }
    }

    /**
     * Goes back to the transaction creation page
     */

    def switchMode() {
        redirect(controller: "RidTransaction", action: "index")
    }
}
