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
package org.geomajas.plugin.deskmanager.client.gwt.geodesk;

import org.geomajas.command.dto.GetConfigurationResponse;



/**
 * Handler that is called when a geodesk is loaded.
 * 
 * @author Oliver May
 */
public interface GeodeskInitializationHandler {
	
	/**
	 * Called when the geodesk is initialized.
	 * 
	 * @param response the response
	 */
	void initialized(GetConfigurationResponse response);

}
