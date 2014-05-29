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

//$(function() {
//    $("#foo").change(function() {
//            var strSel = $("#foo").val().join(",");
//            $("#p1").html(strSel);
//    })
//})

$(function () {
    var nowTemp = new Date();
    var begin = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0).setMonth(nowTemp.getMonth() - 1);
    var end = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

    var dateOfTransaction = $('#transaction-date').datepicker().on('changeDate',function (ev) {
        if (ev.date.valueOf() > end) {
            dateOfTransaction.setValue(end);
            alert("Cannot select a date in the future!");
            return
        }
            dateOfTransaction.hide();
            $('#transaction-date').blur();
        }).data('datepicker');

    var $startDate = $('#start-date');
    var $endDate = $('#end-date');

    $startDate.datepicker('setValue', begin);
    $endDate.datepicker('setValue', end);
    var checkin = $startDate.datepicker().on('changeDate',function (ev) {
        if (ev.date.valueOf() > end) {
            checkin.setValue(end);
            alert("Cannot select a date in the future!");
            return
        }

        if (ev.date.valueOf() > checkout.date.valueOf()) {
            var newDate = new Date(ev.date);
            newDate.setDate(newDate.getDate());
            checkout.setValue(newDate);
        }
        checkin.hide();
        $('#end-date')[0].focus();
    }).data('datepicker');
    var checkout = $endDate.datepicker()
        .on('changeDate',function (ev) {
            if (ev.date.valueOf() > end) {
                checkout.setValue(end);
                alert("Cannot select a date in the future!");
                return
            }
            if (ev.date.valueOf() < checkin.date.valueOf()) {
                var newDate = new Date(ev.date);
                newDate.setDate(newDate.getDate());
                checkin.setValue(newDate);
            }
            checkout.hide();
        }).data('datepicker');

});

$(function () {
    var choiceRank = $("#rank").find("option:selected").attr("inForm");
    if (choiceRank == "2") {
        $("#otherRankDiv").show();
        $("#otherRank").val("");
    }
    var choiceService = $("#serviceProvided").find("option:selected").attr("inForm");
    if (choiceService == "2") {
        $("#otherServiceDiv").show();
        $("#otherService").val("");
    }
    var choiceSchool = $("#school").find("option:selected").attr("inForm");
    if (choiceSchool == "2") {
        $("#otherSchoolDiv").show();
        $("#otherSchool").val("");
    }
    var choiceCourseSponsor = $("#courseSponsor").find("option:selected").attr("inForm");
    if (choiceCourseSponsor == "2") {
        $("#otherCourseSponsorDiv").show();
        $("#otherCourseSponsor").val("");
    }
    var choiceUserGoal = $("#userGoal").find("option:selected").attr("inForm");
    if (choiceUserGoal == "2") {
        $("#otherUserGoalDiv").show();
        $("#otherUserGoal").val("");
    }
    var choiceMode = $("#modeOfConsultation").find("option:selected").attr("inForm");
    if (choiceMode == "2") {
        $("#otherModeOfConsultationDiv").show();
        $("#otherModeOfConsultation").val("");
    }
    var choiceInstructionalMaterials = $("#instructionalMaterials").find("option:selected").attr("inForm");
    if (choiceInstructionalMaterials == "2") {
        $("#otherInstructionalMaterialsDiv").show();
        $("#otherInstructionalMaterials").val("");
    }
    var choiceExpertise = $("#expertise").find("option:selected").attr("inForm");
    if (choiceExpertise == "2") {
        $("#otherExpertiseDiv").show();
        $("#otherExpertise").val("");
    }

    var choiceLocation = $("#location").find("option:selected").attr("inForm");
    if (choiceLocation == "2") {
        $("#otherLocationDiv").show();
        $("#otherLocation").val("");
    }

});

$(function () {
    $("#modeOfConsultation").change(function () {
        var choice = $("#modeOfConsultation").find("option:selected").attr("inForm");
        if (choice == "2") {
            $("#otherModeOfConsultationDiv").show();
        }
        else {
            $("#otherModeOfConsultationDiv").hide();
            $("#otherModeOfConsultation").val("");
        }
    })
});

$(function () {
    $("#userGoal").change(function () {
        var choice = $("#userGoal").find("option:selected").attr("inForm");
        if (choice == "2") {
            $("#otherUserGoalDiv").show();
        }
        else {
            $("#otherUserGoalDiv").hide();
            $("#otherUserGoal").val("");
        }
    })
});

