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
                <textarea style="display: none;" class="form-control">${document.content}</textarea>
            </div>
            <div class="col-md-2">
                <input class="save-content-button btn btn-primary" type="button" value="Save"/>
                <input class="show-changes-button vert-offset-top-1 btn btn-primary" type="button" value="Show changes"/>
            </div>
        </div>
    </div>

    <#--<footer style="height: 100px; background-color: #333333; bottom: 0; width: 100%;">-->
        <#--<div class="container clearfix">-->
        <#--</div>-->
    <#--</footer>-->

    <input id="document-id" type="hidden" value="${document.id?c}">

    <div class="modal" id="modal-id">
    	<div class="modal-dialog modal-lg">
    		<div class="modal-content">
    			<div class="modal-header">
    				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    				<h4 class="modal-title">Modal title</h4>
    			</div>

    			<div class="modal-body">
                    <div class="row">
                        <div id="last-version-of-content" class="col-md-6"></div>
                        <div id="modified-version-of-content" class="col-md-6" style="border-left: 1px solid darkgray;"></div>
                    </div>
                </div>

    			<div class="modal-footer">
    				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    			</div>
    		</div><!-- /.modal-content -->
    	</div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <script type="text/javascript" src="/resources/js/libs/jquery.js"></script>
    <script type="text/javascript" src="/resources/js/libs/bootstrap.js"></script>
    <script type="text/javascript" src="/resources/js/libs/diff_match_patch_uncompressed.js"></script>
    <script type="text/javascript" src="/resources/js/libs/lepture-editor/editor.js"></script>
    <script type="text/javascript" src="/resources/js/libs/lepture-editor/marked.js"></script>

    <script type="text/javascript" src="/resources/js/client/document.js"></script>
</body>
</html>