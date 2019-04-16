<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-8">
        <div class="box-inner">
            <div class="box-header well">
                <h2><i class="glyphicon glyphicon-edit"></i> 编辑用户</h2>
                <div class="box-icon">
                    <a href="javascript:void(0);" id="submit"  class="btn btn-round btn-default "><i class="glyphicon glyphicon-ok"></i></a>
                </div>
            </div>
            <div class="box-content">
                <form  action="update" method="post" id="filter">
                    <input type="hidden"  name="id"  value="${(e.id)!''}" />
                    <div class="form-group">
                        <label for="username">用户名</label>
                        <input type="text"  class="form-control"  value="${(e.username)!''}"   disabled />
                    </div>
                    <div class="form-group">
                        <label for="realname">姓名</label>
                        <input type="text"  class="form-control"  value="${(e.realname)!''}" name="realname"
                               placeholder="姓名">
                    </div>
                    <div class="form-group">
                        <label for="realname">密码</label>
                        <input type="text"  class="form-control"  value="" name="password" placeholder="密码">
                    </div>
                    <div class="form-group">
                        <label for="realname">父级账户</label>
                        <select id="selectParent" data-rel="chosen" data-placeholder="请选择" name="parent">
                            <option value="0">无</option>
                            <#list users as user>
                                <#if user.id==e.parentId>
                                    <option value="${user.id}" selected>${user.username}</option>
                                <#else>
                                    <option value="${user.id}">${user.username}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="selectLeader">支持投注的游戏</label>
                        <div class="controls" style="height: 38px;">
                            <select id="selectGame" data-rel="chosen" data-placeholder="请选择" name="games" multiple>
                                <option value=""></option>
                            <#list convert('games')?keys as game>
                                <#assign hadGame=0 />
                                <#if (e.userRuleList)??>
                                    <#list e.userRuleList as userRule>
                                        <#if userRule.game==game>
                                            <#assign hadGame=1 />
                                            <#break>
                                        </#if>
                                    </#list>
                                </#if>
                                <#if hadGame==0>
                                    <option value="${game}">${convert('gameCode',game)}</option>
                                <#else>
                                    <option value="${game}" selected>${convert('gameCode',game)}</option>
                                </#if>
                            </#list>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<#include "fragment/footer.ftl" >

<script>
    $(function(){
        $("#submit").on('click',function(){
            $.localFormAjax('filter',function(res){
                alert('操作成功',function(){
                   window.location.href='./list';
                });
            })
        })

    })
</script>