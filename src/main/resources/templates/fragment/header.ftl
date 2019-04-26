<!DOCTYPE html>
<html>
<head>
    <title>管理员系统</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <!-- The styles -->
<#include "fragment/css.ftl"  />
</head>

<body>
<!-- topbar starts -->
<div class="navbar navbar-default" role="navigation">
    <div class="navbar-inner">
        <button type="button" class="navbar-toggle pull-left animated flip">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <div class="btn-group pull-right">
            <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                <i class="glyphicon glyphicon-user"></i><span class="hidden-sm hidden-xs">${(manage.username)!''}</span>
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <li class="divider"></li>
                <li><a href="/JiswyAaKgoJmqutA/logout">退出</a></li>
            </ul>
        </div>
    </div>
</div>
<!-- topbar ends -->
<div class="ch-container">
    <div class="row">
        <!-- left menu starts -->
        <div class="col-sm-2 col-lg-2">
            <div class="sidebar-nav">
                <div class="nav-canvas">
                    <div class="nav-sm nav nav-stacked">
                    </div>
                    <ul class="nav nav-pills nav-stacked main-menu">
                        <li>
                            <a class="ajax-link" href="${base}/lqmJHTqixle2eWaB/user/list">
                                <i class="glyphicon glyphicon-user"></i>&nbsp;<span>用户管理</span>
                            </a>
                            <#if manage.username=='lottery'>
                            <a class="ajax-link" href="${base}/lqmJHTqixle2eWaB/manage/list">
                                <i class="glyphicon glyphicon-user"></i>&nbsp;<span>权限管理</span>
                            </a>
                            </#if>
                        </li>
                        <li class="accordion">
                            <a href="javascript:void(0);"><i
                                    class="glyphicon glyphicon-plus"></i><span> 数据查看</span></a>
                            <ul class="nav nav-pills nav-stacked"  style="display: block;" >
                                <li><a href="${base}/lqmJHTqixle2eWaB/line/penetrated"> 穿越轴</a></li>
                                <li><a href="${base}/lqmJHTqixle2eWaB/line/bull"> BULL曲线</a></li>
                            </ul>
                        </li>
                        <#if manage.username=='lottery'>
                        <li class="accordion">
                            <a href="javascript:void(0);"><i
                                    class="glyphicon glyphicon-plus"></i><span> 投注管理</span></a>
                            <ul class="nav nav-pills nav-stacked"  style="display: block;" >
                                <li><a href="${base}/lqmJHTqixle2eWaB/order/list"> 投注列表</a></li>
                            </ul>
                        </li>
                        </#if>
                        <li class="accordion">
                            <a href="javascript:void(0);"><i
                                    class="glyphicon glyphicon-plus"></i><span> 预测管理</span></a>
                            <ul class="nav nav-pills nav-stacked"  style="display: block;" >
                                <li><a href="${base}/lqmJHTqixle2eWaB/prediction/list/chongqingshishicai"> 重庆时时彩</a></li>
                                <li><a href="${base}/lqmJHTqixle2eWaB/prediction/list/T01"> 腾讯分分彩</a></li>
                            </ul>
                        </li>
                        <#if manage.username=='lottery'>
                        <li class="accordion">
                            <a href="javascript:void(0);"><i
                                    class="glyphicon glyphicon-plus"></i><span> 平台管理</span></a>
                            <ul class="nav nav-pills nav-stacked"  style="display: block;" >
                                <li><a href="${base}/lqmJHTqixle2eWaB/plat/list"> 平台列表</a></li>
                                <li><a href="${base}/lqmJHTqixle2eWaB/plat/listTerminal"> 交互控制</a></li>
                            </ul>
                        </li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
        <!--/span-->
        <!-- left menu ends -->
        <div id="content" class="col-lg-10 col-sm-10">
            <!-- content starts -->
