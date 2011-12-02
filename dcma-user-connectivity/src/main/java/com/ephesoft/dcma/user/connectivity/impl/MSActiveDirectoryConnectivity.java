/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2011 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2011 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2011 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

package com.ephesoft.dcma.user.connectivity.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ephesoft.dcma.user.connectivity.UserConnectivity;
import com.ephesoft.dcma.user.connectivity.constant.UserConnectivityConstant;

/**
 * This class connect to the Active Directory server and fetching the result from the Active Directory server.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.user.connectivity.UserConnectivity
 * 
 */
public class MSActiveDirectoryConnectivity implements UserConnectivity, UserConnectivityConstant {

	/**
	 * Used for handling logs.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MSActiveDirectoryConnectivity.class);

	/**
	 * This value get from the user.msactivedirectory_url in user-connectivity.properties file.
	 */
	private String msActiveDirectoryURL;

	/**
	 * This value get from the user.msactivedirectory_config in user-connectivity.properties file.
	 */
	private String msActiveDirectoryConfig;

	/**
	 * This value get from the user.msactivedirectory_container_name in user-connectivity.properties file.
	 */
	private String msActiveDirectoryContextPath;

	/**
	 * This value get from the user.msactivedirectory_domain_component_name in user-connectivity.properties file.
	 */
	private String msActiveDirectoryDomainName;

	/**
	 * This value get from the user.msactivedirectory_domain_component_organization in user-connectivity.properties file.
	 */
	private String msActiveDirectoryDomainOrganization;

	/**
	 * This value get from the user.msactivedirectory_user_name in user-connectivity.properties file.
	 */
	private String msActiveDirectoryUserName;

	/**
	 * This value get from the user.msactivedirectory_password in user-connectivity.properties file.
	 */
	private String msActiveDirectoryPassword;

	/**
	 * This value get from the user.msactivedirectory_group_search_filter in user-connectivity.properties file.
	 */
	private String msActiveDirectoryGroupSearchFilter;

	/**
	 * @return the msActiveDirectoryUserName
	 */
	public String getMsActiveDirectoryUserName() {
		return msActiveDirectoryUserName;
	}

	/**
	 * @param msActiveDirectoryUserName the msActiveDirectoryUserName to set
	 */
	public void setMsActiveDirectoryUserName(final String msActiveDirectoryUserName) {
		this.msActiveDirectoryUserName = msActiveDirectoryUserName;
	}

	/**
	 * @return the msActiveDirectoryPassword
	 */
	public String getMsActiveDirectoryPassword() {
		return msActiveDirectoryPassword;
	}

	/**
	 * @param msActiveDirectoryPassword the msActiveDirectoryPassword to set
	 */
	public void setMsActiveDirectoryPassword(final String msActiveDirectoryPassword) {
		this.msActiveDirectoryPassword = msActiveDirectoryPassword;
	}

	/**
	 * @return the msActiveDirectoryURL
	 */
	public String getMsActiveDirectoryURL() {
		return msActiveDirectoryURL;
	}

	/**
	 * @param msActiveDirectoryURL the msActiveDirectoryURL to set
	 */
	public void setMsActiveDirectoryURL(final String msActiveDirectoryURL) {
		this.msActiveDirectoryURL = msActiveDirectoryURL;
	}

	/**
	 * @return the msActiveDirectoryConfig
	 */
	public String getMsActiveDirectoryConfig() {
		return msActiveDirectoryConfig;
	}

	/**
	 * @param msActiveDirectoryConfig the msActiveDirectoryConfig to set
	 */
	public void setMsActiveDirectoryConfig(final String msActiveDirectoryConfig) {
		this.msActiveDirectoryConfig = msActiveDirectoryConfig;
	}

	/**
	 * @return the msActiveDirectoryDomainName
	 */
	public String getMsActiveDirectoryDomainName() {
		return msActiveDirectoryDomainName;
	}

	/**
	 * @param msActiveDirectoryDomainName the msActiveDirectoryDomainName to set
	 */
	public void setMsActiveDirectoryDomainName(final String msActiveDirectoryDomainName) {
		this.msActiveDirectoryDomainName = msActiveDirectoryDomainName;
	}

