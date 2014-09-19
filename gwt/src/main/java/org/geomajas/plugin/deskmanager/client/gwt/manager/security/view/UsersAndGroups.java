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
package org.geomajas.plugin.deskmanager.client.gwt.manager.security.view;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import org.geomajas.gwt.client.util.WidgetLayout;
import org.geomajas.plugin.deskmanager.client.gwt.common.GdmLayout;
import org.geomajas.plugin.deskmanager.client.gwt.common.util.DeskmanagerLayout;
import org.geomajas.plugin.deskmanager.client.gwt.manager.events.EditSessionEvent;
import org.geomajas.plugin.deskmanager.client.gwt.manager.events.EditSessionHandler;
import org.geomajas.plugin.deskmanager.client.gwt.manager.events.Whiteboard;
import org.geomajas.plugin.deskmanager.client.gwt.manager.i18n.ManagerMessages;
import org.geomajas.plugin.deskmanager.client.gwt.manager.security.presenter.ObjectsTabHandler;
import org.geomajas.plugin.deskmanager.client.gwt.manager.security.presenter.UsersAndGroupsHandler;
import org.geomajas.plugin.deskmanager.client.gwt.manager.service.Manager;
import org.geomajas.plugin.deskmanager.domain.security.dto.TerritoryDto;
import org.geomajas.plugin.deskmanager.domain.security.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Frontend presentation of a selectable list of users/groups, and a panel that contains configuration options of the
 * selected user or group.
 * 
 * @author Jan De Moerloose
 * @author Jan Venstermans
 */
public class UsersAndGroups extends VLayout implements UsersAndGroupsView, EditSessionHandler {

	private static final ManagerMessages MESSAGES = GWT.create(ManagerMessages.class);

	private TabSet gridTabSet;

	private Map<UsersAndGroupsHandler.MainTab, Tab> mainTabsMap
			= new HashMap<UsersAndGroupsHandler.MainTab, Tab>();

	private Map<UsersAndGroupsHandler.MainTab, TabSet> tabSetWithSubTabsMap
			= new HashMap<UsersAndGroupsHandler.MainTab, TabSet>();

	private Map<UsersAndGroupsHandler.MainTab, Map<ObjectsTabHandler.SubTab, Tab>> subTabMap
			= new HashMap<UsersAndGroupsHandler.MainTab, Map<ObjectsTabHandler.SubTab, Tab>>();

	private TabSet usersTabSet;

	private Tab userDetailTab;

	private static final int MARGIN = 20;

	private UsersAndGroupsHandler handler;

	private UsersView usersView;
	private GroupsView groupsView;
	private AdminAssignView adminsView;

	private IButton userButtonNew;
	private IButton groupButtonNew;

