/**
 * Created by shiqm on 2018/9/15.
 */
jQuery.extend({
    localAjax: function (url, cond, cb) {
        before();
        $.ajax({
            type: "POST",
            url: url + "?timestamp=" + new Date().getTime(),
            dataType: "json",
            async: true,
            cache: false,
            data: cond,
            success: function (result) {
                after();
                if (result.code == 10000) {
                    cb(result);
                } else {
                    alert(result.message);
                }
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                after();
                alert('网络错误,请重试！');
            }
        })
    },
    localFormAjax: function (fromStr, cb, errCb) {
        var self = $("#" + fromStr);
        var url = self.attr("action");
        before();
        $.ajax({
            type: "POST",
            dataType: "json",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            url: url,
            data: self.serialize(),
            success: function (result) {
                after();
                if (result.code == 10000) {
                    cb(result);
                } else {
                    if (errCb) {
                        errCb(result);
                    } else {
                        alert(result.message);
                    }
                }
            },
            error: function (e) {
                after();
                if (errCb) {
                    var result = {};
                    result.message = '网络错误,请重试！';
                    errCb(result);
                } else {
                    alert('网络错误,请重试！');
                }
            }
        });
    },
    showModal: function (id, callBack) {
        var modal = $("#" + id);
        modal.off('hidden.bs.modal');
        if (callBack) {
            modal.find('.conform-but').off('click');
            modal.find('.conform-but').on('click', function () {
                modal.modal('hide');
                modal.on('hidden.bs.modal', callBack);
            })
        }
        modal.modal({
            keyboard: true,
            backdrop: true,
            show: true
        });
    },
    chosenSetting: function ($node, id, data, target, opt, mul) {
        $node.empty();
        var temp;
        if (mul) {
            temp = '<select id="' + id + '"  class="form-control">';
        } else {
            temp = '<select id="' + id + '"  multiple class="form-control">';
        }
        data.forEach(function (obj) {
            var btn = false;
            for (var i = 0; i < target.length; i++) {
                if (obj.key == target[i].key) {
                    btn = true;
                    target.splice(i, 1);
                    break;
                }
            }
            if (btn) {
                temp += '<option value="' + obj.key + '" selected>' + obj.val + '</option>'
            } else {
                temp += '<option value="' + obj.key + '">' + obj.val + '</option>'
            }
        });
        temp += "</select>";
        $node.append(temp);
        if (opt) {
            $('#' + id).chosen(opt);
        } else {
            $('#' + id).chosen({width: '100%'});
        }
    },
    picUpload: function (idStr, cb) {
        before();
        $.ajaxFileUpload
        (
            {
                url: '/assist/uploadPic',
                secureuri: false,
                fileElementId: idStr,
                dataType: 'json',
                success: function (result) {
                    after();
                    if (result.code == 10000) {
                        cb(result);
                    } else {
                        alert(result.message);
                    }
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    after();
                    alert('网络错误,请重试！');
                }
            }
        )
    },
    picsUpload: function (idStr, cb) {
        before();
        $.ajaxFileUpload
        (
            {
                url: '/assist/uploadPics',
                secureuri: false,
                fileElementId: idStr,
                dataType: 'json',
                success: function (result) {
                    after();
                    if (result.code == 10000) {
                        cb(result);
                    } else {
                        alert(result.message);
                    }
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    after();
                    alert('网络错误,请重试！');
                }
            }
        )
    },
    fileUpload: function (idStr,url, cb) {
        before();
        $.ajaxFileUpload
        (
            {
                url: url + "?timestamp=" + new Date().getTime(),
                secureuri: false,
                fileElementId: idStr,
                dataType: 'json',
                success: function (result) {
                    after();
                    if (result.code == 10000) {
                        cb(result);
                    } else {
                        alert(result.message);
                    }
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    after();
                    alert('网络错误,请重试！');
                }
            }
        )
    },
})


Array.prototype.addToArr = function (val, node) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) {
            return;
        }
    }
    this.push(val);
    node.val(this.join(','));
}


Array.prototype.removeByValue = function (val, node) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) {
            this.splice(i, 1);
            node.val(this.join(','));
            break;
        }
    }
}

function formatDateTime(inputTime) {
    if (inputTime == null || inputTime == undefined || inputTime == '') {
        return "--";
    }
    var date = new Date(inputTime);
    var y = date.getFullYear();
    if (isNaN(y)) {
        return "--";
    }
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    return y + '-' + m + '-' + d;
};


window.alert = function (mes, callBack) {
    $('#conform-content').hide();
    $('#alert-content').show();
    $('.tip-content').text(mes);
    var modal = $("#mymodal-data");
    modal.off('hidden.bs.modal');
    if (callBack) {
        modal.on('hidden.bs.modal', callBack);
    }
    modal.modal({
        keyboard: true,
        backdrop: true,
        show: true
    });
}


window.myConfirm = function (mes, callBack) {
    $('#conform-content').show();
    $('#alert-content').hide();
    $('.tip-content').text(mes);
    var modal = $("#mymodal-data");
    modal.off('hidden.bs.modal');
    if (callBack) {
        $('#conform-but').off('click');
        $('#conform-but').on('click', function () {
            modal.modal('hide');
            modal.on('hidden.bs.modal', callBack);
        })
    }
    modal.modal({
        keyboard: true,
        backdrop: true,
        show: true
    });
}


function before() {
    $('#loading').show();
    $('.mask_area').fadeIn();

}

function after() {
    $('#loading').hide();
    $('.mask_area').fadeOut();
}
after();
window.onunload = after;

$(function () {
    $('body').on('click', '.down-file', function () {
        window.location.href='/assist/download?fileName='+$(this).text();
    })
    $('form').on('focus', 'input[type=number]', function (e) {
        $(this).on('mousewheel.disableScroll', function (e) {
            e.preventDefault()
        })
    })
    $('form').on('blur', 'input[type=number]', function (e) {
        $(this).off('mousewheel.disableScroll')
    })

    $('.btn-export').on('click',function(){
        var href=$(this).attr('to')+'?timestamp='+new Date().getTime();
        $('.form-inline').find('.form-control').each(function(){
            var temp=$(this).val();
            if(temp!=undefined && temp!=null && temp!=''){
                href+='&'+$(this).attr('name')+'='+temp;
            }
        })
        window.location.href=href;
    })

})


