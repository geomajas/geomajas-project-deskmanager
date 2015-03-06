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

import com.smartgwt.client.widgets.Canvas;
import org.geomajas.plugin.deskmanager.domain.security.dto.Role;

import java.util.List;


/**
 * Default implementation of
 * {@link org.geomajas.plugin.deskmanager.client.gwt.manager.ManagerTabRegistry.ManagerTabData},
 * extending {@link AbstractManagerTabDataWithoutCanvas}.
 * In this implementation, the canvas element is available on construction of the object.
 *
 * @author Jan Venstermans
 */
public class ManagerTabDataImpl extends AbstractManagerTabDataWithoutCanvas {

	private Canvas canvas;

	/**
	 * Default constructor, all values provided.
	 *
	 * @param tabName
	 * @param canvas
	 * @param visibleForRoles on null, all roles can see the tab
	 */
	public ManagerTabDataImpl(String tabName, Canvas canvas, List<Role> visibleForRoles) {
		super(tabName, visibleForRoles);
		this.canvas = canvas;
	}

	@Override
	public Canvas getCanvas() {
		return canvas;
	}
}
