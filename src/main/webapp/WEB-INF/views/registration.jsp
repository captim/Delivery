<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
</head>
<body>
<div class="container">
    <form:form class="form-signin" id="signup-form" action="/registration" modelAttribute="user">
        <h2 class="form-signin-heading">Please sign up</h2>
        <p>
            <form:label path="email" for="email" class="sr-only">Email</form:label>
            <form:input path="email" type="email" id="email" name="email" class="form-control" placeholder="Email" required="true" autofocus="true"/>
            <form:errors path="email"/>
        </p>
        <p>
            <form:label path="username" for="username" class="sr-only">Username</form:label>
            <form:input path="username" type="text" id="username" name="username" class="form-control" placeholder="Username" required="true"/>
            <form:errors path="username"/>
        </p>
        <p>
            <form:label path="firstName" for="firstname" class="sr-only">First name</form:label>
            <form:input path="firstName" type="text" id="firstname" name="firstName" class="form-control" placeholder="First name" required="true"/>
            <form:errors path="firstName"/>
        </p>
        <p>
            <form:label path="lastName" for="secondname" class="sr-only">Second name</form:label>
            <form:input path="lastName" type="text" id="secondname" name="lastName" class="form-control" placeholder="Last name" required="true"/>
            <form:errors path="lastName"/>
        </p>
        <p>
            <form:label path="patronymic" for="patronymic" class="sr-only">Patronymic</form:label>
            <form:input path="patronymic" type="text" id="patronymic" name="patronymic" class="form-control" placeholder="Patronymic" required="true"/>
            <form:errors path="patronymic"/>
        </p>
        <p>
            <form:label path="password" for="password" class="sr-only">Password</form:label>
            <form:input path="password" type="password" id="password" name="password" class="form-control" placeholder="Password" required="true"/>
            <form:errors path="password"/>
        </p>
        <input type="submit" value="Sign up" class="btn btn-lg btn-primary btn-block">
        <p class="mt-2">Already have an account? <a href="/login">Log In</a></p>
    </form:form>
</div>
</body></html>