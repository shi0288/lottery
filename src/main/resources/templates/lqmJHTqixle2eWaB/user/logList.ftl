<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-user"></i> 投注列表</h2>
            </div>
            <div class="box-content">
                <table class="table table-striped table-bordered bootstrap-datatable datatable responsive">
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>游戏</th>
                        <#--<th>期次</th>-->
                        <th>投注号码</th>
                        <th>金额</th>
                        <th>平台</th>
                        <th>时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if list??>
                        <#list list as e>
                        <tr>
                            <td>${(e.user.username)!''}</td>
                            <td>重庆时时彩</td>
                            <#--<td>${(e.balance?string('#.##'))!''}</td>-->
                            <td>${convert('sscCode',e.code)!''}</td>
                            <td>${(e.money?string('#.##'))!''}</td>
                            <td>${(e.plat.name)!''}</td>
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


<#include "fragment/footer.ftl" >

<script>
    $(function () {

    })
</script>