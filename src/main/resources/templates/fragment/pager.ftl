<#-- 分页（Pager对象、链接URL、参数Map、最大页码显示数） -->
<#macro pager pager    maxShowPageCount = 10 >
    <#local pageNumber = pager.pageNum />
    <#local pageSize = pager.pageSize />
    <#local pageCount = pager.pages />
    <#local baseUrl = base+curPath />
    <#local parameter = "" />

    <#if (curParam)?? && curParam?js_string != ''>
        <#local parameter = "&"+curParam />
    </#if>

    <#if baseUrl?contains("?")>
        <#local baseUrl = baseUrl + "&" />
    <#else>
        <#local baseUrl = baseUrl + "?" />
    </#if>

    <#local firstPageUrl = baseUrl + "page=1" + parameter />
    <#local lastPageUrl = baseUrl + "page=" + pageCount + parameter />
    <#local prePageUrl = baseUrl + "page=" + (pageNumber - 1) + parameter />
    <#local nextPageUrl = baseUrl + "page=" + (pageNumber + 1) + parameter />

    <#if maxShowPageCount <= 0>
        <#local maxShowPageCount = 20>
    </#if>

    <#local segment = ((maxShowPageCount - 1) / 2)?int + 1 />
    <#local startPageNumber = pageNumber - segment />
    <#local endPageNumber = pageNumber + segment />
    <#if (startPageNumber < 1)>
        <#local startPageNumber = 1 />
    </#if>
    <#if (endPageNumber > pageCount)>
        <#local endPageNumber = pageCount />
    </#if>
<div class="row">
<#--<div class="col-md-12">-->
<#--<div class="dataTables_info">当前显示 1 到 23 条记录 总共 ${(pager.total)!''} 条记录</div>-->
<#--</div>-->
    <div class="col-md-12 center-block">
        <div class="dataTables_paginate paging_bootstrap pagination">
            <#if (pageCount > 1)>
                <ul class="pagination">
                <#--首页 -->
                    <#if (pageNumber > 1)>
                        <li><a waitingLoad href="${firstPageUrl}">首页</a></li>
                    <#else>
                        <li class="disabled"><a href="javascript:void(0);">首页</a></li>
                    </#if>
                <#-- 上一页 -->
                    <#if (pageNumber > 1)>
                        <li><a waitingLoad href="${prePageUrl}">← 上一页</a></li>
                    <#else>
                        <li class="disabled"><a href="javascript:void(0);">← 上一页</a></li>
                    </#if>

                    <#list startPageNumber .. endPageNumber as index>
                        <#if pageNumber != index>
                            <li><a waitingLoad href="${baseUrl + "page=" + index + parameter}">${index}</a></li>
                        <#else>
                            <li class="active"><a href="javascript:void(0);">${index}</a></li>
                        </#if>
                    </#list>

                <#-- 下一页 -->
                    <#if (pageNumber < pageCount)>
                        <li><a waitingLoad href="${nextPageUrl}">下一页</a></li>
                    <#else>
                        <li class="disabled"><a href="javascript:void(0);">下一页 → </a></li>
                    </#if>

                <#-- 末页 -->
                    <#if (pageNumber < pageCount)>
                        <li><a waitingLoad href="${lastPageUrl}">末页</a></li>
                    <#else>
                        <li class="disabled"><a href="javascript:void(0);">末页 → </a></li>
                    </#if>
                </ul>
            </#if>
        </div>
    </div>
</div>
</#macro>