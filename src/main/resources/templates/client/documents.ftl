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
            <div class="form-group">
                <div class="col-md-offset-2 col-md-8">
                    <#list documents as document>
                        <div>
                            <h4>
                                <a href="/documents/${document.id?c}/edit">${document.id?c}</a>
                            </h4>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>

    <footer style="height: 100px; background-color: #333333; position: absolute; bottom: 0; width: 100%; z-index: 9999;">
        <div class="container clearfix">
        </div>
    </footer>

    <script type="text/javascript" src="/resources/js/libs/jquery.js"></script>
    <script type="text/javascript" src="/resources/js/libs/bootstrap.js"></script>

    <script type="text/javascript" src="/resources/js/libs/lepture-editor/editor.js"></script>
    <script type="text/javascript" src="/resources/js/libs/lepture-editor/marked.js"></script>

    <script type="text/javascript" src="/resources/js/client/document.js"></script>
</body>
</html>