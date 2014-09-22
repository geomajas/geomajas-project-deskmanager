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
package org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.dto;

import org.geomajas.command.CommandRequest;

/**
 * Request for {@link org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.FindUsersCommand}.
 * 
 * @author Jan De Moerloose
 * 
 */
public class FindUsersRequest implements CommandRequest {

	private static final long serialVersionUID = 115L;

	public static final String COMMAND = "FindUsers";

	private boolean includeProfiles;

	public boolean isIncludeProfiles() {
		return includeProfiles;
	}

	public void setIncludeProfiles(boolean includeProfiles) {
		this.includeProfiles = includeProfiles;
	}
}