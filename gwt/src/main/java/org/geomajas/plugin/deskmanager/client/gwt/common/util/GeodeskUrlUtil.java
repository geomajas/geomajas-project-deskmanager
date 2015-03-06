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
package org.geomajas.plugin.deskmanager.client.gwt.common.util;

import com.google.gwt.core.client.GWT;
import org.geomajas.plugin.deskmanager.client.gwt.common.GdmLayout;
import org.geomajas.plugin.deskmanager.client.gwt.geodesk.impl.CodeServer;

/**
 * Helper class that provides url's for the geodesk previews.
 * 
 * @author Oliver May
 *
 */
public final class GeodeskUrlUtil {
	
	private GeodeskUrlUtil() { }

	public static String createPreviewUrl(String geodeskId) {
		String url = GdmLayout.geodeskIdUtil.buildGeodeskUrl(geodeskId);
		String codeServer = CodeServer.getCodeServer();
		if (codeServer.length() > 0 && url.contains("?")) {
			url += "&" + codeServer;
		} else if (codeServer.length() > 0) {
			url += "?" + codeServer;
		}

		if (isRelative(url)) {
			return GWT.getModuleBaseURL() + url;
		}
		return url;
	}

	static boolean isRelative(String url) {
		return url.startsWith("http");
	}
	
}
