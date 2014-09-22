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

import org.geomajas.plugin.deskmanager.security.internal.role.DeskmanagerAuthentication;
import org.geomajas.security.Authentication;

/**
 * @author Oliver May
 */
public interface AuthenticationTokenService {

	/**
	 * Get an authentication object based on a token.
	 *
	 * @param token the token
	 * @return the authentication object
	 */
	Authentication getAuthentication(String token);

	/**
	 * Invalidate the authentication object
	 * @param token
	 */
	void logout(String token);

	String login(DeskmanagerAuthentication authentication);

}
