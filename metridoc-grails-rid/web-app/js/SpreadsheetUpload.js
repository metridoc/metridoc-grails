/**
 * Created with IntelliJ IDEA.
 * User: intern
 * Date: 1/7/14
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */

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

$('input[id=spreadsheetUpload]').change(function () {
    var fileName = $(this).val().replace("C:\\fakepath\\", "");
    $('#spreadsheet-upload-path').val(fileName);
    checkInput();
});

function checkInput() {
    if ($('input[id=spreadsheetUpload]').valueOf() != "") {
        document.getElementById("submit-spreadsheet").disabled = "";
    }
}