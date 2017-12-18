$(function () {
    var nowTemp = new Date();
    var minDate = new Date("April 01, 2017 00:00:00");
    var maxDate = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), 0);

    var $startDate = $('#entry-record-start-date');
    var $endDate = $('#entry-record-end-date');

    $startDate.datepicker('setValue', minDate);
    $endDate.datepicker('setValue', maxDate);
    var checkin = $startDate.datepicker().on('changeDate',function (ev) {
        if(ev.date.valueOf() < minDate){
            alert("Data starts on 2017/04");
            checkin.setValue(minDate);
            return
        }

        if(ev.date.valueOf() > maxDate){
            alert("This database refreshes on a monthly basis. Information is available up through the end of last month");
            checkin.setValue(maxDate);
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
            if(ev.date.valueOf() < minDate){
                alert("Data starts on 2017/04");
                checkout.setValue(minDate);
                return
            }

            if(ev.date.valueOf() > maxDate){
                alert("This database refreshes on a monthly basis. Information is available up through the end of last month");
                checkout.setValue(maxDate);
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
