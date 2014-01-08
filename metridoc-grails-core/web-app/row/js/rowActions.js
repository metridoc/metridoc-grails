
var rowActions;
jQuery(function ($) {
    rowActions = (function () {
        var result = {};
        /**
         * fires off a delete call to a delete link in a form
         * @param id
         */
        function deleteById(id) {
            if (window.confirm("are you sure you want to do this?")) {
                $('#mdForm-delete-' + id).submit();
            }
        }

        /**
         * add icons and enables delete actions on all a.delete links
         */
        result.transformDeleteLinks = function() {
            var $deleteLink = $('a.delete-link');
            $deleteLink.html("<i class='icon-trash'></i>");
            $deleteLink.click(function (event) {
                //the event will be the enclosed trash icon
                var id = $(event.target).parent().attr('id');
                var deleteRegex = /^delete-(.+)$/g;
                var match = deleteRegex.exec(id);
                var itemToDelete = match[1];
                deleteById(itemToDelete)
            });
        };

        return result;
    }());

    /**
     * do transformation of delete links upon loading
     */
    rowActions.transformDeleteLinks();
});




