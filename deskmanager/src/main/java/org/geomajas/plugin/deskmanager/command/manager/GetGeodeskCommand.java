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
package org.geomajas.plugin.deskmanager.command.manager;

import org.geomajas.command.Command;
import org.geomajas.plugin.deskmanager.command.manager.dto.GeodeskResponse;
import org.geomajas.plugin.deskmanager.command.manager.dto.GetGeodeskRequest;
import org.geomajas.plugin.deskmanager.service.common.DtoConverterService;
import org.geomajas.plugin.deskmanager.service.common.GeodeskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Command that reads the geodesk dto for display and manipulation in the management module.
 * 
 * @author Oliver May
 * @author Kristof Heirwegh
 */
@Component(GetGeodeskRequest.COMMAND)
@Transactional(rollbackFor = { Exception.class })
public class GetGeodeskCommand implements Command<GetGeodeskRequest, GeodeskResponse> {

	private final Logger log = LoggerFactory.getLogger(GetGeodeskCommand.class);

	@Autowired
	private GeodeskService geodeskService;

	@Autowired
	private DtoConverterService dtoService;

	@Override
	public void execute(GetGeodeskRequest request, GeodeskResponse response) throws Exception {
		try {
			response.setGeodesk(dtoService.toDto(geodeskService.getGeodeskById(request.getGeodeskId()), true));
		} catch (Exception orig) {
			Exception e = new Exception("Unexpected error fetching geodesk.", orig);
			log.error(e.getLocalizedMessage(), orig);
			throw e;
		}
	}

	@Override
	public GeodeskResponse getEmptyCommandResponse() {
		return new GeodeskResponse();
	}
}
