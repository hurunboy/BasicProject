<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>首页</title>
</head>
<body>
    hello ${userName}
    <@shiro.hasPermission name="admin">
        admin
    </@shiro.hasPermission>
    <@shiro.hasPermission name="systemUserAdd">
        systemUserAdd
    </@shiro.hasPermission>
</body>
</html>