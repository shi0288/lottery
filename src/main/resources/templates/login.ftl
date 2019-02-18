<!DOCTYPE html>
<html>
<head>
    <title>管理系统</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <!-- The styles -->
<#include "fragment/css.ftl"  />
</head>

<body>
<div class="ch-container">
    <div class="row">
        <div class="row">
            <div class="col-md-12 center login-header">
                <h2>管理系统</h2>
            </div>
        </div>
        <div class="row">
            <div class="well col-md-5 center login-box">
                <div class="alert alert-info">
                    请输入用户名和密码
                </div>
                <form class="form-horizontal" action="checkUser" id="filter" method="post">
                    <fieldset>
                        <div class="input-group input-group-lg">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user red"></i></span>
                            <input type="text" name="username" class="form-control" placeholder="Username">
                        </div>
                        <div class="clearfix"></div>
                        <br>
                        <div class="input-group input-group-lg">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
                            <input type="password" name="password" class="form-control" placeholder="Password">
                        </div>
                        <div class="clearfix"></div>
                        <p class="center col-md-5">
                            <button type="button" id="login" class="btn btn-primary">登录</button>
                        </p>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- external javascript -->
<#include "fragment/js.ftl"  />
<#include  "fragment/common.ftl" >
<script>
    $(function(){
        //鼠标单击登录
        $('#login').on('click',function(){
            $.localFormAjax('filter',function(res){
                window.location.href=  '/lqmJHTqixle2eWaB/user/list';
            })
        })
        //键盘回车登录
        $("body").keydown(function () {
            if (event.keyCode == "13"){
                $.localFormAjax('filter',function(res){
                    window.location.href=  '/lqmJHTqixle2eWaB/user/list';
                })
            }
        })
    })
</script>
</body>
</html>
