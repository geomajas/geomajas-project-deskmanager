/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2015 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
package org.geomajas.geometry;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * {@link SimpleBeanInfo} for {@link Bbox}. Makes maxX/Y transient for encoding to xml.
 * 
 * @author Jan De Moerloose
 *
 */
public class BboxBeanInfo extends SimpleBeanInfo {

	private PropertyDescriptor[] props = new PropertyDescriptor[6];

	public BboxBeanInfo() throws IntrospectionException {
		props[0] = new PropertyDescriptor("x", Bbox.class);
		props[1] = new PropertyDescriptor("y", Bbox.class);
		props[2] = new PropertyDescriptor("width", Bbox.class);
		props[3] = new PropertyDescriptor("height", Bbox.class);
		props[4] = new PropertyDescriptor("maxX", Bbox.class);
		props[4].setValue("transient", Boolean.TRUE);
		props[5] = new PropertyDescriptor("maxY", Bbox.class);
		props[5].setValue("transient", Boolean.TRUE);
	}

	public PropertyDescriptor[] getPropertyDescriptors() {
		return props;
	}

}
