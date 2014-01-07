/*
  - Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
if (typeof jQuery !== 'undefined') {
    (function ($) {
        $('#spinner').ajaxStart(function () {
            $(this).fadeIn();
        }).ajaxStop(function () {
                $(this).fadeOut();
            });
    })(jQuery);
}

$('#runJenkins').click(function () {
        $('#toRunJDiv').children('form').submit()
    }
);

$(function () {
    var dialog = $("#dialog");
    if (dialog.length) {
        dialog.dialog({
            autoOpen:false,
            width:600,
            resizable:false
        });
    }
});


$(document).ready(function () {

     $('#metridocNavigation').find('li').hover(
         function () {
             //show its submenu
            //TODO: delete this if show hide works better
//             $('ul', this).stop().slideDown(100);
             $('ul', this).stop().show();

         },
         function () {
             //hide its submenu
             //TODO: delete this if show hide works better
//             $('ul', this).stop().slideUp(100);
             $('ul', this).stop().hide();
         }
     );

});

function checkAndSetAccess(status) {
    var url = $('#linkUrl_' + status).text();
    if (url.search("checkAccess") == -1) {
        if (url.search('\\?') == -1) {
            url = url + "?"
        }

        url = url + "checkAccess"
    }

    var text = $.ajax({
        url:url,
        type:'GET',
        cache:false,
        async:false
    }).responseText;

    if (text.search("Remember Me?") == -1) {
        $('#linkHasAccess_' + status).html('true')
    } else {
        $('#linkHasAccess_' + status).html('false')
    }
}

$(document).ready(function() {
    //handle links
    $('a[data-confirm]').click(function(ev) {
        var href = $(this).attr('href');

        addConfirmModal();
        var $dataConfirmModal = $('#dataConfirmModal');
        $dataConfirmModal.find('.modal-body').text($(this).attr('data-confirm'));
        $('#dataConfirmOK').attr('href', href);
        $dataConfirmModal.modal({show:true});
        return false;
    });

    //handle forms
    var isSubmitting = false;

    $('form[data-confirm]').submit(function(){
        if(isSubmitting) return true;
        var form = $('form[data-confirm]');

        addConfirmModal();

        $('#dataConfirmOK').click(function(){
            isSubmitting = true;
            form.submit();
        });

        $('#dataConfirmModal').modal({show:true});

        return false;
    });
});

function addConfirmModal() {
    if (!$('#dataConfirmModal').length) {
        $('body').append('<div id="dataConfirmModal" class="modal" role="dialog" aria-labelledby="dataConfirmLabel" aria-hidden="true"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h3 id="dataConfirmLabel">Please Confirm</h3></div><div class="modal-body"></div><div class="modal-footer"><button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button><a class="btn btn-primary" id="dataConfirmOK">OK</a></div></div>');
    }
}