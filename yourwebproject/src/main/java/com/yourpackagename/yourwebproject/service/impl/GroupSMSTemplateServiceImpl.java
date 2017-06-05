/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourpackagename.framework.data.BaseJpaServiceImpl;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.GroupSMSTemplate;
import com.yourpackagename.yourwebproject.model.repository.GroupEmailTemplateRepository;
import com.yourpackagename.yourwebproject.model.repository.GroupSMSTemplateRepository;
import com.yourpackagename.yourwebproject.service.GroupEmailTemplateService;
import com.yourpackagename.yourwebproject.service.GroupSMSTemplateService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupSMSTemplateServiceImpl extends
		BaseJpaServiceImpl<GroupSMSTemplate, Long> implements
		GroupSMSTemplateService {

	private @Autowired GroupSMSTemplateRepository groupSMSTemplateRepository;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupSMSTemplateRepository;
		this.entityClass = GroupSMSTemplate.class;
		this.baseJpaRepository.setupEntityClass(GroupSMSTemplate.class);

	}

	public GroupSMSTemplate findbyTemplateName(String templateName) {
		return groupSMSTemplateRepository.findbyTemplateName(templateName);
	}

	public List<GroupSMSTemplate> findbyGroupCode(String groupCode) {

		return groupSMSTemplateRepository.findbyGroupCode(groupCode);
	}

	public List<GroupSMSTemplate> findbyGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode) {
		return groupSMSTemplateRepository.findbyGroupCodeAndGroupEventCode(
				groupCode, groupEventCode);
	}

}
