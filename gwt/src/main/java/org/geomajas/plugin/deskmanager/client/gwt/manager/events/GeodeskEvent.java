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
package org.geomajas.plugin.deskmanager.client.gwt.manager.events;

import org.geomajas.plugin.deskmanager.domain.dto.GeodeskDto;

/**
 * Event for some action on a {@link GeodeskDto}.
 * 
 * @author Jan De Moerloose
 * @author Jan Venstermans
 *
 */
public class GeodeskEvent {

	/**
	 * Action that triggered the event.
	 */
	public enum Action {
		CREATE,
		DELETE,
		CHANGE
	}

	private final GeodeskDto geodesk;

	private final Action action;

	public GeodeskEvent(GeodeskDto geodesk, Action action) {
		this.geodesk = geodesk;
		this.action = action;
	}

	public GeodeskDto getGeodesk() {
		return geodesk;
	}

	public Action getAction() {
		return action;
	}
}
