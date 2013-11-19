/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2013 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
package org.geomajas.plugin.deskmanager.test.command.manager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.geomajas.command.CommandDispatcher;
import org.geomajas.command.CommandResponse;
import org.geomajas.configuration.client.ClientApplicationInfo;
import org.geomajas.plugin.deskmanager.command.manager.dto.GetGeotoolsVectorCapabilitiesRequest;
import org.geomajas.plugin.deskmanager.command.manager.dto.GetWmsCapabilitiesRequest;
import org.geomajas.plugin.deskmanager.command.manager.dto.GetWmsCapabilitiesResponse;
import org.geomajas.plugin.deskmanager.command.security.dto.RetrieveRolesRequest;
import org.geomajas.plugin.deskmanager.security.DeskmanagerSecurityService;
import org.geomajas.plugin.deskmanager.security.ProfileService;
import org.geomajas.security.GeomajasSecurityException;
import org.geomajas.security.SecurityManager;
import org.geomajas.security.SecurityService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Oliver May
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/org/geomajas/spring/geomajasContext.xml",
		"/org/geomajas/plugin/deskmanager/spring/**/*.xml", "/applicationContext.xml" })
public class GetWmsCapabilitiesCommandTest {

	@Autowired
	private SecurityService securityService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private SecurityManager securityManager;

	@Autowired
	private CommandDispatcher dispatcher;
	
	@Resource(name = "defaultLoketClientInfo")
	private ClientApplicationInfo defaultGeodesk;

	private String userToken;

	private String guestToken;

	@Before
	public void setup() throws Exception {
		// First profile in list is admin
		userToken = ((DeskmanagerSecurityService) securityService).registerRole(RetrieveRolesRequest.MANAGER_ID,
				profileService.getProfiles(null).get(0));
		guestToken = ((DeskmanagerSecurityService) securityService).registerRole(RetrieveRolesRequest.MANAGER_ID,
				DeskmanagerSecurityService.createGuestProfile());

		// Log in
		securityManager.createSecurityContext(userToken);

	}

	@Test
	@Transactional
	public void testGetWmsCapabilities() throws Exception {
		// Get configuration object.
		Map<String, String> connection = new HashMap<String, String>();
		connection.put(GetWmsCapabilitiesRequest.GET_CAPABILITIES_URL,
				"http://apps.geomajas.org/geoserver/geosparc/ows?service=wms&version=1.1.0&request=GetCapabilities");

		GetWmsCapabilitiesRequest request = new GetWmsCapabilitiesRequest();
		request.setConnectionProperties(connection);
		
		GetWmsCapabilitiesResponse response = (GetWmsCapabilitiesResponse) dispatcher
				.execute(GetWmsCapabilitiesRequest.COMMAND, request, userToken, "en");

		Assert.assertTrue(response.getErrors().isEmpty());
		Assert.assertTrue(response.getErrorMessages().isEmpty());
		Assert.assertNotNull(response.getRasterCapabilities().size() > 0);
	}
	
	@Test
	public void testDefaultCrs() throws Exception {
		// Get configuration object.
		Map<String, String> connection = new HashMap<String, String>();
		connection.put(GetWmsCapabilitiesRequest.GET_CAPABILITIES_URL,
				"http://apps.geomajas.org/geoserver/geosparc/ows?service=wms&version=1.1.0&request=GetCapabilities");

		GetWmsCapabilitiesRequest request = new GetWmsCapabilitiesRequest();
		request.setConnectionProperties(connection);
		
		GetWmsCapabilitiesResponse response = (GetWmsCapabilitiesResponse) dispatcher
				.execute(GetWmsCapabilitiesRequest.COMMAND, request, userToken, "en");

		Assert.assertTrue(response.getErrors().isEmpty());
		Assert.assertTrue(response.getErrorMessages().isEmpty());
		Assert.assertEquals(defaultGeodesk.getMaps().get(0).getCrs(), response.getDefaultCrs());
	}
	

	/**
	 * Test security.
	 */
	@Test
	public void testNotAllowed() {
		CommandResponse response = dispatcher.execute(GetGeotoolsVectorCapabilitiesRequest.COMMAND,
				new GetGeotoolsVectorCapabilitiesRequest(), guestToken, "en");

		Assert.assertFalse(response.getExceptions().isEmpty());
		Assert.assertEquals(GeomajasSecurityException.class.getName(), response.getExceptions().get(0).getClassName());
	}
}
