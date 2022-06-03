<%@ page import="com.dumanskyi.delivery.entities.db.User" %>
<%@ page import="com.dumanskyi.delivery.persistence.UserRepository" %>
<%@ page import="com.dumanskyi.delivery.services.api.NPService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
    <%
        UserRepository userRepository = (UserRepository) request.getAttribute("userRepository");
        NPService npService = (NPService) request.getAttribute("npService");
        User user = (User) request.getAttribute("user");
        boolean shippingAddressIsPresent = user.getShippingAddress() != null;
        String warehouseName = npService.getWarehouseById(user.getShippingAddress().getNpWarehouseId()).get(0).getValue();
    %>
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link href="/resources/css/address_form.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="/resources/scripts/address_form.js"></script>
        <script>
            $(function(){
                $("#header").load("/resources/customer_header.html");
                $("#address-form").load("/resources/address_form.html");
                $("#shipping-address-btn").on("click", function () {
                    document.getElementById('form-label').innerHTML='<%=(shippingAddressIsPresent ? "Changing" : "Adding") + " shipping address"%>';
                    document.getElementById('address-form').hidden=false
                })
            });
        </script>
    </head>
    <body>
        <div id="header"></div>
        <div class="container my-5 p-5 rounded bg-light">
            <p></p>
            <p class="h1">Hi ${user.username}</p>
            <p class="h3">First name: ${user.firstName}</p>
            <p class="h3">Last name: ${user.lastName}</p>
            <p class="h3">Patronymic: ${user.patronymic}</p>
            <p class="h5 pt-5 font-weight-bold">Shipping address: <%=shippingAddressIsPresent ? warehouseName : "Empty"%>
                <button id="shipping-address-btn" class="btn btn-primary mx-3"><%=shippingAddressIsPresent ? "Change" : "Add"%></button>
            </p>
                <span hidden="true" id="address-form" style="width:100px">

                </span>
        </div>
    </body>
</html>