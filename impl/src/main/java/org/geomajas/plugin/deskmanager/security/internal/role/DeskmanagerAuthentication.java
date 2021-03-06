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
package org.geomajas.plugin.deskmanager.security.internal.role;

import org.geomajas.plugin.deskmanager.domain.security.Profile;
import org.geomajas.plugin.deskmanager.security.internal.DeskmanagerSecurityServiceImpl;
import org.geomajas.security.Authentication;

import java.util.Date;

/**
 * @author Oliver May
 * 
 */
public class DeskmanagerAuthentication extends Authentication {

	public static final int EXTEND_VALID = 300000;

	public static final int EXTEND_VALID_LIMIT = 86400000;

	private Profile profile;

	public DeskmanagerAuthentication(Profile profile) {
		this.setProfile(profile);
		setSecurityServiceId(DeskmanagerSecurityServiceImpl.SERVICE_ID);
		setExtendValid(EXTEND_VALID);
		setValidUntil(new Date(System.currentTimeMillis() + EXTEND_VALID));
		setInvalidAfter(new Date(System.currentTimeMillis() + EXTEND_VALID_LIMIT));

		setUserId(profile.getId());
		setUserName(profile.getFirstName() + " " + profile.getSurname());

		// guest and admin don't have a group
		setUserOrganization((profile.getTerritory() == null ? "" : profile.getTerritory().getName()));
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Profile getProfile() {
		return profile;
	}

}
