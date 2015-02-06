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
package org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity;

import org.geomajas.command.Command;
import org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.dto.AuthenticationResponse;
import org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.dto.InvalidateAuthenticationTokenRequest;
import org.geomajas.plugin.deskmanager.service.usernamepasswordsecurity.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command for invalidating an authentication session token.
 *
 * @author Jan Venstermans
 */
@Component(InvalidateAuthenticationTokenRequest.COMMAND)
public class InvalidateAuthenticationTokenCommand implements
		Command<InvalidateAuthenticationTokenRequest, AuthenticationResponse> {

	private static final Logger LOG = LoggerFactory.getLogger(InvalidateAuthenticationTokenCommand.class);

	@Autowired
	private AuthenticationService authenticationService;

	public AuthenticationResponse getEmptyCommandResponse() {
		return new AuthenticationResponse();
	}

	public void execute(InvalidateAuthenticationTokenRequest request, AuthenticationResponse response)
			throws Exception {
		String authenticationToken = request.getAuthenticationSessionToken();
		authenticationService.removeAuthenticationSession(authenticationToken);
		LOG.info("Authentication Invalidation of token " + authenticationToken + " successful.");
	}
}