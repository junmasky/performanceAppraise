function setTab(area,id) {
	var tabArea=document.getElementById(area);
	var contents=tabArea.childNodes;
	for(var i=0; i<contents.length; i++) {
		if(contents[i].className=='tabcontent'){contents[i].style.display='none';}
	}
	document.getElementById(id).style.display='';
	var tabs=document.getElementById(area+'tabs').getElementsByTagName('a');
	
	for(var i=0; i<tabs.length; i++) { 
		tabs[i].className='tab'; 
	}
	document.getElementById(id+'tab').className='tab curtab';
	document.getElementById(id+'tab').blur();
	
}