/**
 * 
 */
package com.yourpackagename.yourwebproject.custom.binders;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.StringUtils;

/**
 * @author mevan.d.souza
 *
 */
public class CustomNumberEditorBinder extends CustomNumberEditor {

	public CustomNumberEditorBinder(Class<? extends Number> numberClass,
			boolean allowEmpty) throws IllegalArgumentException {
		super(numberClass, allowEmpty);
		// TODO Auto-generated constructor stub
	}

	@Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            setValue(0);
        }else {
            super.setAsText(text.trim());
        }
    }
}
