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
package org.geomajas.plugin.deskmanager.test.command.manager;

import org.geomajas.command.CommandDispatcher;
import org.geomajas.command.CommandResponse;
import org.geomajas.plugin.deskmanager.command.manager.dto.GetTerritoriesRequest;
import org.geomajas.plugin.deskmanager.command.manager.dto.GetTerritoriesResponse;
import org.geomajas.plugin.deskmanager.domain.security.dto.Role;
import org.geomajas.plugin.deskmanager.test.LoginBeforeTestingWithPredefinedProfileBase;
import org.geomajas.plugin.deskmanager.test.security.StubProfileService;
import org.geomajas.security.GeomajasSecurityException;
import org.junit.Assert;
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
public class GetTerritoriesCommandTest extends LoginBeforeTestingWithPredefinedProfileBase {

	@Autowired
	private CommandDispatcher dispatcher;

	@Override
	protected Role getRoleToLoginWithBeforeTesting() {
		return Role.ADMINISTRATOR;
	}

	@Test
	@Transactional
	public void testGetTerritories() {
		GetTerritoriesRequest request = new GetTerritoriesRequest();

		GetTerritoriesResponse response = (GetTerritoriesResponse) dispatcher.execute(GetTerritoriesRequest.COMMAND,
				request, getTokenOfLoggedInBeforeTesting(), "en");

		Assert.assertTrue(response.getErrors().isEmpty());
		Assert.assertNotNull(response.getTerritories());
		Assert.assertTrue(response.getTerritories().size() > 0);
	}

	/**
	 * Test security.
	 */
	@Test
	public void testNotAllowed() {
		CommandResponse response = dispatcher.execute(GetTerritoriesRequest.COMMAND,
				new GetTerritoriesRequest(), getToken(Role.GUEST), "en");


		Assert.assertFalse(response.getExceptions().isEmpty());
		Assert.assertEquals(GeomajasSecurityException.class.getName(), response.getExceptions().get(0).getClassName());
	}
}
