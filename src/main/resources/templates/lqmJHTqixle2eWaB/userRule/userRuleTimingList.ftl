<div class="table-responsive">
    <input type="hidden"  name="game" id="gameCode" value="" />
    <input type="hidden"  name="uid" id="userRuleTimingUid" value="" />
    <table class="table table-condensed table-striped">
    <#if list?? && (list?size>0)>
        <#list list as e>
            <tr>
                <td>
                    <input type="hidden"  name="userRuleTimingList[${e_index}].id" value="${(e.id)!''}" />
                    <div class="form-group">
                        <label>开始时间</label>
                        <input type="text" class="form-control" name="userRuleTimingList[${e_index}].startTime" value="${(e.startTime)!''}" onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true})"
                               style="cursor: default;"
                               readonly>
                    </div>
                    <div class="form-group">
                        <label>结束时间</label>
                        <input type="text" class="form-control" name="userRuleTimingList[${e_index}].endTime" value="${(e.endTime)!''}" onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true})"
                               style="cursor: default;"
                               readonly>
                    </div>
                    <div class="form-group">
                        <label>上穿点数</label>
                        <input type="text" style="width:60px;" name="userRuleTimingList[${e_index}].upPoint" value="${(e.upPoint)!''}"  class="form-control">
                    </div>
                    <div class="form-group">
                        <label>下穿点数</label>
                        <input type="text" style="width:60px;" name="userRuleTimingList[${e_index}].downPoint" value="${(e.downPoint)!''}"  class="form-control">
                    </div>
                    <div class="form-group">
                        <button type="button" tag="${(e.id)!''}" class="btn btn-danger" role="del-userRuleTiming">删除</button>
                    </div>
                </td>
            </tr>
        </#list>
    <#else>
        <tr>
            <td>
                <input type="hidden"  name="userRuleTimingList[0].id" value="" />
                <div class="form-group">
                    <label>开始时间</label>
                    <input type="text" name="userRuleTimingList[0].startTime" class="form-control" onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true})"
                           style="cursor: default;"
                           readonly>
                </div>
                <div class="form-group">
                    <label>结束时间</label>
                    <input type="text" name="userRuleTimingList[0].endTime" class="form-control" onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true})"
                           style="cursor: default;"
                           readonly>
                </div>
                <div class="form-group">
                    <label>上穿点数</label>
                    <input type="text" name="userRuleTimingList[0].upPoint" style="width:60px;" class="form-control">
                </div>
                <div class="form-group">
                    <label>下穿点数</label>
                    <input type="text" name="userRuleTimingList[0].downPoint"  style="width:60px;" class="form-control">
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-danger" role="del-userRuleTiming">删除</button>
                </div>
            </td>
        </tr>
    </#if>

    </table>
</div>