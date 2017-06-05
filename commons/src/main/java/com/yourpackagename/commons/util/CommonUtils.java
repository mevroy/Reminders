/**
 * @filename CommonUtils.java
 * @author Y.Kamesh Rao
 */
package com.yourpackagename.commons.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;



/**
 * Class that consists of the commonly used small utilities
 *
 * @author Y.Kamesh Rao
 */
public class CommonUtils {
	
	private static Logger log = LoggerFactory.getLogger(CommonUtils.class);
	/**
	 * Returns the current timestamp in timezone independent way
	 *
	 *
	 * @return String form timestamp
	 */
	public static String getTimestamp() {
		String created;
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);

		String month = (cal.get(Calendar.MONTH) + 1) + "";
		if (month.length() != 2) {
			month = "0" + month;
		}

		String day = cal.get(Calendar.DAY_OF_MONTH) + "";
		if (day.length() != 2) {
			day = "0" + day;
		}

		String hour = cal.get(Calendar.HOUR_OF_DAY) + "";
		if (hour.length() != 2) {
			hour = "0" + hour;
		}

		String minute = cal.get(Calendar.MINUTE) + "";
		if (minute.length() != 2) {
			minute = "0" + minute;
		}

		String second = cal.get(Calendar.SECOND) + "";
		if (second.length() != 2) {
			second = "0" + second;
		}

		created = year + "-" + month + "-" + day + "T" + hour + ":" + minute
				+ ":" + second + "Z";

		return created;
	}

	/**
	 * Converts the data to hex representation.
	 *
	 * @param data
	 *            The data to be converted
	 * @return
	 */
	public static String toHex(final byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int intData = (int) data[i] & 0xFF;
			if (intData < 0x10)
				buf.append("0");
			// Display 2 digits per byte i.e. 09
			buf.append(Integer.toHexString(intData));
		}
		return (buf.toString());
	}

	public static boolean isValidDates(final Date startDate,
			final Date expiryDate) {
		if (isValidStartDate(startDate) && isValidEndDate(expiryDate))
			return true;
		return false;
	}

	private static boolean isValidStartDate(final Date startDate) {
		return (startDate == null || startDate.before(Calendar.getInstance()
				.getTime()));

	}

	private static boolean isValidEndDate(final Date endDate) {
		return (endDate == null || endDate.after(Calendar.getInstance()
				.getTime()));

	}

	public static boolean isValidPhoneNumber(String phoneNumber,
			String defaultRegion) {
		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		try {
			Phonenumber.PhoneNumber phoneNumbe = phoneNumberUtil.parse(
					phoneNumber, defaultRegion);
			if (phoneNumberUtil.isValidNumber(phoneNumbe)) {
				return true;
			}
		} catch (NumberParseException e) {
			return false;
		}
		return false;
	}

	public static String printDateInHomeTimeZone(Date hostTime) {
		if (hostTime != null) {
			DateFormat formatter = new SimpleDateFormat("MMM dd yyyy h:mm:ss a");
			formatter.setTimeZone(TimeZone.getTimeZone("Australia/Melbourne"));
			// formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
			return formatter.format(hostTime).toString();
		}
		return "";
	}

	public static List<String> convertStringToList(String value, String regex) {
		if (StringUtils.isNotBlank(regex) && StringUtils.isNotBlank(value)) {
			String[] values = value.split(regex);
			if (values != null && values.length > 0) {
				return Arrays.asList(values);
			}
		} else if (StringUtils.isNotBlank(value)) {
			return Arrays.asList(value);
		}

		List<String> emptyList = new ArrayList<String>();
		return emptyList;

	}
	
	public static byte[] readFiletoOPStreamFromURL(String url) throws Exception
	{
		try {
			URL path = new URL(url);
	        URLConnection conn = path.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setReadTimeout(5000);
	        conn.connect(); 

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        IOUtils.copy(conn.getInputStream(), baos);
	        return baos.toByteArray();
	    }
	    catch (IOException e)
	    {
	        return null;
	    }
	}
	
	public static void setAcroFormField(PDAcroForm acroForm, String fieldName, String fieldValue)
	{
		if(acroForm!=null && StringUtils.isNotBlank(fieldName))
		{
			PDTextField field = (PDTextField) acroForm.getField(fieldName);
			if(field!=null)
			{
				try {
					field.setValue(fieldValue);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static String generateRandomString(int minLength, int maxLength) {
		if ((minLength == 0 && maxLength == 0 ) || (minLength < 0 || maxLength < 0)) {
			return null;
		}
		int eventCodeLength = minLength;
		if (maxLength > minLength)
			eventCodeLength = maxLength;
		int halfSize = eventCodeLength / 2;
		int secondHalfSize = eventCodeLength - halfSize;
		String randomInvitationCode = UUID.randomUUID().toString()
				.replaceAll("-", "");
		log.info("Random Code - "+randomInvitationCode);
		String secondHalf = randomInvitationCode.substring(
				randomInvitationCode.length() - secondHalfSize,
				randomInvitationCode.length()).toUpperCase();
		if (eventCodeLength == 1) {
			return secondHalf;
		}
		String firstHalf = randomInvitationCode.substring(0, halfSize)
				.toUpperCase();

		return firstHalf + secondHalf;

	}
}
