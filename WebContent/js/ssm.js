var buttons = $.extend([], $.fn.datebox.defaults.buttons);

buttons.splice(1, 0, {
    text: '清空',
    handler: function(target) {
        $(target).datebox("setValue", "").datebox("hidePanel");
    }
});
$.fn.datebox.defaults.buttons = buttons;

var _LoadingHtml = '<div id="loadingDiv" style="position:absolute;left:0;height:100%;width:103%;top:0;background:#f3f8ff;opacity:1.0;filter:alpha(opacity=100);z-index:10000;">' +
    '<div style="position: absolute; cursor1: wait;padding-top:5px; left: 40%; top:40%; width: auto; height: 37px; line-height: 37px; padding-left: 2px; padding-right: 5px;' +
    'background: #fff ; border: 2px solid #95B8E7; color: #000000; font-family:\'Microsoft YaHei\';"><img src="../js/jquery-easyui-1.4.4/themes/gray/images/loading.gif">页面加载中，请等待...</div></div>';

//呈现loading效果
document.write(_LoadingHtml);

//监听加载状态改变
document.onreadystatechange = completeLoading;

//加载状态为complete时移除loading效果
function completeLoading() {
    if(document.readyState == "complete") {
        var loadingMask = document.getElementById('loadingDiv');
        loadingMask.parentNode.removeChild(loadingMask);
    }
}

$('#editWin').dialog({
    width: 800,
    height: 450,
    closed: false,
    closable: true,
    modal: true,
    resizable: true,
    maximizable: true,
    draggable: true
});

$('#editWin').dialog("close");

//加载列表
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
        loadFilter: function(result) {
//			if(result.code){

            if(result.data) {
                if(result.code == 0){
                    return result.data;
                }else{
                    $.messager.alert("提示",result.desc,"error");
                }

            } else {
                return result;
            }
//			}

        },
        // 		sortName: 'COMPLETE_DEADLINE',
        // 		sortOrder: 'asc',
        singleSelect: true, //是否单选
        pagination: true,
        remoteSort: false,
        //		idField: 'id',
        pageSize: 100,
        pageList:[20,50,100],
        rownumbers: true,
        onBeforeLoad: function(param) {
            //param.auditType="${auditType}";
            //param.typeId="${typeId}";

        },
        rowStyler: function(index, row) {
            // 			if(row.IS_COMPLETE == "是"){
            // 				return "color:green;";
            // 			}
        },
        onDblClickRow: function(rowIndex, rowData) {
            toEdit(moduleName,title);
        },
        onSelect: function(index, row) {
            //$("#editPasswd").hide();
            //			if(row.USERID == "${userSession.userId}" || ",${userSession.userRole},".indexOf(",role_admin,") > -1){
            //					$("#editPasswd").show();
            //			}
        },
        toolbar: '#bar'
    });
}

//去新增界面
function toAdd(moduleName, title) {
    var src = "../" + moduleName + "/goEdit?editType=add";
    $("#editIFrame").attr("src", src);
    openWin("新增" + title + "信息", true);
}

//去修改界面
function toEdit(moduleName, title) {
    var id;
    var rows = $('#list').datagrid("getSelections");
    if(rows.length == 1) {
        id = rows[0].id;
    } else {
        $.messager.alert("提示", "请选择一条要修改的记录！", "info");
        return false;
    }
    var src = "../" + moduleName + "/goEdit?editType=update&id=" + id;
    $("#editIFrame").attr("src", src);
    openWin("修改" + title + "信息", true);
}

//刪除
function remove(moduleName, title) {
    var id = "";
    var rows = $('#list').datagrid('getSelections');
    if(rows.length > 0) {
        id = rows[0].id;
        if(id != "") {
            $.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
                if(r) {
                    $.ajax({
                        cache: true,
                        type: "POST",
                        url: "../" + moduleName + "/remove?editType=delete",
                        data: {
                            id: id
                        },
                        async: false,
                        error: function(request) {
                            $.messager.alert("提示", "调用失败", "error");
                        },
                        success: function(result) {
                            if(result.code == 0) {
                                $.messager.alert("提示", "删除成功", "info");
                                $('#list').datagrid("reload");
                            } else {
                                $.messager.alert("提示", result.desc, "error");
                            }
                        }
                    });
                }
            });

        } else {
            $.messager.alert("提示", "请选择要删除的记录", "info");
        }
    } else if(rows.length == 0) {
        $.messager.alert("提示", "请选择删除的记录", "info");
    }
}

//保存按钮方法
function save(moduleName, title) {
    if(!$("#thisForm").form('validate')) {
        return false;
    }

    $.ajax({
        cache: true,
        type: "POST",
        url: "../" + moduleName + "/saveEdit",
        data: $('#thisForm').serialize(),
        async: true,
        error: function(request) {
            $.messager.alert("提示", "调用失败", "error");
        },
        success: function(result) {
            if(result.code == 0) {

                $.messager.alert("提示", "保存成功", "info", function() {
                    backList();
                });

            } else {
                $.messager.alert("提示", result.desc, "error");
            }
        }
    });

}

//返回列表页
function backList() {
    var id = $("#id").val();
    parent.closeWin("list", id);
}

//查询框变化事件调用方法
//其他浏览器
function OnInput(event) {
    if(event.target.value != event.target.placeholder) {
        $('#list').datagrid('reload', {
            queryText: event.target.value
        });
    }
}
//IE
function OnPropChanged(event) {
    if(event.propertyName.toLowerCase() == "value") {
        $('#list').datagrid('reload', {
            queryText: event.srcElement.value
        });
    }
}

//查询列表
function searchList() {
    var queryText = $("#queryText").val();
    $('#list').datagrid('reload', {
        queryText: queryText
    });
}

//进度条格式化
function progressFormatter(value, rowData, rowIndex) {
    var val = parseInt(value.replace('%', ""));
    var htmlstr = '<div class="easyui-progressbar progressbar" style="width:100%;background:transparent;"><div class="progressbar-text" style="width: 100%">' + value + '</div><div class="progressbar-value" style="width: ' + value + '"><div class="progressbar-text" style="width: ' + (100 * 100 / val) + '%;color:-internal-quirk-inherit">' + value + '</div></div></div>';

    return htmlstr;
}

//标题格式化
function titleFormatter(value, rowData, rowIndex) {
    return '<span title="' + (value ? value : '') + '">' + (value ? value : '') + '</span>';
}