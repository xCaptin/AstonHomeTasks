<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Product Management Application</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .navbar {
            background-color: #007bff;
        }

        .navbar-brand {
            color: #fff;
            font-weight: bold;
        }

        .navbar-nav .nav-link {
            color: #fff;
        }

        .container {
            margin-top: 30px;
        }

        .card {
            border: none;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .card-body {
            padding: 30px;
        }

        .form-group label {
            font-weight: bold;
        }

        .form-control {
            border-radius: 5px;
        }

        button[type="submit"] {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark">
        <div>
            <a href="#" class="navbar-brand">Products Shop</a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list" class="nav-link">Products</a></li>
        </ul>
    </nav>
</header>

<br>
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <c:if test="${product != null}">
            <form action="update" method="post">
                </c:if>
                <c:if test="${product == null}">
                <form action="insert" method="post">
                    </c:if>

                    <caption>
                        <h2>
                            <c:if test="${product != null}">
                                Edit Product
                            </c:if>
                            <c:if test="${product == null}">
                                Add New Product
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${product != null}">
                        <input type="hidden" name="id" value="<c:out value='${product.getId()}' />"/>
                    </c:if>

                    <fieldset class="form-group">
                        <label>Product Name</label>
                        <input type="text" value="<c:out value='${product.getName()}' />" class="form-control"
                               name="name" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Product Kcal</label>
                        <input type="text" value="<c:out value='${product.getKcal()}' />" class="form-control"
                               name="kcal" pattern="[0-9]+" title="Please enter only numbers">
                    </fieldset>

                    <button type="submit" class="btn btn-primary">Save</button>
                </form>
        </div>
    </div>
</div>
</body>
</html>