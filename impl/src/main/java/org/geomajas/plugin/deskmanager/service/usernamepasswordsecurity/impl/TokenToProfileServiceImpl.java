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
package org.geomajas.plugin.deskmanager.service.usernamepasswordsecurity.impl;

import org.geomajas.global.GeomajasException;
import org.geomajas.plugin.deskmanager.domain.usernamepasswordsecurity.AuthenticationSession;
import org.geomajas.plugin.deskmanager.domain.usernamepasswordsecurity.GroupMember;
import org.geomajas.plugin.deskmanager.domain.security.Profile;
import org.geomajas.plugin.deskmanager.domain.security.dto.Role;
import org.geomajas.plugin.deskmanager.security.ProfileService;
import org.geomajas.plugin.deskmanager.service.common.DtoConverterService;
import org.geomajas.plugin.deskmanager.service.usernamepasswordsecurity.AuthenticationSessionService;
import org.geomajas.plugin.deskmanager.service.usernamepasswordsecurity.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * Default implementation of {@link ProfileService}.
 * Uses authentication tokens from the database.
 *
 * @author Oliver May
 * @author An Buyle
 * @author Jan Venstermans
 */
public class TokenToProfileServiceImpl implements ProfileService {

	@Autowired
	private AuthenticationSessionService authenticationSessionService;

	@Autowired
	private UserService userService;

	@Autowired
	private DtoConverterService converterService;

	@Override
	public List<Profile> getProfiles(String token) {
		AuthenticationSession session = authenticationSessionService.getActiveSessionOfToken(token);
		List<Profile> profileList = new ArrayList<Profile>();
		if (session != null) {
			for (GroupMember member : userService.findGroupsOfUser(session.getUser().getId())) {
				try {
					profileList.add(converterService.toProfile(member));
				} catch (GeomajasException e) {
					// don't add this member
				}
			}
		}
		return profileList;
	}

	@Override
	public Profile createGuestProfile() {
		Profile profile = new Profile();
		profile.setRole(Role.GUEST);
		return profile;
	}
}
