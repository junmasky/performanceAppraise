var buttons = $.extend([], $.fn.datebox.defaults.buttons);
buttons.splice(1, 0, {
    text: '清空',
    handler: function (target) {
        $(target).datebox("setValue", "").datebox("hidePanel");
    }
});
$.fn.datebox.defaults.buttons = buttons;

var _LoadingHtml = '<div id="loadingDiv" style="position:absolute;left:0;height:100%;width:103%;top:0;background:#f3f8ff;opacity:1.0;filter:alpha(opacity=100);z-index:10000;">'
    + '<div style="position: absolute; cursor1: wait;padding-top:5px; left: 40%; top:40%; width: auto; height: 37px; line-height: 57px; padding-left: 2px; padding-right: 5px;'
    + 'background: #fff ; border: 2px solid #95B8E7; color: #000000; font-family:\'Microsoft YaHei\';"><img src="${ctx}/js/jquery-easyui-1.4.4/themes/gray/images/loading.gif">页面加载中，请等待...</div></div>';
//呈现loading效果
document.write(_LoadingHtml);
//监听加载状态改变
document.onreadystatechange = completeLoading;
//加载状态为complete时移除loading效果
function completeLoading() {
    if (document.readyState == "complete") {
        var loadingMask = document.getElementById('loadingDiv');
        loadingMask.parentNode.removeChild(loadingMask);
    }
}

function formSubmit(target, url) {
    if (!target.form('validate')) {
        return false;
    }

    $.ajax({
        cache: true,
        type: "POST",
        url: url,
        data: target.serialize(),
        async: true,
        beforeSend:function(request){
        	$.messager.progress({
        		msg:'请稍等',
        		text:'正在保存...',
        		interval:3000
        	});
        },
        complete:function(request){
        	$.messager.progress('close');
        },
        error: function (request) {
            alert("调用失败");
        },
        success: function (data) {
            rsltarr = eval("(" + data + ")");
            if (rsltarr.id == "1") {
                $.messager.alert("提示", "保存成功", "info", function () {
                    back();
                });
            } else {
                $.messager.alert("提示", rsltarr.text);
            }
        }
    });
}

function loadDatagrid(target, url, data) {
    target.datagrid({
        iconCls: 'icon-save',
        fit: true,
        nowrap: true,
        striped: true,
        collapsible: true,
        fitColumns: true,
        url: url,
        queryParams: data,
// 		sortName: 'COMPLETE_DEADLINE',
// 		sortOrder: 'asc', 
        singleSelect: true,//是否单选
        pagination: true,
        remoteSort: false,
        idField: 'id',
        pageSize: 50,
        rownumbers: true,
        onBeforeLoad: function (param) {
// 			param.auditType="${auditType}";
            //param.typeId="${typeId}";

        },
        rowStyler: function (index, row) {
// 			if(row.IS_COMPLETE == "是"){
// 				return "color:green;";
// 			}
        },
        onDblClickRow: function (rowIndex, rowData) {
            edit();
        },
        toolbar: '#bar'
    });
}

function progressFormatter(value, rowData, rowIndex) {
    var val = parseInt(value.replace('%', ""));
    var htmlstr = '<div class="easyui-progressbar progressbar" style="width:100%;background:transparent;"><div class="progressbar-text" style="width: 100%">' + value + '</div><div class="progressbar-value" style="width: ' + value + '"><div class="progressbar-text" style="width: ' + (100 * 100 / val) + '%;color:-internal-quirk-inherit">' + value + '</div></div></div>';

    return htmlstr;
}

function titleFormatter(value, rowData, rowIndex) {
    return '<span title="' + (value ? value : '') + '">' + (value ? value : '') + '</span>';
}