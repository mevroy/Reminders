/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.repository.GroupMembersRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupMembersRepositoryImpl extends BaseHibernateJpaRepository<GroupMember, String> implements GroupMembersRepository{

    @SuppressWarnings("unchecked")
	public List<GroupMember> findByGroupCodeAndMemberCategoryCode(String groupCode, String membercategoryCode) {
        return (List<GroupMember>) sessionFactory.getCurrentSession().createQuery("from GroupMember g where g.groupCode = ? and g.memberCategoryCode=?").setString(0,
                groupCode).setString(1, membercategoryCode).list();
    }

    @SuppressWarnings("unchecked")
	public List<GroupMember> findByGroupCode(String groupCode) {
        return (List<GroupMember>) sessionFactory.getCurrentSession().createQuery("from GroupMember g where g.groupCode = ?").setString(0,
                groupCode).list();
    }

	public List<GroupMember> executeGenericQuery(String hibernateQuery) {
		return (List<GroupMember>) sessionFactory.getCurrentSession().createQuery(hibernateQuery).list();
	}

	public List<GroupMember> findByGroupCodeAndMemberCategoryCodeNotExitingInGroupEventInvite(
			String groupCode, String membercategoryCode, String groupEventCode) {
        return (List<GroupMember>) sessionFactory.getCurrentSession().createQuery("from GroupMember g where g.groupCode = ? and g.memberCategoryCode=? and not exists (select gei.groupEventInviteId from GroupEventInvite gei where gei.groupMember.serialNumber = g.serialNumber and g.groupCode = gei.groupCode and g.memberCategoryCode=gei.memberCategoryCode and gei.groupEventCode = ?)").setString(0,
                groupCode).setString(1, membercategoryCode).setString(2, groupEventCode).list();

	}
	
	public List<GroupMember> findByGroupCodeNotExitingInGroupEventInvite(
			String groupCode, String groupEventCode) {
        return (List<GroupMember>) sessionFactory.getCurrentSession().createQuery("from GroupMember g where g.groupCode = ? and not exists (select gei.groupEventInviteId from GroupEventInvite gei where gei.groupMember.serialNumber = g.serialNumber and g.groupCode = gei.groupCode and g.memberCategoryCode=gei.memberCategoryCode and gei.groupEventCode=?)").setString(0,
                groupCode).setString(1, groupEventCode).list();

	}

	public GroupMember findbyMembershipIdentifier(String membershipIdentifier) {

		Criteria c = this.createCriteria();
		c.add(Restrictions.eq("membershipIdentifier", membershipIdentifier));
		//return (GroupMember)sessionFactory.getCurrentSession().createQuery("from GroupMember g where g.membershipIdentifier = ?").setString(0, membershipIdentifier).uniqueResult();
		return (GroupMember)c.uniqueResult();
	}

	public List<GroupMember> findByAssociatedEmailForGroupMember(String emailAddress, String groupCode) {
		return (List<GroupMember>) sessionFactory.getCurrentSession().createQuery("select distinct g from GroupMember g, GroupDependents gd where g.groupCode = ? and ((g.primaryEmail like :emailAddress or g.otherEmail like :emailAddress) or (g.serialNumber=gd.groupMember.serialNumber and gd.email like :emailAddress))").setString(0,
                groupCode).setString("emailAddress", "%"+emailAddress+"%").list();
	}

}
