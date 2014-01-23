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

package metridoc.penn.bd

class BorrowDirectController extends BdEzbController{

    static homePage = [
            title: "Borrow Direct Data Repository",
            description: """
                 ILL activity within the Borrow Direct consortium
            """
    ]

	public BorrowDirectController(){
		serviceKey = BorrowDirectService.BD_SERVICE_KEY;
	}
	def index(){
		super.index();
	}
	def data_dump(DataDumpCommand cmd){
		super.data_dump(cmd)
	}
	def data_dump_mult(DataDumpMultCommand cmd){
		super.data_dump_mult(cmd)
	}
	def summary(){
		super.summary()
	}
	def lc_report(){
		super.lc_report()
	}
	def lib_data_summary(LibReportCommand cmd){
		super.lib_data_summary(cmd)
	}
	def historical_summary(){
		super.historical_summary()
	}
}