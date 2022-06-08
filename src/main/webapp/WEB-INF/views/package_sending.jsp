<%@ page import="com.dumanskyi.delivery.persistence.RequestRepository" %>
<%@ page import="com.dumanskyi.delivery.entities.db.Request" %>
<%@ page import="com.dumanskyi.delivery.entities.db.Status" %>
<%@ page import="com.dumanskyi.delivery.utils.StringUtil" %>
<%@ page import="com.dumanskyi.delivery.services.api.NPService" %>
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
      if (key === 'sent-to-ukrainian-warehouse') {
        var checkboxes = document.getElementsByClassName("arrived-to-international-warehouse");
        for (let i = 0; i < checkboxes.length; i++) {
          if (checkboxes[i].checked === true) {
            body.push(checkboxes[i].value);
          }
        }
      } else if (key === 'sent-to-customer') {
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
  NPService npService = (NPService) request.getAttribute("npService");
%>
<body style="background-image: url('/resources/imgs/background.jpg');">
<div id="header"></div>
<div class="container my-5 p-5 rounded bg-light">
  <input id="first-btn" type="button" class="btn btn-primary" value="Sending to Ukrainian warehouse" onclick="setPage(0)">
  <input id="second-btn" type="button" class="btn btn-primary" value="Sending to customer" onclick="setPage(1)">
  <div id="first-page" hidden>
    <div class="border rounded mt-5 p-3 container">
      <form action="/deliveryman/package_receiving" method="post">
        <p class="h3 mb-3">Chose sent package and click <input type="button" id="sent-to-ukrainian-warehouse" onclick="updateStatus('sent-to-ukrainian-warehouse')" class="btn btn-primary" disabled value="Send"></p>
        <div class="container p-2 m-2">
          <%for (Request userRequest : requestRepository.findRequestByStatus(Status.ARRIVED_AT_THE_INTERNATIONAL_WAREHOUSE)) {%>
          <div class="border rounded row p-2 m-1">
            <p class="h5"><input onclick="checkboxClick('arrived-to-international-warehouse', 'sent-to-ukrainian-warehouse')" class="form-check-input arrived-to-international-warehouse" type="checkbox" value="<%=userRequest.getRequestId()%>">Request #<%=userRequest.getRequestId()%></p>
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
        <p class="h3 mb-3">Chose sent package and click <button class="btn btn-primary" id="sent-to-customer" onclick="updateStatus('sent-to-customer')" disabled>Send</button></p>
        <div class="container p-2 m-2">
          <%for (Request userRequest : requestRepository.findRequestByStatus(Status.ARRIVED_AT_THE_UKRAINIAN_WAREHOUSE)) {%>
          <div class="border rounded row p-2 m-1">
            <p class="h5"><input onclick="checkboxClick('arrived-to-ukrainian-warehouse', 'sent-to-customer')" class="form-check-input arrived-to-ukrainian-warehouse" type="checkbox" value="<%=userRequest.getRequestId()%>">Request #<%=userRequest.getRequestId()%></p>
            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Customer: <%=userRequest.getUser().getFullName()%></div>
            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Package size: <%=StringUtil.capitalizeFirstLetter(userRequest.getPackageSize().name())%></div>
            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Creation date: <%=userRequest.getCreationDate()%></div>
            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Transaction id: <%=userRequest.getTransactionId()%></div>
            <div class="col-sm-12 col-md-12 col-lg-8 py-2">Delivery number: <%=userRequest.getDeliveryNumber()%></div>
            <div class="col-sm-12 col-md-6 col-lg-4 py-2">Status: <%=userRequest.getStatus().getShortMessage()%></div>
            <div class="col-sm-12 col-md-12 col-lg-8 py-2">Customer address: <%=npService.getWarehouseFullAddressById(userRequest.getUser().getShippingAddress().getNpWarehouseId())%></div>
          </div>
          <%}%>
        </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>