	/**
	 * @return the msActiveDirectoryDomainOrganization
	 */
	public String getMsActiveDirectoryDomainOrganization() {
		return msActiveDirectoryDomainOrganization;
	}

	/**
	 * @param msActiveDirectoryDomainOrganization the msActiveDirectoryDomainOrganization to set
	 */
	public void setMsActiveDirectoryDomainOrganization(final String msActiveDirectoryDomainOrganization) {
		this.msActiveDirectoryDomainOrganization = msActiveDirectoryDomainOrganization;
	}

	/**
	 * @return the msActiveDirectoryContextPath
	 */
	public String getMsActiveDirectoryContextPath() {
		return msActiveDirectoryContextPath;
	}

	/**
	 * @param msActiveDirectoryContextPath the msActiveDirectoryContextPath to set
	 */
	public void setMsActiveDirectoryContextPath(String msActiveDirectoryContextPath) {
		this.msActiveDirectoryContextPath = msActiveDirectoryContextPath;
	}

	/**
	 * @return the msActiveDirectoryGroupSearchFilter
	 */
	public String getMsActiveDirectoryGroupSearchFilter() {
		return msActiveDirectoryGroupSearchFilter;
	}

	/**
	 * @param msActiveDirectoryGroupSearchFilter the msActiveDirectoryGroupSearchFilter to set
	 */
	public void setMsActiveDirectoryGroupSearchFilter(String msActiveDirectoryGroupSearchFilter) {
		this.msActiveDirectoryGroupSearchFilter = msActiveDirectoryGroupSearchFilter;
	}

	/**
	 * This method is used to connect the Active Directory and fetching the list of group object from the active directory.
	 * 
	 * @return Set<String> if connected and group is found else return null
	 */
	@Override
	public Set<String> getAllGroups() {
		Set<String> allGroups = null;
		try {
			LOG.info("======Inside MSActiveDirectoryConnectivity======");
			LOG.info("Fetching all available groups from the active directory");
			allGroups = this.fetchActiveDirectoryList(UserConnectivityConstant.MSACTIVEDIRECTORY_GROUP);
			LOG.info("Ending fetching list from Active Directory");
		} catch (Exception e) {
			LOG.error("Error in fetching all groups " + e.getMessage(), e);
		}
		return allGroups;
	}

