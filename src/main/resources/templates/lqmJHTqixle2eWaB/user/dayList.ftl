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
                <br/>
                <br/>
                <div class="form-group">
                    <label>时间</label>
                    <input type="text" class="form-control" value="${(startTime)!''}" name="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                           style="cursor: default;"
                           readonly>
                </div>
                <div class="form-group">
                    <label>--</label>
                    <input type="text" class="form-control" value="${(endTime)!''}" name="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                           style="cursor: default;"
                           readonly>
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
                <h2><i class="glyphicon glyphicon-user"></i> 投注列表</h2>
            </div>
            <div class="box-content">
                <table class="table table-condensed table-hover">
                    <thead>
                    <tr>
                        <th>日期</th>
                        <th>注数</th>
                        <th>投注金额</th>
                        <th>实际派奖</th>
                        <th>当日盈收</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if page.list??>
                        <#list page.list as e>
                        <tr>
                            <td>
                                <#if game??>
                                    <a href="./${uid}/${e.everyday}?game=${game}">${(e.everyday)!''}</a>
                                <#else>
                                    <a href="./${uid}/${e.everyday}">${(e.everyday)!''}</a>
                                </#if>
                            </td>
                            <td>${(e.num)!''}</td>
                            <td>${(e.money?string('#.##'))!''}</td>
                            <td>${(e.result?string('#.##'))!''}</td>
                            <td>
                                <#if (e.bonus>0)>
                                    <span>${(e.bonus?string('#.##'))!''}</span>
                                <#else>
                                    <span  style="color: red">${(e.bonus?string('#.##'))!''}</span>
                                </#if>
                            </td>
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
        $('#userSelect').chosen({width: '300px'});
    })
</script>

