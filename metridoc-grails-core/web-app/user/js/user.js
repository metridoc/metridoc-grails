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

function deleteUser(userId) {
    if (window.confirm("are you sure you want to delete this user?")) {
        $('#mdForm_' + userId).submit();
    }
}

var userInput = $('.username')
var userNameInputExists = userInput.length > 0;
if (userNameInputExists) {
    var userNameInputIsActive = !userInput.is(':disabled');
    if (userNameInputIsActive) {
        console.log('username input exists and is active on page, will add functionality for validation');
    }

    userInput.change(function () {
            var user = userInput.val();
            var url = 'hasUser/' + user;
            console.log("calling url " + url + " to see if user " + user + ' exists');
            $.get(url, function (data) {
                    userInput.each(function (index, value) {
                        if (data == "true") {
                            console.log("user " + user + " exists, this form submission will not work");
                            value.setCustomValidity("user already exists")
                        } else {
                            value.setCustomValidity("")
                        }
                    })
                }
            );
        }
    );
}

var password = $('#password');
var confirmPassword = $('#confirmPassword');
var passwordAndConfirmPasswordExist = password.length > 0 && confirmPassword.length > 0;


if (passwordAndConfirmPasswordExist) {
    console.log("password and confirm inputs exist, adding custom validity")
    confirmPassword.change(function () {
        password.each(function (index, value) {
            var passwordValue = value.value;
            var confirmElement = confirmPassword.get(index)
            var confirmValue = confirmElement.value;

            if (passwordValue != confirmValue) {
                console.log("passwords do not match, form submission will fail")
                confirmElement.setCustomValidity("Passwords do not match");
            } else {
                confirmElement.setCustomValidity("");
            }
        });
    });
}
function showUserForm() {
    $('#createUserForm').toggle();
    $('#createUser').toggleClass('icon-plus-sign icon-circle-arrow-up')
}

