<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <!-- Meta, title, CSS, favicons, etc. -->
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>Gentelella Alela! | </title>

  <!-- Bootstrap -->
  <link href="${request.contextPath}/static/vendors/bootstrap/dist/css/bootstrap.min.css"
        rel="stylesheet">
  <!-- Font Awesome -->
  <link href="${request.contextPath}/static/vendors/font-awesome/css/font-awesome.min.css"
        rel="stylesheet">
  <!-- NProgress -->
  <link href="${request.contextPath}/static/vendors/nprogress/nprogress.css" rel="stylesheet">
  <!-- Animate.css -->
  <link href="${request.contextPath}/static/vendors/animatecss/animate.min.css" rel="stylesheet">

  <!-- Custom Theme Style -->
  <link href="${request.contextPath}/static/build/css/custom.min.css" rel="stylesheet">
</head>

<body class="login">
<div>
  <a class="hiddenanchor" id="signup"></a>
  <a class="hiddenanchor" id="signin"></a>

  <div class="login_wrapper">
    <div class="animate form login_form">
      <section class="login_content">
        <form method="post" action="${request.contextPath}/login.htm">
          <h1>后台管理系统</h1>
          <div>
            <input type="text" name="loginName" class="form-control" placeholder="登录名" required=""
                    <#if loginName??> value="${loginName}" </#if> />
          </div>
          <div>
            <input type="password" name="password" class="form-control" placeholder="密码"
                   required=""/>
          </div>
          <div>
              <#if message??>
                  ${message}
              </#if>
          </div>
          <div>
            <button id="send" type="submit" class="btn btn-success">登录</button>
              <#--<a class="btn btn-default submit" href="index.html">登录</a>-->
              <#--<a class="reset_pass" href="#">忘记密码?</a>-->
          </div>
        </form>
        <div class="clearfix"></div>

        <div class="separator">

          <div class="clearfix"></div>
          <br/>

          <div>
            <h1><i class="fa fa-paw"></i> XXXX mj</h1>
            <p>©2020 All Rights Reserved. Gentelella Alela! is a Bootstrap 3 template. Privacy and
              Terms</p>
          </div>
        </div>

      </section>
    </div>

    <div id="register" class="animate form registration_form">
      <section class="login_content">
        <form>
          <h1>Create Account</h1>
          <div>
            <input type="text" class="form-control" placeholder="Username" required=""/>
          </div>
          <div>
            <input type="email" class="form-control" placeholder="Email" required=""/>
          </div>
          <div>
            <input type="password" class="form-control" placeholder="Password" required=""/>
          </div>
          <div>
            <a class="btn btn-default submit" href="index.html">Submit</a>
          </div>

          <div class="clearfix"></div>

          <div class="separator">
            <p class="change_link">Already a member ?
              <a href="#signin" class="to_register"> Log in </a>
            </p>

            <div class="clearfix"></div>
            <br/>

            <div>
              <h1><i class="fa fa-paw"></i> Gentelella Alela!</h1>
              <p>©2016 All Rights Reserved. Gentelella Alela! is a Bootstrap 3 template. Privacy and
                Terms</p>
            </div>
          </div>
        </form>
      </section>
    </div>
  </div>
</div>
</body>
</html>
