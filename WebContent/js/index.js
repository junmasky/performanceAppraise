var _tid=0;
var shown=false;

function hideAllMenu()
{
//��Ϊ����ҳ��û����ȫload����������û����͵����body���ʱ����ΪoTableû�У��ͻ����
	try{
		var i = 0;
		var oTable = document.getElementById( "TABLE_Menu" );
		for( i = 0; i < oTable.rows(0).cells.length; i++ )
		{
			if(oTable.rows(0).cells(i).className == "menutopic")
				oTable.rows(0).cells(i).className = "menutopic";
		}
	}catch(e){}
}

function hideAllMenu1(){
	$('.divmenu').css("display","none") ;
	$('.menutopic_over').addClass('menutopic');
	$('.menutopic_over').removeClass('menutopic');	
	hideIframe();
}

function hidex(o)
{
	alert(2);
	if(o)
	o.style.display="none";
}
/**
 * ���ز˵�
 * @param sID
 */
function hideMenu( sID )
{
	hideIframe();

}

/**
 * ���ز˵�
 * @param sID
 */
function hideMenu2( sID )
{
	shown=false;
	clearTimeout(_tid);
	hideIframe();
	var x = 0, y = 0;
	var oMenu = document.getElementById( sID );
	if(oMenu==null)
		return;
	
	var left = parseInt(oMenu.style.left);
	var top = parseInt(oMenu.style.top);
	var bottom = parseInt(oMenu.style.top) + oMenu.clientHeight;
	var right = parseInt(oMenu.style.left) + oMenu.clientWidth;
	
	x = event.clientX-2;// + document.body.scrollLeft;
	y = event.clientY;// + document.body.scrollTop;
	if( x < left || x > right || y < top || y > bottom )
	{
		
		oMenu.style.display = "none";
		oMenu.style.pixelLeft=0;
		oMenu.style.pixelTop=0;
		var i = 0;var oTable = document.getElementById( "TABLE_Menu" );
		for( i = 0; i < oTable.rows[0].cells.length; i++ ){
			if(oTable.rows[0].cells[i].className.indexOf("menutopic_over")!=-1 )
				oTable.rows[0].cells[i].className = "menutopic";
		}
	}
	//ie6���µİ汾�������˵��Ὣ��λѡ�����ס�����Ҫ���⴦��
	var IEversion = parseFloat(navigator.appVersion.split("MSIE")[1]);
	if (IEversion < 7)
	{
		var oDrop = document.frames[0].document.getElementById("ID_SelectCompany");
		if (oDrop)
			oDrop.style.visibility = "visible";
	}
}

