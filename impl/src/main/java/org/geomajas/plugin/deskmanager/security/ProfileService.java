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
package org.geomajas.plugin.deskmanager.security;

import org.geomajas.annotation.Api;
import org.geomajas.annotation.UserImplemented;
import org.geomajas.plugin.deskmanager.domain.security.Profile;

import java.util.List;

/**
 * Provides a list of profiles that are valid for the current logged in user. These profiles can come from HTTP headers
 * or another database like single sign on.
 *
 * This is the main hook for using custom authentication.
 *
 * @author Oliver May
 * @since 1.0.0
 */
@Api(allMethods = true)
@UserImplemented
public interface ProfileService {

	/**
	 * Get a list of profiles that are available for the security token.
	 *
	 * @param securityToken securityToken for which profiles should be retrieved.
	 *
	 * @return the list of profiles, must not be null.
	 */
	List<Profile> getProfiles(String securityToken);

	/**
	 * Create a default guest profile.
	 *
	 * @return the guest profile
	 */
	Profile createGuestProfile();
}
