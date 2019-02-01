<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-user"></i> 平台列表</h2>
                <h2 style="float: right"><a href="./add">添加账号</a></h2>
            </div>
            <div class="box-content">
                <table class="table table-striped table-bordered bootstrap-datatable datatable responsive">
                    <thead>
                    <tr>
                        <th>平台</th>
                        <th>账号</th>
                        <th>用户名</th>
                        <th>密码</th>
                        <th>登陆接口</th>
                        <th>余额接口</th>
                        <th>投注接口</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if list??>
                        <#list list as e>
                        <tr>
                            <td>${(e.platCategory.name)!''}</td>
                            <td>${(e.name)!''}</td>
                            <td>${(e.username)!''}</td>
                            <td>${(e.password)!''}</td>
                            <td>${(e.loginUrl)!''}</td>
                            <td>${(e.balanceUrl)!''}</td>
                            <td>${(e.touzhuUrl)!''}</td>
                            <td>
                                <a  href="./edit?id=${(e.id)!''}" class="btn btn-info btn-xs">
                                <i class="glyphicon glyphicon-edit icon-white"></i>
                                修改
                                </a>
                            </td>
                        </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<#include "fragment/footer.ftl" >
