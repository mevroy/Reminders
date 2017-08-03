/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.framework.data.BaseJpaServiceImpl;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPassCategory;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentTransaction;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupEventPassesRepository;
import com.yourpackagename.yourwebproject.service.GroupEventPassCategoryService;
import com.yourpackagename.yourwebproject.service.GroupEventPassesService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupEventPassesServiceImpl extends
		BaseJpaServiceImpl<GroupEventPass, String> implements
		GroupEventPassesService {

	@Autowired
	private GroupEventPassesRepository groupEventPassesRepository;
	@Autowired
	private GroupEventPassCategoryService groupEventPassCategoryService;

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupEventPassesRepository;
		this.entityClass = GroupEventPass.class;
		this.baseJpaRepository.setupEntityClass(GroupEventPass.class);

	}

	public List<GroupEventPass> findByGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode, boolean includeAll) {
		return groupEventPassesRepository.findByGroupCodeAndGroupEventCode(
				groupCode, groupEventCode, includeAll);
	}

	public List<GroupEventPass> findByGroupEventInvite(
			GroupEventInvite groupEventInvite) {
		return groupEventPassesRepository
				.findByGroupEventInvite(groupEventInvite);
	}

	public List<GroupEventPass> findByGroupMember(GroupMember groupMember) {
		return groupEventPassesRepository.findByGroupMember(groupMember);
	}

	public GroupEventPass findByPassIdentifier(String passIdentifier) {
		return groupEventPassesRepository.findByPassIdentifier(passIdentifier);
	}

	public GroupEventPass findByPassBarcode(String passBarcode) {
		return groupEventPassesRepository.findByPassBarcode(passBarcode);
	}

	public List<GroupEventPass> findByGroupMemberAndGroupEventCode(
			GroupMember groupMember, String groupEventCode) {
		return groupEventPassesRepository.findByGroupMemberAndGroupEventCode(
				groupMember, groupEventCode);
	}

	public List<GroupEventPass> findSoldTicketsByGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode) {
		return groupEventPassesRepository
				.findSoldTicketsByGroupCodeAndGroupEventCode(groupCode,
						groupEventCode);
	}

	public List<GroupEventPass> findUnSoldTicketsByGroupCodeAndGroupEventCode(
			String groupCode, String groupEventCode) {
		return groupEventPassesRepository
				.findUnSoldTicketsByGroupCodeAndGroupEventCode(groupCode,
						groupEventCode);
	}

	public List<GroupEventPass> findUnSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
			String groupCode, String groupEventCode,
			GroupEventPassCategory groupEventPassCategory) {
		return groupEventPassesRepository
				.findUnSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
						groupCode, groupEventCode, groupEventPassCategory);
	}

	public List<GroupEventPass> findByTransaction(
			GroupEventPaymentTransaction groupEventPaymentTransaction) {
		return groupEventPassesRepository
				.findByTransaction(groupEventPaymentTransaction);
	}

	public List<GroupEventPass> assignPassesToTransactionCopy(
			GroupEventPaymentTransaction groupEventPaymentTransaction,
			List<GroupEventPassCategory> groupEventPassCategories) {

		HashMap<String, Long> counter = new HashMap<String, Long>();
		boolean notEnoughTickets = false;
		List<GroupEventPass> finalPassList = new ArrayList<GroupEventPass>();
		try {
			GroupEventInvite gei = groupEventPaymentTransaction
					.getGroupEventInvite();
			List<GroupEventPassCategory> gpcsRaw = groupEventPassCategories;
			HashMap<String, GroupEventPassCategory> rawCategoriesMap = new HashMap<String, GroupEventPassCategory>();
			for (GroupEventPassCategory gcpR : gpcsRaw) {
				if (!StringUtils
						.equals(gcpR.getGroupEventPassCategoryId(), "0")) {
					long count = gcpR.getNumberOfPasses();
					if (rawCategoriesMap.containsKey(gcpR
							.getGroupEventPassCategoryId())) {
						gcpR = rawCategoriesMap.get(gcpR
								.getGroupEventPassCategoryId());
						gcpR.setNumberOfPasses(gcpR.getNumberOfPasses() + count);
					}
					rawCategoriesMap.put(gcpR.getGroupEventPassCategoryId(),
							gcpR);
				}
			}
			List<GroupEventPassCategory> gpcs = new ArrayList<GroupEventPassCategory>();

			for (Map.Entry<String, GroupEventPassCategory> entry : rawCategoriesMap
					.entrySet()) {
				GroupEventPassCategory value = entry.getValue();
				gpcs.add(value);

			}

			if (CollectionUtils.isNotEmpty(gpcs)) {
				for (GroupEventPassCategory gpcUser : gpcs) {
					if (gpcUser != null
							&& !StringUtils.equals(
									gpcUser.getGroupEventPassCategoryId(), "0"))

					{
						GroupEventPassCategory gpc = groupEventPassCategoryService
								.findById(gpcUser.getGroupEventPassCategoryId());
						List<GroupEventPass> geps = this
								.findUnSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
										gei.getGroupCode(),
										gei.getGroupEventCode(), gpc);
						if (geps != null
								&& geps.size() >= gpcUser.getNumberOfPasses()) {
							for (int a = 0; a < gpcUser.getNumberOfPasses(); a++) {
								geps.get(a).setPassPrice(gpc.getPassPrice());
								geps.get(a).setGroupEventPassCategory(gpc);
								finalPassList.add(geps.get(a));

							}
							Long currentCurrent = gpcUser.getNumberOfPasses();
							if (counter.containsKey(gpc
									.getGroupEventPassCategoryId())) {
								currentCurrent = counter.get(gpc
										.getGroupEventPassCategoryId())
										+ currentCurrent;

							}
							counter.put(gpc.getGroupEventPassCategoryId(),
									currentCurrent);

						} else {
							notEnoughTickets = true;

						}

					}
				}
				if (notEnoughTickets) {
					return null;
				}
			}

			if (CollectionUtils.isEmpty(finalPassList)) {
				return finalPassList;
			}
			for (GroupEventPass gep : finalPassList) {
				gep.setSold(true);
				gep.setUpdatedAt(Calendar.getInstance().getTime());
				gep.setPassInvalidated(false);
				gep.setSoldBy("Online");
				gep.setGroupEventInvite(gei);
				gep.setGroupEventPaymentTransaction(groupEventPaymentTransaction);
				gep = this.update(gep);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return finalPassList;
	}

	public List<GroupEventPass> assignPassesToTransaction(
			GroupEventPaymentTransaction groupEventPaymentTransaction,
			List<GroupEventPassCategory> groupEventPassCategories) {

		HashMap<String, Long> counter = new HashMap<String, Long>();
		boolean notEnoughTickets = false;
		List<GroupEventPass> finalPassList = new ArrayList<GroupEventPass>();
		try {
			GroupEventInvite gei = groupEventPaymentTransaction
					.getGroupEventInvite();
			List<GroupEventPassCategory> gpcsRaw = groupEventPassCategories;
			HashMap<String, GroupEventPassCategory> rawCategoriesMap = new HashMap<String, GroupEventPassCategory>();
			for (GroupEventPassCategory gcpR : gpcsRaw) {
				if (!StringUtils
						.equals(gcpR.getGroupEventPassCategoryId(), "0")) {
					long count = gcpR.getNumberOfPasses();
					if (rawCategoriesMap.containsKey(gcpR
							.getGroupEventPassCategoryId())) {
						gcpR = rawCategoriesMap.get(gcpR
								.getGroupEventPassCategoryId());
						gcpR.setNumberOfPasses(gcpR.getNumberOfPasses() + count);
					}
					rawCategoriesMap.put(gcpR.getGroupEventPassCategoryId(),
							gcpR);
				}
			}
			List<GroupEventPassCategory> gpcs = new ArrayList<GroupEventPassCategory>();

			for (Map.Entry<String, GroupEventPassCategory> entry : rawCategoriesMap
					.entrySet()) {
				GroupEventPassCategory value = entry.getValue();
				gpcs.add(value);

			}

			if (CollectionUtils.isNotEmpty(gpcs)) {
				for (GroupEventPassCategory gpcUser : gpcs) {
					if (gpcUser != null
							&& !StringUtils.equals(
									gpcUser.getGroupEventPassCategoryId(), "0"))

					{
						GroupEventPassCategory gpc = groupEventPassCategoryService
								.findById(gpcUser.getGroupEventPassCategoryId());
						if (this.checkAndReturnPassAvailability(gpc,
								(int) gpcUser.getNumberOfPasses()) >= gpcUser
								.getNumberOfPasses()) {
							List<GroupEventPass> geps = this
									.findUnSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
											gei.getGroupCode(),
											gei.getGroupEventCode(), gpc);
							int autoGenerationCount = geps.size()
									- (int) gpcUser.getNumberOfPasses();
							if (autoGenerationCount < 0) {
								List<GroupEventPass> autogeneratedPasses = this
										.createNewPasses(gpc, autoGenerationCount
												* -1);
								if (CollectionUtils
										.isNotEmpty(autogeneratedPasses))
									geps.addAll(autogeneratedPasses);
							}

							if (geps != null
									&& geps.size() >= gpcUser
											.getNumberOfPasses()) {
								for (int a = 0; a < gpcUser.getNumberOfPasses(); a++) {
									geps.get(a)
											.setPassPrice(gpc.getPassPrice());
									geps.get(a).setGroupEventPassCategory(gpc);
									finalPassList.add(geps.get(a));

								}
								Long currentCurrent = gpcUser
										.getNumberOfPasses();
								if (counter.containsKey(gpc
										.getGroupEventPassCategoryId())) {
									currentCurrent = counter.get(gpc
											.getGroupEventPassCategoryId())
											+ currentCurrent;

								}
								counter.put(gpc.getGroupEventPassCategoryId(),
										currentCurrent);

							}
						}

						else {
							notEnoughTickets = true;

						}

					}
				}
				if (notEnoughTickets) {
					return null;
				}
			}

			if (CollectionUtils.isEmpty(finalPassList)) {
				return finalPassList;
			}
			for (GroupEventPass gep : finalPassList) {
				gep.setSold(true);
				gep.setUpdatedAt(Calendar.getInstance().getTime());
				gep.setPassInvalidated(false);
				gep.setSoldBy("Online");
				gep.setGroupEventInvite(gei);
				if(StringUtils.isNotBlank(groupEventPaymentTransaction.getTransactionId()))
				gep.setGroupEventPaymentTransaction(groupEventPaymentTransaction);
				gep = this.insertOrUpdate(gep);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return finalPassList;
	}

	public GroupEventPass releaseGroupEventPass(GroupEventPass pass) {

		pass.setGroupEventInvite(null);
		pass.setGroupMember(null);
		pass.setGroupEventPaymentTransaction(null);
		pass.setSold(false);
		pass.setSoldBy(null);
		pass.setTableNumber(null);
		pass.setPassInvalidated(true);
		pass.setTrackingDate(null);
		try {
			this.update(pass);
			if(pass.isAutogenerated())
			{
				this.delete(pass);
			}
		} catch (Exception e) {

		}
		return pass;
	}

	public List<GroupEventPass> createNewPasses(
			GroupEventPassCategory groupEventPassCategory, int numberOfPasses) {
		List<GroupEventPass> createdList = new ArrayList<GroupEventPass>();
		for (int a = 0; a < numberOfPasses; a++) {
			Date now = Calendar.getInstance().getTime();
			GroupEventPass gep = new GroupEventPass();
			gep.setCreatedAt(now);
			gep.setCreatedBy("Random Generator");
			gep.setGroupCode(groupEventPassCategory.getGroup().getGroupCode());
			gep.setGroupEventCode(groupEventPassCategory.getGroupEvent()
					.getEventCode());
			gep.setGroupEventPassCategory(groupEventPassCategory);
			gep.setNoOfPeopleTagged(1);
			String code = CommonUtils.generateRandomString(5, 5);
			gep.setPassBarcode(code);
			if (StringUtils.isNotBlank(groupEventPassCategory.getPassPrefix()))
				code = groupEventPassCategory.getPassPrefix() + code;
			if (StringUtils.isNotBlank(groupEventPassCategory.getPassSuffix()))
				code = code + groupEventPassCategory.getPassSuffix();
			gep.setPassIdentifier(code);
			gep.setPassExpiryDate(groupEventPassCategory.getPassExpiryDate());
			gep.setPassStartDate(groupEventPassCategory.getPassStartDate());
			gep.setPassInvalidated(true);
			gep.setPassPrice(groupEventPassCategory.getPassPrice());
			gep.setAutogenerated(true);

			try {
	//			gep = this.insert(gep);
				createdList.add(gep);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return createdList;
	}

	public List<GroupEventPass> findSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
			String groupCode, String groupEventCode,
			GroupEventPassCategory groupEventPassCategory) {

		return groupEventPassesRepository
				.findSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
						groupCode, groupEventCode, groupEventPassCategory);
	}

	
	//This method may not be accurate
	@Deprecated
	public boolean checkAndReturnAvailability(
			GroupEventPassCategory groupEventPassCategory,
			int requiredNumberOfPasses) {

		List<GroupEventPass> geps = this
				.findUnSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
						groupEventPassCategory.getGroup().getGroupCode(),
						groupEventPassCategory.getGroupEvent().getEventCode(),
						groupEventPassCategory);
		int finalAffordability = requiredNumberOfPasses;
		if (requiredNumberOfPasses > 0) {
			if ((CollectionUtils.isEmpty(geps) || geps.size() < requiredNumberOfPasses)) {
				// No uploaded Passes Available, hence check if we are allowed
				// to
				// randomly generate passes.
				if (groupEventPassCategory.isRandomPassNumbers()) {

					if (groupEventPassCategory.getGroupEvent()
							.getMaxNumberOfPasses() > 0) {
						int remainingEventAffordability = groupEventPassCategory
								.getGroupEvent().getMaxNumberOfPasses()
								- this.findSoldTicketsByGroupCodeAndGroupEventCode(
										groupEventPassCategory.getGroup()
												.getGroupCode(),
										groupEventPassCategory.getGroupEvent()
												.getEventCode()).size();

						if (remainingEventAffordability > 0) {
							// Now as per above if - the event has affrdability
							// and
							// the no. of new passes required are less than the
							// availbility. Need to see if the
							// groupeventcategory
							// has a limit or free to assign any number below.
							if (groupEventPassCategory.getNumberOfPasses() > 0) {
								int soldPassesForGEPC = this
										.findSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
												groupEventPassCategory
														.getGroup()
														.getGroupCode(),
												groupEventPassCategory
														.getGroupEvent()
														.getEventCode(),
												groupEventPassCategory).size();
								if ((groupEventPassCategory.getNumberOfPasses() - soldPassesForGEPC) >= requiredNumberOfPasses
										&& (remainingEventAffordability >= requiredNumberOfPasses)) {
									int remainingForCategory = (int) groupEventPassCategory
											.getNumberOfPasses()
											- soldPassesForGEPC;
									finalAffordability = remainingForCategory < remainingEventAffordability ? remainingForCategory
											: remainingEventAffordability;
									System.out.println("Final Affordability:"
											+ finalAffordability);
									// requested passes are within the limits
									return true;
								}
							} else {
								finalAffordability = remainingEventAffordability;
								System.out.println("Final Affordability:"
										+ finalAffordability);
								return remainingEventAffordability >= requiredNumberOfPasses;
							}
						} else {
							finalAffordability = remainingEventAffordability;
							System.out.println("Final Affordability:"
									+ finalAffordability);
						}
					} else {
						if (groupEventPassCategory.getNumberOfPasses() > 0) {
							int soldPassesForGEPC = this
									.findSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
											groupEventPassCategory.getGroup()
													.getGroupCode(),
											groupEventPassCategory
													.getGroupEvent()
													.getEventCode(),
											groupEventPassCategory).size();
							finalAffordability = (int) groupEventPassCategory
									.getNumberOfPasses() - soldPassesForGEPC;
							System.out.println("Final Affordability:"
									+ finalAffordability);
							return groupEventPassCategory.getNumberOfPasses()
									- soldPassesForGEPC >= requiredNumberOfPasses;
						} else {
							System.out
									.println("Final Affordability is Same as IP:"
											+ finalAffordability);
							return true;
						}
					}
				}

			}
			finalAffordability = geps.size();
			System.out.println("Final Affordability:" + finalAffordability);
			return geps.size() >= requiredNumberOfPasses;

		}
		return false;
	}

	public int checkAndReturnPassAvailability(
			GroupEventPassCategory groupEventPassCategory,
			int requiredNumberOfPasses) {

		List<GroupEventPass> geps = this
				.findUnSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
						groupEventPassCategory.getGroup().getGroupCode(),
						groupEventPassCategory.getGroupEvent().getEventCode(),
						groupEventPassCategory);
		int finalAffordability = geps.size();
		if (requiredNumberOfPasses > 0) {
			if ((CollectionUtils.isEmpty(geps) || geps.size() < requiredNumberOfPasses)) {
				// No uploaded Passes Available, hence check if we are allowed
				// to
				// randomly generate passes.
				finalAffordability = geps.size();
				if (groupEventPassCategory.isRandomPassNumbers()) {

					if (groupEventPassCategory.getGroupEvent()
							.getMaxNumberOfPasses() > 0) {
						int remainingEventAffordability = groupEventPassCategory
								.getGroupEvent().getMaxNumberOfPasses()
								- this.findSoldTicketsByGroupCodeAndGroupEventCode(
										groupEventPassCategory.getGroup()
												.getGroupCode(),
										groupEventPassCategory.getGroupEvent()
												.getEventCode()).size();

						if (remainingEventAffordability > 0) {
							// Now as per above if - the event has affrdability
							// and
							// the no. of new passes required are less than the
							// availbility. Need to see if the
							// groupeventcategory
							// has a limit or free to assign any number below.
							if (groupEventPassCategory.getNumberOfPasses() > 0) {
								int soldPassesForGEPC = this
										.findSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
												groupEventPassCategory
														.getGroup()
														.getGroupCode(),
												groupEventPassCategory
														.getGroupEvent()
														.getEventCode(),
												groupEventPassCategory).size();
								if ((groupEventPassCategory.getNumberOfPasses() - soldPassesForGEPC) >= requiredNumberOfPasses
										&& (remainingEventAffordability >= requiredNumberOfPasses)) {
									int remainingForCategory = (int) groupEventPassCategory
											.getNumberOfPasses()
											- soldPassesForGEPC;
									finalAffordability = remainingForCategory < remainingEventAffordability ? remainingForCategory
											: remainingEventAffordability;
									System.out.println("Final Affordability:"
											+ finalAffordability);
									// requested passes are within the limits
									// return true;
								}
								else 
								{
									int remainingForCategory = (int) groupEventPassCategory
											.getNumberOfPasses()
											- soldPassesForGEPC;
									finalAffordability = remainingForCategory < remainingEventAffordability ? remainingForCategory
											: remainingEventAffordability;
									System.out.println("Final Affordability:"
											+ finalAffordability);
								}
							} else {
								finalAffordability = remainingEventAffordability;
								System.out.println("Final Affordability:"
										+ finalAffordability);
								// return remainingEventAffordability >=
								// requiredNumberOfPasses;
							}
						} else {
							finalAffordability = remainingEventAffordability;
							System.out.println("Final Affordability:"
									+ finalAffordability);
						}
					} else {
						if (groupEventPassCategory.getNumberOfPasses() > 0) {
							int soldPassesForGEPC = this
									.findSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(
											groupEventPassCategory.getGroup()
													.getGroupCode(),
											groupEventPassCategory
													.getGroupEvent()
													.getEventCode(),
											groupEventPassCategory).size();
							finalAffordability = (int) groupEventPassCategory
									.getNumberOfPasses() - soldPassesForGEPC;
							System.out.println("Final Affordability:"
									+ finalAffordability);
							// return groupEventPassCategory.getNumberOfPasses()
							// - soldPassesForGEPC >= requiredNumberOfPasses;
						} else {
							System.out
									.println("Final Affordability is Same as IP:"
											+ finalAffordability);
							// return true;
						}
					}
				}
				System.out.println("Final Affordability Returned:"
						+ finalAffordability);
				return finalAffordability;
			}
			
			
			else {
			
			int remainingEventAffordability = groupEventPassCategory
					.getGroupEvent().getMaxNumberOfPasses()
					- this.findSoldTicketsByGroupCodeAndGroupEventCode(
							groupEventPassCategory.getGroup()
									.getGroupCode(),
							groupEventPassCategory.getGroupEvent()
									.getEventCode()).size();
			int remainingPassCategoryAffordability = (int)groupEventPassCategory.getNumberOfPasses() - this.findSoldTicketsByGroupCodeAndGroupEventCodeAndGroupEventPassCategory(groupEventPassCategory.getGroup()
									.getGroupCode(),
							groupEventPassCategory.getGroupEvent()
									.getEventCode(), groupEventPassCategory).size();
			
			if(groupEventPassCategory.getGroupEvent()
							.getMaxNumberOfPasses() > 0 && groupEventPassCategory.getNumberOfPasses()>0)
			{
				finalAffordability = remainingEventAffordability<remainingPassCategoryAffordability?remainingEventAffordability:remainingPassCategoryAffordability;
				System.out.println("Final Affordability:" + finalAffordability);
				 
			}
			else if(groupEventPassCategory.getGroupEvent()
							.getMaxNumberOfPasses() > 0)
			{
				finalAffordability =  remainingEventAffordability;
				System.out.println("Final Affordability:" + finalAffordability);
			}
			else if(groupEventPassCategory.getNumberOfPasses()>0)
			{
				finalAffordability =  remainingPassCategoryAffordability;
				System.out.println("Final Affordability:" + finalAffordability);
			}
			else 
			{
				finalAffordability = geps.size();
				System.out.println("Final Affordability:" + finalAffordability);			
			}
			}

			// return geps.size() >= requiredNumberOfPasses;

		}
		return finalAffordability;
	}

	public List<GroupEventPass> findApprovedPassesByGroupEventInvite(
			GroupEventInvite groupEventInvite) {

		return groupEventPassesRepository.findApprovedPassesByGroupEventInvite(groupEventInvite);
	}

	
	public int checkPassAttendance(String groupEventPassCategoryId) {
		// TODO Auto-generated method stub
		return groupEventPassesRepository.checkPassAttendance(groupEventPassCategoryId);
	}

	public int checkTotalEventAttendance(String groupEventCode) {
		// TODO Auto-generated method stub
		return groupEventPassesRepository.checkTotalEventAttendance(groupEventCode);
	}
}
