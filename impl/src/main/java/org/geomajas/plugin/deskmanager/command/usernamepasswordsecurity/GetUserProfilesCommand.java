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
package org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity;

import org.geomajas.command.Command;
import org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.dto.GetUserProfilesRequest;
import org.geomajas.plugin.deskmanager.command.usernamepasswordsecurity.dto.GetUserProfilesResponse;
import org.geomajas.plugin.deskmanager.domain.usernamepasswordsecurity.GroupMember;
import org.geomajas.plugin.deskmanager.service.common.DtoConverterService;
import org.geomajas.plugin.deskmanager.service.usernamepasswordsecurity.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Command for finding a user by it's id.
 * 
 * @author Jan Venstermans
 * 
 */
@Component(GetUserProfilesRequest.COMMAND)
public class GetUserProfilesCommand implements Command<GetUserProfilesRequest, GetUserProfilesResponse> {

	private static final Logger LOG = LoggerFactory.getLogger(GetUserProfilesCommand.class);

	@Autowired
	private UserService userService;

	@Autowired
	private DtoConverterService converterService;

	@Override
	public GetUserProfilesResponse getEmptyCommandResponse() {
		return new GetUserProfilesResponse();
	}

	@Override
	public void execute(GetUserProfilesRequest request, GetUserProfilesResponse response) throws Exception {
		try {
			List<GroupMember> groupMemberList = userService.findGroupsOfUser(request.getId());
			for (GroupMember groupMember : groupMemberList) {
				response.getProfiles().add(converterService.toProfileDto(groupMember));
			}
			LOG.info("found " + response.getProfiles().size() + " profiles for user " + request.getId());
		} catch (Exception e) {
			LOG.error("Unexpected error", e);
			throw e;
		}
	}

}
