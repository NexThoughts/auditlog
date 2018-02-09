<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Login</title>
    <asset:stylesheet href="floating-labels.css"/>
</head>

<body>
<div class="limiter">
    <div class="container-login100">
        <div class="wrap-login100 p-t-85 p-b-20">
            <form class="login100-form validate-form" action="${postUrl ?: '/login/authenticate'}" method="POST">
                <span class="login100-form-title p-b-70">
                    Welcome
                </span>
                <span class="login100-form-avatar">
                    <asset:image src="avatar-01.jpg" alt="AVATAR"/>
                </span>

                <div class="wrap-input100 validate-input m-t-85 m-b-35" data-validate="Enter username">
                    <input type="email" id="username" name="username" class="input100">
                    <span class="focus-input100" data-placeholder="Username"></span>
                </div>

                <div class="wrap-input100 validate-input m-b-50" data-validate="Enter password">
                    <input type="password" id="password" name="password" class="input100">
                    <span class="focus-input100" data-placeholder="Password"></span>
                </div>

                <div class="container-login100-form-btn">
                    <button class="login100-form-btn" type="submit">Login</button>
                </div>
                <ul class="login-more p-t-190">
                    <li class="m-b-8">
                        <span class="txt1">
                            Forgot
                        </span>

                        <a href="#" class="txt2">
                            Username / Password?
                        </a>
                    </li>

                    <li>
                        <span class="txt1">
                            Don’t have an account?
                        </span>

                        <a href="${createLink(controller: 'public', action: 'register')}" class="txt2">
                            Sign up
                        </a>
                    </li>
                </ul>
            </form>
        </div>
    </div>
</div>

</body>
</html>
