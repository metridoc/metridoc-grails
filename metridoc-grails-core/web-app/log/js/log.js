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
 * User: dongheng
 * Date: 8/24/12
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */


//add all the handlers for the buttons
$(function () {

    $('#scrollBottom').click(function (event) {
        event.preventDefault();
        $("#metridocLogs").scrollTop($('#metridocLogs').prop("scrollHeight"));
    });

    $('#scrollTop').click(function (event) {
        event.preventDefault();
        $("#metridocLogs").scrollTop(0);
    });

    $('#metridocLogs').bind('scroll', chk_scroll);

    $('#logFile').change(function () {
        $('#metridocLogs').fadeOut(200, function () {
            forceLogUpdate(true);
        });
    });

});

var isAtBottomOfLog = false;

function chk_scroll(e) {
    var elem = $(e.currentTarget);
    var scrollHeight = elem[0].scrollHeight;
    var scrollTop = elem.scrollTop();
    var outerHeaight = elem.outerHeight();
    var difference = scrollHeight - scrollTop - outerHeaight;
    var fudgeFactor = 20;
    isAtBottomOfLog = Math.abs(difference) < fudgeFactor;
}

function forceLogUpdate(fade) {
    var encodedFileName = encodeURIComponent($('#logFile').val());
    if (fade) {
        $("#metridocLogs").load("plain/" + encodedFileName, function () {
            $('#metridocLogs').fadeIn()
        });
    } else {
        $("#metridocLogs").load("plain/" + encodedFileName);
    }
}

function updateLog() {
    var isStreaming = $('#doStreaming').is(":checked");
    if (isStreaming && isAtBottomOfLog) {
        forceLogUpdate(false);
        $("#metridocLogs").scrollTop($('#metridocLogs').prop("scrollHeight"));
        //make checks frequent
        triggerNextLogUpdate(1000);
    } else {
        //don't need to check for awhile
        triggerNextLogUpdate(3000);
    }
}

//by using set timeout instead of interval we guarantee that there is a one second break in refreshing the log
function triggerNextLogUpdate(time) {
    window.setTimeout(updateLog, time);
}

triggerNextLogUpdate(3000);

