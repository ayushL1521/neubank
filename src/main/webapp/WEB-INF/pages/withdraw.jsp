<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/resources/style.css" />">
        <title>Transfer :: NeuBank</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
    </head>
    <body>
        <header class="header-white">
            <h2 class="logo">NEUBANK</h2>
           <div class="nav">
                <a href="<%= request.getContextPath() %>/dashboard">Accounts</a>
                <a href="<%= request.getContextPath() %>/deposits">Deposit</a>
                <a href="<%= request.getContextPath() %>/withdrawal">Withdraw</a>
                <a href="<%= request.getContextPath() %>/transfers">Transfers</a>
                <a href="<%= request.getContextPath() %>/billpay">Pay Bills</a>
            </div>
            <a href="<%= request.getContextPath() %>/logout"><img src="<c:url value="/resources/img/avatar.svg" />" class="avatar" /></a>
       </header>
        <div class="container">
            <h2>Welcome to NeuBank Online Banking</h2>
            <input type="text" id="cust_id" readonly="true" class="hidden" value="<c:out value="${login_id}" />" />
            <div class="detailsSection">
                <div id="accountList">
                    <h3 class="cardTitle">Withdraw Amount</h3>
                    <form action="<%= request.getContextPath() %>/withdraw" method="post" enctpye="application/json" class="loginForm">
                        <label for="amount">Amount:</label>
                        <input type="number" name="amount" id="amount" placeholder="Enter amount" value="1000"><br />
                        <label for="account_id">Account:</label>
                        <select name="account_id" id="account_id">
                            <option value="" selected="true" disabled="true">Choose an account</option>
                        </select><br />
                        <button type="button" onclick="doWithdraw();" value="proceed">Proceed</button>
                    </form>
                </div>
            </div>
        </div>
        <script>
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
                            document.getElementById("account_id").innerHTML += "<div class='accountItem'>\n\
            <option value='" + result[i].id + "'>" + result[i].id +
                                    "</option>";
                        }
                    }
                }
                ;
                xhr.send();
            }

            function doWithdraw() {
                var customerId = document.getElementById("cust_id").value;
                var account_id = document.getElementById("account_id").value;
                var amount = document.getElementById("amount").value;
                var data = {
                    account_id, amount
                };
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "<%= request.getContextPath() %>/withdraw", false);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.setRequestHeader("Accept", "application/json");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        //var result = JSON.parse(xhr.responseText);
                        //console.log(result);
                        window.location.replace("<%= request.getContextPath() %>/dashboard");
                    }
                }
                ;
                xhr.send(JSON.stringify(data));
            }

            window.onload = () => {
                getAccountList();
            };
        </script>

    </body>
</html>
