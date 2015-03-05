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
package org.geomajas.plugin.deskmanager.client.gwt.manager;

import com.smartgwt.client.widgets.Canvas;
import org.geomajas.plugin.deskmanager.domain.security.dto.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Registry system for adding custom tabs to manager window.
 *
 * @author Jan Venstermans
 *
 */
public final class ManagerTabRegistry {

	private static final ManagerTabRegistry TAB_REGISTRY = new ManagerTabRegistry();

	private List<ManagerTabData> managerTabDataList = new ArrayList<ManagerTabData>();

	private ManagerTabRegistry() {
	}

	public static ManagerTabRegistry getTabRegistry() {
		return TAB_REGISTRY;
	}

	/**
	 *  Register a {@link ManagerTabData}.
	 *
	 * @param managerTabData contains all info for creating tab
	 */
	public void register(ManagerTabData managerTabData) {
		managerTabDataList.add(managerTabData);
	}

	/**
	 * Returns all tabs registered in a map.
	 *
	 * @return map of tabs
	 */
	public List<ManagerTabData> getManagerTabDataList() {
		return managerTabDataList;
	}

	/**
	 * Interface conting info necessary to create a tab in the manager panel.
	 *
	 * @author Jan Venstermans
	 */
	public interface ManagerTabData {

		String getTabName();

		Canvas getCanvas();

		List<Role> getVisibleForRoles();
	}
}
