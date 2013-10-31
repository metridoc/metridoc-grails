<div id="${myID ? myID : 'myModal'}" class="modal hide fade" role="dialog">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">x</button>

        <h3 id="myModalTitle" style="color: #48802c;">${title}</h3>
    </div>

    <div class="modal-body">Loading ...</div>

    %{--<div class="modal-footer">  </div>--}%
</div>

<script type="text/javascript">
    $('#myModal').on('hidden', function () {
        $(this).removeData('modal');
    });
    $('#myDepartment').on('hidden', function () {
        $(this).removeData('modal');
    });
    // just for spreadsheet uploading function in ridLibraryUnit administration page
    $(document).delegate('input[id=spreadsheetUpload]', 'change', function () {
        var fileName = $(this).val().replace("C:\\fakepath\\", "");
        $('#spreadsheetUploadPath').val(fileName);
    });
</script>