function showMenu(sID,o)
{
	if(!shown)
		_tid=setTimeout(function(){showMenu2(sID,o)},200);
	else
		showMenu2(sID,o);
}
function showMenu2( sID, o )
{
	hideAllMenu1();
	
	shown=true;
	var i = 0;
	//var oTD = event.srcElement;
	var oTD = o;
	var oTable = oTD.parentNode.parentNode.parentNode;
//	alert(oTD.parentNode.parentNode.outerHTML);
	for( i = 0; i < oTable.rows[0].cells.length; i++ )
	{
		if(oTable.rows[0].cells[i].className == "menutopic" )
			oTable.rows[0].cells[i].className = "menutopic";
	}
	//���������Ӳ˵�
	for( var i = 0; i < 20; i++ )
	{
		oDiv = document.getElementById( "DIV_xmenu_" + i.toString() );
		if(oDiv!=null)
			if(oDiv.style.display!="none")
				oDiv.style.display="none";
	}
	var oMenu = document.getElementById( sID );
	if(oMenu==null)
		return false;
	//���û���Ӳ˵����ͻ�������һ����ʽ��������ʽ����������ܶ��б߿�
	if(oMenu.getElementsByTagName("DIV").length==0)
	{
		oTD.className = "menutopic_over2";
		return false;
	}
	else
		oTD.className = "menutopic";

	//���ⲿ�ʼ��˵�������ת��Ϊʵ�ʵ��ʼ���ַ
	if (oMenu.innerHTML.indexOf("*mail*")!=-1)
	{
		oMenu.innerHTML = oMenu.innerHTML.replace(/\*mail\*/g,g_sUserMailFile);
	}
	//oMenu.style.display = "";

	//setTimeout("fade('"+oMenu.id+"')",100);
	var s = "";
	var l = 0, t = 0;
	t = parseInt(o.offsetHeight);
	while( o != null )
	{
//		s += o.tagName + " " + o.height + "\n";
		l += o.offsetLeft;
		t += o.offsetTop;
		o = o.offsetParent;
	}
//	alert(l+" "+t);
//	if(g_sUserMailFile.indexOf("liubin")!=-1)
	oMenu.style.top = t+"px";
	oMenu.style.display = "block";
//	alert(typeof(document.body.clientWidth)+ " " + document.body.clientWidth + "\nMenu width: " + oMenu.clientWidth + " MenuID: " + oMenu.id);
	var ww=document.body.clientWidth,w=oMenu.clientWidth;
	//���λ�ò�����������ƫ��һ�¡���22���ǹ������Ŀ��
	if(w>ww-l)
		oMenu.style.left = ((ww-w-22)<0?"0":((ww-w-22).toString()))+"px";
	else
		oMenu.style.left = l+"px";

	//ie6���µİ汾�������˵��Ὣ��λѡ�����ס�����Ҫ���⴦��
	var IEversion = parseFloat(navigator.appVersion.split("MSIE")[1]);
	if (IEversion < 7)
	{
		var oDrop = document.frames[0].document.getElementById("ID_SelectCompany");
		if (oDrop)
		{
			var pos = sID.lastIndexOf("_");
			if (parseInt(sID.substring(pos+1,sID.lengh))<=2)
			{
				oDrop.style.visibility = "hidden";
			}else{
				oDrop.style.visibility = "visible";
			}
		}
	}
	var $menu = $('#'+sID) ;
	
	createIframe({top:$menu.offset().top,left:$menu.offset().left,width:$menu.width(),height:$menu.height()}) ; 
	
}


function hideIframe() {
	var myIframe = document.getElementById("myMenuIframe") ;
	
	if(myIframe){
		 myIframe.style.display = "none" ;
	}
} 

//չ�����۵���ͷ
function TopCollapse(t)
{
	var height=$("#centerpanel").height();
	if(t.className == "current_03")
	{
		$("#DIV_TOP").css("display","none");
		$('#body').layout('panel', 'north').panel('resize',{height:27});
		$('#body').layout('panel', 'center').panel('resize',{height:height+72});
		$("#homepageHead").css("height",25);
		t.className = "current_03_active";
	}
	else{
		$("#DIV_TOP").css("display","block");
		$("#DIV_TOP").css("height","70px");
		$('#body').layout('panel', 'north').panel('resize',{height:97});
		$('#body').layout('panel', 'center').panel('resize',{height:height-70});
		$("#northpanel").css("height",70);
		t.className = "current_03";
	}
	$('#body').layout('resize');
	
	$('#ids').layout('resize');
}


function killallmenu(){
	hideAllMenu1();
}

function createIframe(coordinate) {

	var myIframe = document.getElementById("myMenuIframe") ;
	
	if(!myIframe){
		 var myIframe = document.createElement('iframe');  
		 myIframe.id='myMenuIframe';  
		 myIframe.style.zIndex='-1'; 
		 myIframe.setAttribute('frameborder','0');  
		 myIframe.setAttribute('src',''); 
		 document.getElementById("iframe-div").appendChild(myIframe);  
	}
	
    myIframe.style.position='absolute';  
    myIframe.style.top=coordinate.top;  

    myIframe.style.left=coordinate.left-1;     
    myIframe.style.width=coordinate.width+2;   
    myIframe.style.height=coordinate.height; 
    myIframe.style.filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';
    myIframe.style.display = "" ;
   
   
} 


