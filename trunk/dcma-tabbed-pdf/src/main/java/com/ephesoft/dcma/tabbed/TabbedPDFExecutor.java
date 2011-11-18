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

package com.ephesoft.dcma.tabbed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ephesoft.dcma.core.exception.DCMAApplicationException;
import com.ephesoft.dcma.core.threadpool.BatchInstanceThread;
import com.ephesoft.dcma.core.threadpool.ProcessExecutor;
import com.ephesoft.dcma.tabbed.constant.TabbedPdfConstant;

public class TabbedPDFExecutor {

	private final String outputFolderPath;
	private final List<String> documentPDFPaths;
	private final String pdfMarkPath;
	private final BatchInstanceThread thread;
	private final String tabbedPDFName;
	private final String pdfCreationParam;

	/**
	 * Logger instance for logging using slf4j for logging information.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TabbedPDFExecutor.class);

	public TabbedPDFExecutor(String tabbedPDFName, String outputFolderPath, List<String> documentPDFPaths, String pdfMarkPath,
			BatchInstanceThread thread, String pdfCreationParam) throws DCMAApplicationException {
		this.tabbedPDFName = tabbedPDFName;
		this.outputFolderPath = outputFolderPath;
		this.documentPDFPaths = documentPDFPaths;
		this.pdfMarkPath = pdfMarkPath;
		this.thread = thread;
		this.pdfCreationParam = pdfCreationParam;
		run();
	}

	public void run() throws DCMAApplicationException {
		try {
			String pdfCreationParams[] = pdfCreationParam.split(" ");
			ArrayList<String> commandList = new ArrayList<String>();
			commandList.add("cmd");
			commandList.add("/c");
			commandList.add("gswin32c");
			for (String param : pdfCreationParams) {
				commandList.add(param);
			}
			// //commandList.add(pdfCreationParam);
			// commandList.add("-q");
			// commandList.add("-dBATCH");
			// commandList.add("-dNOPAUSE");
			// commandList.add("-sDEVICE=pdfwrite");
			commandList.add("\"-sOutputFile=" + outputFolderPath + File.separator + tabbedPDFName + "\"");

			for (String documentPDFPath : documentPDFPaths) {
				commandList.add("\"" + documentPDFPath + "\"");

			}
			commandList.add("\"" + pdfMarkPath + "\"");

			String[] cmds = new String[commandList.size()];
			for (int i = 0; i < commandList.size(); i++) {
				if (commandList.get(i).contains("cmd")) {
					LOGGER.info("inside cmd");
					cmds[i] = commandList.get(i);
				} else if (commandList.get(i).contains("/c")) {
					LOGGER.info("inside /c");
					cmds[i] = commandList.get(i);
				} else {
					LOGGER.info("inside remaining params");
					cmds[i] = commandList.get(i);
				}
			}

			LOGGER.info("command formed is :");
			for (int i = 0; i < cmds.length; i++) {
				LOGGER.info(cmds[i]);
			}
			LOGGER.info("command formed Ends.");

			thread.add(new ProcessExecutor(cmds, new File(System.getenv(TabbedPdfConstant.GHOSTSCRIPT_HOME))));

		} catch (Exception e) {
			LOGGER.error("Exception while generating Tabbed PDF. " + e.getMessage());
			throw new DCMAApplicationException("Exception while generating Tabbed PDF." + e.getMessage(), e);
		}
	}

}
