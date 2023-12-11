<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Product Management App</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <style>
        body {
            background-color: #f1f1f1;
        }

        .navbar {
            background-color: #333;
        }

        .navbar-brand {
            color: #fff;
        }

        .navbar-nav .nav-link {
            color: #fff;
        }

        .container {
            margin-top: 30px;
            background-color: #fff;
            padding: 30px;
            border-radius: 5px;
        }

        h3 {
            color: #333;
            margin-bottom: 20px;
        }

        table {
            background-color: #fff;
        }

        th {
            background-color: #333;
            color: #fff;
        }

        td a {
            color: #333;
            text-decoration: none;
        }
    </style>
</head>
<body>


<header>
    <nav class="navbar navbar-expand-md navbar-dark">
        <div>
            <a href="#" class="navbar-brand">Product Management App</a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list" class="nav-link">Products</a></li>
        </ul>
    </nav>
</header>
<br>

<div class="row">
    <div class="container">
        <h3 class="text-center">List of Products</h3>
        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add New Product</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Kcal</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="product" items="${productList}">
                <tr>
                    <td><c:out value="${product.getId()}"/></td>
                    <td><c:out value="${product.getName()}"/></td>
                    <td><c:out value="${product.getKcal()}"/></td>
                    <td>
                        <a href="edit?id=<c:out value='${product.getId()}' />" class="btn btn-primary btn-sm">Edit</a>
                        <a href="delete?id=<c:out value='${product.getId()}' />" class="btn btn-danger btn-sm">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>