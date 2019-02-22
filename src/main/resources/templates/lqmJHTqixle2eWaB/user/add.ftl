<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-8">
        <div class="box-inner">
            <div class="box-header well">
                <h2><i class="glyphicon glyphicon-edit"></i> 添加用户</h2>
                <div class="box-icon">
                    <a href="javascript:void(0);" id="submit" class="btn btn-round btn-default "><i
                            class="glyphicon glyphicon-ok"></i></a>
                </div>
            </div>
            <div class="box-content">
                <form action="save" method="post" id="filter">
                    <div class="form-group">
                        <label for="realname">姓名</label>
                        <input type="text" auto-complete="off" class="form-control" name="realname" id="realname"
                               placeholder="姓名">
                    </div>
                    <div class="form-group">
                        <label for="username">用户名</label>
                        <input type="text" auto-complete="off" class="form-control" name="username" id="username"
                               placeholder="用户名">
                    </div>
                    <div class="form-group">
                        <label for="password">密码(不填默认123456)</label>
                        <input type="password" auto-complete="off" class="form-control" name="password" id="password"
                               placeholder="密码">
                    </div>
                    <div class="form-group">
                        <label for="balance">余额</label>
                        <input type="text" class="form-control" name="balance" id="balance" placeholder="余额">
                    </div>
                    <div class="form-group">
                        <label for="selectLeader">支持投注的游戏</label>
                        <div class="controls" style="height: 38px;">
                            <select id="selectGame" data-rel="chosen" data-placeholder="请选择" name="games" multiple>
                                <option value=""></option>
                            <#list convert('games')?keys as e >
                                <option value="${e}">${convert('gameCode',e)}</option>
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
    $(function () {
        $("#submit").on('click', function () {
            $.localFormAjax('filter', function (res) {
                alert('操作成功', function () {
                    window.location.href = './list';
                });
            })
        })

    })
</script>