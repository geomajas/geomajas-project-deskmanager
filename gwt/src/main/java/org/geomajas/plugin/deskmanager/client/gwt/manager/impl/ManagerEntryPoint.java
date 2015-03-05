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
package org.geomajas.plugin.deskmanager.client.gwt.manager.impl;

import com.smartgwt.client.widgets.Canvas;
import org.geomajas.plugin.deskmanager.client.gwt.manager.ManagerTabRegistry;
import org.geomajas.plugin.deskmanager.client.gwt.manager.blueprints.Blueprints;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.Datalayers;
import org.geomajas.plugin.deskmanager.client.gwt.manager.geodesk.Geodesks;
import org.geomajas.plugin.deskmanager.client.gwt.manager.gin.DeskmanagerClientGinjector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;
import org.geomajas.plugin.deskmanager.client.gwt.manager.i18n.ManagerMessages;
import org.geomajas.plugin.deskmanager.domain.security.dto.Role;

import java.util.Collections;

/**
 * @author Oliver May
 * @author Kristof Heirwegh
 * @author Jan Venstermans
 */
public class ManagerEntryPoint implements EntryPoint {

	private final DeskmanagerClientGinjector ginjector = GWT.create(DeskmanagerClientGinjector.class);

	private static final ManagerMessages MESSAGES = GWT.create(ManagerMessages.class);

	public DeskmanagerClientGinjector getGinjector() {
		return ginjector;
	}

	private static ManagerEntryPoint instance;
	
	public ManagerEntryPoint() {
		instance = this;
	}

	@Override
	public void onModuleLoad() {
		DelayedBindRegistry.bind(ginjector);

		// configure tabs, order is important
		ManagerTabRegistry.getTabRegistry().register(
				new AbstractManagerTabDataWithoutCanvas(MESSAGES.mainTabGeodesks(), null) {

					@Override
					public Canvas getCanvas() {
						return new Geodesks();
					}
				});
		ManagerTabRegistry.getTabRegistry().register(
				new AbstractManagerTabDataWithoutCanvas(MESSAGES.mainTabDataLayers(), null) {

					@Override
					public Canvas getCanvas() {
						return new Datalayers();
					}
				});
		ManagerTabRegistry.getTabRegistry().register(
				new AbstractManagerTabDataWithoutCanvas(MESSAGES.mainTabBlueprints(),
						Collections.singletonList(Role.ADMINISTRATOR)) {

					@Override
					public Canvas getCanvas() {
						return new Blueprints();
					}
				});
	}
	
	public static ManagerEntryPoint getInstance() {
		return instance;
	}
}
