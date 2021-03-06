/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2012 Ephesoft Inc. 
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

package com.ephesoft.dcma.gwt.reporting.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ephesoft.dcma.core.DCMAException;
import com.ephesoft.dcma.core.common.Order;
import com.ephesoft.dcma.core.common.WorkflowType;
import com.ephesoft.dcma.da.domain.BatchClass;
import com.ephesoft.dcma.da.service.BatchClassService;
import com.ephesoft.dcma.gwt.core.server.DCMARemoteServiceServlet;
import com.ephesoft.dcma.gwt.core.shared.ReportDTO;
import com.ephesoft.dcma.gwt.core.shared.exception.GWTException;
import com.ephesoft.dcma.gwt.reporting.client.ReportingModelService;
import com.ephesoft.dcma.gwt.reporting.client.i18n.ReportingConstants;
import com.ephesoft.dcma.performance.reporting.domain.ReportDisplayData;
import com.ephesoft.dcma.performance.reporting.service.ReportDataService;
import com.ephesoft.dcma.util.ApplicationConfigProperties;

/**
 * The server side implementation for the calls coming from client side.
 * 
 * @author Ephesoft
 * 
 */
public class ReportingServiceImpl extends DCMARemoteServiceServlet implements ReportingModelService {

	/**
	 *Serial version id.
	 */
	private static final long serialVersionUID = 263606265567100312L;

	/**
	 * The path where the ant is placed.
	 */
	public static final String ANT_HOME_PATH = "ANT_HOME_PATH";

	@Override
	public List<Integer> getSystemStatistics(Date startDate, Date endDate,List<String> batchClassIdList) throws GWTException {
		List<Integer> statistics = new ArrayList<Integer>();
		ReportDataService reportDataService = this.getSingleBeanOfType(ReportDataService.class);
		try {
			statistics = reportDataService.getSystemStatistics(endDate, startDate,batchClassIdList);
		} catch (DCMAException dcmae) {
			throw new GWTException("Exception while Getting System Statistics." + dcmae);
		}
		return statistics;
	}

	@Override
	public List<ReportDTO> getTableData(List<String> batchClassIds, WorkflowType workflowType, Date startDate, Date endDate,
			int startIndex, int maxResults, Order order) throws GWTException {
		ReportDataService reportDataService = this.getSingleBeanOfType(ReportDataService.class);
		List<ReportDisplayData> reports = new ArrayList<ReportDisplayData>();
		try {
			reports = reportDataService.getReportByWorkflow(batchClassIds, workflowType, endDate, startDate, startIndex, maxResults,
					order);
		} catch (DCMAException dcmae) {
			throw new GWTException("Exception while Getting Table Data." + dcmae);
		}
		return createReportDTOs(reports);
	}

	@Override
	public List<ReportDTO> getTableDataForUser(List<String> batchClassIds, String user, Date startDate, Date endDate, int startIndex,
			int maxResults, Order order) throws GWTException {
		ReportDataService reportDataService = this.getSingleBeanOfType(ReportDataService.class);
		List<ReportDisplayData> reports = new ArrayList<ReportDisplayData>();
		try {
			reports = reportDataService.getReportByUser(batchClassIds, user, endDate, startDate, startIndex, maxResults, order);
		} catch (DCMAException dcmae) {
			throw new GWTException("Exception while Getting table Data for User." + dcmae);
		}
		return createReportDTOs(reports);
	}

	@Override
	public Map<String, String> getAllBatchClasses() {
		BatchClassService batchClassService = this.getSingleBeanOfType(BatchClassService.class);
		List<BatchClass> batchClassList = batchClassService.getAllBatchClasses();
		LinkedHashMap<String, String> batchClassNames = new LinkedHashMap<String, String>();
		batchClassNames.put(ReportingConstants.ALL_TEXT, ReportingConstants.ALL_TEXT);

		for (Iterator<BatchClass> iterator = batchClassList.iterator(); iterator.hasNext();) {
			BatchClass batchClass = (BatchClass) iterator.next();
			batchClassNames.put(batchClass.getIdentifier(), batchClass.getIdentifier() + " - " + batchClass.getDescription());
		}
		return batchClassNames;
	};

