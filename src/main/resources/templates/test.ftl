<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>

    <link rel="stylesheet" href="/resources/css/libs/bootstrap.css" type="text/css">

    <script type="text/javascript" src="/resources/js/libs/jquery.js"></script>
    <script type="text/javascript" src="/resources/js/libs/diff_match_patch_uncompressed.js"></script>
</head>
<body>
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <textarea id="text1" class="form-control" name="text" cols="30" rows="10"></textarea>
            <textarea id="text2" class="form-control" name="text" cols="30" rows="10"></textarea>

            <input id="diff" type="button" value="Diff"/>
        </div>
    </div>

    <script>
        $('#diff').click(function () {
            var text1 = $('#text1').val();
            var text2 = $('#text2').val();

            var diffMatchPatch = new diff_match_patch;

            var message = diffMatchPatch.diff_main(text1, text2);
            console.log(message);

            diffMatchPatch.diff_cleanupSemantic(message);

            $('body').append(diffMatchPatch.diff_prettyHtml(message));

            console.log(JSON.stringify(diff_match_patch.diff_main))
        });


    </script>
</body>
</html>