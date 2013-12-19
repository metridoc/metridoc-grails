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

/**
 * Created with IntelliJ IDEA.
 * User: intern
 * Date: 7/18/13
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */

function protect() {
    $('#is-protected').prop("checked", true)
}

function unProtect() {
    $('#is-protected').prop("checked", false)
}

function getControllerNames() {
    var cNames = [];
    var table = document.getElementById("controllerTable");
    var i, j;
    var k = 0;
    var cellText;

    var boxes = $('input[name="controllerNames"]');

    for (i = 1, j = table.rows.length; i < j; i++) {
        if (boxes[i - 1].checked) {
            cellText = table.rows[i].cells[1].innerHTML.replace('<a href=\"/metridoc-core/manageReport/show/', "");
            cellText = cellText.replace('</a>', "");
            cellText = cellText.replace(/[a-zA-Z]*">/, "");
            cNames.push(cellText);
        }
    }
    $('#controllerNames').val(cNames);
    var searchField = $('#searchControllers').val();
    $('#searchFilter').val(searchField);
    var roleField = $('#roleFilter').val();
    $('#rFilter').val(roleField);
    var e = jQuery.Event( "change" );
    $( "#controllerNames" ).trigger( e );

}

$(document).ready(function () {
    $('#searchControllers').keyup(function () {
        var searchText = $('#searchControllers').val();
        var cellText;
        var table = document.getElementById("controllerTable");
        var i, j;
        //When changing search, boxes should be unchecked
        $('input[name=selectAll]').prop("checked", false);
        $('input[name=controllerNames]').prop("checked", false);

        for (i = 1, j = table.rows.length; i < j; i++) {
            cellText = table.rows[i].cells[1].innerHTML.replace('<a href=\"/metridoc-core/manageReport/show/', "");
            cellText = cellText.replace('</a>', "");
            cellText = cellText.replace(/[a-zA-Z]*">/, "");
            if (cellText.indexOf(searchText) != -1) {
                $('#controllerTable tr').slice(i, i + 1).show();
            }
            else {
                $('#controllerTable tr').slice(i, i + 1).hide();
            }
        }

        var filterValue = $('#roleFilter').val();
        table = document.getElementById("controllerTable");
        var selRow, lastCell;
        //When changing search, boxes should be unchecked
        for (i = 2, j = table.rows.length; i <= j; i++) {
            selRow = $('#controllerTable tr:nth-child(' + i + ')');
            lastCell = selRow.find('td:last');
            cellText = lastCell.html();
            if (cellText.indexOf(filterValue) == -1) {
                $('#controllerTable tr').slice(i - 1, i).hide();
            }
        }
    });

    $(function () {
        $('#roleFilter').change(function () {
            var filterValue = $('#roleFilter').val();
            var cellText;
            var table = document.getElementById("controllerTable");
            var i, j;
            var selRow, lastCell;
            //When changing search, boxes should be unchecked
            $('input[name=selectAll]').prop("checked", false);
            $('input[name=controllerNames]').prop("checked", false);
            for (i = 2, j = table.rows.length; i <= j; i++) {
                selRow = $('#controllerTable tr:nth-child(' + i + ')');
                lastCell = selRow.find('td:last');
                cellText = lastCell.html();
                if (cellText.indexOf(filterValue) != -1) {
                    $('#controllerTable tr').slice(i - 1, i).show();
                }
                else {
                    $('#controllerTable tr').slice(i - 1, i).hide();
                }
            }
            $('[name="roles"] option').each(function () {
                if ($(this).val() == filterValue) {
                    $(this).prop('selected', true);
                }
                else {
                    $(this).prop('selected', false);
                }
            })

            var searchText = $('#searchControllers').val();
            table = document.getElementById("controllerTable");
            //When changing search, boxes should be unchecked
            for (i = 1, j = table.rows.length; i < j; i++) {
                cellText = table.rows[i].cells[1].innerHTML.replace('<a href=\"/metridoc-core/manageReport/show/', "");
                cellText = cellText.replace('</a>', "");
                cellText = cellText.replace(/[a-zA-Z]*">/, "");
                if (cellText.indexOf(searchText) == -1) {
                    $('#controllerTable tr').slice(i, i + 1).hide();
                }
            }
        });
    });

    $('input[name=selectAll]').click(function () {
        if (this.checked) {
            $('#controllerTable tr').slice(1).each(function () {
                if ($(this).is(':visible')) {
                    $(this).find('input:checkbox').prop("checked", true);
                }
            });
        }
        else {
            $('input[name=controllerNames]').prop("checked", false);
        }
        getControllerNames()
    });
});
$(function () {
    $("[rel='tooltip']").tooltip();
});

$(function () {
    $("[rel='popover']").popover();
});

$(document).ready(function () {
    $(function () {
        $(".popRoles")
            .mouseover({
                offset: 10
            })
    })
});

$(document).ready(function () {
    function triggerFilter() {
        var searchText = $('#searchControllers').val();
        var roleFilter = $('#roleFilter').val();
        var cellText;
        var table = document.getElementById("controllerTable");
        var i, j;
        //When changing search, boxes should be unchecked
        $('input[name=selectAll]').prop("checked", false);
        $('input[name=controllerNames]').prop("checked", false);

        for (i = 1, j = table.rows.length; i < j; i++) {
            cellText = table.rows[i].cells[1].innerHTML.replace('<a href=\"/metridoc-core/manageReport/show/', "");
            cellText = cellText.replace('</a>', "");
            cellText = cellText.replace(/[a-zA-Z]*">/, "");
            if (cellText.indexOf(searchText) != -1) {
                $('#controllerTable tr').slice(i, i + 1).show();
            }
            else {
                $('#controllerTable tr').slice(i, i + 1).hide();
            }
        }
    }

    triggerFilter();
});
