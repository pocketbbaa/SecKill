<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!--引入jstl-->
<%@include file="common/tag.jsp" %>
<%@include file="common/head.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>登录页</title>
</head>
<body>
<div class="container">
    <form method="post" action="login">
        <div class="col-lg-6">
            <div class="input-group">
                <input type="text" class="form-control" name="phone" placeholder="input your phone ...">
                  <span class="input-group-btn">
                    <button class="btn btn-default" type="submit">Go!</button>
                  </span>
            </div><!-- /input-group -->
        </div><!-- /.col-lg-6 -->
    </form>
</div>

</body>
</html>
