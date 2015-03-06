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
package org.geomajas.plugin.deskmanager.test;

import junit.framework.Assert;
import org.geomajas.plugin.deskmanager.client.gwt.common.GdmLayout;
import org.junit.Test;

/**
 * Test the default geodeskUrlUtil implementation.
 *
 * @author Oliver May
 */
public class GeodeskUrlUtilTest {

	@Test
	public void getGeodeskIdTest() {
		String url = "http://localhost/desk/TEST_GEODESK";
		Assert.assertEquals("TEST_GEODESK", GdmLayout.geodeskIdUtil.parseGeodeskId(url));
	}

	@Test
	public void getGeodeskUrlTest() {
		String geodeskId = "TEST_GEODESK";
		Assert.assertEquals("desk/TEST_GEODESK", GdmLayout.geodeskIdUtil.buildGeodeskUrl(geodeskId));
	}

}
