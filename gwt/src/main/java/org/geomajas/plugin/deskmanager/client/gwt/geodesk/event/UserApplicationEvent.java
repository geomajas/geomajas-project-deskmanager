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
package org.geomajas.plugin.deskmanager.client.gwt.geodesk.event;

import com.google.gwt.event.shared.GwtEvent;
import org.geomajas.plugin.deskmanager.client.gwt.common.GwtUserApplication;

/**
 * User application event thrown by the {@link UserApplicationHandler}.
 * 
 * @author Oliver May
 */
public class UserApplicationEvent extends GwtEvent<UserApplicationHandler> {

	public static final Type<UserApplicationHandler> TYPE = new Type<UserApplicationHandler>();

	private GwtUserApplication userApplication;

	/**
	 * Construct a new user applicaiton event.
	 * 
	 * @param userApplication the application
	 */
	public UserApplicationEvent(GwtUserApplication userApplication) {
		this.setUserApplication(userApplication);
	}

	/**
	 * Set the user application.
	 * 
	 * @param userApplication the user application.
	 */
	public void setUserApplication(GwtUserApplication userApplication) {
		this.userApplication = userApplication;
	}

	/**
	 * Get the user application.
	 * 
	 * @return the user application/
	 */
	public GwtUserApplication getUserApplication() {
		return userApplication;
	}

	@Override
	public Type<UserApplicationHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserApplicationHandler handler) {
		handler.onUserApplicationLoad(this);
	}
}
