/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2014 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
package org.geomajas.plugin.deskmanager.test.service;

import org.geomajas.plugin.deskmanager.service.manager.ShapeFileServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

/**
 * @author Oliver May
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/org/geomajas/spring/geomajasContext.xml",
//		"/org/geomajas/plugin/deskmanager/spring/**/*.xml", "/applicationContext.xml" })

public class ShapeFileServiceImplTest {

	private ShapeFileServiceImpl shapeFileService;

	@Before
	public void setUp() throws Exception {
		shapeFileService = new ShapeFileServiceImpl();
	}

	@Test
	 public void testIsShpZipEntryNameIncorrectValues() throws Exception {
		String[] inputValues = new String[] { "abc", "abc/", "abc/abc.shp", "abc\\"} ;
		boolean [] result = new boolean[inputValues.length];

		for (int i = 0 ; i < inputValues.length ; i++) {
			result[i] = shapeFileService.isShpZipEntryName(inputValues[i]);
		}

		for (int i = 0 ; i < inputValues.length ; i++) {
			Assert.assertFalse(result[i]);
		}
	}

	@Test
	public void testIsShpZipEntryNameCorrectValues() throws Exception {
		String[] inputValues = new String[] { "abc.shp", "abc.shx", "abc.dbf",
				"abc.prj", "abc.sbn", "abc.sbx", "abc.fbn", "abc.fbx", "abc.ain",
				"abc.aih", "abc.aih", "abc.mxs", "abc.atx", "abc.shp.xml", "abc.cpg", "abc.qix"} ;
		boolean [] result = new boolean[inputValues.length];

		for (int i = 0 ; i < inputValues.length ; i++) {
			result[i] = shapeFileService.isShpZipEntryName(inputValues[i]);
		}

		for (int i = 0 ; i < inputValues.length ; i++) {
			Assert.assertTrue(result[i]);
		}
	}

}
