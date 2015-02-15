$(document).ready(function () {
    var diffMatchPatch = new diff_match_patch();

    var editor = new Editor();
    editor.render();

    var lastVersionOfContent = editor.codemirror.getValue();

    //var document;
    //
    //$.ajax({
    //    url: './json',
    //    type: 'GET',
    //    data: {
    //        id: $('#document-id').val()
    //    },
    //    async: false,
    //    dataType: 'json'
    //}).done(function (response) {
    //    if (response.status === 'success') {
    //        document = response.data.document;
    //    } else {
    //        alert('Error during downloading document');
    //    }
    //});
    //
    //editor.codemirror.setValue(document.content);

    $('.save-content-button').click(function () {
        $.ajax({
            url: './save',
            type: 'POST',
            data: {
                documentId: $('#document-id').val(),
                content: editor.codemirror.getValue()
            }
        }).done(function (response) {
            if (response.status === 'success') {
                console.log('Saved');
            }
        })
    });

    $('.show-changes-button').click(function () {
        var modifiedContent = editor.codemirror.getValue();

        var message = diffMatchPatch.diff_main(lastVersionOfContent, modifiedContent);
        diffMatchPatch.diff_cleanupSemantic(message);
        var prettyHtmlMessage = customPrettifyDiff(message);

        var pattern_amp = /&/g;
        var pattern_lt = /</g;
        var pattern_gt = />/g;
        var pattern_para = /\n/g;

        var preparedForViewLastVersion = lastVersionOfContent
            .replace(pattern_amp, '&amp;')
            .replace(pattern_lt, '&lt;')
            .replace(pattern_gt, '&gt;')
            //.replace(pattern_para, '&para;<br>');
            .replace(pattern_para, '<br>');

        $('#last-version-of-content').html(preparedForViewLastVersion);
        $('#modified-version-of-content').html(prettyHtmlMessage);

        $('#modal-id').modal('show');
    });

    function customPrettifyDiff(diffs) {
        var html = [];
        var pattern_amp = /&/g;
        var pattern_lt = /</g;
        var pattern_gt = />/g;
        var pattern_para = /\n/g;

        for (var x = 0; x < diffs.length; x++) {
            var op = diffs[x][0];    // Operation (insert, delete, equal)
            var data = diffs[x][1];  // Text of change.

            var text = data
                .replace(pattern_amp, '&amp;')
                .replace(pattern_lt, '&lt;')
                .replace(pattern_gt, '&gt;')
                //.replace(pattern_para, '&para;<br>');
                .replace(pattern_para, '<br>');

            switch (op) {
                case DIFF_INSERT:
                    html[x] = '<ins style="background:#e6ffe6;">' + text + '</ins>';
                    break;
                case DIFF_DELETE:
                    html[x] = '<del style="background:#ffe6e6;">' + text + '</del>';
                    break;
                case DIFF_EQUAL:
                    html[x] = '<span>' + text + '</span>';
                    break;
            }
        }

        return html.join('');
    }
});