	public UsersAndGroups() {
		super(MARGIN);

		// --------- Top part
		VLayout topContainer = new VLayout(5);
		topContainer.setShowResizeBar(true);
		topContainer.setMinHeight(200);
		topContainer.setHeight("30%");
		topContainer.setLayoutBottomMargin(5);

		/* Add buttons */
		userButtonNew = new IButton(MESSAGES.securityNewUserButtonText());
		userButtonNew.setWidth(userButtonNew.getTitle().length() * GdmLayout.buttonFontWidth + GdmLayout.buttonOffset);
		userButtonNew.setIcon(WidgetLayout.iconAdd);
		userButtonNew.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				createObject(UsersAndGroupsHandler.MainTab.USERS);
			}

		});

		groupButtonNew = new IButton(MESSAGES.securityNewGroupButtonText());
		groupButtonNew.setWidth(userButtonNew.getTitle().length() * GdmLayout.buttonFontWidth + GdmLayout.buttonOffset);
		groupButtonNew.setIcon(WidgetLayout.iconAdd);
		groupButtonNew.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				createObject(UsersAndGroupsHandler.MainTab.GROUPS);
			}
		});

		usersView = Manager.getUsersManagementViews().getUsersView();
		groupsView = Manager.getUsersManagementViews().getGroupsView();
		adminsView = Manager.getUsersManagementViews().getAdminsView();

		/* main tabs*/
		VLayout userLayout = new VLayout();
		userLayout.addMember((Canvas) usersView);
		userLayout.addMember(userButtonNew);
		Tab usersTab = new Tab(MESSAGES.securityUsersTab());
		usersTab.setPane(userLayout);

		VLayout groupLayout = new VLayout();
		groupLayout.addMember((Canvas) groupsView);
		groupLayout.addMember(groupButtonNew);
		Tab groupsTab = new Tab(MESSAGES.securityGroupsTab());
		groupsTab.setPane(groupLayout);

		VLayout adminLayout = new VLayout();
		adminLayout.addMember((Canvas) adminsView);
		Tab adminsTab = new Tab(MESSAGES.securityAdminsTab());
		adminsTab.setPane(adminLayout);

		gridTabSet = new TabSet();
		gridTabSet.addTab(usersTab);
		gridTabSet.addTab(groupsTab);
		gridTabSet.addTab(adminsTab);
		gridTabSet.setTabBarPosition(Side.LEFT);
		gridTabSet.setTabBarThickness(DeskmanagerLayout.tabBarLeftThickness);

		topContainer.addMember(gridTabSet);

		// Bottom part-------------------
		UserDetailView userDetail = Manager.getUsersManagementViews().getUserDetailView();
		userDetailTab = new Tab(MESSAGES.securityUserDetailTab());
		userDetailTab.setPane((Canvas) userDetail);

		RoleSelectAssignView<UserDto, TerritoryDto> userAssign = Manager.getUsersManagementViews().getUserAssignView();
		Tab userAssignTab = new Tab(MESSAGES.securityUserAssignTab());
		userAssignTab.setPane((Canvas) userAssign);

		usersTabSet = new TabSet();
		usersTabSet.addTab(userDetailTab);
		usersTabSet.addTab(userAssignTab);

		GroupDetailView groupDetail = Manager.getUsersManagementViews().getGroupDetailView();
		Tab groupDetailTab = new Tab(MESSAGES.securityGroupDetailTab());
		groupDetailTab.setPane((Canvas) groupDetail);

		RoleSelectAssignView groupAssign = Manager.getUsersManagementViews().getGroupAssignView();
		Tab groupAssignTab = new Tab(MESSAGES.securityGroupAssignTab());
		groupAssignTab.setPane((Canvas) groupAssign);

		TabSet groupsTabSet = new TabSet();
		groupsTabSet.addTab(groupDetailTab);
		groupsTabSet.addTab(groupAssignTab);

		HLayout detailContainer = new HLayout();
		detailContainer.setStyleName(DeskmanagerLayout.STYLE_DESKMANAGER_TABPANE_DETAIL);
		detailContainer.setMinHeight(200);
		detailContainer.setHeight("40%");
		detailContainer.setLayoutTopMargin(5);
		detailContainer.addMember(usersTabSet);
		detailContainer.addMember(groupsTabSet);

		addMember(topContainer);
		addMember(detailContainer);

		// Whiteboard.registerHandler(this);

		/* fill the maps */
		// create tabSetWithMainTabsMap
		mainTabsMap.put(UsersAndGroupsHandler.MainTab.USERS, usersTab);
		mainTabsMap.put(UsersAndGroupsHandler.MainTab.GROUPS, groupsTab);
		mainTabsMap.put(UsersAndGroupsHandler.MainTab.ADMINS, adminsTab);

		// create tabSetWithSubTabsMap
		tabSetWithSubTabsMap.put(UsersAndGroupsHandler.MainTab.USERS, usersTabSet);
		tabSetWithSubTabsMap.put(UsersAndGroupsHandler.MainTab.GROUPS, groupsTabSet);

		// create subTabMap
		Map<ObjectsTabHandler.SubTab, Tab> usersSubTabMap = new HashMap<ObjectsTabHandler.SubTab, Tab>();
		usersSubTabMap.put(ObjectsTabHandler.SubTab.DETAILS, userDetailTab);
		usersSubTabMap.put(ObjectsTabHandler.SubTab.ASSIGN, userAssignTab);
		subTabMap.put(UsersAndGroupsHandler.MainTab.USERS, usersSubTabMap);
		Map<ObjectsTabHandler.SubTab, Tab> groupsSubTabMap = new HashMap<ObjectsTabHandler.SubTab, Tab>();
		groupsSubTabMap.put(ObjectsTabHandler.SubTab.DETAILS, groupDetailTab);
		groupsSubTabMap.put(ObjectsTabHandler.SubTab.ASSIGN, groupAssignTab);
		subTabMap.put(UsersAndGroupsHandler.MainTab.GROUPS, groupsSubTabMap);
	}

	@Override
	public void setHandler(UsersAndGroupsHandler handler) {
		this.handler = handler;
		bind();
	}

	private void bind() {
		gridTabSet.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				if (mainTabsMap.containsValue(event.getTab())) {
					for (Map.Entry<UsersAndGroupsHandler.MainTab, Tab> entry : mainTabsMap.entrySet()) {
						if (entry.getValue().equals(event.getTab())) {
							onMainTabSelected(entry.getKey());
						}
					}
				}
			}

		});
		Whiteboard.registerHandler(this);
	}

	private void createObject(UsersAndGroupsHandler.MainTab objectTab) {
		handler.onCreateObjectForTab(objectTab);
	}

	@Override
	public void readData() {
//		if (gridTabSet.getSelectedTabNumber() == 0) {
//			showUsers();
//		} else {
//			showGroups();
//		}
	}

	@Override
	public void selectSubTab(UsersAndGroupsHandler.MainTab mainTab, ObjectsTabHandler.SubTab subTab) {
		if (subTabMap.containsKey(mainTab) && tabSetWithSubTabsMap.containsKey(mainTab)) {
			TabSet tabParent = tabSetWithSubTabsMap.get(mainTab);
			Map<ObjectsTabHandler.SubTab, Tab> secondTabMap = subTabMap.get(mainTab);
			if (secondTabMap.containsKey(subTab)) {
				Tab tabChild = secondTabMap.get(subTab);
				tabParent.selectTab(tabChild);
			}
		}
	}

	private void onMainTabSelected(UsersAndGroupsHandler.MainTab mainTab) {
		handler.onMainTabSelected(mainTab);
		// visibility of button part of our view, we manage this ourselves
		showTabSetOfMainTab(mainTab);
	}

	private void showTabSetOfMainTab(UsersAndGroupsHandler.MainTab mainTab) {
	   for (Map.Entry<UsersAndGroupsHandler.MainTab, TabSet> entry : tabSetWithSubTabsMap.entrySet()) {
		  if (entry.getKey().equals(mainTab)) {
			  entry.getValue().show();
		  } else {
			  entry.getValue().hide();
		  }
	   }
	}

	// -- EditSessionHandler--------------------------------------------------------

	@Override
	public void onEditSessionChange(EditSessionEvent ese) {
		boolean isStart = ese.isSessionStart();
		Canvas editSource = ese.getRequestee();
		UsersAndGroupsHandler.MainTab sourceAsSubTab = getMainTabOfSubTabPane(editSource);
		if (sourceAsSubTab != null) {
			ObjectsTabHandler.SubTab subTab = getSubTabOfSubTabPane(editSource, sourceAsSubTab);
			switch (sourceAsSubTab) {
				case USERS:
					usersView.setDisabled(isStart);
					userButtonNew.setDisabled(isStart);
					disableTabs(sourceAsSubTab, subTab, isStart);
					break;
				case GROUPS:
					groupsView.setDisabled(isStart);
					groupButtonNew.setDisabled(isStart);
					disableTabs(sourceAsSubTab, subTab, isStart);
					break;
			}

		} else {
			UsersAndGroupsHandler.MainTab sourceAsTab = getMainTabOfTabPane(editSource);
			if (sourceAsTab != null) {
				switch (sourceAsTab) {
					case ADMINS:
						//adminsView.setDisabled(isStart);
						disableTabs(sourceAsTab, null, isStart);
						break;
				}
			}
		}
	}

	private void disableTabs(UsersAndGroupsHandler.MainTab sourceMainTab,
							 ObjectsTabHandler.SubTab sourceSubTab, boolean disable) {
		// disable main tabs
		for (Map.Entry<UsersAndGroupsHandler.MainTab, Tab> tabEntry : mainTabsMap.entrySet()) {
			if (!tabEntry.getKey().equals(sourceMainTab)) {
				tabEntry.getValue().setDisabled(disable);
			}
		}
		// disable sub tabs
		if (subTabMap.containsKey(sourceMainTab)) {
			for ( Map.Entry<ObjectsTabHandler.SubTab, Tab> subTabEntry : subTabMap.get(sourceMainTab).entrySet()) {
				if (!subTabEntry.getKey().equals(sourceSubTab)) {
					subTabEntry.getValue().setDisabled(disable);
				}
			}
		}
	}

	/**
	 * Returns the {@link UsersAndGroupsHandler.MainTab} of the subTab whose Canvas is the attribute.
	 * If no subTab with the required Canvas exists, null is returned.
	 *
	 * @param subTabPane
	 * @return
	 */
	private UsersAndGroupsHandler.MainTab getMainTabOfSubTabPane(Canvas subTabPane) {
		for (Map.Entry<UsersAndGroupsHandler.MainTab, Map<ObjectsTabHandler.SubTab, Tab>> subTabMapEntry :
				subTabMap.entrySet()) {
			for (Tab subTab : subTabMapEntry.getValue().values()) {
			   if (subTab.getPane().equals(subTabPane)) {
				   return subTabMapEntry.getKey();
			   }
			}
		}
		return null;
	}

	private ObjectsTabHandler.SubTab getSubTabOfSubTabPane(Canvas editSource,
														   UsersAndGroupsHandler.MainTab mainTab) {
		for (Map.Entry<ObjectsTabHandler.SubTab, Tab> subtTabEntry : subTabMap.get(mainTab).entrySet()) {
			if (subtTabEntry.getValue().getPane().equals(editSource)) {
				return subtTabEntry.getKey();
			}
		}
		return null;
	}

	/**
	 * Returns the {@link UsersAndGroupsHandler.MainTab} of the tab whose Canvas is the attribute.
	 * If no tab with the required Canvas exists, null is returned.
	 *
	 * @param tabPane
	 * @return
	 */
	private UsersAndGroupsHandler.MainTab getMainTabOfTabPane(Canvas tabPane) {
		for (Map.Entry<UsersAndGroupsHandler.MainTab, Tab> tabMapEntry : mainTabsMap.entrySet()) {
			// main tab is a VPanel
			for (Canvas child : tabMapEntry.getValue().getPane().getChildren()) {
				if (tabPane.equals(child)) {
					return tabMapEntry.getKey();
				}
			}
		}
		return null;
	}
}