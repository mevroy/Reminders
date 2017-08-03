/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPaymentType;
import com.yourpackagename.yourwebproject.model.repository.GroupEventPaymentTypeRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupEventPaymentTypeRepositoryImpl extends BaseHibernateJpaRepository<GroupEventPaymentType, Long> implements GroupEventPaymentTypeRepository{

	public List<GroupEventPaymentType> findByGroup(String groupCode, boolean includeExpired) {

		 return (List<GroupEventPaymentType>)sessionFactory.getCurrentSession().createQuery("select gept from GroupEventPaymentType gept where gept.group.groupCode = :groupCode "+(includeExpired?"":" and ((gept.expiryDate is null or (gept.expiryDate) >= NOW()) and (gept.startDate is null or (gept.startDate) <= NOW()))")).setParameter("groupCode", groupCode).list();
	}


}
