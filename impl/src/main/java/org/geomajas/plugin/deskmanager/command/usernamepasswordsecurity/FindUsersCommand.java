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
package org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity;

import org.geomajas.command.Command;
import org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.dto.FindUsersRequest;
import org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.dto.FindUsersResponse;
import org.geomajas.plugin.deskmanager.domain.usernamepasswordsecurity.User;
import org.geomajas.plugin.deskmanager.service.common.DtoConverterService;
import org.geomajas.plugin.deskmanager.service.usernamepasswordsecurity.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Command for finding users.
 * 
 * @author Jan De Moerloose
 * 
 */
@Component(FindUsersRequest.COMMAND)
public class FindUsersCommand implements Command<FindUsersRequest, FindUsersResponse> {

	private static final Logger LOG = LoggerFactory.getLogger(FindUsersCommand.class);

	@Autowired
	private UserService userService;

	@Autowired
	private DtoConverterService converterService;

	@Override
	public FindUsersResponse getEmptyCommandResponse() {
		return new FindUsersResponse();
	}

	@Override
	public void execute(FindUsersRequest request, FindUsersResponse response) throws Exception {
		try {
			boolean includeProfiles = request.isIncludeProfiles();
			List<User> users = userService.findAll(includeProfiles);
			for (User user : users) {
				response.getUsers().add(converterService.toDto(user, includeProfiles));
			}
			LOG.info("Found " + users.size() + " users." +
					(includeProfiles ? "Included profiles." : "Did not include profiles"));
		} catch (Exception e) {
			LOG.error("Unexpected error", e);
			throw e;
		}
	}

}
