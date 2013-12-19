$(function () {
    var $buttons = $('.active-on-change');
    var $form = $('.monitored-form');
    $buttons.attr('disabled', 'disabled');

    $('form').areYouSure({'silent': true});

    $form.on('dirty.areYouSure', function () {
        // Enable save button only as the form is dirty.
        $buttons.removeAttr('disabled');
    });
    $form.on('clean.areYouSure', function () {
        // Form is clean so nothing to save - disable the save button.
        $buttons.attr('disabled', 'disabled');
    });
});
