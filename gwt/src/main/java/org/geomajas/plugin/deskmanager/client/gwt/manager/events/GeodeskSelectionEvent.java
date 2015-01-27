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
package org.geomajas.plugin.deskmanager.client.gwt.manager.events;

import org.geomajas.plugin.deskmanager.domain.dto.GeodeskDto;

/**
 * Event that indicates a change in selection of current
 * {@link org.geomajas.plugin.deskmanager.domain.dto.GeodeskDto} in the grid.
 *
 * @author Jan Venstermans
 *
 */
public class GeodeskSelectionEvent {

	private final GeodeskDto geodesk;

	/**
	 *
	 * @param geodesk currently selected geodesk. If no selected, pass null.
	 */
	public GeodeskSelectionEvent(GeodeskDto geodesk) {
		this.geodesk = geodesk;
	}

	public GeodeskDto getGeodesk() {
		return geodesk;
	}

}
