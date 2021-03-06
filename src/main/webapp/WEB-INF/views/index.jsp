<%@ page import="org.springframework.security.authentication.UsernamePasswordAuthenticationToken" %>
<%@ page import="org.springframework.security.core.GrantedAuthority" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Delivery - Welcome</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script>
            $(function(){
                let header_url = "resources/anonym_header.html";
                <sec:authorize access="hasAuthority('CUSTOMER')">
                    header_url = "/resources/customer_header.html";
                </sec:authorize>
                <sec:authorize access="hasAuthority('ADMIN')">
                    header_url = "/resources/admin_header.html"
                </sec:authorize>
                <sec:authorize access="hasAuthority('DELIVERYMAN')">
                header_url = "/resources/deliveryman_header.html";
                </sec:authorize>
                $("#header").load(header_url);
            });
        </script>
    </head>
    <body style="background-image: url('/resources/imgs/background.jpg');">
        <div id="header"></div>
        <div class="container bg-light p-5">
            <p class="h3">How it works:</p>
            <p class="h6">1) You send your order to our international warehouse</p>
            <p class="h6">2) We receive it</p>
            <p class="h6">3) And send it to you</p>
        </div>
    </body>
</html>