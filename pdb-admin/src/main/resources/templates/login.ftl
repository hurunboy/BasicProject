<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>登录</title>
</head>
<body>
<h1>
    <#if message??>
        ${message}
    </#if></h1>
<form method="post" action="${request.contextPath}/login">
    用户名：<input name="username"><br>
    密码：<input name="password"><br>
    <input type="submit" value="登录">
</form>

</body>
</html>
