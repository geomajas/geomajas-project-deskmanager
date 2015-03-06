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
package org.geomajas.plugin.deskmanager.client.gwt.manager.impl;

import org.geomajas.plugin.deskmanager.client.gwt.manager.ManagerTabRegistry;
import org.geomajas.plugin.deskmanager.domain.security.dto.Role;

import java.util.List;


/**
 * Implementation of {@link ManagerTabRegistry.ManagerTabData}
 * with construction values needed for tabName and visibleForRoles.
 * Canvas getter is left to the implementation.
 *
 * @author Jan Venstermans
 */
public abstract class AbstractManagerTabDataWithoutCanvas implements ManagerTabRegistry.ManagerTabData {

	private String tabName;

	private List<Role> visibleForRoles;

	/**
	 * Default constructor, all values provided.
	 *
	 * @param tabName
	 * @param visibleForRoles on null, all roles can see the tab
	 */
	public AbstractManagerTabDataWithoutCanvas(String tabName, List<Role> visibleForRoles) {
		this.tabName = tabName;
		this.visibleForRoles = visibleForRoles;
	}

	@Override
	public String getTabName() {
		return tabName;
	}

	@Override
	public List<Role> getVisibleForRoles() {
		return visibleForRoles;
	}
}
