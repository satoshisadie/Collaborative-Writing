$(document).ready(function () {
    var editor = new Editor();
    editor.render();

    var document;

    $.ajax({
        url: './json',
        type: 'GET',
        data: {
            id: $('#document-id').val()
        },
        async: false,
        dataType: 'json'
    }).done(function (response) {
        if (response.status === 'success') {
            document = response.data.document;
        } else {
            alert('Error during downloading document');
        }
    });

    $('textarea').val(document.content);

    $('.save-content').click(function () {
        $.ajax({
            url: './save',
            type: 'POST',
            data: {
                documentId: $('#document-id').val(),
                content: editor.codemirror.getValue()
            }
        }).done(function (response) {

        })
    });
});
