package com.yourpackagename.yourwebproject.model.entity;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.yourpackagename.framework.data.JpaEntity;
import com.yourpackagename.yourwebproject.common.Key;
import com.yourpackagename.yourwebproject.model.entity.embedded.Address;
import com.yourpackagename.yourwebproject.model.entity.enums.Role;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.ParamDef;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User entity
 *
 * @author: Y Kamesh Rao
 * @created: 3/22/12 9:02 AM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
@Entity
@FilterDef(name="filterUserRoles", 
parameters=@ParamDef( name="groupCode", type="string" ) )
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@XmlRootElement(name = Key.user)
public class User extends JpaEntity<Long> implements Serializable {
    private String userName;
    private String email;
    private String mobile;
    private @Transient Role loggedInRole;

    private @XStreamOmitField String passWord;
    private @XStreamOmitField Integer loginCount;
    private @XStreamOmitField Date currentLoginAt;
    private @XStreamOmitField Date lastLoginAt;
    private @XStreamOmitField String currentLoginIp;
    private @XStreamOmitField String lastLoginIp;
   
	@OneToMany(cascade = CascadeType.ALL, targetEntity = GroupUserRole.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Filter(
			name = "filterUserRoles",
			condition="group_code = :groupCode"
		)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private List<GroupUserRole> groupUserRoles;
	
    @Column
    private boolean adminUser;
    

    @Column
    private Date loginExpiryDate;


	@Column(name = "serialNumber")
    private String serialNumber;

    
/*	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "serialNumber", referencedColumnName = "serialNumber")
	private List<Feedback> feedbacks;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "serialNumber" , referencedColumnName="serialNumber")
	private GroupMember groupMember;*/
	
    @XmlElement @NotNull @Length(min = 6, max = 15) @NotBlank @Column
    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    @XmlElement @NotNull @Email @NotBlank @Column
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    @XmlElement @NotNull @NotBlank @Column
    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


/*    @XmlElement(name = Key.role) @NotNull @Column
    public Role getRole() {
        return role;
    }


    public void setRole(Role role) {
        this.role = role;
    }
*/

    /**
	 * @return the loggedInRole
	 */
	public Role getLoggedInRole() {
		if(CollectionUtils.isNotEmpty(this.groupUserRoles))
		{
			return this.groupUserRoles.get(0).getRole();
		}
		return Role.ANONYMOUS;
	}


	@JsonIgnore @NotNull @Column
    public String getPassWord() {
        return passWord;
    }


    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    @JsonIgnore @Column
    public Integer getLoginCount() {
        return loginCount;
    }


    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }


    @JsonIgnore @Column
    public Date getCurrentLoginAt() {
        return currentLoginAt;
    }


    public void setCurrentLoginAt(Date currentLoginAt) {
        this.currentLoginAt = currentLoginAt;
    }


    @JsonIgnore @Column
    public Date getLastLoginAt() {
        return lastLoginAt;
    }


    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }


    @JsonIgnore @Column
    public String getCurrentLoginIp() {
        return currentLoginIp;
    }


    public void setCurrentLoginIp(String currentLoginIp) {
        this.currentLoginIp = currentLoginIp;
    }


    @JsonIgnore @Column
    public String getLastLoginIp() {
        return lastLoginIp;
    }


    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }




	/**
     * Method to create the SHA hash of the password before storing
     *
     * @param pass
     *
     * @return SHA hash digest of the password
     */
    public static synchronized String hashPassword(String pass) {
        return org.apache.commons.codec.digest.DigestUtils.shaHex(pass);
    }


 /*   *//**
	 * @return the feedbacks
	 *//*
	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}


	*//**
	 * @param feedbacks the feedbacks to set
	 *//*
	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	*/

/*	*//**
	 * @return the groupMember
	 *//*
	public GroupMember getGroupMember() {
		return groupMember;
	}


	*//**
	 * @param groupMember the groupMember to set
	 *//*
	public void setGroupMember(GroupMember groupMember) {
		this.groupMember = groupMember;
	}

*/
	/**
	 * @return the loginExpiryDate
	 */
	public Date getLoginExpiryDate() {
		return loginExpiryDate;
	}


	/**
	 * @param loginExpiryDate the loginExpiryDate to set
	 */
	public void setLoginExpiryDate(Date loginExpiryDate) {
		this.loginExpiryDate = loginExpiryDate;
	}


	/**
	 * @return the adminUser
	 */
	public boolean isAdminUser() {
		return adminUser;
	}


	/**
	 * @param adminUser the adminUser to set
	 */
	public void setAdminUser(boolean adminUser) {
		this.adminUser = adminUser;
	}


	/**
	 * @return the groupUserRoles
	 */
	public List<GroupUserRole> getGroupUserRoles() {
		return groupUserRoles;
	}


	/**
	 * @param groupUserRoles the groupUserRoles to set
	 */
	public void setGroupUserRoles(List<GroupUserRole> groupUserRoles) {
		this.groupUserRoles = groupUserRoles;
	}


	@Override public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", role=" + this.getLoggedInRole() +
                ", loginCount=" + loginCount +
                ", currentLoginAt=" + currentLoginAt +
                ", lastLoginAt=" + lastLoginAt +
                ", currentLoginIp='" + currentLoginIp + '\'' +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", roles='" + groupUserRoles + '\'' +
                '}';
    }
}
