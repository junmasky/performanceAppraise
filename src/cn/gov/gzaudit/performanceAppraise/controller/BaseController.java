package cn.gov.gzaudit.performanceAppraise.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;


@ControllerAdvice("cn.gov.gzaudit.performanceAppraise.controller")
public class BaseController {
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd"), true));
//		binder.registerCustomEditor(int.class, new IntEditor());
//		binder.registerCustomEditor(long.class, new LongEditor());
//		binder.registerCustomEditor(double.class, new DoubleEditor());
//		binder.registerCustomEditor(float.class, new FloatEditor());
//		IntEditor a = new IntEditor();
	}
}
