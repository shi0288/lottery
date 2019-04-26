<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-user"></i> 权限管理列表</h2>
            </div>
            <div class="box-content">
                <div class="table-responsive">
                    <table class="table table-condensed table-hover">
                        <thead>
                        <tr>
                            <th>管理员</th>
                            <th>密码</th>
                            <th>可控用户</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list list as e >
                            <tr>
                                <td>${e.username}</td>
                                <td>${e.password}</td>
                                <td>
                                    <select data-id="${e.id}" class="selectUser" data-rel="chosen" data-placeholder="请选择" name="users" multiple>
                                        <option value=""></option>
                                        <#list userList as user>
                                            <#assign hadUser=0 />
                                            <#if (e.users)??>
                                                <#list e.users as uu>
                                                    <#if uu.id==user.id>
                                                        <#assign hadUser=1 />
                                                        <#break>
                                                    </#if>
                                                </#list>
                                            </#if>
                                            <#if hadUser==0>
                                                <option data-h="${hadUser}" value="${user.id}">${user.username}</option>
                                            <#else>
                                                <option data-h="${hadUser}" value="${user.id}" selected>${user.username}</option>
                                            </#if>
                                        </#list>
                                    </select>
                                </td>
                                <td>
                                    <a class="save" data-id="${e.id}">保存修改</a>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<#include "fragment/footer.ftl" >

<script>
    $(function () {
        $('.userSelect').chosen({width: '300px'});

        $(".save").click(function(){
            var params = {};
            params.id = $(this).data("id");
            var uids = $(this).parent().prev().find(".selectUser").val();
            if (uids==null) {
                uids = "";
            }else{
                uids = uids.toString();
            }
            params.uids = uids;
            $.post("save", params, function(data){
                if (data.code==10000) {
                    alert("修改成功");
                }else{
                    alert("修改失败");
                }
            }, 'json');
        });
    })

</script>

