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

$(document).ready(function () {

    $('table td:nth-child(n+4), table  th:nth-child(n+4)').hide();

    $('#Table1 td:nth-child(4), #Table1  th:nth-child(4)').show();
    $('#Table1 td:nth-child(17), #Table1  th:nth-child(17)').hide();

    $('#Table2 tr').slice(6).hide();
    $('#Table3 tr').slice(6).hide();

    $('div[id*="dTable"]').hide();

    $('input[id*="toggleCol"]').click(function () {
        var idNumber = $(this).attr("id").match(/\d+$/);
        var tableID = "Table" + idNumber;

        $('#' + tableID + ' td:nth-child(n+2), #' + tableID + ' th:nth-child(n+2)').toggle();

        if ($(this).val() == "Expand to monthly view") {
            $(this).val("Return to overview");
        }
        else {
            $(this).val("Expand to monthly view");
        }
    });

    $('input[id^="show"]').click(function () { //Finds showDepartments and showCourses
        var idNumber = $(this).attr("id").match(/\d+$/);
        var tableID = "Table" + idNumber;
        var fieldName = $(this).attr("id").replace(/show/, '').replace(/\d/, '').replace(/_/, '');

        $('#' + tableID + ' tr').slice(1).toggle();

        if ($(this).val() == "Show all " + fieldName) {
            $(this).val("Show top five " + fieldName);

        }
        else {
            $(this).val("Show all " + fieldName);

        }
    });

    $('input[id^="all"]').click(function () { //Finds the rest of the all(Field) buttons
        var idNumber = $(this).attr("id").match(/\d+$/);
        var tableID = 'Table' + idNumber;
        var fieldName = $(this).attr("id").replace(/all/, '').replace(/\d/, '').replace(/_/, '');
        var table = document.getElementById(tableID);
        var i, j, m, n;
        var count = 0;

        for (i = 0, j = table.rows.length; i < j; i++) {
            for (m = 0, n = table.rows[i].cells.length; m < n; m++) {
                if (table.rows[i].cells[m].innerHTML != "0") {
                    count++;
                }
            }
            if (count < 2) {
                //alert(table.rows[i].cells[0].innerHTML);
                $('#' + tableID + ' tr').slice(i, i + 1).toggle();
            }
            count = 0;
        }
        if ($(this).val() == "Show all " + fieldName) {
            $(this).val("Show only " + fieldName + " with transactions");

        }
        else {
            $(this).val("Show all " + fieldName);

        }
    });

    $('#toggleAll').click(function () {
        if (this.checked) {
            $('div[id*="dTable"]').show();

            $('input[id*="toggle"]').prop("checked", true);

        }
        else {
            $('div[id*="dTable"]').hide();

            $('input[id*="toggle"]').prop("checked", false);

        }
    });

    $('input[id^="toggle_"]').click(function () { //Finds the toggle checkboxes but not toggleAll or toggleCol
        var idNumber = $(this).attr("id").match(/\d+$/);
        var tableID = 'dTable' + idNumber;
        $('#' + tableID).toggle();
        document.getElementById('toggleAll').checked = false;
    });
});
