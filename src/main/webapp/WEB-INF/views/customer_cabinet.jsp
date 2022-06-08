<%@ page import="com.dumanskyi.delivery.entities.db.User" %>
<%@ page import="com.dumanskyi.delivery.persistence.UserRepository" %>
<%@ page import="com.dumanskyi.delivery.services.api.NPService" %>
<%@ page import="com.dumanskyi.delivery.entities.db.Request" %>
<%@ page import="com.dumanskyi.delivery.utils.StringUtil" %>
<%@ page import="com.dumanskyi.delivery.entities.db.Status" %>
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
        String warehouseName = "";
        String cityName = "";
        if (shippingAddressIsPresent) {
            warehouseName = npService.getWarehouseById(user.getShippingAddress().getNpWarehouseId()).get(0).getValue();
            cityName = npService.getCityByWarehouseId(user.getShippingAddress().getNpWarehouseId()).get(0).getValue();
        }
    %>
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link href="/resources/css/address_form.css" rel="stylesheet">
        <link href="/resources/css/customer_cabinet.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="/resources/scripts/address_form.js"></script>
        <script>
            $(function(){
                $("#header").load("/resources/customer_header.html");
                $("#address-form").load("/resources/address_form.html");
                $("#shipping-address-btn").on("click", function () {
                    document.getElementById('form-label').innerHTML='<%=(shippingAddressIsPresent ? "Changing" : "Adding") + " shipping address"%>';
                    if (document.getElementById('address-form').hidden) {
                        document.getElementById('address-form').hidden=false;
                    } else {
                        document.getElementById('address-form').hidden=true;
                    }
                })
            });
            function finishRequest(id) {
                const request = new XMLHttpRequest();
                const url = '/customer/request/finished';
                request.open("POST", url);
                request.setRequestHeader('Content-Type', 'application/json');
                request.send(JSON.stringify(id));

                request.onreadystatechange = (e) => {
                    window.location.reload();
                }
            }
        </script>
    </head>
    <body style="background-image: url('/resources/imgs/background.jpg');">
        <div id="header"></div>
        <div class="container my-5 p-5 rounded bg-light w-50">
            <p class="h1">Hi ${user.username}</p>
            <p class="h3">First name: ${user.firstName}</p>
            <p class="h3">Last name: ${user.lastName}</p>
            <p class="h3">Patronymic: ${user.patronymic}</p>
            <div class="mb-2"><p class="h5 pt-5 font-weight-bold">Shipping address:
            <%if(shippingAddressIsPresent) {%>
                <p class="h6">
                    <%=npService.getWarehouseFullAddressById(user.getShippingAddress().getNpWarehouseId())%>
                </p>
            <%} else {%>
                Empty
            <%}%>
            </div>
                <button id="shipping-address-btn" class="btn btn-primary mx-3"><%=shippingAddressIsPresent ? "Change" : "Add"%></button>
            </p>
            <span hidden="true" id="address-form"></span>
            <%if (!user.getRequestList().isEmpty()) {%>
            <div class="border rounded mt-5 p-3 container">
                <p class="h3 mb-3">Requests</p>
                <div class="container p-2 m-2">

                    <%for (Request userRequest : user.getRequestList()) {%>
                    <div class="border rounded row p-2 mt-2 request">
                        <p class="h5">
                            <%if(userRequest.getStatus().equals(Status.DELIVERED)) {%>
                                <img height="20px" class="mx-1" src="/resources/imgs/check_mark.png">
                            <%}%>
                            Request #<%=userRequest.getRequestId()%>
                        </p>
                        <div class="col-sm-12 col-md-6 col-lg-4 py-2">Status: <%=userRequest.getStatus().getMessage()%></div>
                        <div class="col-sm-12 col-md-6 col-lg-4 py-2">Package size: <%=StringUtil.capitalizeFirstLetter(userRequest.getPackageSize().name())%></div>
                        <div class="col-sm-12 col-md-6 col-lg-4 py-2">Creation date: <%=userRequest.getCreationDate()%></div>
                        <div class="col-sm-12 col-md-6 col-lg-4 py-2">Transaction id: <%=userRequest.getTransactionId()%></div>
                        <div class="col-sm-12 col-md-12 col-lg-8 py-2">Delivery number: <%=userRequest.getDeliveryNumber()%></div>
                        <%if (userRequest.getStatus().equals(Status.SENT_TO_CUSTOMER)) {%>
                            <div class="col-sm-12 col-md-12 col-lg-8 py-2">Shipping address: <%=npService.getWarehouseFullAddressById(userRequest.getShippingAddress().getNpWarehouseId())%></div>
                            <div class="col-sm-12 col-md-12 col-lg-8 py-2"><input type="button" onclick="finishRequest(<%=userRequest.getRequestId()%>)" class="btn btn-success" value="I already received this package"></div>
                        <%}%>
                    </div>
                    <%}%>
                </div>
            </div>
            <%}%>
        </div>
    </body>
</html>