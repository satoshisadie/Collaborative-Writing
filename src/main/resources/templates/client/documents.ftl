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
                <h3>My Documents</h3>
                <#list documents as document>
                    <div>
                        <h4>
                            <a href="/documents/${document.id?c}/edit">${document.title}</a>
                        </h4>

                        <a href="#">Edit</a>
                        <a href="#">Discuss</a>
                        <a href="#">Share</a>
                    </div>
                </#list>
            </div>
        </div>
    </div>

    <footer style="height: 100px; background-color: #333333; position: absolute; bottom: 0; width: 100%; z-index: 9999;">
        <div class="container clearfix">
        </div>
    </footer>

    <script type="text/javascript" src="/resources/js/libs/jquery.js"></script>
    <script type="text/javascript" src="/resources/js/libs/bootstrap.js"></script>
</body>
</html>