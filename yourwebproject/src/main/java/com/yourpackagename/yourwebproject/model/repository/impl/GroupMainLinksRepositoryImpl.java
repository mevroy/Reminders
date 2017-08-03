/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;


import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupMainLink;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;
import com.yourpackagename.yourwebproject.model.repository.GroupMainLinksRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupMainLinksRepositoryImpl extends BaseHibernateJpaRepository<GroupMainLink, Long> implements GroupMainLinksRepository{


	public List<GroupMainLink> findByGroupCodeAndUser(String groupCode,
			User user, boolean enableFilter) {

		return (List<GroupMainLink>)sessionFactory.getCurrentSession().createQuery("select distinct(g) from GroupMainLink g, GroupSubLink gsl, GroupLinkAccess gla , GroupLinkAccessRole glar where g.disabled = 0 and gsl.groupMainLink = g and gsl.disabled = 0 and gla.groupSubLink = gsl and ((gla.expiryDate is null or date(gla.expiryDate) >= NOW()) and (gla.startDate is null or date(gla.startDate) <= NOW())) and gla.group.groupCode = ? and glar.groupLinkAccess = gla and glar.role= ? and ((glar.expiryDate is null or date(glar.expiryDate) >= NOW()) and (glar.startDate is null or date(glar.startDate) <= NOW()))").setParameter(0, groupCode).setParameter(1, user.getGroupUserRoles().get(0).getRole()).list();
		//return (List<GroupMainLink>)sessionFactory.getCurrentSession().createQuery("from GroupMainLink g left join g,groupSubLinks gsl join gsl.groupLinkAccess gla join gla.groupLinkAccessRoles glar where gla.group.groupCode = :groupCode and ((gla.expiryDate is null or date(gla.expiryDate) >= CURDATE()) and (gla.startDate is null or date(gla.startDate) <= CURDATE())) and glar.role = :roleType and ((glar.expiryDate is null or date(glar.expiryDate) >= CURDATE()) and (glar.startDate is null or date(glar.startDate) <= CURDATE()))").setParameter("roleType", user.getGroupUserRoles().get(0).getRole().toString()).setParameter("groupCode", groupCode).list();
	}

	public List<GroupMainLink> findByGroupCodeAndRoles(String groupCode,
			List<Role> roles) {
		return (List<GroupMainLink>)sessionFactory.getCurrentSession().createQuery("from GroupMainLink g ").list();
	}

	public List<GroupMainLink> findAll(boolean includeDisabled) {
		// TODO Auto-generated method stub
		return (List<GroupMainLink>)sessionFactory.getCurrentSession().createQuery("from GroupMainLink g "+(!includeDisabled?" where disabled = 0":"")).list();
	}

}
