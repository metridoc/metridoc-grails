$(function () {
    var nowTemp = new Date();
    var begin = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
    var end = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

    var $startDate = $('#entry-record-start-date');
    var $endDate = $('#entry-record-end-date');

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
        $('#entry-record-end-date')[0].focus();
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
