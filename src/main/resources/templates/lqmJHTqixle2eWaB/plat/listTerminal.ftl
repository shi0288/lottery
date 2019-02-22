<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-user"></i> 交互列表</h2>
                <h2 style="float: right"><a href="./addTerminal">添加</a></h2>
            </div>
            <div class="box-content">
                <table class="table table-striped table-bordered bootstrap-datatable responsive">
                    <thead>
                    <tr>
                        <th>平台</th>
                        <th>账号</th>
                        <th>用户名</th>
                        <th>游戏</th>
                        <th>规则</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if page.list??>
                        <#list page.list as e>
                            <#list e.platGameList as platGame>
                            <tr>
                                <td>${(e.platCategory.name)!''}</td>
                                <td>${(e.name)!''}</td>
                                <td>${(e.username)!''}</td>
                                <td>${convert('gameCode',platGame.game)!''}</td>
                                <td>
                                    <#if platGame.direction==1>
                                        <span class="label-success label label-default">正向</span>
                                    <#else>
                                        <span class="label-warning label label-default">反向</span>
                                    </#if>
                                </td>
                                <td>
                                    <button tag="${(platGame.id)!''}" role="update_convert" class="btn btn-info btn-xs">
                                        <i class="glyphicon glyphicon-repeat icon-white"></i>
                                        更改方向
                                    </button>
                                    <button tag="${(platGame.id)!''}"  role="del_plat_game" class="btn btn-danger btn-xs">
                                        <i class="glyphicon glyphicon-trash icon-white"></i>
                                        删除
                                    </button>
                                </td>
                            </tr>
                            </#list>
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

<#include "fragment/footer.ftl" >

<script>
    $(function(){

        $('body').on('click', '[role="update_convert"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            myConfirm("确定要更新规则吗?", function () {
                $.localAjax('./update_convert', {id: id}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })

        $('body').on('click', '[role="del_plat_game"]', function () {
            var self = $(this);
            var id = self.attr('tag');
            myConfirm("确定删除吗?", function () {
                $.localAjax('./del_plat_game', {id: id}, function () {
                    alert('操作成功', function () {
                        history.go(0);
                    });
                })
            });
        })

    })

</script>
