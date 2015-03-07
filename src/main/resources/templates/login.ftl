<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>

    <link rel="stylesheet" href="/resources/css/libs/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="/resources/css/libs/bootstrap-3-vert-offset-shim.css" type="text/css"/>
</head>
<body>
    <#include "menu.ftl">

    <div class="container">
        <div class="row">
            <div class="col-xs-offset-2 col-xs-8 col-md-offset-4 col-md-4">
                <form action="/check-credentials" method="POST" role="form" class="vert-offset-top-8">
                	<div class="form-group">
                		<label for="user-name">Login</label>
                		<input id="user-name" class="form-control" name="username" type="text">
                	</div>

                    <div class="form-group">
                        <label for="password" class="control-label">Password</label>
                        <input id="password" class="form-control" name="password" type="text">
                    </div>

                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
    </div>

    <#--<footer style="height: 100px; background-color: #333333; position: absolute; bottom: 0; width: 100%;">-->
        <#--<div class="container">-->
            <#--text text text text text text text text text text text text text text text text text text text-->
            <#--text text text text text text text text text text text text text text text text text text text-->
            <#--text text text text text text text text text text text text text text text text text text text-->
        <#--</div>-->
    <#--</footer>-->

    <script type="text/javascript" src="/resources/js/libs/jquery.js"></script>
    <script type="text/javascript" src="/resources/js/libs/bootstrap.js"></script>
</body>
</html>