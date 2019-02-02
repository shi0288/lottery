<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-user"></i> 预测列表</h2>
                <h2 style="float: right"><a href="javascript:void(0);" role="setting">查看账号</a></h2>
            </div>
            <div class="box-content">
                <table class="table table-striped table-bordered bootstrap-datatable datatable responsive">
                    <thead>
                    <tr>
                        <th>游戏名称</th>
                        <th>期次</th>
                        <th>预测</th>
                        <th>获取时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if list??>
                        <#list list as e>
                        <tr>
                            <td>重庆时时彩</td>
                            <td>${(e.term)!''}</td>
                            <td>${convert('ssc',e.data)!''}</td>
                            <td>${(e.createAt?string("yyyy-MM-dd HH:mm:ss"))!''}</td>
                        </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal fade bs-example-modal-lg" id="setting" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span>
                    <span class="sr-only">Close</span></button>
                <h4 class="modal-title">获取账号</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <input type="hidden"  class="form-control" value="${(e.id)!''}"  id="id" >
                    <div class="control-group">
                        <label class="control-label">用户名</label>
                        <input type="text"  class="form-control" value="${(e.username)!''}"  id="username" >
                    </div>
                    <div class="control-group">
                        <label class="control-label">密码</label>
                        <input type="text"  class="form-control" value="${(e.password)!''}"  id="password">
                    </div>
                    <div class="control-group">
                        <label class="control-label">登陆地址</label>
                        <input type="text"  class="form-control" value="${(e.loginUrl)!''}"  id="login_url">
                    </div>
                    <div class="control-group">
                        <label class="control-label">数据地址</label>
                        <input type="text"  class="form-control" value="${(e.dataUrl)!''}"  id="data_url">
                    </div>
                    <div class="control-group">
                        <label class="control-label">中奖历史</label>
                        <input type="text"  class="form-control" value="${(e.prizeUrl)!''}"  id="prize_url">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary conform-but">保存</button>
            </div>
        </div>
    </div>
</div>

<#include "fragment/footer.ftl" >

<script>
    $(function () {
        $('body').on('click','[role="setting"]', function () {
            var self = $(this);
            $.showModal("setting", function () {
                var id = $('#id').val();
                var username = $('#username').val();
                var password = $('#password').val();
                var loginUrl = $('#login_url').val();
                var dataUrl = $('#data_url').val();
                var prizeUrl = $('#prize_url').val();
                $.localAjax('./setting', {id: id, username: username,password:password,loginUrl:loginUrl,dataUrl:dataUrl,prizeUrl:prizeUrl}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            })

        })
    })
</script>