	@Override
	public List<String> getAllUsers() {
		Set<String> usersSet = getAllUser();
		List<String> usersList = new ArrayList<String>();
		usersList.add(ReportingConstants.ALL_TEXT);
		usersList.addAll(usersSet);
		return usersList;
	}

	private List<ReportDTO> createReportDTOs(List<ReportDisplayData> reports) {
		List<ReportDTO> reportDTOs = new ArrayList<ReportDTO>();
		if (reports != null && !reports.isEmpty()) {
			for (ReportDisplayData reportDisplayData : reports) {
				ReportDTO reportDTO = new ReportDTO();
				reportDTO.setBatch(reportDisplayData.getBatch());
				reportDTO.setDocs(reportDisplayData.getDocs());
				reportDTO.setEntityName(reportDisplayData.getEntityName());
				reportDTOs.add(reportDTO);
				reportDTO.setPages(reportDisplayData.getPages());
			}
		}
		return reportDTOs;
	}

	@Override
	public Integer getTotalRowCount(List<String> batchClassIds, WorkflowType workflowType, Date startDate, Date endDate)
			throws GWTException {
		ReportDataService reportDataService = this.getSingleBeanOfType(ReportDataService.class);
		Integer totalRC = 0;
		try {
			totalRC = reportDataService.getReportTotalRowCountByWorkflow(batchClassIds, workflowType, endDate, startDate);
		} catch (DCMAException dcmae) {
			throw new GWTException("Exception while Getting Total Row Count for Workflows." + dcmae);
		}
		return totalRC;
	}

	@Override
	public Integer getTotalRowCountForUser(List<String> batchClassIds, String user, Date startDate, Date endDate) throws GWTException {
		ReportDataService reportDataService = this.getSingleBeanOfType(ReportDataService.class);
		Integer totalRC = 0;
		try {
			totalRC = reportDataService.getReportTotalRowCountByUser(batchClassIds, user, endDate, startDate);
		} catch (DCMAException dcmae) {
			throw new GWTException("Exception while Getting Total Row Count for Users." + dcmae);
		}
		return totalRC;
	}

	@Override
	public void syncDatabase() throws GWTException {
		ReportDataService reportService = this.getSingleBeanOfType(ReportDataService.class);
		try {
			ApplicationConfigProperties app = ApplicationConfigProperties.getApplicationConfigProperties();
			String antPath = app.getProperty("report.ant.buildfile.path");
			reportService.syncDatabase(antPath);
		} catch (Exception e) {
			throw new GWTException("Exception while running reporting. " + e.getMessage(), e);
		}
	}

	@Override
	public Boolean isAnotherUserConnected() throws GWTException {
		ReportDataService reportDataService = this.getSingleBeanOfType(ReportDataService.class);
		Boolean isAnotherUserAlreadyConnected = false;
		try {
			isAnotherUserAlreadyConnected = reportDataService.isAnotherUserConnected();
		} catch (Exception e) {
			throw new GWTException(e.getMessage(), e);
		}
		return isAnotherUserAlreadyConnected;
	}

	@Override
	public Map<String, String> getCustomReportButtonPopUpConfigs() throws GWTException {
		ReportDataService reportDataService = this.getSingleBeanOfType(ReportDataService.class);

		Map<String, String> popUpConfigs = new HashMap<String, String>();
		try {
			popUpConfigs = reportDataService.getCustomReportButtonPopUpConfigs();
		} catch (Exception e) {
			throw new GWTException("Exception while Getting pop-up configs." + e, e);
		}
		return popUpConfigs;
	}

	@Override
	public Map<String, String> getAllBatchClassesForUserRoles(Set<String> userRoles) {
		BatchClassService batchClassService = this.getSingleBeanOfType(BatchClassService.class);
		List<BatchClass> batchClassList = batchClassService.getAllBatchClassesByUserRoles(userRoles);
		LinkedHashMap<String, String> batchClassNames = new LinkedHashMap<String, String>();
		batchClassNames.put(ReportingConstants.ALL_TEXT, ReportingConstants.ALL_TEXT);
		for (Iterator<BatchClass> iterator = batchClassList.iterator(); iterator.hasNext();) {
			BatchClass batchClass = (BatchClass) iterator.next();
			batchClassNames.put(batchClass.getIdentifier(), batchClass.getIdentifier() + " - " + batchClass.getDescription());
		}
		return batchClassNames;
	}

}
