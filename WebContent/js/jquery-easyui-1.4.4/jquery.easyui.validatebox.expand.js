$.extend($.fn.validatebox.defaults.rules,{
	isSpace:{
		validator:function (value,param){return $.trim(value)!=''},
		message:'请输入有效值'
	}
});