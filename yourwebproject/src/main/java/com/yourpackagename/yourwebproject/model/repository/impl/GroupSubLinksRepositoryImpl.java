/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;


import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupMainLink;
import com.yourpackagename.yourwebproject.model.entity.GroupSubLink;
import com.yourpackagename.yourwebproject.model.repository.GroupSubLinksRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupSubLinksRepositoryImpl extends BaseHibernateJpaRepository<GroupSubLink, String> implements GroupSubLinksRepository{


	public List<GroupSubLink> findByGroupMainLink(GroupMainLink groupMainLink,
			boolean includeDisabled) {

		return (List<GroupSubLink>)sessionFactory.getCurrentSession().createQuery("from GroupSubLink g where g.groupMainLink = :groupMainLink "+(includeDisabled?"":" and g.disabled = 0")).setParameter("groupMainLink", groupMainLink).list();
	}

	public List<GroupSubLink> findByURL(String url, boolean includeDisabled) {
		return (List<GroupSubLink>)sessionFactory.getCurrentSession().createQuery("from GroupSubLink g where g.linkHref = :url "+(includeDisabled?"":" and g.disabled = 0")).setParameter("url", url).list();
	}

}
