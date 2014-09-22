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
package org.geomajas.plugin.deskmanager.security.internal;

import org.geomajas.annotation.Api;
import org.geomajas.annotation.UserImplemented;
import org.geomajas.plugin.deskmanager.domain.security.Profile;
import org.geomajas.security.SecurityService;

/**
 * Security service for the deskmanager application, allows registration of login profiles.
 *
 * @author Oliver May
 */
@Api(allMethods = true)
@UserImplemented
public interface DeskmanagerSecurityService extends SecurityService {

	/**
	 * Register a user profile for a given geodesk and return the security token. The geodesk is needed because
	 * security on the same layer might differ between geodesks.
	 *
	 * @param geodeskId the geodesk id
	 * @param profile the profile
	 * @return the security token
	 */
	String registerProfile(String geodeskId, Profile profile);
}
