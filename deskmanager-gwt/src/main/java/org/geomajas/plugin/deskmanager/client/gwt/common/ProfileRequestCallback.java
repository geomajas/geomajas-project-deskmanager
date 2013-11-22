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
package org.geomajas.plugin.deskmanager.client.gwt.common;

import org.geomajas.plugin.deskmanager.domain.security.dto.ProfileDto;

/**
 * Callback class for the token request handler.
 *
 * @author Oliver May
 *
 */
public interface ProfileRequestCallback {

	/**
	 * Callback when a role is selected.
	 *
	 * @param token
	 *            the selected token.
	 */
	void onTokenChanged(String token, ProfileDto profile);
}
