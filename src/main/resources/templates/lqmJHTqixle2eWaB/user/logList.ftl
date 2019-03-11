<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-12">
        <div class="well">
            <form class="form-inline">
                <div class="form-group">
                    <label>彩种</label>
                    <select data-placeholder="请选择" id="gameSelect" name="game">
                        <option value="">全部</option>
                    <#list convert('games')?keys as e >
                        <#if e==game>
                            <option value="${e}" selected>${convert('gameCode',e)}</option>
                        <#else>
                            <option value="${e}">${convert('gameCode',e)}</option>
                        </#if>
                    </#list>
                    </select>
                </div>
        </div>
        <button type="submit" class="btn btn-default">查询</button>
        </form>
    </div>
</div>


<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-user"></i> 用户投注列表</h2>
            </div>
            <div class="box-content">
                <table class="table table-condensed table-hover">
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>游戏</th>
                        <th>期次</th>
                        <th>投注号码</th>
                        <th>金额</th>
                        <th>平台</th>
                        <th>赔率</th>
                        <th>策略</th>
                        <th>是否结算</th>
                        <th>派奖</th>
                        <th>时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if page.list??>
                        <#list page.list as e>
                        <tr>
                            <td>${(e.user.username)!''}</td>
                            <td>${convert('gameCode',e.game)!''}</td>
                            <td>${(e.term)!''}</td>
                            <td>${convert('sscCode',e.code)!''}</td>
                            <td>${(e.money?string('#.##'))!''}</td>
                            <td>${(e.plat.name)!''}</td>
                            <td>${(e.rate?string('#.##'))!''}</td>
                            <td>
                                <#if e.rule==1>
                                    等值
                                <#elseif e.rule==2>
                                    倍投
                                </#if>
                            </td>
                            <td>
                                <#if e.settle==1>
                                    已结算
                                </#if>
                            </td>
                            <td>${(e.result?string('#.##'))!''}</td>
                            <td>${(e.createAt?string("yyyy-MM-dd HH:mm:ss"))!''}</td>
                        </tr>
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
    $(function () {
        $('#gameSelect').chosen({width: '300px'});
    })
</script>