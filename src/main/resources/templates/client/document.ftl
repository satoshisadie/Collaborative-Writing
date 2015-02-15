<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>

    <link rel="stylesheet" href="/resources/css/libs/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="/resources/css/libs/bootstrap-3-vert-offset-shim.css" type="text/css"/>
    <link rel="stylesheet" href="/resources/css/libs/lepture-editor/editor.css" type="text/css"/>
</head>
<body>
    <#include "../menu.ftl">

    <div class="container">
        <div class="row">
            <div class="col-md-offset-2 col-md-8">
                <textarea style="display: none;" class="form-control"></textarea>
            </div>
            <div class="col-md-2">
                <input class="save-content btn btn-primary btn-lg" type="button" value="Save"/>
            </div>
        </div>
    </div>

    <footer style="height: 100px; background-color: #333333; position: absolute; bottom: 0; width: 100%; z-index: 9999;">
        <div class="container clearfix">
        </div>
    </footer>

    <input id="document-id" type="hidden" value="${document.id?c}">

    <script type="text/javascript" src="/resources/js/libs/jquery.js"></script>
    <script type="text/javascript" src="/resources/js/libs/bootstrap.js"></script>

    <script type="text/javascript" src="/resources/js/libs/lepture-editor/editor.js"></script>
    <script type="text/javascript" src="/resources/js/libs/lepture-editor/marked.js"></script>

    <script type="text/javascript" src="/resources/js/client/document.js"></script>
</body>
</html>