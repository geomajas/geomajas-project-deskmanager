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
package org.geomajas.plugin.deskmanager.test.command.configuration;

import org.geomajas.command.CommandDispatcher;
import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.GetConfigurationRequest;
import org.geomajas.command.dto.GetConfigurationResponse;
import org.geomajas.plugin.deskmanager.domain.dto.DeskmanagerApplicationInfoUserData;
import org.geomajas.plugin.deskmanager.domain.security.dto.Role;
import org.geomajas.plugin.deskmanager.test.LoginBeforeTestingWithPredefinedProfileBase;
import org.geomajas.plugin.deskmanager.test.service.ExampleDatabaseProvisioningServiceImpl;
import org.geomajas.security.GeomajasSecurityException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Oliver May
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/org/geomajas/spring/geomajasContext.xml",
		"/org/geomajas/plugin/deskmanager/spring/**/*.xml", "/applicationContext.xml" })
public class GetConfigurationCommandTest extends LoginBeforeTestingWithPredefinedProfileBase {

	@Autowired
	private CommandDispatcher dispatcher;

	@Override
	protected Role getRoleToLoginWithBeforeTesting() {
		return null;
	}

	@Test
	public void testGetPublicConfiguration() {
		GetConfigurationRequest request = new GetConfigurationRequest();
		request.setApplicationId(ExampleDatabaseProvisioningServiceImpl.GEODESK_TEST_BE);

		GetConfigurationResponse response = (GetConfigurationResponse) dispatcher.execute(
				GetConfigurationRequest.COMMAND, request, getToken(Role.GUEST), "en");

		Assert.assertTrue(response.getErrorMessages().isEmpty());
		Assert.assertNotNull(response.getApplication());
		Assert.assertTrue(response.getApplication().getUserData() instanceof DeskmanagerApplicationInfoUserData);
		Assert.assertEquals(((DeskmanagerApplicationInfoUserData) response.getApplication().getUserData())
				.getUserApplicationKey(), ExampleDatabaseProvisioningServiceImpl.CLIENTAPPLICATION_ID);
	}

	@Test
	public void testGetPrivateConfiguration() {
		GetConfigurationRequest request = new GetConfigurationRequest();
		request.setApplicationId(ExampleDatabaseProvisioningServiceImpl.GEODESK_TEST_DE_PRIVATE);

		CommandResponse response = dispatcher.execute(
				GetConfigurationRequest.COMMAND, request, getToken(Role.GUEST), "en");

		Assert.assertFalse(response.getErrorMessages().isEmpty());
		Assert.assertEquals(GeomajasSecurityException.class.getName(), response.getExceptions().get(0).getClassName());
	}

	@Test
	public void testGetPublicConfigurationWithoutAuthentication() {
		GetConfigurationRequest request = new GetConfigurationRequest();
		request.setApplicationId(ExampleDatabaseProvisioningServiceImpl.GEODESK_TEST_BE);

		CommandResponse response = dispatcher.execute(
				GetConfigurationRequest.COMMAND, request,
				"guest-" + ExampleDatabaseProvisioningServiceImpl.GEODESK_TEST_BE, "en");

		Assert.assertTrue(response instanceof GetConfigurationResponse);
		Assert.assertTrue(response.getErrorMessages().isEmpty());
		Assert.assertNotNull(((GetConfigurationResponse) response).getApplication());
		Assert.assertTrue(((GetConfigurationResponse) response).getApplication().getUserData() instanceof
				DeskmanagerApplicationInfoUserData);
		Assert.assertEquals(((DeskmanagerApplicationInfoUserData) ((GetConfigurationResponse) response).getApplication
				().getUserData())
				.getUserApplicationKey(), ExampleDatabaseProvisioningServiceImpl.CLIENTAPPLICATION_ID);
		Assert.assertFalse(((GetConfigurationResponse) response).getApplication().getMaps().get(0).getLayers().isEmpty
				());
	}
}
