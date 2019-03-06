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
                <table class="table table-condensed">
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>余额</th>
                        <th>姓名</th>
                        <th>总投注</th>
                        <th>总赔付</th>
                        <th>总盈收</th>
                    <#if manage.username=='lottery'>
                        <th>操作</th>
                    </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <#if page.list??>
                        <#list page.list as e>
                        <tr class="heng">
                            <td><a href="./dayList/${(e.id)!''}"><strong>${(e.username)!''}</strong></a></td>
                            <td><strong>${(e.balance?string('#.##'))!''}</strong></td>
                            <td><strong>${(e.realname)!''}</strong></td>
                            <td><strong>${(e.money?string('#.##'))!''}</strong></td>
                            <td><strong>${(e.result?string('#.##'))!''}</strong></td>
                            <td>
                                <#if (e.bonus>0)>
                                    <strong><span>${(e.bonus?string('#.##'))!''}</span></strong>
                                <#else>
                                    <strong> <span style="color: red">${(e.bonus?string('#.##'))!''}</span></strong>
                                </#if>
                            </td>
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
                                    <#if e.setting==1>
                                        <button role="setting_close" tag="${(e.id)!''}" class="btn btn-danger btn-xs">
                                            关闭客户设置
                                        </button>
                                    <#else>
                                        <button role="setting_open" tag="${(e.id)!''}" class="btn btn-success btn-xs">
                                            允许客户设置
                                        </button>
                                    </#if>
                                </td>
                            </#if>
                        </tr>
                            <#if (e.userRuleList)?? && (e.userRuleList?size>0)>
                            <tr>
                                <td  <#if manage.username=='lottery'>colspan="7" <#else>colspan="6"  </#if>>
                                    <table class="table table-condensed table-hover">
                                        <thead>
                                        <tr>
                                            <th>游戏名</th>
                                            <th>投注金额</th>
                                            <th>止损金额</th>
                                            <th>止盈金额</th>
                                            <th>自动投注</th>
                                            <th>止盈损</th>
                                            <th>移动止损</th>
                                            <th>投注平台</th>
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
                                                                <button role="updateInitMoney" tag="${(userRule.id)!''}"
                                                                        class="btn btn-default"
                                                                        type="button">修改</button>
                                                            </span>
                                                        </div>
                                                    <#else>
                                                    ${(userRule.initMoney?string('#.##'))!''}
                                                    </#if>
                                                </td>
                                                <td>
                                                    <#if manage.username=='lottery'>
                                                        <div class="input-group">
                                                            <input type="text" class="form-control"
                                                                   value="${(userRule.limitLose?string('#.##'))!''}"
                                                                   placeholder="金额">
                                                            <span class="input-group-btn">
                                                                <button role="updateLimitLose" tag="${(userRule.id)!''}"
                                                                        class="btn btn-default"
                                                                        type="button">修改</button>
                                                            </span>
                                                        </div>
                                                    <#else>
                                                    ${(userRule.limitLose?string('#.##'))!''}
                                                    </#if>
                                                </td>
                                                <td>
                                                    <#if manage.username=='lottery'>
                                                        <div class="input-group">
                                                            <input type="text" class="form-control"
                                                                   value="${(userRule.limitWin?string('#.##'))!''}"
                                                                   placeholder="金额">
                                                            <span class="input-group-btn">
                                                                <button role="updateLimitWin" tag="${(userRule.id)!''}"
                                                                        class="btn btn-default"
                                                                        type="button">修改</button>
                                                            </span>
                                                        </div>
                                                    <#else>
                                                    ${(userRule.limitWin?string('#.##'))!''}
                                                    </#if>
                                                </td>
                                                <td>
                                                    <#if manage.username=='lottery'>
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
                                                    <#else>
                                                        <#if userRule.isOpen==1>
                                                            开启中
                                                        <#else>
                                                            关闭中
                                                        </#if>
                                                    </#if>
                                                </td>
                                                <td>
                                                    <#if manage.username=='lottery'>
                                                        <#if userRule.isDividing==1>
                                                            <button role="closeDividing" tag="${(userRule.id)!''}"
                                                                    class="btn btn-danger btn-xs">
                                                                关闭止盈损
                                                            </button>
                                                        <#else>
                                                            <button role="openDividing" tag="${(userRule.id)!''}"
                                                                    class="btn btn-warning btn-xs">
                                                                开启止盈损
                                                            </button>
                                                        </#if>
                                                    <#else>
                                                        <#if userRule.isDividing==1>
                                                            开启中
                                                        <#else>
                                                            关闭中
                                                        </#if>
                                                    </#if>
                                                </td>
                                                <td>
                                                    <#if manage.username=='lottery'>
                                                        <#if userRule.isTraceLose==1>
                                                            <button role="closeTraceLose" tag="${(userRule.id)!''}"
                                                                    class="btn btn-danger btn-xs">
                                                                关闭移动止损
                                                            </button>
                                                        <#else>
                                                            <button role="openTraceLose" tag="${(userRule.id)!''}"
                                                                    class="btn btn-warning btn-xs">
                                                                开启移动止损
                                                            </button>
                                                        </#if>
                                                    <#else>
                                                        <#if userRule.isTraceLose==1>
                                                            开启中
                                                        <#else>
                                                            关闭中
                                                        </#if>
                                                    </#if>
                                                </td>
                                                <td>
                                                    <#if manage.username=='lottery'>
                                                        <button role="plat-setting" tag="${(userRule.id)!''}"
                                                                class="btn btn-warning btn-xs" data-toggle="tooltip" data-placement="top" title="点击选择">
                                                            <strong>${(userRule.platName)!'未设置'}</strong>
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