	/**
	 * This method is used to connect to the active directory and used to return the Set of string of result fetch in accordance of the
	 * argument passed to it.
	 * 
	 * @param {@link String}
	 * @return Set<String> if connected and result is found else return null
	 */
	private Set<String> fetchActiveDirectoryList(final String name) {
		Set<String> resultList = new HashSet<String>();

		boolean isValid = isValidData();

		if (isValid) {

			Hashtable<Object, Object> env = new Hashtable<Object, Object>(); // NOPMD . Required to work with Hashtable for Active
			// Directory
			env.put(Context.INITIAL_CONTEXT_FACTORY, msActiveDirectoryConfig);
			env.put(Context.PROVIDER_URL, msActiveDirectoryURL);
			env.put(Context.SECURITY_PRINCIPAL, msActiveDirectoryUserName);
			env.put(Context.SECURITY_CREDENTIALS, msActiveDirectoryPassword);

			DirContext dctx = null;
			List<NamingEnumeration<?>> results = null;
			dctx = createDirectoryConnection(env);

			if (dctx != null) {
				LOG.info("Start Fetching result set from Active Directory");
				results = getResultSet(name, dctx);

				if (results != null) {
					for (NamingEnumeration<?> resultContainer : results) {
						resultSetValues(resultList, resultContainer);
					}
				} else {
					LOG.error("Results found from Active Directory is  null or empty.");
				}
				try {
					if (dctx != null) {
						LOG.info("Closing directory context of Active Directory");
						dctx.close();
					}
					if (results != null && !results.isEmpty()) {
						for (NamingEnumeration<?> resultContainer : results) {
							LOG.info("Closing result set of Active Directory");
							resultContainer.close();
						}
					}
				} catch (NamingException ne) {
					LOG.error(ne.getMessage(), ne);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			} else {
				LOG.error("Invalid directory context of Active Directory.");
			}
		}
		return resultList;
	}

	/**
	 * This method is used to create the connection to the directory server
	 * 
	 * @param environment
	 * @param directory
	 * @return
	 */
	private DirContext createDirectoryConnection(Hashtable<Object, Object> environment) { // NOPMD 
		// Hashtable for Active Directory
		DirContext directory = null;
		try {
			directory = new InitialDirContext(environment);
		} catch (NamingException ne) {
			LOG.error(ne.getMessage(), ne);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return directory;
	}

	/**
	 * This method is used to get the result from the msActiveDirectory
	 * 
	 * @param name
	 * @param dctx
	 * @return
	 */
	private List<NamingEnumeration<?>> getResultSet(final String name, DirContext dctx) {
		SearchControls searchControl = new SearchControls();
		String[] attributeFilter = {UserConnectivityConstant.COMMON_NAME};
		searchControl.setReturningAttributes(attributeFilter);
		searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String filter = UserConnectivityConstant.EMPTY_STRING;
		if (name == UserConnectivityConstant.MSACTIVEDIRECTORY_GROUP && msActiveDirectoryGroupSearchFilter != null && !msActiveDirectoryGroupSearchFilter.isEmpty()) {
			StringBuffer filterBuffer = new StringBuffer(UserConnectivityConstant.MSACTIVEDIRECTORY_START_FILTER);
			filterBuffer.append(UserConnectivityConstant.MSACTIVEDIRECTORY_AMP_SYMBOL);
			filterBuffer.append(UserConnectivityConstant.MSACTIVEDIRECTORY_START_FILTER);
			filterBuffer.append(UserConnectivityConstant.MSACTIVEDIRECTORY_OBJECT_CLASS);
			filterBuffer.append(UserConnectivityConstant.MSACTIVEDIRECTORY_EQUAL_SYMBOL);
			filterBuffer.append(name);
			filterBuffer.append(UserConnectivityConstant.MSACTIVEDIRECTORY_END_FILTER);
			filterBuffer.append(msActiveDirectoryGroupSearchFilter);
			filterBuffer.append(UserConnectivityConstant.MSACTIVEDIRECTORY_END_FILTER);
			filter = filterBuffer.toString();
		} else {
			StringBuffer filterBuffer = new StringBuffer(UserConnectivityConstant.MSACTIVEDIRECTORY_START_FILTER);
			filterBuffer.append(UserConnectivityConstant.MSACTIVEDIRECTORY_OBJECT_CLASS);
			filterBuffer.append(UserConnectivityConstant.MSACTIVEDIRECTORY_EQUAL_SYMBOL);
			filterBuffer.append(name);
			filterBuffer.append(UserConnectivityConstant.MSACTIVEDIRECTORY_END_FILTER);
			filter = filterBuffer.toString();
		}
		LOG.info("Added filter to Active Directory :" + filter);
		List<NamingEnumeration<?>> results = new ArrayList<NamingEnumeration<?>>();
		String[] msActiveDirectoryContextPathName = msActiveDirectoryContextPath.split(DOUBLE_SEMICOLON_DELIMITER);
		for (String msActiveDirectoryFullPath : msActiveDirectoryContextPathName) {
			String paramName = null;
			try {
				StringBuffer stringBuffer = new StringBuffer(msActiveDirectoryFullPath);
				stringBuffer.append(UserConnectivityConstant.COMMA_SYMBOL);
				stringBuffer.append(UserConnectivityConstant.DOMAIN_COMPONENT);
				stringBuffer.append(UserConnectivityConstant.EQUAL_SYMBOL);
				stringBuffer.append(msActiveDirectoryDomainName);
				stringBuffer.append(UserConnectivityConstant.COMMA_SYMBOL);
				stringBuffer.append(UserConnectivityConstant.DOMAIN_COMPONENT);
				stringBuffer.append(UserConnectivityConstant.EQUAL_SYMBOL);
				stringBuffer.append(msActiveDirectoryDomainOrganization);
				paramName = stringBuffer.toString();
				LOG.info("Context Path for Active Directory :" + paramName);
				results.add(dctx.search(paramName, filter, searchControl));
			} catch (NamingException ne) {
				LOG.error(ne.getMessage(), ne);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return results;
	}

	/**
	 * This method manipulates the result into string to be added in the resultset
	 * 
	 * @param resultList
	 * @param results
	 */
	private void resultSetValues(Set<String> resultList, NamingEnumeration<?> results) {
		try {
			while (results.hasMore()) {
				SearchResult searchResult = null;
				try {
					searchResult = (SearchResult) results.next();
				} catch (NamingException e) {
					LOG.error(e.getMessage(), e);
				}

				if (null != searchResult) {
					LOG.info("Group found of Active Directory is :" + searchResult);
					String result = searchResult.toString().split(UserConnectivityConstant.COLON_SYMBOL)[0];
					if (null != result && !result.isEmpty()) {
						String[] cnConnectionNameArr = result.split(UserConnectivityConstant.COLON_SYMBOL);
						if (cnConnectionNameArr != null && cnConnectionNameArr.length >= 1) {
							String userName = cnConnectionNameArr[0];
							String[] userNameArr = userName.split(UserConnectivityConstant.EQUAL_SYMBOL);
							if (userNameArr != null && userNameArr.length >= 2) {
								resultList.add(userNameArr[1]);
							}
						}
					}
				} else {
					LOG.error("No groups found in the active directory.");
				}
			}
		} catch (NamingException ne) {
			LOG.error("No result found" + ne.getMessage(), ne);
		}
	}

	/**
	 * Check the user-connectivity.properties are valid or not
	 * 
	 * @param isValid
	 * @return true if valid else false
	 */
	private boolean isValidData() {
		boolean check = true;
		if (null == msActiveDirectoryConfig || msActiveDirectoryConfig.isEmpty()) {
			LOG.error("msactivedirectoryConfig not found.");
			if (check) {
				check = false;
			}
		}

		if (null == msActiveDirectoryURL || msActiveDirectoryURL.isEmpty()) {
			LOG.error("msactivedirectoryUrl not found.");
			if (check) {
				check = false;
			}
		}

		if (null == msActiveDirectoryDomainName || msActiveDirectoryDomainName.isEmpty()) {
			LOG.error("msactivedirectoryDomainName is null or empty.");
			if (check) {
				check = false;
			}
		}

		if (null == msActiveDirectoryDomainOrganization || msActiveDirectoryDomainOrganization.isEmpty()) {
			LOG.error("msactivedirectoryDomainOrganization is null or empty.");
			if (check) {
				check = false;
			}
		}

		if (null == msActiveDirectoryContextPath || msActiveDirectoryContextPath.isEmpty()) {
			LOG.error("msActiveDirectoryContainer is null or empty.");
			if (check) {
				check = false;
			}
		}

		if (null == msActiveDirectoryUserName || msActiveDirectoryUserName.isEmpty()) {
			LOG.error("msActiveDirectoryUserName is null or empty.");
			if (check) {
				check = false;
			}
		}

		if (null == msActiveDirectoryPassword || msActiveDirectoryPassword.isEmpty()) {
			LOG.error("msActiveDirectoryPassword is null or empty.");
			if (check) {
				check = false;
			}
		}
		return check;
	}

	/**
	 * This method is used to connect the Active Directory and fetching the list of user object from the active directory.
	 * 
	 * @return Set<String> if connected and user is found else return null
	 */
	@Override
	public Set<String> getAllUser() {
		Set<String> allUser = null;
		try {
			allUser = fetchActiveDirectoryList(UserConnectivityConstant.MSACTIVEDIRECTORY_USER);
		} catch (Exception e) {
			LOG.error("Error in fetching all users " + e.getMessage(), e);
		}
		return allUser;
	}
}
