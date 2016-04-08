/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupDependents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupDependentsRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupDependentsRepositoryImpl extends
		BaseHibernateJpaRepository<GroupDependents, String> implements GroupDependentsRepository {

	public List<GroupDependents> findByGroupMember(GroupMember groupMember) {
        return (List<GroupDependents>) sessionFactory.getCurrentSession().createQuery("from GroupDependents g where g.serialNumber = ?").setString(0,
                groupMember.getSerialNumber()).list();
	}

	public List<GroupDependents> findByGroupMemberSerialNumber(
			String serialNumber) {
        return (List<GroupDependents>) sessionFactory.getCurrentSession().createQuery("from GroupDependents g where g.serialNumber = ?").setString(0,
                serialNumber).list();
	}

}
