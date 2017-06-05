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
import java.util.TimeZone;

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
	private static final DateFormat simpleOne = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat dateWithTime = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static final String homeTimeZone = "Australia/Melbourne";
	private static final String hostTimeZone = "America/New_York";
	/* (non-Javadoc)
	 * @see java.text.DateFormat#format(java.util.Date, java.lang.StringBuffer, java.text.FieldPosition)
	 */
	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo,
			FieldPosition fieldPosition) {
	    StringBuffer res = new StringBuffer();
        for (final DateFormat dateFormat : DATE_FORMATS) {
        	System.out.println("DB Date:"+date);
            if ((res = dateFormat.format(date, toAppendTo, fieldPosition)) != null) {
            	System.out.println("Formatted Date:"+res.toString());
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
       	System.out.println("Raw Date:"+source);
/*        for (final DateFormat dateFormat : DATE_FORMATS) {
        	System.out.println("Raw Date:"+source);
        	dateFormat.setTimeZone(TimeZone.getTimeZone(homeTimeZone));
            if ((res = dateFormat.parse(source, pos)) != null) {
            	System.out.println("Formate Date:"+res.toString());
                return res;
            }
        }*/
	    	dateWithTime.setTimeZone(TimeZone.getTimeZone(homeTimeZone));
        if ((res = dateWithTime.parse(source, pos)) != null) {
        	System.out.println("Formate Date dateWithTime:"+res.toString());
            return res;
        }
        else if ((res = simpleOne.parse(source, pos)) != null) {
        	System.out.println("Formate Date simpleOne:"+res.toString());
            return res;
        }
        return null;
	}

}
