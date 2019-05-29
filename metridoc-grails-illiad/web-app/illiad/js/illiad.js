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
 * User: tbarker
 * Date: 7/13/12
 * Time: 9:32 AM
 * To change this template use File | Settings | File Templates.
 */
addToolTips();

function addToolTips() {
    $('.aggregation-header').tooltip();
    $('.icon-download-alt').tooltip();
}

var modal = document.getElementById('download_modal');

var span = document.getElementsByClassName("close")[0];

function toggleModal(){
  modal.style.display = "block";
}

$(document).on('click', '.open_download_modal', toggleModal);

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
} 