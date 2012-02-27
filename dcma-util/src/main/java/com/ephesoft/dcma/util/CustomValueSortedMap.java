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

package com.ephesoft.dcma.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

public class CustomValueSortedMap extends TreeSet<CustomMapClass> {

	private static final long serialVersionUID = 1L;

	private int maxValue;

	public CustomValueSortedMap(int maxValue) {
		this(maxValue, new DoubleComparator());
	}

	public CustomValueSortedMap(int maxValue, Comparator<CustomMapClass> comparator) {
		super(comparator);
		this.maxValue = maxValue;
	}

	@Override
	public boolean add(CustomMapClass clazz) {
		boolean returnValue = true;
		if (maxValue == 0) {
			returnValue = super.add(clazz);
		} else {
			if (super.size() == maxValue) {
				if (super.add(clazz)) {
					if (super.pollFirst() != null) {
						returnValue = true;
					} else {
						returnValue = false;
					}
				} else {
					Iterator<CustomMapClass> itr = super.iterator();
					while (itr.hasNext()) {
						CustomMapClass customMapClass = itr.next();
						if (customMapClass.equals(clazz) && clazz.getValue().compareTo(customMapClass.getValue()) > 0) {
							super.remove(customMapClass);
							super.add(clazz);
							returnValue = true;
							break;
						}
					}
				}
			} else {
				returnValue = super.add(clazz);
			}
		}
		return returnValue;
	}

	public boolean add(String key, Double value) {
		CustomMapClass customMapClass = new CustomMapClass(key, value);
		return this.add(customMapClass);
	}

	public Map<String, Double> getMap() {
		Map<String, Double> linkedHashMap = new LinkedHashMap<String, Double>();
		Iterator<CustomMapClass> itr = super.iterator();
		while (itr.hasNext()) {
			CustomMapClass customMapClass = (CustomMapClass) itr.next();
			Double doubleValue = linkedHashMap.get(customMapClass.getKey());
			Double newValue = customMapClass.getValue();
			if (doubleValue == null) {
				linkedHashMap.put(customMapClass.getKey(), newValue);
			} else if (doubleValue.compareTo(newValue) < 0) {
				linkedHashMap.put(customMapClass.getKey(), newValue);
			}
		}
		return linkedHashMap;
	}

	public Map<String, Double> getReverseSortedMap() {
		Map<String, Double> linkedHashMap = new LinkedHashMap<String, Double>();
		Iterator<CustomMapClass> itr = super.descendingIterator();
		while (itr.hasNext()) {
			CustomMapClass customMapClass = (CustomMapClass) itr.next();
			Double doubleValue = linkedHashMap.get(customMapClass.getKey());
			Double newValue = customMapClass.getValue();
			if (doubleValue == null) {
				linkedHashMap.put(customMapClass.getKey(), newValue);
			} else if (doubleValue.compareTo(newValue) < 0) {
				linkedHashMap.put(customMapClass.getKey(), newValue);
			}
		}
		return linkedHashMap;
	}

	public Map<String, Float> getReverseSortedMapValueInFloat() {
		Map<String, Float> linkedHashMap = new LinkedHashMap<String, Float>();
		Iterator<CustomMapClass> itr = super.descendingIterator();
		while (itr.hasNext()) {
			CustomMapClass customMapClass = (CustomMapClass) itr.next();
			Float doubleValue = linkedHashMap.get(customMapClass.getKey());
			Float newValue = (float) ((double) customMapClass.getValue());
			if (doubleValue == null) {
				linkedHashMap.put(customMapClass.getKey(), newValue);
			} else if (doubleValue.compareTo(newValue) < 0) {
				linkedHashMap.put(customMapClass.getKey(), newValue);
			}
		}
		return linkedHashMap;
	}

}

class DoubleComparator implements Comparator<CustomMapClass> {

	@Override
	public int compare(CustomMapClass object1, CustomMapClass object) {
		int returnValue = object1.getValue().compareTo(object.getValue());
		if (returnValue == 0) {
			returnValue = -1;
		}
		// if (!o1.getKey().equalsIgnoreCase(o2.getKey())) {
		// returnValue = o1.getValue().compareTo(o2.getValue());
		// if (returnValue == 0) {
		// returnValue = o1.getKey().compareTo(o2.getKey());
		// }
		// System.out.println("");
		// }
		return returnValue;
	}
}
