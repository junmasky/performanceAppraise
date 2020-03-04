$.fn.datebox.defaults.formatter =function(date){
	if (!date){return '';}
    if (!(date instanceof Date)){
        date = new Date(date);
    }
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'/'+m+'/'+d;
}
$.fn.datebox.defaults.parser = function(s){
    if (!s) {
        return new Date();
    }
    var date = new Date(s);
    if (date) {
        return date;
    }
	var ss = (s.split('-'));
	if(!ss[1]){
		ss=(s.split('/'));
	}
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	var d = parseInt(ss[2],10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d);
	} else {
		return new Date();
	}
}

function yearMonthFormatter(date){
    if (!date){return '';}
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    return y + '/' + (m<10?('0'+m):m);
}

function yearMonthParser(s){
    if (!s){return null;}
    var ss = s.split('/');
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    if (!isNaN(y) && !isNaN(m)){
        return new Date(y,m-1,1);
    } else {
        return new Date();
    }
}

function datetimeFormatter(date) {
    if (!date){return '';}
    if (!(date instanceof Date)){
        date = new Date(date);
    }
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    var hours = date.getHours();
    var minutes = date.getMinutes();
    return y + '/' + (m < 10 ? ('0' + m) : m) + '/' + (d < 10 ? ('0' + d) : d) + " "+ hours+":"+minutes;
}

function dateFormatter(date){
    if (!date){return '';}
    if (!(date instanceof Date)){
    	date = new Date(date);
    }    
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    return y + '/' + (m < 10 ? ('0' + m) : m) + '/' + (d < 10 ? ('0' + d) : d);
}


/**
 * 转换yyyy/mm/dd日期字符串
 * @param date
 * @returns {*}
 */
function dateFormatterymd(date){
    if (!date){return '';}
    if (!(date instanceof Date)){
        date = new Date(date);
    }
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    return y+'/'+(m<10?('0'+m):m)+'/'+d;
}

function parser(s){
    var t = Date.parse(s);
    if (!isNaN(t)){
        return new Date(t);
    }else{
        return "";
    }
}
