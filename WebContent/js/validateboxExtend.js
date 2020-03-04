/**
 * easyui validatebox验证扩展
 * @author Tom Luo
 * 使用实例：
 * [valid]
 * <input name="ce_staff_id" class="easyui-textbox" validType="valid[/^\d{8,9}$/,'Please enter a valid 盛华工号']">
 * input中添加属性validType，方法valid[regex,message],regex:正则表达式（已符号/^开头，$/结尾）,message:字符串类型,验证不通过时返回消息
 * 
 * [checkByRemote]
 * checkByRemote[url,fieldname]
 */
$.extend($.fn.validatebox.defaults.rules, {
	valid:{
		validator: function (value,param) {
            return param[0].test(value);
        },  
        message: '{1}'
	},
	dateValid:{
		validator: function (value,param) {
            return /((^((1[8-9]\d{2})|([2-9]\d{3}))(\/)(10|12|0?[13578])(\/)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(\/)(11|0?[469])(\/)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(\/)(0?2)(\/)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(\/)(0?2)(\/)(29)$)|(^([3579][26]00)(\/)(0?2)(\/)(29)$)|(^([1][89][0][48])(\/)(0?2)(\/)(29)$)|(^([2-9][0-9][0][48])(\/)(0?2)(\/)(29)$)|(^([1][89][2468][048])(\/)(0?2)(\/)(29)$)|(^([2-9][0-9][2468][048])(\/)(0?2)(\/)(29)$)|(^([1][89][13579][26])(\/)(0?2)(\/)(29)$)|(^([2-9][0-9][13579][26])(\/)(0?2)(\/)(29)$))/.test(value);
        },  
        message: '{0}'
	},
	checkByRemote:{
		validator: function (value,param) {
            var check;
            if(value==''){
            	check='false';
            }
            else{
            	var data={};
                data[param[1]]=value;
                $.ajax({
                	async : false,
                    cache : false,
                    type : 'post',    
                    url : param[0],    
                    data : data,
                    success:function(result){
                    	if(result&&!result.error){
                    		check='true';
                    	}
                    	else{
                    		check='false';
                    	}
                    }
                }); 
            }               
            return check==='true';   
        },  
        message: '请填写正确的 '+'{1}'
	}
})