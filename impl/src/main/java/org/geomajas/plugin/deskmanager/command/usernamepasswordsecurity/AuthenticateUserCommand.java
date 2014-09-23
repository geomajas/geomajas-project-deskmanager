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
package org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity;

import org.geomajas.command.Command;
import org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.dto.AuthenticateUserRequest;
import org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.dto.AuthenticationResponse;
import org.geomajas.plugin.deskmanager.service.usernamepasswordsecurity.AuthenticationService;
import org.geomajas.security.GeomajasSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command that will retrieve the profiles of a user for a specific geodesk.
 *
 * @author Jan Venstermans
 */
@Component(AuthenticateUserRequest.COMMAND)
public class AuthenticateUserCommand implements Command<AuthenticateUserRequest, AuthenticationResponse> {

	private final Logger log = LoggerFactory.getLogger(AuthenticateUserCommand.class);

	@Autowired
	private AuthenticationService authenticationService;

	public AuthenticationResponse getEmptyCommandResponse() {
		return new AuthenticationResponse();
	}

	public void execute(AuthenticateUserRequest request, AuthenticationResponse response) throws Exception {
		String username = request.getUserName();
		try {
			String token = authenticationService.authenticateUsernamePassword(
					username, request.getPassword());
			response.setAuthenticationToken(token);
			response.setUsername(username);
			log.info("Authentication Request (via username and password) for user "
					+ request.getUserName() + " successful.");
		} catch (GeomajasSecurityException ex) {
			log.info("Authentication exception for user " + username);
			// return an empty response, in compliance with static security
		}
	}
}