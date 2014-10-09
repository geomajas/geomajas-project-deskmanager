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
import org.geomajas.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Create, manage and cache authentication tokens.
 * 
 * @author Oliver May
 */
@Component
public class AuthenticationTokenServiceImpl implements AuthenticationTokenService {
	
	@Autowired
	private CacheService cacheService;

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationTokenServiceImpl.class);

	@Override
	public Authentication getAuthentication(String token) {
		LOG.debug("Getting authentication for token {}", token);
		return cacheService.get(AuthenticationTokenServiceImpl.class.toString(), token, Authentication.class);
	}

	@Override
	public void logout(String token) {
		cacheService.remove(AuthenticationTokenServiceImpl.class.toString(), token);
	}

	@Override
	public String login(DeskmanagerAuthentication authentication) {
		String token = randomToken();
		return login(token, authentication);
	}

	private String randomToken() {
		return "gdm." + UUID.randomUUID().toString();
	}

	private String login(String token, DeskmanagerAuthentication authentication) {
		LOG.debug("Registering token {} for authentication {}", token, authentication);
		if (null == token) {
			return login(authentication);
		}
		cacheService.put(AuthenticationTokenServiceImpl.class.toString(), token, authentication);
		return token;
	}
}
