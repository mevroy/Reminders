/**
 * 
 */
package com.yourpackagename.yourwebproject.custom.binders;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author mevan.d.souza
 *
 */
public class CustomDateFormatBinder extends DateFormat {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6677404320037287520L;
	private static final List<? extends DateFormat> DATE_FORMATS = Arrays.asList(
            new SimpleDateFormat("dd/MM/yyyy HH:mm"),
            new SimpleDateFormat("dd/MM/yyyy"));
	/* (non-Javadoc)
	 * @see java.text.DateFormat#format(java.util.Date, java.lang.StringBuffer, java.text.FieldPosition)
	 */
	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo,
			FieldPosition fieldPosition) {
	    StringBuffer res = new StringBuffer();
        for (final DateFormat dateFormat : DATE_FORMATS) {
            if ((res = dateFormat.format(date, toAppendTo, fieldPosition)) != null) {
                return res;
            }
        }
		return res;
	}

	/* (non-Javadoc)
	 * @see java.text.DateFormat#parse(java.lang.String, java.text.ParsePosition)
	 */
	@Override
	public Date parse(String source, ParsePosition pos) {
	    Date res = null;
        for (final DateFormat dateFormat : DATE_FORMATS) {
            if ((res = dateFormat.parse(source, pos)) != null) {
                return res;
            }
        }

        return null;
	}

}
