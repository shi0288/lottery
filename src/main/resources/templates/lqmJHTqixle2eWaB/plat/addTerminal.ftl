<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-8">
        <div class="box-inner">
            <div class="box-header well">
                <h2><i class="glyphicon glyphicon-edit"></i> 添加交互规则</h2>
                <div class="box-icon">
                    <a href="javascript:void(0);" id="submit" class="btn btn-round btn-default "><i
                            class="glyphicon glyphicon-ok"></i></a>
                </div>
            </div>
            <div class="box-content">
                <form action="savePlatGame" method="post" id="filter">
                    <div class="form-group">
                        <label for="selectPlat">平台</label>
                        <select id="selectPlat" data-placeholder="请选择" name="platId">
                            <option value=""></option>
                        <#if platList??>
                            <#list platList as e >
                                <option value="${e.id}">${e.name}</option>
                            </#list>
                        </#if>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="selectPlat">选择游戏</label>
                        <select data-placeholder="请选择" id="gameSelect" name="game">
                            <option value=""></option>
                        <#list convert('games')?keys as e >
                            <option value="${e}">${convert('gameCode',e)}</option>
                        </#list>
                        </select>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>



<#include "fragment/footer.ftl" >

<script>
    $(function () {
        $('#selectPlat').chosen({width: '300px'});
        $('#gameSelect').chosen({width: '300px'});


        $("#submit").on('click', function () {
            $.localFormAjax('filter', function (res) {
                alert('操作成功', function () {
                    window.location.href = './listTerminal';
                });
            })
        })

    })
</script>