$(function () {
    $("#rank").change(function () {
        var choice = $("#rank").find("option:selected").attr("inForm");
        if (choice == "2") {
            $("#otherRankDiv").show();
        }
        else {
            $("#otherRankDiv").hide();
            $("#otherRank").val("");
        }
    })
});

$(function () {
    $("#serviceProvided").change(function () {
        var choice = $("#serviceProvided").find("option:selected").attr("inForm");
        if (choice == "2") {
            $("#otherServiceDiv").show();
        }
        else {
            $("#otherServiceDiv").hide();
            $("#otherService").val("");
        }
    })
});

$(function () {
    $("#school").change(function () {
        var choice = $("#school").find("option:selected").attr("inForm");
        if (choice == "2") {
            $("#otherSchoolDiv").show();
        }
        else {
            $("#otherSchoolDiv").hide();
            $("#otherSchool").val("");
        }
    })
});

$(function () {
    $("#courseSponsor").change(function () {
        var choice = $("#courseSponsor").find("option:selected").attr("inForm");
        if (choice == "2") {
            $("#otherCourseSponsorDiv").show();
        }
        else {
            $("#otherCourseSponsorDiv").hide();
            $("#otherCourseSponsor").val("");
        }
    })
});

$(function () {
    $("#instructionalMaterials").change(function () {
        var choice = $("#instructionalMaterials").find("option:selected").attr("inForm");
        if (choice == "2") {
            $("#otherInstructionalMaterialsDiv").show();
        }
        else {
            $("#otherInstructionalMaterialsDiv").hide();
            $("#otherInstructionalMaterials").val("");
        }
    })
});

$(function () {
    $("#expertise").change(function () {
        var choice = $("#expertise").find("option:selected").attr("inForm");
        if (choice == "2") {
            $("#otherExpertiseDiv").show();
        }
        else {
            $("#otherExpertiseDiv").hide();
            $("#otherExpertise").val("");
        }
    })
});


$(function () {
    $("#location").change(function () {
        var choice = $("#location").find("option:selected").attr("inForm");
        if (choice == "2") {
            $("#otherLocationDiv").show();
        }
        else {
            $("#otherLocationDiv").hide();
            $("#otherLocation").val("");
        }
    })
});

$(function () {
    $("#resetButton").click(function () {
        $("#otherRankDiv").hide();
        $("#otherRank").val("");
        $("#otherServiceDiv").hide();
        $("#otherService").val("");
        $("#otherSchoolDiv").hide();
        $("#otherSchool").val("");
        $("#otherCourseSponsorDiv").hide();
        $("#otherCourseSponsor").val("");
        $("#otherUserGoalDiv").hide();
        $("#otherUserGoal").val("");
        $("#otherInstructionalMaterialsDiv").hide();
        $("#otherInstructionalMaterials").val("");
        $("#otherExpertiseDiv").hide();
        $("#otherExpertise").val("");
        $("#otherLocationDiv").hide();
        $("#otherLocation").val("");
    })
});


