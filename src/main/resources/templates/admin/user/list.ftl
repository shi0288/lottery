<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-user"></i> 用户列表</h2>
                <h2 style="float: right"><a href="./add">添加用户</a></h2>
            </div>
            <div class="box-content">
                <table class="table table-striped table-bordered bootstrap-datatable datatable responsive">
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>余额</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if list??>
                        <#list list as e>
                        <tr>
                            <td>${(e.username)!''}</td>
                            <td>${(e.balance?string('#.##'))!''}</td>
                            <td>
                                <a  href="./edit?id=${(e.id)!''}" class="btn btn-info btn-xs">
                                    <i class="glyphicon glyphicon-edit icon-white"></i>
                                    修改
                                </a>
                                <button role="recharge"  tag="${(e.id)!''}" class="btn btn-warning btn-xs">
                                    <i class="glyphicon glyphicon-yen icon-white"></i>
                                    充值
                                </button>
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

<div class="modal fade" id="recharge_setting" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span>
                    <span class="sr-only">Close</span></button>
                <h4 class="modal-title">用户充值</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="control-group">
                        <label class="control-label">充值金额</label>
                        <input type="text"  class="form-control"  id="balance" placeholder="金额">
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
        $('body').on('click','[role="recharge"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            $('#balance').val('');
            $.showModal("recharge_setting", function () {
                var value = $('#balance').val();
                $.localAjax('./recharge', {id: id, balance: value}, function () {
                    alert('操作成功', function () {
//                        $("#recharge_setting").modal('hide');
                        history.go(0);
                    });
                })
            })

        })
    })
</script>