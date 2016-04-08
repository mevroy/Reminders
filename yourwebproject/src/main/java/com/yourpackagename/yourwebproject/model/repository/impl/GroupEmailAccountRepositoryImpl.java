/**
 * 
 */
package com.yourpackagename.yourwebproject.model.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yourpackagename.framework.data.BaseHibernateJpaRepository;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailAccount;
import com.yourpackagename.yourwebproject.model.repository.GroupEmailAccountRepository;

/**
 * @author mevan.d.souza
 *
 */
@Repository
public class GroupEmailAccountRepositoryImpl extends
		BaseHibernateJpaRepository<GroupEmailAccount, Long>  implements GroupEmailAccountRepository{

	public GroupEmailAccount findByEmailAccountCode(String emailAccountCode) {
		return (GroupEmailAccount)sessionFactory.getCurrentSession().createQuery("from GroupEmailAccount gea where gea.emailAccountCode = ?").setString(0,
				emailAccountCode).uniqueResult();
	}

	public List<GroupEmailAccount> findByGroupCode(String groupCode) {
		return (List<GroupEmailAccount>)sessionFactory.getCurrentSession().createQuery("from GroupEmailAccount gea where gea.groupCode = ?").setString(0,
				groupCode).list();
	}

}
