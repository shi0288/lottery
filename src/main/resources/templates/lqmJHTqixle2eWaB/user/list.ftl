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
                <div class="table-responsive">
                    <table class="table table-condensed">
                        <tbody>
                        <#if page.list??>
                            <#list page.list as e>
                            <tr class="heng">
                                <td>帐号:<a href="./dayList/${(e.id)!''}">
                                    <strong>${(e.username)!''}
                                        <#if e.parentId gt 0>
                                            (归属于${(e.parentName)!''})
                                        </#if>
                                    </strong></a>
                                </td>
                                <td>名称:<strong style="color: red">${(e.realname)!''}</strong></td>
                                <td>余额:<strong>${(e.balance?string('#.##'))!''}</strong></td>
                                <!--<td>平台余额:<strong>${(e.platMoney?string('#.##'))!''}</strong></td>-->
                                <td>总投注:<strong>${(e.money?string('#.##'))!''}</strong></td>
                                <td>总赔付:<strong>${(e.result?string('#.##'))!''}</strong></td>
                                <td>总盈收:
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
                                            <button role="setting_close" tag="${(e.id)!''}"
                                                    class="btn btn-danger btn-xs">
                                                关闭客户设置
                                            </button>
                                        <#else>
                                            <button role="setting_open" tag="${(e.id)!''}"
                                                    class="btn btn-success btn-xs">
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
                                                <th>止损点数</th>
                                                <th>止盈点数</th>
                                                <th>保本止损启动盈利点数</th>
                                                <th>保本止损保本盈利点数</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                                <#list e.userRuleList as userRule>
                                                <tr>
                                                    <td>${convert('gameCode',userRule.game)!''}</td>
                                                    <td>
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
                                                    </td>
                                                    <td>
                                                        <div class="input-group">
                                                            <input type="text" class="form-control"
                                                                   value="${(userRule.limitLose?string('#.##'))!''}"
                                                                   placeholder="点数">
                                                            <span class="input-group-btn">
                                                            <button role="updateLimitLose" tag="${(userRule.id)!''}"
                                                                    class="btn btn-default"
                                                                    type="button">修改</button>
                                                        </span>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div class="input-group">
                                                            <input type="text" class="form-control"
                                                                   value="${(userRule.limitWin?string('#.##'))!''}"
                                                                   placeholder="点数">
                                                            <span class="input-group-btn">
                                                            <button role="updateLimitWin" tag="${(userRule.id)!''}"
                                                                    class="btn btn-default"
                                                                    type="button">修改</button>
                                                        </span>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div class="input-group">
                                                            <input type="text" class="form-control"
                                                                   value="${(userRule.limitMaxWin?string('#.##'))!''}"
                                                                   placeholder="点数">
                                                            <span class="input-group-btn">
                                                            <button role="updateLimitMaxWin"
                                                                    tag="${(userRule.id)!''}"
                                                                    class="btn btn-default"
                                                                    type="button">修改</button>
                                                        </span>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div class="input-group">
                                                            <input type="text" class="form-control"
                                                                   value="${(userRule.limitMinWin?string('#.##'))!''}"
                                                                   placeholder="点数">
                                                            <span class="input-group-btn">
                                                                <button role="updateLimitMinWin"
                                                                        tag="${(userRule.id)!''}"
                                                                        class="btn btn-default"
                                                                        type="button">修改</button>
                                                            </span>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr class="none-border">
                                                    <td colspan="8">
                                                        <#if userRule.isOpen==1>
                                                            <button role="closeTouzhu" tag="${(userRule.id)!''}"
                                                                    class="btn btn-danger btn-xs">
                                                                关闭自动投注
                                                            </button>
                                                        <#else>
                                                            <button role="openTouzhu" tag="${(userRule.id)!''}"
                                                                    class="btn btn-warning btn-xs">
                                                                开启自动投注
                                                            </button>
                                                        </#if>
                                                        &nbsp; &nbsp; &nbsp;
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
                                                        &nbsp; &nbsp; &nbsp;
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
                                                        &nbsp; &nbsp; &nbsp;
                                                        <#if userRule.isBottomwin==1>
                                                            <button role="closeBottomWin" tag="${(userRule.id)!''}"
                                                                    class="btn btn-danger btn-xs">
                                                                关闭保本止损(当前状态:开启)
                                                            </button>
                                                        <#elseif userRule.isBottomwin==2>
                                                            <button role="closeBottomWin" tag="${(userRule.id)!''}"
                                                                    class="btn btn-danger btn-xs">
                                                                关闭保本止损(当前状态:进行中)
                                                            </button>
                                                        <#else>
                                                            <button role="openBottomWin" tag="${(userRule.id)!''}"
                                                                    class="btn btn-warning btn-xs">
                                                                开启保本止损
                                                            </button>
                                                        </#if>
                                                        &nbsp; &nbsp; &nbsp;
                                                        <#if manage.username=='lottery'>
                                                            <button role="plat-setting" tag="${(userRule.id)!''}"
                                                                    class="btn btn-warning btn-xs" data-toggle="tooltip"
                                                                    data-placement="top" title="点击选择">
                                                                <strong>${(userRule.platName)!'未设置投注平台'}</strong>
                                                            </button>
                                                        </#if>
                                                        &nbsp; &nbsp; &nbsp;
                                                        <select class="form-control" style="width:73px;height:23px;display:inline-block;">
                                                            <option value="0" ${(userRule.direction==0)?string("selected","")}>未设置</option>
                                                            <option value="1" ${(userRule.direction==1)?string("selected","")}>正向</option>
                                                            <option value="-1" ${(userRule.direction==-1)?string("selected","")}>负向</option>
                                                        </select>
                                                        <span class="input-group-btn" style="display:inline-block;height:24px">
                                                                <button role="updateDirection" style="height:24px;padding-top:2px;"
                                                                        tag="${(userRule.id)!''}"
                                                                        class="btn btn-default"
                                                                        type="button">修改</button>
                                                        </span>
                                                        &nbsp; &nbsp; &nbsp;
                                                        <div class="btn-group" role="group" style="margin-left:35px;">
                                                            <button role="topDownSetting" tag="${(userRule.uid)!''}"
                                                                    tagGame="${(userRule.game)!''}"
                                                                    class="btn btn-warning btn-xs" data-toggle="tooltip"
                                                                    data-placement="top" title="点击查看">
                                                                <strong>上下穿设置</strong>
                                                            </button>
                                                            <#if userRule.isTiming==1>
                                                                <button role="topDownClose" tag="${(userRule.id)!''}"
                                                                        class="btn btn-danger btn-xs" >
                                                                    <strong>关闭</strong>
                                                                </button>
                                                            <#else>
                                                                <button role="topDownOpen"  tag="${(userRule.id)!''}"
                                                                        class="btn btn-warning btn-xs" >
                                                                    <strong>开启</strong>
                                                                </button>
                                                            </#if>
                                                        </div>
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
                    <div
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

    <div class="modal fade" id="upDownSetting" tabindex="-1">
        <div class="modal-dialog  modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span>&times;</span><span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">上下穿设置</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <form class="form-inline" action="../userRuleTiming/save" id="upDownHtml">

                        </form>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" role="add-userRuleTiming" class="btn btn-success">新增</button>
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
                myConfirm("确定更新止损点数吗?", function () {
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
                myConfirm("确定更新止赢点数吗?", function () {
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

            $('body').on('click', '[role="updateDirection"]', function () {
                var self = $(this);
                var id = self.attr('tag');
                var direction = self.parent().prev().val();
                myConfirm("确定修改交易方向吗？(交易方向由平台设置方向、账号设置方向，定时交易自动变向组成)", function () {
                    $.localAjax('./updateDirection', {id: id, direction: direction}, function () {
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
                        resData.push({key: val.id, val: o.platCategory.name + '-->' + o.name})
                    })
                });
                return resData;
            }

            function transToTarget(data) {
                var resData = [];
                resData.push({key: data.platGameId, val: data.game})
                return resData;
            }

            $('body').on('click', '[role="plat-setting"]', function () {
                var self = $(this);
                var id = self.attr('tag');
                $.localAjax('getPlat', {id: id}, function (res) {
                    var data = transToChosenData(res.data.list);
                    var target = transToTarget(res.data.target);
                    $.chosenSetting($('.plat_select'), 'plat_select', data, target, {
                        width: '100%',
                        placeholder_text_single: '选择平台账号'
                    }, true);
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

            $('body').on('click', '[role="updateLimitMaxWin"]', function () {
                var self = $(this);
                var id = self.attr('tag');
                var money = self.parent().prev().val();
                myConfirm("确定更新保本止损启动盈利点数吗?", function () {
                    $.localAjax('./updateLimitMaxWin', {id: id, money: money}, function () {
                        alert('操作成功', function () {
                            history.go(0);
                        });
                    })
                });
            })

            $('body').on('click', '[role="updateLimitMinWin"]', function () {
                var self = $(this);
                var id = self.attr('tag');
                var money = self.parent().prev().val();
                myConfirm("确定更新保本止损保本盈利点数吗?", function () {
                    $.localAjax('./updateLimitMinWin', {id: id, money: money}, function () {
                        alert('操作成功', function () {
                            history.go(0);
                        });
                    })
                });
            })


            $('body').on('click', '[role="closeBottomWin"]', function () {
                var self = $(this);
                var id = self.attr('tag');
                myConfirm("确定要关闭保本止损吗?", function () {
                    $.localAjax('./closeBottomWin', {id: id}, function () {
                        alert('操作成功', function () {
                            history.go(0);
                        });
                    })
                });
            })

            $('body').on('click', '[role="openBottomWin"]', function () {
                var self = $(this);
                var id = self.attr('tag');
                myConfirm("确定要开启保本止损吗?", function () {
                    $.localAjax('./openBottomWin', {id: id}, function () {
                        alert('操作成功', function () {
                            history.go(0);
                        });
                    })
                });
            })

            $('body').on('click', '[role="topDownSetting"]', function () {
                var self = $(this);
                var uid = self.attr('tag');
                var game = self.attr('tagGame');
                $('#upDownHtml').empty();
                $.localAjax('../userRuleTiming/getList', {uid: uid, game: game}, function (res) {
                    $('#upDownHtml').append(res.data);
                    $('.iphone-toggle').iphoneStyle();
                    $('#gameCode').val(game);
                    $('#userRuleTimingUid').val(uid);
                    var deal = function () {
                        $.localFormAjax('upDownHtml', function (res) {
                            alert('操作成功');
                        }, function reShowModal(err) {
                            alert(err.message, function () {
                                $.showModal("upDownSetting", deal)
                            });
                        })
                    }
                    $.showModal("upDownSetting", deal)
                })
            })


            $('body').on('click', '[role="del-userRuleTiming"]', function () {
                var self = $(this);
                var id = self.attr('tag');
                if (id != undefined && id != '' && id != null) {
                    myConfirm('确定要删除这条记录吗?', function () {
                        $.localAjax('../userRuleTiming/delete', {id: id}, function () {
                            var curContentObj = self.closest("tr");
                            var index = $('#upDownHtml tr').index(curContentObj);
                            var nextContentObj = curContentObj.next();
                            while (nextContentObj.length > 0) {
                                nextContentObj.find('input').replaceWith(function () {
                                    var str = $(this).prop("outerHTML").replace(/userRuleTimingList\[\d+\]/g, "userRuleTimingList[" + index + "]");
                                    var target = $(str);
                                    target.val($(this).val());
                                    return target;
                                });
                                index++;
                                nextContentObj = nextContentObj.next();
                            }
                            curContentObj.remove();
                        })
                    })
                } else {
                    var curContentObj = self.closest("tr");
                    var index = $('#upDownHtml tr').index(curContentObj);
                    var nextContentObj = curContentObj.next();
                    while (nextContentObj.length > 0) {
                        nextContentObj.find('input').replaceWith(function () {
                            var str = $(this).prop("outerHTML").replace(/userRuleTimingList\[\d+\]/g, "userRuleTimingList[" + index + "]");
                            var target = $(str);
                            target.val($(this).val());
                            return target;
                        });
                        index++;
                        nextContentObj = nextContentObj.next();
                    }
                    curContentObj.remove();
                }
            })

            $('body').on('click', '[role="add-userRuleTiming"]', function () {
                var i = $('#upDownHtml').find('tr').length;
                var a = '<tr><td><input type="hidden"  name="userRuleTimingList[0].id" value="" /><div class="form-group"><label>开始时间</label>  <input type="text" name="userRuleTimingList[0].startTime" class="form-control" onclick="WdatePicker({dateFmt:\'HH:mm\',autoPickDate:true})" style="cursor: default;" readonly></div> <div class="form-group"><label>结束时间</label> <input type="text" name="userRuleTimingList[0].endTime" class="form-control" onclick="WdatePicker({dateFmt:\'HH:mm\',autoPickDate:true})" style="cursor: default;"  readonly></div> <div class="form-group"> <label>上穿点数</label> <input type="text" name="userRuleTimingList[0].upPoint" style="width:60px;" class="form-control"></div> <div class="form-group"> <label>下穿点数</label> <input type="text" name="userRuleTimingList[0].downPoint"  style="width:60px;" class="form-control"> </div> <div class="form-group"> <button type="button" class="btn btn-danger" role="del-userRuleTiming">删除</button> </div> <br/> <div class="checkbox"> <label><input  name="userRuleTimingList[0].isBullAxisMove"  type="checkbox" value="1">是否布林轴偏移</label></div>  <div class="checkbox"><label><input  name="userRuleTimingList[0].isTradeBeforeAxisMove" type="checkbox" value="1">布林轴偏移前是否交易</label></div> <div class="checkbox"><label><input  name="userRuleTimingList[0].isBullDirectionOnly"  type="checkbox" value="1">是否布林轴多空边界交易</label></div> </td></tr>';
                if (i > 0) {
                    a = a.replace(/userRuleTimingList\[\d+\]/g, "userRuleTimingList[" + i + "]");
                }
                $('#upDownHtml').find('table').append(a);
                $('.iphone-toggle').iphoneStyle();
            })


            $('body').on('click', '[role="topDownClose"]', function () {
                var self = $(this);
                var id = self.attr('tag');
                myConfirm("确定要关闭上下穿吗?", function () {
                    $.localAjax('./closeIsTiming', {id: id}, function () {
                        alert('操作成功', function () {
                            history.go(0);
                        });
                    })
                });
            })

            $('body').on('click', '[role="topDownOpen"]', function () {
                var self = $(this);
                var id = self.attr('tag');
                myConfirm("确定要开启上下穿吗?", function () {
                    $.localAjax('./openIsTiming', {id: id}, function () {
                        alert('操作成功', function () {
                            history.go(0);
                        });
                    })
                });
            })



        })
    </script>