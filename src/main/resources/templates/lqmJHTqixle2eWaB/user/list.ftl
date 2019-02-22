<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-user"></i> 用户列表</h2>
            <#if manage.username=='lottery'>
                <h2 style="float: right"><a href="./add">添加用户</a></h2>
            </#if>
            </div>
            <div class="box-content">
                <table class="table table-striped table-bordered bootstrap-datatable responsive">
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>姓名</th>
                        <th>余额</th>
                    <#if manage.username=='lottery'>
                        <th>操作</th>
                    </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <#if page.list??>
                        <#list page.list as e>
                        <tr   class="heng">
                            <td><a href="./dayList/${(e.id)!''}">${(e.username)!''}</a></td>
                            <td>${(e.realname)!''}</td>
                            <td><strong>${(e.balance?string('#.##'))!''}</strong></td>
                            <#if manage.username=='lottery'>
                                <td>
                                    <a href="./edit?id=${(e.id)!''}" class="btn btn-info btn-xs">
                                        <i class="glyphicon glyphicon-edit icon-white"></i>
                                        修改
                                    </a>
                                    <button role="recharge" tag="${(e.id)!''}" class="btn btn-success btn-xs">
                                        充值
                                    </button>
                                    <button role="mul" tag="${(e.id)!''}" class="btn btn-primary btn-xs">
                                        减钱
                                    </button>
                                    <#if e.status==1>
                                        <button role="frozen" tag="${(e.id)!''}" class="btn btn-danger btn-xs">
                                            冻结
                                        </button>
                                    <#else>
                                        <button role="thaw" tag="${(e.id)!''}" class="btn btn-warning btn-xs">
                                            解冻
                                        </button>
                                    </#if>

                                </td>
                            </#if>
                        </tr>
                            <#if (e.userRuleList)?? && (e.userRuleList?size>0)>
                            <tr>
                                <td  <#if manage.username=='lottery'>colspan="4" <#else>colspan="3"  </#if>>
                                    <table class="table table-striped table-bordered bootstrap-datatable responsive">
                                        <thead>
                                        <tr>
                                            <th>游戏名</th>
                                            <th>投注金额</th>
                                            <th>是否开启</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                            <#list e.userRuleList as userRule>
                                            <tr>
                                                <td>${convert('gameCode',userRule.game)!''}</td>
                                                <td>

                                                    <#if manage.username=='lottery'>
                                                        <div class="input-group">
                                                            <input type="text" class="form-control"
                                                                   value="${(userRule.initMoney?string('#.##'))!''}"
                                                                   placeholder="金额">
                                                            <span class="input-group-btn">
                                                                <button role="updateInitMoney" tag="${(userRule.id)!''}" class="btn btn-default"
                                                                type="button">修改</button>
                                                            </span>
                                                        </div>
                                                    <#else>
                                                        ${(userRule.initMoney?string('#.##'))!''}
                                                    </#if>
                                                </td>
                                                <td>
                                                    <#if userRule.isOpen==1>
                                                        <button role="closeTouzhu" tag="${(userRule.id)!''}"
                                                                class="btn btn-danger btn-xs">
                                                            关闭投注
                                                        </button>
                                                    <#else>
                                                        <button role="openTouzhu" tag="${(userRule.id)!''}"
                                                                class="btn btn-warning btn-xs">
                                                            开启投注
                                                        </button>
                                                    </#if>
                                                </td>
                                            </tr>
                                            </#list>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                            </#if>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            <#if  (page.list)??>
                <#import "/fragment/pager.ftl" as p>
                <@p.pager pager = page />
            </#if>
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
                        <input type="text" class="form-control" id="balance" placeholder="金额">
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


<div class="modal fade" id="mul_setting" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span>
                    <span class="sr-only">Close</span></button>
                <h4 class="modal-title">减钱</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="control-group">
                        <label class="control-label">减少金额</label>
                        <input type="text" class="form-control" id="mul" placeholder="金额">
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
        $('body').on('click', '[role="recharge"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            $('#balance').val('');
            $.showModal("recharge_setting", function () {
                var value = $('#balance').val();
                $.localAjax('./recharge', {id: id, balance: value}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            })
        })

        $('body').on('click', '[role="mul"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            $('#mul').val('');
            $.showModal("mul_setting", function () {
                var value = $('#mul').val();
                $.localAjax('./mul', {id: id, balance: value}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            })
        })


        $('body').on('click', '[role="frozen"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            myConfirm("确定要冻结这个用户吗?", function () {
                $.localAjax('./frozen', {id: id}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })

        $('body').on('click', '[role="thaw"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            myConfirm("确定要解冻这个用户吗?", function () {
                $.localAjax('./thaw', {id: id}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })


        $('body').on('click', '[role="closeTouzhu"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            myConfirm("确定要关闭自动投注吗?", function () {
                $.localAjax('./closeTouzhu', {id: id}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })

        $('body').on('click', '[role="openTouzhu"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            myConfirm("确定要开启自动投注吗?", function () {
                $.localAjax('./openTouzhu', {id: id}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })

        $('body').on('click', '[role="updateInitMoney"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            var money = self.parent().prev().val();
            myConfirm("确定更新投注金额吗?", function () {
                $.localAjax('./updateInitMoney', {id: id, initMoney: money}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })

    })
</script>