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
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */

var newID = "category"
var newHeader = "header"
var newIcon = "icon"
$(function () {
    $('.categoryDiv').each(function (i) {
        $(this).attr({id: newID + i});
    });

    $('.categoryHeader').each(function (i) {
        $(this).attr({id: newHeader + i});
    });
    $('.icon-minus-sign').each(function (i) {
        $(this).attr({id: newIcon + i});
    });


});
function showApps(id) {
    var targetID = id.replace("header", "category")
    var iconID = id.replace("header", "icon")
    $('#' + targetID).toggle()
    $('#' + iconID).toggleClass('icon-minus-sign icon-plus-sign')
}
$(document).ready(function () {
    function assignID() {
        var newID = "category"
        var newHeader = "header"
        var newIcon = "icon"
        $('.categoryDiv').each(function (i) {
            $(this).attr({id: newID + i});
        });
        $('.categoryHeader').each(function (i) {
            $(this).attr({id: newHeader + i});
        });
        $('.icon-minus-sign').each(function (i) {
            $(this).attr({id: newIcon + i});
        });

    };
});




