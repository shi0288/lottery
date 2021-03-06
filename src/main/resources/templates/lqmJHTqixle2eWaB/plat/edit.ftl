<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-8">
        <div class="box-inner">
            <div class="box-header well">
                <h2><i class="glyphicon glyphicon-edit"></i> 修改账户</h2>
                <div class="box-icon">
                    <a href="javascript:void(0);" id="submit" class="btn btn-round btn-default "><i
                            class="glyphicon glyphicon-ok"></i></a>
                </div>
            </div>
            <div class="box-content">
                <form action="update" method="post" id="filter">
                    <input type="hidden" name="id" value="${(e.id)!''}"/>
                    <div class="form-group">
                        <label for="selectCate">平台</label>
                        <div class="controls">
                            <select id="selectCate" data-placeholder="请选择" name="categoryId">
                                <option value=""></option>
                            <#if cateList??>
                                <#list cateList as item >
                                    <#if item.id==e.categoryId>
                                        <option value="${item.id}" selected>${item.name}</option>
                                    <#else>
                                        <option value="${item.id}">${item.name}</option>
                                    </#if>
                                </#list>
                            </#if>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="username">账号名称</label>
                        <input type="text" auto-complete="off" class="form-control" value="${(e.name)!''}" name="name"
                               id="name" placeholder="用户名">
                    </div>
                    <div class="form-group">
                        <label for="username">用户名</label>
                        <input type="text" auto-complete="off" class="form-control" value="${(e.username)!''}"
                               name="username" id="username" placeholder="用户名">
                    </div>
                    <div class="form-group">
                        <label for="password">密码</label>
                        <input type="text" auto-complete="off" class="form-control" value="${(e.password)!''}"
                               name="password" id="password" placeholder="密码">
                    </div>
                    <div class="form-group">
                        <label for="loginUrl">登陆接口</label>
                        <input type="text" auto-complete="off" class="form-control" value="${(e.loginUrl)!''}"
                               name="loginUrl" id="loginUrl" placeholder="登陆接口">
                    </div>
                    <div class="form-group">
                        <label for="balanceUrl">余额接口</label>
                        <input type="text" auto-complete="off" class="form-control" value="${(e.balanceUrl)!''}"
                               name="balanceUrl" id="balanceUrl" placeholder="余额接口">
                    </div>
                    <div class="form-group">
                        <label for="touzhuUrl">投注接口</label>
                        <input type="text" auto-complete="off" class="form-control" value="${(e.touzhuUrl)!''}"
                               name="touzhuUrl" id="touzhuUrl" placeholder="投注接口">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>



<#include "fragment/footer.ftl" >

<script>
    $(function () {
        $('#selectCate').chosen({width: '20%'});
        $("#submit").on('click', function () {
            $.localFormAjax('filter', function (res) {
                alert('操作成功', function () {
                    window.location.href = './list';
                });
            })
        })

    })
</script>