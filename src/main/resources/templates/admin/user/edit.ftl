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
                        <label for="password">密码(不填默认123456)</label>
                        <input type="password" auto-complete="off" class="form-control" name="password" id="password" placeholder="密码" />
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