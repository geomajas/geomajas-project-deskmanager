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
package org.geomajas.plugin.deskmanager.security.internal;

import org.geomajas.plugin.deskmanager.domain.security.Profile;
import org.geomajas.plugin.deskmanager.domain.security.dto.Role;
import org.geomajas.plugin.deskmanager.security.internal.role.DeskmanagerAuthentication;
import org.geomajas.plugin.deskmanager.security.internal.role.authorization.DeskmanagerAuthorization;
import org.geomajas.security.Authentication;
import org.geomajas.security.BaseAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Provides authentication objects based on an application token.
 * 
 * @author Oliver May
 * 
 */
@Component("DeskmanagerSecurityService")
public class DeskmanagerSecurityServiceImpl implements DeskmanagerSecurityService {

	public static final String SERVICE_ID = "DM_SECURITY_SERVICE";

	@Autowired
	private AuthenticationTokenService tokenService;

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public String getId() {
		return this.getClass().getName();
	}

	@Override
	public Authentication getAuthentication(String authenticationToken) {
		if (authenticationToken == null || ("null").equals(authenticationToken)) {
			DeskmanagerAuthentication auth = new DeskmanagerAuthentication(createUnassignedProfile());
			auth.setAuthorizations(new BaseAuthorization[] { new DeskmanagerAuthorization(auth.getProfile(),
					null, applicationContext) });
			return auth;
		} else if (authenticationToken.startsWith("guest-")) {
			String geodeskId = authenticationToken.split("-")[1];
			DeskmanagerAuthentication auth = new DeskmanagerAuthentication(createGuestProfile());
			auth.setAuthorizations(new BaseAuthorization[] { new DeskmanagerAuthorization(auth.getProfile(),
					geodeskId, applicationContext) });
			return auth;
		}
		return tokenService.getAuthentication(authenticationToken);
	}

	@Override
	public String registerProfile(String geodeskId, Profile profile) {
		DeskmanagerAuthentication auth = new DeskmanagerAuthentication(profile);
		auth.setAuthorizations(new BaseAuthorization[] { new DeskmanagerAuthorization(auth.getProfile(), geodeskId,
				applicationContext) });
		String token = tokenService.login(auth);
		return token;
	}

	/**
	 * Utility method to create a guest profile.
	 * 
	 * @return the guest profile.
	 */
	public static Profile createGuestProfile() {
		Profile profile = new Profile();
		profile.setRole(Role.GUEST);

		return profile;
	}

	/**
	 * Utility method to create an unidentified profile. This means that the user has not chosen a role yet.
	 *
	 * @return the guest profile.
	 */
	public static Profile createUnassignedProfile() {
		Profile profile = new Profile();
		profile.setRole(Role.UNASSIGNED);

		return profile;
	}

	
}
