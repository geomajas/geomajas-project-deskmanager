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
package org.geomajas.plugin.deskmanager.client.gwt.common.i18n;

import com.google.gwt.i18n.client.Messages;

/**
 * Interface for i18n common messages.
 * 
 * @author An Buyle
 *
 * @author Dosi Bingov
 *
 */
public interface CommonMessages extends Messages {

	String rolesWindowUnauthorizedWindowTitle();

	String rolesWindowaskRoleWindowTitle();
	String rolesWindowWelcome();
	String rolesWindowPleaseSpecifyRole();
	String rolesWindowInsufficientRightsForDesk();
	String rolesWindowStillInsufficientRightsForDesk();
	
	String roleUnassignedDescription();
	String roleGuestDescription();
	String roleAdministratorDescription();
	String roleDeskmanagerDescription();
	String roleConsultingUserDescription();
	String roleEditingUserDescription();

	String fileIsUploading();
	String errorWhileUploadingFile();


	// Editors

	// configuration of image configuration editor
	String imageConfigurationEditorName();
	String imageConfigLinkTitle();
	String imageConfigAltTitle();
	String imageConfigAltTooltip();
	String imageConfigFileTitle();
	String imageConfigGroupTitle();
	String imageConfigIdealSize(int width, int height);


	// configuration of loading screen configuration editor
	String loadingScreenEditorName();
	String loadingScreenGroupTitle();

	String logoutButtonText();

}
