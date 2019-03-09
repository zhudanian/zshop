<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>我的购物车</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>

    <script>
        function shopping(){
            location.href='${pageContext.request.contextPath}/front/product/main';
        }

    </script>
</head>

<body>
<div class="navbar navbar-default clear-bottom">
    <div class="container">
        <%request.setAttribute("index",2);%>

        <!-- navbar start -->
        <jsp:include page="top.jsp"/>
        <!-- navbar end -->
    </div>
</div>
<!-- content start -->
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="page-header" style="margin-bottom: 0px;">
                <h3>我的购物车</h3>
            </div>
        </div>
    </div>
    <table class="table table-hover table-striped table-bordered">
        <tr>
            <th>
                <input type="checkbox" id="all">
            </th>
            <th>序号</th>
            <th>商品名称</th>
            <th>商品图片</th>
            <th>商品数量</th>
            <th>商品单价</th>
            <th>商品总价</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${sessionScope.shoppingcart.items}" var="item" varStatus="status">
        <tr>
            <td>
                <input type="checkbox">
            </td>
            <td>${status.count}</td>
            <td>${item.product.name}</td>
            <td> <img src="images/hotaddress1.jpeg" alt="" width="60" height="60"></td>
            <td>
                <input type="text" value="${item.quantity}" size="5">
            </td>
            <td>${item.product.price}</td>
            <td>${item.product.price*item.quantity}</td>
            <td>
                <button class="btn btn-success" type="button"> <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>修改</button>
                <button class="btn btn-danger" type="button" onclick="javaScript:return confirm('是否确认删除');">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                </button>
            </td>
        </tr>
        </c:forEach>
        <tr>
            <td colspan="7" align="right">
                <button class="btn btn-warning margin-right-15" type="button"> 删除选中项</button>
                <button class="btn btn-warning  margin-right-15" type="button"> 清空购物车</button>
                <button class="btn btn-warning margin-right-15" type="button" onclick="shopping()"> 继续购物</button>
                <a href="order.html">
                    <button class="btn btn-warning " type="button"> 结算</button>
                </a>
            </td>
        </tr>

        <tr>
            <td colspan="7" align="right" class="foot-msg">
                总计： <b><span>${shoppingcart.totalMoney}</span> </b>元
            </td>
        </tr>
    </table>
</div>
<!-- content end-->
<!-- footers start -->
<div class="footers">
    版权所有：中兴软件技术
</div>
<!-- footers end -->

</body>

</html>