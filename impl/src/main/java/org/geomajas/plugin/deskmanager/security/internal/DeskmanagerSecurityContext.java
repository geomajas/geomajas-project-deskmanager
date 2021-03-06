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

package org.geomajas.plugin.deskmanager.security.internal;

import org.geomajas.plugin.deskmanager.security.internal.role.authorization.DeskmanagerGeodeskAuthorization;
import org.geomajas.plugin.deskmanager.security.internal.role.authorization.DeskmanagerManagementAuthorization;

/**
 * The security context is a thread scoped service which allows you to query the authorization details for the logged in
 * user.
 * 
 * @author Oliver May
 * @author Kristof Heirwegh
 * @author Jan De Moerloose
 * 
 * @since 1.0.0
 * 
 */
public interface DeskmanagerSecurityContext extends DeskmanagerManagementAuthorization,
		DeskmanagerGeodeskAuthorization, DeskmanagerUserInfo {

}
