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
package org.geomajas.plugin.deskmanager.command.configuration;

import org.geomajas.command.dto.GetConfigurationRequest;
import org.geomajas.command.dto.GetConfigurationResponse;
import org.geomajas.configuration.client.ClientApplicationInfo;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.geomajas.plugin.deskmanager.domain.Geodesk;
import org.geomajas.plugin.deskmanager.domain.dto.DeskmanagerApplicationInfoUserData;
import org.geomajas.plugin.deskmanager.service.common.GeodeskConfigurationService;
import org.geomajas.plugin.deskmanager.service.common.GeodeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * GetConfigurationCommand for the deskmanager. Fetches from the
 * {@link org.geomajas.plugin.deskmanager.service.common.GeodeskConfigurationService}.
 * 
 * @author Oliver May
 * @author Kristof Heirwegh
 * 
 */
public class GetConfigurationCommand extends org.geomajas.command.configuration.GetConfigurationCommand {

	@Autowired
	private GeodeskService geodeskService;

	@Autowired
	private GeodeskConfigurationService configurationService;

	/**
	 * Application id of the manager section.
	 */
	private static String managerApplicationId = "appDeskManager";

	public GetConfigurationResponse getEmptyCommandResponse() {
		return new GetConfigurationResponse();
	}

	@Transactional(readOnly = true)
	public void execute(GetConfigurationRequest request, GetConfigurationResponse response) throws Exception {
		if (null == request.getApplicationId()) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "applicationId");
			//FIXME: remove manager application stuff.
		} else if (managerApplicationId.equals(request.getApplicationId())) {
			// if the application is the manager application, then use the default
			// {@link org.geomajas.command.configuration.GetConfigurationCommand}.
			super.execute(request, response);
		} else {
			// this checks if geodesk is allowed
			Geodesk loket = geodeskService.getGeodeskByPublicId(request.getApplicationId());

			if (loket != null) {
				ClientApplicationInfo loketConfig = configurationService.createClonedGeodeskConfiguration(loket, true);
				response.setApplication(loketConfig);
				DeskmanagerApplicationInfoUserData clientUserDataInfo = new
						DeskmanagerApplicationInfoUserData();
			} else {
				throw new GeomajasException(ExceptionCode.APPLICATION_NOT_FOUND, request.getApplicationId());
			}
		}
	}

	public static void setManagerApplicationId(String managerApplicationId) {
		GetConfigurationCommand.managerApplicationId = managerApplicationId;
	}
}
