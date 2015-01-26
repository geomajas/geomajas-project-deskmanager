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
package org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.dto;

import org.geomajas.command.CommandRequest;

/**
 * Command request for
 * {@link org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.InvalidateAuthenticationTokenCommand}.
 *
 * @author Jan Venstermans
 */
public class InvalidateAuthenticationTokenRequest implements CommandRequest {

	private static final long serialVersionUID = 1L;

	public static final String COMMAND = "command.deskmanager.security.InvalidateAuthenticationToken";

	private String authenticationSessionToken;

	public String getAuthenticationSessionToken() {
		return authenticationSessionToken;
	}

	public void setAuthenticationSessionToken(String authenticationSessionToken) {
		this.authenticationSessionToken = authenticationSessionToken;
	}
}
