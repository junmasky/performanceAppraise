function knockoutGetOne(url, data, target) {

    $.ajax({
        type: "POST",
        async: false,
        url: url,
        data: data,
        success: function (result) {
            for (var p in target) {
                if (!result[p]) {
                    continue;
                }
                if (typeof (target[p]) == 'function') {
                    target[p](result[p]);
                }
                else {
                    target[p] = result[p];
                }
            }
        }
    }).error(function (XMLHttpRequest, textStatus, errorThrown) {
        /* try{
         console.log(textStatus + "  " + errorThrown);
         }
         catch(e){

         }*/
        if (textStatus == "parsererror") {
            alert('Session Time out , please login again！');
        }
        else {
            alert('It is internal server errors！');
        }
    });
}

function knockoutGetList(url, data, target) {

    $.ajax({
        type: "POST",
        async: false,
        url: url,
        data: data,
        success: function (result) {
            target(result.rows);
        }
    }).error(function (XMLHttpRequest, textStatus, errorThrown) {
        /* try{
         console.log(textStatus + "  " + errorThrown);
         }
         catch(e){

         }*/
        if (textStatus == "parsererror") {
            alert('Session Time out , please login again！');
        }
        else {
            alert('It is internal server errors！');
        }
    });
}

function getUrlRequest(url){
    var getRequest = new Object();
    if(url.indexOf("?")!=-1){
        var str = url.split("?")[1];
        var strs = str.split("&");
        for(var i = 0;i < strs.length;i++){
            getRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
        }
    }
    return getRequest;
}

function getUrlMain(url){
    var strs =url.split("/");
    var urlMain='';
    for(var i=0;i<strs.length-1;i++){
        urlMain=urlMain+strs[i]+'/';
    }
    return urlMain;
}

function mergeObject(obj1,obj2){
    for(var key in obj2){
        if(obj1.hasOwnProperty(key)) continue;
        obj1[key]=obj2[key];
    }
    return obj1;
}