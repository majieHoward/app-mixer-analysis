package com.howard.www.common.web.mvc;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
/**
 * 
 * @author majie
 *
 */
public class RichFreeMarkerViewResolver extends AbstractTemplateViewResolver {
	public RichFreeMarkerViewResolver() {
		setViewClass(RichFreeMarkerView.class);
	}

	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		AbstractUrlBasedView view = super.buildView(viewName);
		view.getUrl();
		if (viewName.startsWith("/")) {
			view.setUrl(viewName + getSuffix());
		}
		return view;
	} 

}
