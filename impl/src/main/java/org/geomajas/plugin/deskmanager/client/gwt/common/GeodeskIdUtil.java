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
package org.geomajas.plugin.deskmanager.client.gwt.common;

/**
 * Utility to parse and build the geodesk id (from URL).
 *
 * @author Oliver May
 */
public interface GeodeskIdUtil {

	/**
	 * Parse the geodesk id for the given url.
	 *
	 * @param url the current request url
	 * @return the geodesk id
	 */
	String parseGeodeskId(String url);

	/**
	 * Build the geodesk url for the given geodesk id.
	 *
	 * @param geodeskId the geodesk id
	 * @return the geodesk url
	 */
	String buildGeodeskUrl(String geodeskId);

}
