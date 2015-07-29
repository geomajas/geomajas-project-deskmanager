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
 * Utility class to change default behaviour of the deskmanager layouts.
 * 
 * @author Oliver May
 * 
 */
public final class GdmLayout { // NOSONAR

	/**
	 * The file download url.
	 */
	public static final String FILEDOWNLOAD_URL = "/fileDownload";

	/**
	 * The file download file id.
	 */
	public static final String FILEDOWNLOAD_ID = "id";

	/**
	 * The main map name.
	 */
	public static final String MAPMAIN_ID = "mainMap";

	/**
	 * The overview map name.
	 */
	public static final String MAPOVERVIEW_ID = "overviewMap";

	/**
	 * Exception code for inactive geodesks.
	 */
	public static final int EXCEPTIONCODE_GEODESKINACTIVE = 10000001;

	/**
	 * Exception code for unauthorized actions.
	 */
	public static final int EXCEPTIONCODE_UNAUTHORIZED = 100000011;

	// CHECKSTYLE VISIBILITY MODIFIER: OFF

	/** The geodesk url builder. **/
	public static GeodeskIdUtil geodeskIdUtil = new GeodeskIdUtil() {

		String geodeskPrefix = "desk/";

		@Override
		public String parseGeodeskId(String url) {
			if (!url.contains(geodeskPrefix)) {
				return null;
			}
			String geodeskId = url.substring(url.indexOf(geodeskPrefix)
					+ geodeskPrefix.length());
			//Remove trailing slash and everything after.
			if (geodeskId.contains("/")) {
				geodeskId = geodeskId.substring(0, geodeskId.indexOf('/'));
			}
			return geodeskId;
		}

		@Override
		public String buildGeodeskUrl(String geodeskId) {
			return geodeskPrefix + geodeskId;
		}
	};

	/**
	 * The url prefix of the manager application.
	 */
	public static String managerPrefix = "manager/";

	/**
	 * Z index for the role select.
	 */
	public static int roleSelectZindex = Integer.MAX_VALUE;

	/**
	 * Logo shown on loading. By default: no logo.
	 */
	public static String loadingLogo;

	/**
	 * Version of the application.
	 */
	public static String version = "";

	/**
	 * Build number of the application.
	 */
	public static String build = "";

	/**
	 * Z index for the loading screen.
	 */
	public static int loadingZindex = 300000;

	/**
	 * Average width in pixels text characters, used to calculate button size. (buttonOffset + buttonFontWidth *
	 * title.length())
	 */
	public static int buttonFontWidth = 7;

	/** Offset size of a button without the text, used to calculate button size. */
	public static int buttonOffset = 28;

	// CHECKSTYLE VISIBILITY MODIFIER: ON

	private GdmLayout() {
	}
}
