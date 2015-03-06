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
package org.geomajas.plugin.deskmanager.client.gwt.manager;

import com.smartgwt.client.widgets.layout.HLayout;
import org.geomajas.plugin.deskmanager.client.gwt.manager.common.ManagerTab;
import org.geomajas.plugin.deskmanager.client.gwt.manager.events.EditSessionEvent;
import org.geomajas.plugin.deskmanager.client.gwt.manager.events.EditSessionHandler;
import org.geomajas.plugin.deskmanager.client.gwt.manager.events.Whiteboard;
import org.geomajas.plugin.deskmanager.client.gwt.manager.i18n.ManagerMessages;
import org.geomajas.plugin.deskmanager.domain.security.dto.Role;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

import java.util.List;

/**
 * The main layout of the manager console, containing tabs.
 * 
 * @author Oliver May
 * @author Jan Venstermans
 */
public class ManagerLayout extends VLayout implements EditSessionHandler {
	private static final ManagerMessages MESSAGES = GWT.create(ManagerMessages.class);
	
	private TabSet tabSet;

	public ManagerLayout(HLayout header) {
		this();
		addMember(header, 0);
	}

	public ManagerLayout() {
		super();
		setWidth100();
		setHeight100();

		// ---- main section ----
		tabSet = new TabSet();
		tabSet.setWidth100();
		tabSet.setHeight100();

		for (ManagerTabRegistry.ManagerTabData managerTabData :
				ManagerTabRegistry.getTabRegistry().getManagerTabDataList()) {
			if (isCurrentUserAllowedToSeeTab(managerTabData.getVisibleForRoles())) {
				Tab tab = new Tab(managerTabData.getTabName());
				tab.setPane(managerTabData.getCanvas());
				tabSet.addTab(tab);
			}
		}

		addMember(tabSet);
		
		tabSet.addTabSelectedHandler(new TabSelectedHandler() {
			
			public void onTabSelected(TabSelectedEvent event) {
				Canvas tab = event.getTab().getPane();
				if (tab != null) {
					((ManagerTab) tab).readData();
				}
			}
		});
		
		Whiteboard.registerHandler(this);
	}

	public void destroy() {
		Whiteboard.unregisterHandler(this);
		super.destroy();
	}


	// -- EditSessionHandler--------------------------------------------------------

	public void onEditSessionChange(EditSessionEvent ese) {
		boolean disabled = ese.isSessionStart();
		for (Tab tab : tabSet.getTabs()) {
			if (tab.getPane() == null || !ese.isParentOfRequestee(tab.getPane())) {
				tab.setDisabled(disabled);
			}
		}
	}

	// -- private methods--------------------------------------------------------

	/**
	 * Indicates whether current logged in user can see a tab, based on a role list.
	 * Will return true is role list is null or when logged in user's role is in the role list.
	 * Will return false when user's role is not in the provided role list.
	 *
	 * @param allowedRoleList list of roles
	 * @return can see tab
	 */
	private boolean isCurrentUserAllowedToSeeTab(List<Role> allowedRoleList) {
		if (allowedRoleList == null) {
			return true;
		}
		return allowedRoleList.contains(ManagerApplicationLoader.getInstance().getUserProfile().getRole());
	}

}
