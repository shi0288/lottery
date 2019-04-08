<#include "fragment/header.ftl" >

<div class="row">
    <div class="box col-md-12">
        <div class="well">
            <form action="./bullData" class="form-inline" id="filter">
                <div class="form-group">
                    <label>时间</label>
                    <input type="text" class="form-control" name="date" value="${.now?string["yyyy-MM-dd"]}"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                           style="cursor: default;"
                           readonly>
                </div>
                <div class="form-group">
                    <label>系数</label>
                    <input type="number" class="form-control" value="120" name="count">
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
                <h2>BULL曲线</h2>
            </div>
            <div class="box-content">
                <div id="charts" style="width:100%;height: 500px"></div>
            </div>
        </div>
    </div>
</div>

<#include "fragment/footer.ftl" >

<script>
    $(function () {


        $('[role="query"]').on('click', function () {
            drawBullLine();
        })

        //绘制bull曲线
        var data1 = [];
        var data2 = [];
        var data3 = [];
        var data4 = [];
        var xdata = [];
        var chart = echarts.init(document.getElementById('charts'));
        var getData = function () {
            $.localFormAjax("filter", function (res) {
                xdata = [];
                data1 = [];
                data2 = [];
                data3 = [];
                data4 = [];
                $.each(JSON.parse(res.data).data, function (index, obj) {
                    xdata.push(obj.term.substr(8));
                    data1.push(obj.value);
                    data2.push(obj.mb);
                    data3.push(obj.up);
                    data4.push(obj.dn);
                })
                chart.setOption({
                    xAxis: {
                        data: xdata
                    },
                    series: [{
                        data: data1
                    }, {
                        data: data2
                    }, {
                        data: data3
                    }, {
                        data: data4
                    }]
                }, false);
            }, 'json')
        };


        var drawBullLine = function () {
            var option = {
                title: {
                    text: '',
                    x: '2.5%'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['点位', 'MB', 'UP', 'DN']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '15%',
                    show: true,
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: xdata,
                    show: true
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '点位',
                        type: 'line',
                        roam: true,
                        data: data1
                    },
                    {
                        name: 'MB',
                        type: 'line',
                        roam: true,
                        data: data2
                    },
                    {
                        name: 'UP',
                        type: 'line',
                        roam: true,
                        data: data3
                    },
                    {
                        name: 'DN',
                        type: 'line',
                        roam: true,
                        data: data4
                    }
                ],
                dataZoom: {
                    show: true,
                    type: 'slider',
                    realtime: true,
                    height: 20,
                    start: 0,
                    end: 100
                }
            };
            chart.setOption(option, true);
            setInterval(function () {
                getData();
            }, 60000);
            getData();
        };
        drawBullLine();

    })
</script>

