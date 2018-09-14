/**
 * 
 */
package com.yourpackagename.yourwebproject.webapp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yourpackagename.yourwebproject.actor.MailSenderActor;
import com.yourpackagename.yourwebproject.actor.MailSenderUntypedActor;
import com.yourpackagename.yourwebproject.common.CheckPermission;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailAccount;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.model.repository.GroupEmailAccountRepository;
import com.yourpackagename.yourwebproject.service.GroupEmailAccountService;

/**
 * @author mevan.d.souza
 *
 */
@Controller
@CheckPermission(allowedRoles = {Role.SUPER_ADMIN, Role.ADMIN})
public class GroupEmailAccountController extends BaseWebAppController {
	@Autowired
	private GroupEmailAccountService groupEmailAccountService;
	@Autowired
	private MailSenderUntypedActor mailSenderUntypedActor;

	@RequestMapping(value = "/addGroupEmailAccount", method = RequestMethod.GET)
	public String addGroupEmailAccount(Locale locale, Model model,
			@PathVariable String groupCode) {
		GroupEmailAccount groupEmailAccount = new GroupEmailAccount();
		groupEmailAccount.setGroupCode(groupCode);
		model.addAttribute("groupEmailAccount", groupEmailAccount);
		return "addGroupEmailAccount";
	}

	@RequestMapping(value = "/saveGroupEmailAccount", method = RequestMethod.POST)
	public String saveGroupEmailAccount(Locale locale, Model model,
			@ModelAttribute GroupEmailAccount groupEmailAccount,
			BindingResult results) {

		if (results.hasErrors()) {
			return "addGroupEmailAccount";
		}
		try {
			mailSenderUntypedActor.sendTestEmail(getloggedInUser(),
					groupEmailAccount);
		} catch (MailAuthenticationException e) {
			addAlert(
					"The email account could not be authenticated!\nIf this is the first time you are adding this account on this server, then it might be because host email server has blocked access from this web application server. If this is the case, please login into your account and mark the unusual activity as yours and then try adding the account again. Thank You! ",
					model);
			return "addGroupEmailAccount";
		} catch (Exception e) {
			addAlert(
					"An exception has occured!\nIf this is the first time you are adding this account on this server, then it might be because host email server has blocked access from this web application server. If this is the case, please login into your account and mark the unusual activity as yours and then try adding the account again. Thank You! ",
					model);
			return "addGroupEmailAccount";
		}
		groupEmailAccount.setUpdatedAt(Calendar.getInstance().getTime());
		try {
			GroupEmailAccount newgroupEmailAccount = groupEmailAccountService
					.insert(groupEmailAccount);
			addSuccess("Email Account has been successfully added/updated. Thanks",
					model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addError("Error has Occured while saving the values", model);
		}

		return "addGroupEmailAccount";
	}

	@RequestMapping(value = "/json/viewGroupEmailAccounts", method = RequestMethod.GET)
	public @ResponseBody List<HashMap<String, Object>> viewGroupEmailAccounts(
			Locale locale, Model model, @PathVariable String groupCode) {

		List<GroupEmailAccount> groupEmailAccounts = groupEmailAccountService
				.findByGroupCode(groupCode);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (GroupEmailAccount groupEmailAccount : groupEmailAccounts) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("label", groupEmailAccount.getEmailAccountCode()+"-"+groupEmailAccount.getUsername());
			hm.put("value", groupEmailAccount.getEmailAccountCode());
			list.add(hm);
		}
		return list;
		
	}
}
