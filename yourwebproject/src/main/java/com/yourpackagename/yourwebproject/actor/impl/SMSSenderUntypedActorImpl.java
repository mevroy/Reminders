package com.yourpackagename.yourwebproject.actor.impl;

import com.yourpackagename.yourwebproject.actor.SMSSenderUntypedActor;
import com.yourpackagename.yourwebproject.common.Props;


import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.Map;
import java.util.TimeZone;

/**
 * Mail sender actor impl
 *
 * @author: Y Kamesh Rao
 * @created: 4/21/12 5:29 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
public class SMSSenderUntypedActorImpl implements SMSSenderUntypedActor {
	private VelocityEngine smsVelocityEngine;
	private Props props;



	public String prepareSMSBody(String templateName,
			Map<String, Object> model) {

		/* Adding some helping classes into the classes for use in the templates */
		if (model != null) {
			model.put("DateTool", new DateTool());
			model.put("MathTool", new MathTool());
			model.put("StringUtils", StringUtils.class);
			model.put("homeTimeZone", TimeZone.getTimeZone(props.homeTimeZone));
			model.put("hostTimeZone", TimeZone.getTimeZone(props.hostTimeZone));
			model.put("url",props.applicationUrl);
			model.put("project", props.applicationProject);
			// model.put("DateTimeUtils", DateTimeUtils.class);
		}
		String text = VelocityEngineUtils.mergeTemplateIntoString(
				smsVelocityEngine, templateName, model);
		return text;
	}



	/**
	 * @return the smsVelocityEngine
	 */
	public VelocityEngine getSmsVelocityEngine() {
		return smsVelocityEngine;
	}



	/**
	 * @param smsVelocityEngine the smsVelocityEngine to set
	 */
	public void setSmsVelocityEngine(VelocityEngine smsVelocityEngine) {
		this.smsVelocityEngine = smsVelocityEngine;
	}



	/**
	 * @return the props
	 */
	public Props getProps() {
		return props;
	}



	/**
	 * @param props the props to set
	 */
	public void setProps(Props props) {
		this.props = props;
	}

}