<div class="modal fade" id="plat_setting" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span>
                    <span class="sr-only">Close</span></button>
                <h4 class="modal-title">平台选择</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="control-group">
                        <label class="control-label">请选择投注平台</label>
                        <div class="controls plat_select">
                        </div>
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


        $('body').on('click', '[role="setting_close"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            myConfirm("确定关闭这个用户自我设置的权限吗?", function () {
                $.localAjax('./setting_close', {id: id}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })

        $('body').on('click', '[role="setting_open"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            myConfirm("确定打开这个用户自我设置的权限吗?", function () {
                $.localAjax('./setting_open', {id: id}, function () {
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


        $('body').on('click', '[role="updateLimitLose"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            var money = self.parent().prev().val();
            myConfirm("确定更新止损金额吗?", function () {
                $.localAjax('./updateLimitLose', {id: id, limitLoseMoney: money}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })

        $('body').on('click', '[role="updateLimitWin"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            var money = self.parent().prev().val();
            myConfirm("确定更新止赢金额吗?", function () {
                $.localAjax('./updateLimitWin', {id: id, limitWinMoney: money}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })


        $('body').on('click', '[role="closeDividing"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            myConfirm("确定要关闭止损盈吗?", function () {
                $.localAjax('./closeDividing', {id: id}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })

        $('body').on('click', '[role="openDividing"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            myConfirm("确定要开启止损盈吗?", function () {
                $.localAjax('./openDividing', {id: id}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })


        $('body').on('click', '[role="closeTraceLose"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            myConfirm("确定要关闭移动止损吗?", function () {
                $.localAjax('./closeTraceLose', {id: id}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })

        $('body').on('click', '[role="openTraceLose"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            myConfirm("确定要开启移动止损吗?", function () {
                $.localAjax('./openTraceLose', {id: id}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })

        function transToChosenData(data) {
            var resData = [];
            data.forEach(function (o) {
                o.platGameList.forEach(function (val) {
                    resData.push({key: val.id, val: o.platCategory.name+'-->'+o.name})
                })
            });
            return resData;
        }
        function transToTarget(data) {
            var resData = [];
            resData.push({key: data.platGameId, val: data.game})
            return resData;
        }

        $('body').on('click','[role="plat-setting"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            $.localAjax('getPlat', {id: id}, function (res) {
                var data = transToChosenData(res.data.list);
                var target = transToTarget(res.data.target);
                $.chosenSetting($('.plat_select'), 'plat_select', data, target, {
                    width: '100%',
                    placeholder_text_single: '选择平台账号'
                },true);
                $.showModal("plat_setting", function () {
                    var value = $('#plat_select').val();
                    $.localAjax('chosePlat', {id: id, platGameId: value}, function () {
                        alert('操作成功', function () {
                            history.go(0);
                        });
                    })
                })
            })
        })


    })
</script>