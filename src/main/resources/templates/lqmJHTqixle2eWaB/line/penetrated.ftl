<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-12">
        <div class="well">
            <form action="./penetratedData" class="form-inline" id="filter">
                <div class="form-group">
                    <label>时间</label>
                    <input type="text" class="form-control" name="startTime"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                           style="cursor: default;"
                           readonly>
                    <label>---</label>
                    <input type="text" class="form-control"  name="endTime"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                           style="cursor: default;"
                           readonly>
                </div>
                <div class="form-group">
                    <label>轴值</label>
                    <input type="number" class="form-control" name="downPoint">
                    <label>---</label>
                    <input type="number" class="form-control" name="upPoint">
                </div>
                <button type="button" class="btn btn-default" role="query">查询</button>
            </form>
        </div>
    </div>
</div>

<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-user"></i> 数据列表</h2>
            </div>
            <div class="box-content">
                <table class="table table-condensed table-hover">
                    <thead>
                    <tr>
                        <th>信息</th>
                    </tr>
                    </thead>
                    <tbody id="dataArea">

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
        function resetData() {
            $.localFormAjax("filter", function (res) {
                $('#dataArea').empty();
                $.each(res.data, function (index, obj) {
                    var json=JSON.parse(obj);
                    $('#dataArea').append("<tr><td>"+ json.data.result+"</td></tr>");
                })
            })
        }

        $('[role="query"]').on('click', function () {
            before();
            resetData();
        })
    })
</script>

