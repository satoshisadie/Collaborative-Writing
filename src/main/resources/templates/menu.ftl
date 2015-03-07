<#assign security = JspTaglibs["http://www.springframework.org/security/tags"]/>

<nav class="navbar navbar-default" role="navigation">
    <div class="container">
        <div class="row">
            <div class="col-md-offset-2 col-md-8">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">YACWT</a>
                </div>

                <div class="collapse navbar-collapse navbar-ex1-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="#">About</a></li>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <@security.authorize ifNotGranted="ROLE_USER">
                            <li><a href="/login">Log In</a></li>
                            <li><a href="/">Sign Up</a></li>
                        </@security.authorize>

                        <@security.authorize ifAnyGranted="ROLE_USER">
                            <li><a href="/logout">Log Out</a></li>
                            <li><a href="/documents/new">New document</a></li>
                            <li><a href="/documents/">My Documents</a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <@security.authentication property="principal.username"/>
                                    <b class="caret"></b>
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Action</a></li>
                                    <li><a href="#">Another action</a></li>
                                </ul>
                            </li>
                        </@security.authorize>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>


<#--<@security.authorize ifAnyGranted="ROLE_ADMIN">-->
<#--Your is Administrator-->
<#--</@security.authorize>-->
<#--<@security.authorize ifNotGranted="ROLE_ADMIN">-->
<#--Your is Nothing-->
<#--</@security.authorize><br>-->