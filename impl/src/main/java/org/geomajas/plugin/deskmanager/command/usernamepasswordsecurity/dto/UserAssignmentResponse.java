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

import org.geomajas.command.CommandResponse;
import org.geomajas.plugin.deskmanager.domain.security.dto.ProfileDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Response for {@link org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.UserAssignmentCommand}.
 * 
 * @author Jan Venstermans
 * 
 */
public class UserAssignmentResponse extends CommandResponse {

	private static final long serialVersionUID = 115L;

	private long userId;

	private List<ProfileDto> profiles = new ArrayList<ProfileDto>();

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<ProfileDto> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<ProfileDto> profiles) {
		this.profiles = profiles;
	}
}
