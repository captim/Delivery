<%@ page import="com.dumanskyi.delivery.entities.db.User" %>
<%@ page import="com.dumanskyi.delivery.persistence.UserRepository" %>
<%@ page import="com.dumanskyi.delivery.services.api.NPService" %>
<%@ page import="com.dumanskyi.delivery.entities.db.PackageSize" %>
<%@ page import="com.dumanskyi.delivery.utils.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<%
    UserRepository userRepository = (UserRepository) request.getAttribute("userRepository");
    NPService npService = (NPService) request.getAttribute("npService");
    User user = (User) request.getAttribute("user");
    boolean shippingAddressIsPresent = user.getShippingAddress() != null;
    String warehouseName = "";
    if (shippingAddressIsPresent) {
        warehouseName = npService.getWarehouseById(user.getShippingAddress().getNpWarehouseId()).get(0).getValue();
    }
%>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="/resources/css/address_form.css" rel="stylesheet">
    <script src="/resources/scripts/address_form.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        .address-form {
            padding-bottom: 1rem!important;
        }
        .error-msg {
            color: red;
        }
    </style>
    <script>
        $(function(){
            $("#header").load("/resources/customer_header.html");
            $("#address-form").load("/resources/address_form.html");
        });
        function setPrice(index) {
            console.log(index);
            document.getElementById("price").innerHTML = document.getElementsByTagName("option")[index].id;
        }
        function onload() {
            <%if (!shippingAddressIsPresent) {%>
            document.getElementById("address-form").hidden = false;
            document.getElementById("add_request_btn").disabled = true;
            document.getElementById("deliveryNumber").disabled = true;
            document.getElementById("packageSizeId").disabled = true;
            document.getElementById("creditCard.phoneNumber").disabled = true;
            document.getElementById("creditCard.number").disabled = true;
            document.getElementById("creditCard.expMonth").disabled = true;
            document.getElementById("creditCard.expYear").disabled = true;
            document.getElementById("creditCard.cvv").disabled = true;
            alert("First of all you need to choose shipping address")
            <%}%>
            document.getElementById('creditCard.expMonth').value='';
            document.getElementById('creditCard.expYear').value='';
            document.getElementById('creditCard.cvv').value='';
        }
    </script>
</head>
<body onload="onload()" style="background-image: url('/resources/imgs/background.jpg');">
    <div id="header"></div>
    <div class="w-75 container my-4 p-5 rounded bg-light">
        <div class="row justify-content-center">
            <form:form modelAttribute="request" class="col-lg-6 rounded border p-4" method="post" action="/customer/request/add" id="add_request_form">
                <p class="h2">Create new request</p>
                <div class="form-group my-5">
                    <form:label path="deliveryNumber" class="h4">Enter delivery number</form:label>
                    <form:input path="deliveryNumber" type="text" name="deliveryNumber" class="form-control mt-2 w-75" placeholder="Shop's delivery number" required="true"/>
                    <form:errors path="deliveryNumber" cssClass="error-msg"/>
                </div>
                <div class="form-group my-5">
                    <label class="h4">Choose size of your package</label>
                    <select id="packageSizeId" name="packageSizeId" class="form-select mt-2 w-75" onclick="setPrice(this.selectedIndex)">
                        <%for (PackageSize size: PackageSize.values()) {%>
                        <option value="<%=size.getId()%>" id="<%=size.getPrice()%>">
                            <%=StringUtil.capitalizeFirstLetter(size.name())%> (less than <%=size.getMaxWeight()%> kilograms) - <%=size.getPrice()%>$
                        </option>
                        <%}%>
                    </select>
                </div>
                <div class="form-group my-5">
                    <form:label path="creditCard.phoneNumber" class="h4">Enter your phone number</form:label>
                    <form:input path="creditCard.phoneNumber" typ="text" name="phoneNumber" class="form-control mt-2 w-75" placeholder="38XXXXXXXXXX" required="true"/>
                    <form:errors path="creditCard.phoneNumber" cssClass="error-msg"/>
                </div>
                <div class="rounded border py-3 px-5 w-75">
                    <div class="form-group my-2 w-75">
                        <form:label path="creditCard.number" class="h4">Card number: </form:label>
                        <form:input path="creditCard.number" type="text" name="number" class="form-control mt-2" placeholder="0000 0000 0000 0000" maxlength="16" title="Should be 16 digits" required="true"/>
                        <form:errors path="creditCard.number" cssClass="error-msg"/>
                    </div>
                    <div class="form-group my-2 container px-0 mt-3">
                        <div class="row">
                            <div class="form-group col-5">
                                <label class="h6">Expiration date: </label>
                                <div class="input-group">
                                    <form:input path="creditCard.expMonth" type="text" name="expMonth" class="form-control w-50" placeholder="MM" maxlength="2" required="true"/>
                                    <form:input path="creditCard.expYear" type="text" name="expYear" class="form-control w-50" placeholder="YY" maxlength="2" required="true"/>
                                </div>
                                <form:errors path="creditCard.expMonth" cssClass="error-msg"/>
                                <form:errors path="creditCard.expYear" cssClass="error-msg"/>
                            </div>
                            <div class="col-3">
                                <form:label path="creditCard.cvv" class="h6">CVV: </form:label>
                                <form:input path="creditCard.cvv" type="text" name="cvv" class="form-control" maxlength="3" required="true"/>
                                <form:errors path="creditCard.cvv" cssClass="error-msg"/>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>
            <div class="col-lg-4 mx-2 px-0 py-2">
                <div id="address-form" hidden="true" class="mb-2"></div>
                <div class="h-auto border rounded p-4">
                    <p class="h3">Request summary</p>
                    <p class="h5 my-4">Total to pay: <span id="price"><%=PackageSize.SMALL.getPrice()%></span>$</p>
                    <input class="btn btn-primary px-4" type="submit" id="add_request_btn" form="add_request_form" value="Place order"/>
                </div>
            </div>
        </div>
    </div>
</body>
</html>