<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/resources/style.css" />">
        <title>Pay Bills :: NeuBank</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
    </head>
    <body>
        <header class="header-white">
            <h2 class="logo">NEUBANK</h2>
            <div class="nav">
                <a href="">Accounts</a>
                <a href="">Deposit</a>
                <a href="">Withdraw</a>
                <a href="">Transfers</a>
                <a href="">Pay Bills</a>
            </div>
            <img src="<c:url value="/resources/img/avatar.svg" />" class="avatar" />
        </header>
        <div class="container">
            <h2>Welcome to NeuBank Online Banking</h2>
            <input type="text" id="cust_id" readonly="true" class="hidden" value="<c:out value="${login_id}" />" />
            <div class="detailsSection">
                <div id="accountList">
                    <h3 class="cardTitle">Pay Bills</h3>
                    <form method="post" enctpye="application/json" class="loginForm">
                        <label for="amount">Amount:</label>
                        <input type="number" name="amount" id="amount" placeholder="Enter amount" value="1000"><br />
                        <label for="account_id">From Account:</label>
                        <select name="from_account_id" id="from_account_id">
                            <option value="" selected="true" disabled="true">Choose an account</option>
                        </select><br />
                        <label for="amount">To Provider:</label>
                        <select name="to_account_id" id="to_account_id">
                            <option value="" selected="true" disabled="true">Choose a provider</option>
                        </select><br />
                        <button type="button" onclick="checkBalance();" value="proceed">Pay</button>
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
                            document.getElementById("from_account_id").innerHTML += "<div class='accountItem'>\n\
            <option value='" + result[i].id + "'>" + result[i].id +
                                    "</option>";
                        }
                    }
                }
                ;
                xhr.send();
            }

            function getVendorList() {
                var customerId = document.getElementById("cust_id").value;
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "<%= request.getContextPath() %>/accounts/vendors", false);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.setRequestHeader("Accept", "application/json");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var result = JSON.parse(xhr.responseText);
                        console.log(result.length);
                        for (var i = 0; i < result.length; i++) {
                            document.getElementById("to_account_id").innerHTML += "<div class='accountItem'>\n\
            <option value='" + result[i].id + "'>" + result[i].fullname +
                                    "</option>";
                        }
                    }
                }
                ;
                xhr.send();
            }

            function checkBalance() {
                var accountId = document.getElementById("from_account_id").value;
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "<%= request.getContextPath() %>/account/balance/" + accountId, false);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.setRequestHeader("Accept", "application/json");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var result = xhr.responseText;
                        if (result) {
                            doTransfer();
                        } else {
                            alert("Insufficient Balance");
                        }
                    }
                }
                ;
                xhr.send();
            }

            function doTransfer() {
                var customerId = document.getElementById("cust_id").value;
                var from_account_id = document.getElementById("from_account_id").value;
                var to_account_id = document.getElementById("to_account_id").value;
                var amount = document.getElementById("amount").value;
                var data = {
                    from_account_id, to_account_id, amount
                };
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "<%= request.getContextPath() %>/transfer", false);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.setRequestHeader("Accept", "application/json");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var result = xhr.responseText;
                        window.location.replace("<%= request.getContextPath() %>/dashboard");
                    }
                }
                ;
                xhr.send(JSON.stringify(data));
            }

            window.onload = () => {
                getAccountList();
                getVendorList();
            };
        </script>

    </body>
</html>
