<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Register</title>
    <asset:stylesheet href="floating-labels.css"/>
</head>

<body>
<g:form controller="public" action="saveRegister" method="POST" class="form-signin">
    <div class="form-label-group">
        <input type="email" id="username" name="username" class="form-control" placeholder="Email address" required=""
               value="${userCO.username}" autofocus="">
        <label for="username">Email address</label>
    </div>

    <div class="form-label-group">
        <input type="text" id="name" name="name" class="form-control" placeholder="Full Name" required=""
               value="${userCO.name}" autofocus="">
        <label for="username">Full Name</label>
    </div>

    <div class="form-label-group">
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required="">
        <label for="password">Password</label>
    </div>

    <div class="form-label-group">
        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control"
               placeholder="Confirm Password" required="">
        <label for="password">Confirm Password</label>
    </div>
    <g:submitButton name="save" value="Save" class="btn btn-primary btn-lg btn-block"/>
</g:form>
</body>
</html>
