
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Please sign in</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="/resources/scripts/registration.js"></script>
    <script>
        $('.form-signup').submit(function(){
            let header_url = "resources/anonym_header.html";
            $("#header").load(header_url);
        });
    </script>
</head>
<body>
<div class="container">
    <form class="form-signin" id="signup-form">
        <h2 class="form-signin-heading">Please sign up</h2>
        <p>
            <label for="email" class="sr-only">Email</label>
            <input type="email" id="email" name="email" class="form-control" placeholder="Email" onchange="uniqueEmailValidation(document.getElementById('email'))" required autofocus>
        </p>
        <p>
            <label for="username" class="sr-only">Username</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="Username" onchange="uniqueUsernameValidation(document.getElementById('username'))" required autofocus>
        </p>
        <p>
            <label for="firstname" class="sr-only">First name</label>
            <input type="text" id="firstname" name="firstName" class="form-control" placeholder="First name" required autofocus>
        </p>
        <p>
            <label for="secondname" class="sr-only">Second name</label>
            <input type="text" id="secondname" name="lastName" class="form-control" placeholder="Last name" required autofocus>
        </p>
        <p>
            <label for="patronymic" class="sr-only">Patronymic</label>
            <input type="text" id="patronymic" name="patronymic" class="form-control" placeholder="Patronymic" required autofocus>
        </p>
        <p>
            <label for="password" class="sr-only">Password</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
        </p>
        <input type="button" value="Sign up" class="btn btn-lg btn-primary btn-block" onclick="createCustomer()">
        <p class="mt-2">Already have an account? <a href="/login">Log In</a></p>
    </form>
</div>
</body></html>