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

package com.ephesoft.dcma.gwt.admin.bm.client;

public enum ViewType {

	BATCH_CLASS_LISTING("Batch Class List"), BATCH_CLASS("Batch Class"), MODULE("Module"), PLUGIN("Plugin"), KV_PP_PLUGIN(
			"KV Page Process"), DOCUMENT_TYPE("Document Type"), PAGE_TYPE("Page Type"), DOCUMENT_DETAIL("Document Detail"), FUZZY_DB(
			"Plugin"), DATABASE_MAPPING("Database Mapping"), TABLE_MAPPING("Table Mapping"), DOCUMENT_LEVEL_FIELD(
			"Document Level Field"), KV_EXTRACTION("KV Extraction"), REGEX("Regex"), EMAIL("Email"), BATCH_CLASS_FIELD(
			"Batch_Class_Field"), KV_PP_PLUGIN_CONFIG_ADD("Add KV Page Process"), KV_PP_PLUGIN_CONFIG_EDIT("Edit KV Page Process"),
	TABLE_INFO("Table Info"), TABLE_COLUMN_INFO("Table Column Info"), FUNCTION_KEY("Function Key"), CONFIGURE_MODULE(
			"Configure modules"), CONFIGURE_PLUGIN("Configure plugins"), KV_PP_PLUGIN_CONFIG("Configure"), WEB_SCANNER("Web Scanner"),
	CMIS("Cmis"), BOX_EXPORTER("Box Exporter");

	String value;

	private ViewType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
