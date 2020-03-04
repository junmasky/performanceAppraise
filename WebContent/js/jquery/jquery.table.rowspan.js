(function($){
	$.fn.extend({
		"rowspan":function(colIdx){
			return this.each(function(){
				var that;
				$('tr',this).each(function(row){
					$('td:eq(' + colIdx +')',this).filter(':visible').each(function(col){
						if(that != null && $(this).html() == $(that).html()){
							rowspan = $(that).attr("rowspan");
							if(rowspan == undefined){
								$(that).attr("rowspan",1);
								rowspan = $(that).attr("rowspan");
							}
							rowspan = Number(rowspan) + 1;
							$(that).attr("rowspan",rowspan);
							$(this).hide();
						}
						else{
							that = this;
						}
					});
				});
			});
		}
	});
})(jQuery);