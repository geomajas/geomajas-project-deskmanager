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
package org.geomajas.plugin.deskmanager.test.service.manager;

import org.geomajas.plugin.deskmanager.service.manager.DiscoveryService;
import org.geomajas.plugin.deskmanager.service.manager.DiscoveryServiceImpl;
import org.geotools.data.ows.Layer;
import org.geotools.data.ows.OperationType;
import org.geotools.data.ows.WMSRequest;
import org.geotools.data.wms.WMSUtils;
import org.geotools.data.wms.WebMapServer;
import org.geotools.ows.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Oliver May
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/org/geomajas/spring/geomajasContext.xml",
		"/org/geomajas/plugin/deskmanager/spring/**/*.xml", "/applicationContext.xml" })
public class DiscoveryServiceImplCheck {

	@Autowired
	private DiscoveryService discoveryService;

	private static final String TAB = "  ";

	private static final String WMS_URL =
//			"http://grb.agiv.be/geodiensten/raadpleegdiensten/GRB-basiskaart/wms";
			"http://apps.geomajas.org/geoserver/ows";

	private static final String USERNAME = null;
	private static final String PASSWORD = null;

	@Test
	public void printGetRasterCapabilities() throws Exception {
		try {
			WebMapServer webMapServer = ((DiscoveryServiceImpl) discoveryService).createWebMapServer(
					WMS_URL, USERNAME, PASSWORD);
			System.out.println("Wms possibilities of url " + WMS_URL);
			WMSRequest wmsRequest = webMapServer.getCapabilities().getRequest();
			System.out.println("GetCapabilities:");
			printOperationType(wmsRequest.getGetCapabilities());
			System.out.println("GetMap:");
			printOperationType(wmsRequest.getGetMap());
			System.out.println("GetFeatureInfo:");
			printOperationType(wmsRequest.getGetFeatureInfo());
			System.out.println("Describe layer:");
			printOperationType(wmsRequest.getDescribeLayer());
			System.out.println("Legend Graphics:");
			printOperationType(wmsRequest.getGetLegendGraphic());
			System.out.println("GetStyles:");
			printOperationType(wmsRequest.getGetStyles());
			System.out.println("PutStyles:");
			printOperationType(wmsRequest.getPutStyles());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void printInfoOfLayers() throws Exception {
		WebMapServer webMapServer = ((DiscoveryServiceImpl) discoveryService).createWebMapServer(
				WMS_URL, USERNAME, PASSWORD);
		Layer[] layerArray = WMSUtils.getNamedLayers(webMapServer.getCapabilities());
		if (layerArray != null) {
			System.out.println(layerArray.length + " layer(s) available at " + WMS_URL);
			for (Layer layer : layerArray) {
				printLayer(layer);
			}
		} else {
			System.out.println("No layer(s) available at " + WMS_URL);
		}
	}

	private void printOperationType(OperationType operationType) {
		if (operationType != null) {
			System.out.println(TAB + "get URL: " + operationType.getGet());
			System.out.println(TAB + "post URL: " + operationType.getPost());
			System.out.println(TAB + operationType.getFormats().size() + " format(s): " + operationType.getFormats());
		} else {
			System.out.println(TAB + "no info");
		}
	}

	private void printLayer(Layer layer) {
		if (layer != null) {
			System.out.println(TAB + "layer " + layer.getName());
			System.out.println(TAB + TAB + "queryable " + layer.isQueryable());
			if (layer.getParent() != null) {
				System.out.println(TAB + TAB + "parent " + layer.getParent().getName());
			}
			if (layer.getChildren() != null && layer.getChildren().length != 0) {
				System.out.println(TAB + TAB + "children " + Arrays.asList(layer.getChildren()));
			}
		} else {
			System.out.println(TAB + "no info");
		}
	}

}
