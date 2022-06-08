<%@ page import="com.dumanskyi.delivery.persistence.RequestRepository" %>
<%@ page import="com.dumanskyi.delivery.entities.db.Request" %>
<%@ page import="com.dumanskyi.delivery.entities.db.Status" %>
<%@ page import="com.dumanskyi.delivery.utils.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="/resources/css/address_form.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/resources/scripts/address_form.js"></script>
    <script>
        var pageNumber = 0;
        $(function() {
            $("#header").load("/resources/deliveryman_header.html");
        });
        function setPage(pageNumber) {
            if (pageNumber === 0) {
                document.getElementById("first-btn").disabled = true;
                document.getElementById("second-btn").disabled = false;
                document.getElementById("first-page").hidden = false;
                document.getElementById("second-page").hidden = true;
            } else {
                document.getElementById("first-btn").disabled = false;
                document.getElementById("second-btn").disabled = true;
                document.getElementById("first-page").hidden = true;
                document.getElementById("second-page").hidden = false;
            }
        }
        function checkboxClick(checkboxClass, buttonId) {
            var checkboxes = document.getElementsByClassName(checkboxClass);
            for (let i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].checked === true) {
                    document.getElementById(buttonId).disabled = false;
                    return;
                }
            }
            document.getElementById(buttonId).disabled = true;
        }
        function updateStatus(key) {
            var body = [];
            if (key === 'receive-international') {
                var checkboxes = document.getElementsByClassName("not-arrived");
                for (let i = 0; i < checkboxes.length; i++) {
                    if (checkboxes[i].checked === true) {
                        body.push(checkboxes[i].value);
                    }
                }
            } else if (key === 'receive-ukrainian') {
                var checkboxes = document.getElementsByClassName("arrived-to-ukrainian-warehouse");
                for (let i = 0; i < checkboxes.length; i++) {
                    if (checkboxes[i].checked === true) {
                        body.push(checkboxes[i].value);
                    }
                }
            }
            const request = new XMLHttpRequest();
            const url = '/deliveryman/updateRequestStatus';
            request.open("POST", url);
            request.setRequestHeader('Content-Type', 'application/json');
            request.send(JSON.stringify(body));

            request.onreadystatechange = (e) => {
                window.location.reload();
            }
        }
    </script>
</head>
<%
    RequestRepository requestRepository = (RequestRepository) request.getAttribute("requestRepository");
%>
<body style="background-image: url('/resources/imgs/background.jpg');">
    <div id="header"></div>
    <div class="container my-5 p-5 rounded bg-light">
        <input id="first-btn" type="button" class="btn btn-primary" value="Receiving at international warehouse" onclick="setPage(0)">
        <input id="second-btn" type="button" class="btn btn-primary" value="Receiving at Ukrainian warehouse" onclick="setPage(1)">
        <div id="first-page" hidden>
            <div class="border rounded mt-5 p-3 container">
                <form action="/deliveryman/package_receiving" method="post">
                    <p class="h3 mb-3">Chose received package and click <input type="button" id="receive-international" onclick="updateStatus('receive-international')" class="btn btn-primary" disabled value="Receive"></p>
                    <div class="container p-2 m-2">
                        <%for (Request userRequest : requestRepository.findRequestByStatus(Status.NOT_ARRIVED)) {%>
                        <div class="border rounded row p-2 m-1">
                            <p class="h5"><input onclick="checkboxClick('not-arrived', 'receive-international')" class="form-check-input not-arrived" type="checkbox" value="<%=userRequest.getRequestId()%>">Request #<%=userRequest.getRequestId()%></p>
                            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Customer: <%=userRequest.getUser().getFullName()%></div>
                            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Package size: <%=StringUtil.capitalizeFirstLetter(userRequest.getPackageSize().name())%></div>
                            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Creation date: <%=userRequest.getCreationDate()%></div>
                            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Transaction id: <%=userRequest.getTransactionId()%></div>
                            <div class="col-sm-12 col-md-12 col-lg-8 py-2">Delivery number: <%=userRequest.getDeliveryNumber()%></div>
                            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Status: <%=userRequest.getStatus().getShortMessage()%></div>
                        </div>
                        <%}%>
                    </div>
                </form>
            </div>
        </div>
        <div id="second-page" hidden>
            <div class="border rounded mt-5 p-3 container">
                <form>
                    <p class="h3 mb-3">Chose received package and click <button class="btn btn-primary" id="receive-ukrainian" onclick="updateStatus('receive-ukrainian')" disabled>Receive</button></p>
                    <div class="container p-2 m-2">
                        <%for (Request userRequest : requestRepository.findRequestByStatus(Status.SENT_TO_UKRAINE)) {%>
                        <div class="border rounded row p-2 m-1">
                            <p class="h5"><input onclick="checkboxClick('arrived-to-ukrainian-warehouse', 'receive-ukrainian')" class="form-check-input arrived-to-ukrainian-warehouse" type="checkbox" value="<%=userRequest.getRequestId()%>">Request #<%=userRequest.getRequestId()%></p>
                            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Customer: <%=userRequest.getUser().getFullName()%></div>
                            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Package size: <%=StringUtil.capitalizeFirstLetter(userRequest.getPackageSize().name())%></div>
                            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Creation date: <%=userRequest.getCreationDate()%></div>
                            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Transaction id: <%=userRequest.getTransactionId()%></div>
                            <div class="col-sm-12 col-md-12 col-lg-8 py-2">Delivery number: <%=userRequest.getDeliveryNumber()%></div>
                            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Status: <%=userRequest.getStatus().getShortMessage()%></div>
                        </div>
                        <%}%>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>