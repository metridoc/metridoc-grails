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

$(function () {
    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

    var $ssd = $('#shutdownDate');
    $ssd.datepicker({onRender: function(date) {
        return date.valueOf() < now.valueOf() ? 'disabled' : '';
    }});

    var shutdownDate = $ssd.datepicker().on('changeDate',function (ev) {
        $('.monitored-form').children().trigger('change');
        shutdownDate.hide();
    }).data('datepicker');

    $('#clearDate').click(function(){
        $ssd.val("");
        $('.monitored-form').submit();
    })

});


$('input[id=metridocConfig]').change(function () {
    var fileName = $(this).val().replace("C:\\fakepath\\", "");
    $('#metridocConfigPath').val(fileName);
});