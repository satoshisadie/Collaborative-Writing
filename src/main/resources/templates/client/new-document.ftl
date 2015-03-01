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
            <div class="col-md-offset-3 col-md-6">
                <form action="/documents/submit-new" method="POST" role="form">
                    <h3>New Document Form</h3>

                    <div class="form-group">
                        <label for="title">Title</label>
                        <input id="title" class="form-control" type="text" name="title">
                    </div>

                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" class="form-control" name="description" cols="30" rows="5"></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">Create</button>
                </form>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="/resources/js/libs/jquery.js"></script>
    <script type="text/javascript" src="/resources/js/libs/bootstrap.js"></script>
</body>
</html>