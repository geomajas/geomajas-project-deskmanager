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
package org.geomajas.plugin.deskmanager.domain.dto;

import org.geomajas.configuration.client.ClientUserDataInfo;

/**
 * Extra information that is attached to the ApplicationInfo of deskmanager geodesks.
 *
 * @author Oliver May
 */
public class DeskmanagerApplicationInfoUserData implements ClientUserDataInfo {

	private String userApplicationKey;

	private String deskmanagerVersion;

	private String deskmanagerBuild;

	/**
	 * Get the user application key for this geodesk.
	 *
	 * @return the user application key.
	 */
	public String getUserApplicationKey() {
		return userApplicationKey;
	}

	/**
	 * Set the user application key for this geodesk.
	 *
	 * @param userApplicationKey the user application key
	 */
	public void setUserApplicationKey(String userApplicationKey) {
		this.userApplicationKey = userApplicationKey;
	}

	/**
	 * Set the deskmanager application version.
	 *
	 * @param deskmanagerVersion the deskmanagerVersion to set.
	 */
	public void setDeskmanagerVersion(String deskmanagerVersion) {
		this.deskmanagerVersion = deskmanagerVersion;
	}

	/**
	 * Get the deskmanager application version.
	 *
	 * @return the deskmanager version string, injected by maven.
	 */
	public String getDeskmanagerVersion() {
		return deskmanagerVersion;
	}

	/**
	 * Set the deskmanager build version.
	 *
	 * @param deskmanagerBuild the deskmanagerBuild to set.
	 */
	public void setDeskmanagerBuild(String deskmanagerBuild) {
		this.deskmanagerBuild = deskmanagerBuild;
	}

	/**
	 * Get the deskmanager build version.
	 *
	 * @return the unique application build string, injected by maven.
	 */
	public String getDeskmanagerBuild() {
		return deskmanagerBuild;
	}

}
