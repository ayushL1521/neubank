<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/resources/style.css" />">
        <title>Dashboard :: NeuBank</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
    </head>
    <body>
        <header class="header-white">
            <h2 class="logo">NEUBANK</h2>
            <div class="nav">
                <a href="">Accounts</a>
                <a href="<%= request.getContextPath() %>/deposits">Deposit</a>
                <a href="<%= request.getContextPath() %>/withdrawal">Withdraw</a>
                <a href="<%= request.getContextPath() %>/transfers">Transfers</a>
                <a href="<%= request.getContextPath() %>/billpay">Pay Bills</a>
            </div>
            <img src="<c:url value="/resources/img/avatar.svg" />" class="avatar" />
        </header>
        <div class="container">
            <h2>Welcome to NeuBank Online Banking</h2>
            <input type="text" id="cust_id" readonly="true" class="hidden" value="<c:out value="${login_id}" />" />
            <div class="detailsSection">
                <div id="customerDetails">
                    <h3 class="cardTitle">Customer Details</h3>
                    <img src="<c:url value="/resources/img/avatar.svg" />" class="avatar xl" />
                </div>
                <div id="accountList">
                    <h3 class="cardTitle">My Accounts</h2>
                        <div id="myAccounts"></div>
                </div>
            </div>
        </div>
        <script>
            function getCustomer() {
                var customerId = document.getElementById("cust_id").value;
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "<%= request.getContextPath() %>/customer/" + customerId, false);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.setRequestHeader("Accept", "application/json");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var customer = JSON.parse(xhr.responseText);
                        document.getElementById("customerDetails").innerHTML += "<p><b>Customer ID:</b><br />" + customer.id + "<br>" +
                                "</p><p><b>Full Name:</b><br />" + customer.fullName + "<br>" +
                                "</p><p><b>Username:</b><br />" + customer.username + "</p>";
                    }
                };
                xhr.send();
            }

            function getAccountList() {
                var customerId = document.getElementById("cust_id").value;
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "<%= request.getContextPath() %>/accounts/" + customerId, false);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.setRequestHeader("Accept", "application/json");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var result = JSON.parse(xhr.responseText);
                        console.log(result.length);
                        for (var i = 0; i < result.length; i++) {
                            document.getElementById("myAccounts").innerHTML += "<div class='accountItem'><h3 class='accountId t-blue'>" + result[i].id +
                                    "</h3><p class='balance'><b>Account Balance:</b><br />" + result[i].balance +
                                    "</p><p class='branchId'><b>Branch Code:</b><br />" + result[i].branch_id +
                                    "</p><p class='acType'><b>Account Type:</b><br />" + result[i].type + "</p></div>";
                        }
                    }
                }
                ;
                xhr.send();
            }

            window.onload = () => {
                getCustomer();
                getAccountList();
            };
        </script>

    </body>
</html>