//$(function () {
    function toggleOther(){
//    $("#ridLibraryUnit").change(function () {
        var choiceType = $("#ridLibraryUnit").val();
        var choiceMode = $("#currentModeOfConsultation").text();
        var choiceService = $("#currentServiceProvided").text();
        var choiceGoal = $("#currentUserGoal").text();
        var choiceSession = $("#currentSessionType").text();
        var choiceMaterials = $("#currentInstructionalMaterials").text();
        var choiceLocation = $("#currentLocation").text();

        $.ajax({
            //url: '${g.createLink(controller: 'RidConsTransaction', action: 'ajaxChooseType')}',
            url: 'ajaxChooseType',
            type: 'POST',
            dataType: 'json',
            data: {
                typeId: choiceType,
                modeID: choiceMode,
                serviceID: choiceService,
                goalID: choiceGoal,
                sessionID: choiceSession,
                materialsID: choiceMaterials,
                locationID: choiceLocation
            },
            success: function (data) {
                $.each(data, function (index, itemList) {
                    $('#' + index + ' > option').remove();
                    $.each(itemList, function (id, element) {

                        $('#' + index).append($("<option>", {
                            text: element.name
                        }).attr('value', element.id).attr('inForm', element.inForm));
                    });
                    if (itemList.length == 0)
                        $('#' + index).attr("disabled", "");
                    else
                        $('#' + index).removeAttr("disabled");
                });
            },
            error: function (request, status, error) {
                alert(error);
            },
            complete: function () {
                var choiceService = $("#serviceProvided").children("option:first").attr("inForm");
                if (choiceService == "2") {
                    $("#otherServiceDiv").show();
                }
                else {
                    $("#otherServiceDiv").hide();
                    $("#otherService").val("");
                }
                var choiceUserGoal = $("#userGoal").children("option:first").attr("inForm");
                if (choiceUserGoal == "2") {
                    $("#otherUserGoalDiv").show();
                }
                else {
                    $("#otherUserGoalDiv").hide();
                    $("#otherUserGoal").val("");
                }
                var choiceMode = $("#modeOfConsultation").children("option:first").attr("inForm");
                if (choiceMode == "2") {
                    $("#otherModeOfConsultationDiv").show();
                }
                else {
                    $("#otherModeOfConsultationDiv").hide();
                    $("#otherModeOfConsultation").val("");
                }
                var choiceSessionType = $("#sessionType").children("option:first").attr("inForm");
                if (choiceSessionType == "2") {
                    $("#otherSessionTypeDiv").show();
                }
                else {
                    $("#otherSessionTypeDiv").hide();
                    $("#otherSessionType").val("");
                }

                var choiceMaterials = $("#instructionalMaterials").children("option:first").attr("inForm");
                if (choiceMaterials == "2") {
                    $("#otherInstructionalMaterialsDiv").show();
                }
                else {
                    $("#otherInstructionalMaterialsDiv").hide();
                    $("#otherInstructionalMaterials").val("");
                }

                var choiceLocation = $("#location").children("option:first").attr("inForm");
                if (choiceLocation == "2") {
                    $("#otherLocationDiv").show();
                }
                else {
                    $("#otherLocationDiv").hide();
                    $("#otherLocation").val("");
                }
                
            }
        });
 //   })
    }
//});

function removeRequired() {
    $("#ridLibraryUnit").removeAttr("required");
    $("#dateOfConsultation").removeAttr("required");
    $("#staffPennkey").removeAttr("required");
    $("#prepTime").removeAttr("required");
    $("#eventLength").removeAttr("required");
    $("#rank").removeAttr("required");
    $("#school").removeAttr("required");
    $("#interactOccurrences").removeAttr("required");
    $("#transaction-date").removeAttr("required");
}

function setRequired() {
    $("#ridLibraryUnit").attr("required", "");
    $("#dateOfConsultation").attr("required", "");
    $("#staffPennkey").attr("required", "");
    $("#prepTime").attr("required", "");
    $("#eventLength").attr("required", "");
    $("#rank").attr("required", "");
    $("#school").attr("required", "");
    $("#interactOccurrences").attr("required", "");
    $("#transaction-date").attr("required", "");
}

function setDepartment(id) {
    $('#deptModal').modal('hide');
    $('#department').val(id).attr('selected', true);
    $('.monitored-form').addClass('dirty');
    $('.active-on-change').removeAttr('disabled');

}

function isFull(obj) {

    if (obj.value.length >= 499) {
        obj.style.borderColor = "#FF0000";
        obj.style.background = "-webkit-gradient(linear, left top, left 25, from(#FFF0F5), color-stop(4%, #EEE0E5), to(#FFF0F5))";
        /*Chrome and Safari*/
        obj.style.background = "-moz-linear-gradient(top, #FFF0F5 #EEE0E5 1px, #FFF0F5 25px)";
        /* Firefox Browsers */
        obj.style.filter = "progid:DXImageTransform.Microsoft.gradient(startColorStr = '#EEE0E5', EndColorStr = '#FFF0F5')";
    }
    else {
        obj.style.borderColor = "#CCCCCC";
        obj.style.backgroundColor = "#efefef";
        obj.style.background = "-webkit-gradient(linear, left top, left 25, from(#FFFFFF), color-stop(4%, #EEEEEE), to(#FFFFFF))";
        /*Chrome and Safari*/
        obj.style.background = "-moz-linear-gradient(top, #FFFFFF, #EEEEEE 1px, #FFFFFF 25px)";
        /* Firefox Browsers */
        obj.style.filter = "progid:DXImageTransform.Microsoft.gradient(startColorStr = '#EEEEEE', EndColorStr = '#FFFFFF')";


    }
}