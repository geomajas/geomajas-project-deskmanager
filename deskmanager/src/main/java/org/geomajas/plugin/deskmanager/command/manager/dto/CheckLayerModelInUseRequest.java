/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2013 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
package org.geomajas.plugin.deskmanager.command.manager.dto;

import org.geomajas.command.CommandRequest;

/**
 * Request for {@link org.geomajas.plugin.deskmanager.command.manager.CheckLayerModelInUseCommand}.
 * 
 * @author Kristof Heirwegh
 * @author Oliver May
 */
public class CheckLayerModelInUseRequest implements CommandRequest {

	private static final long serialVersionUID = 1L;

	public static final String COMMAND = "command.deskmanager.manager.CheckLayerModelInUseCommand";

	private String layerModelId;

	public String getLayerModelId() {
		return layerModelId;
	}

	public void setLayerModelId(String layerModelId) {
		this.layerModelId = layerModelId;
	}
}
