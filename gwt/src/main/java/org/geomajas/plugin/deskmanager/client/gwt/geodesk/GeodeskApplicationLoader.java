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
package org.geomajas.plugin.deskmanager.client.gwt.geodesk;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import org.geomajas.command.dto.GetConfigurationResponse;
import org.geomajas.gwt.client.command.TokenRequestHandler;
import org.geomajas.plugin.deskmanager.client.gwt.common.GdmLayout;
import org.geomajas.plugin.deskmanager.client.gwt.common.GwtUserApplication;
import org.geomajas.plugin.deskmanager.client.gwt.common.HasTokenRequestHandler;
import org.geomajas.plugin.deskmanager.client.gwt.common.UserApplicationConfiguration;
import org.geomajas.plugin.deskmanager.client.gwt.common.UserApplicationRegistry;
import org.geomajas.plugin.deskmanager.client.gwt.common.impl.DeskmanagerTokenRequestHandler;
import org.geomajas.plugin.deskmanager.client.gwt.geodesk.event.UserApplicationEvent;
import org.geomajas.plugin.deskmanager.client.gwt.geodesk.event.UserApplicationHandler;
import org.geomajas.plugin.deskmanager.client.gwt.geodesk.i18n.GeodeskMessages;
import org.geomajas.plugin.deskmanager.client.gwt.geodesk.impl.LoadingScreen;
import org.geomajas.plugin.deskmanager.domain.dto.DeskmanagerApplicationInfoUserData;

/**
 * Main class for deskmanager applications. This entrypoint will show a loading screen and will load the deskmanager
 * application, if it's needed asking for a login role.
 * 
 * The entrypoint listens to Mapwidget and MapModel events to set some generic configuration options.
 *
 * @author Oliver May
 *
 *
 */
public class GeodeskApplicationLoader implements HasTokenRequestHandler {

	private static final GeodeskMessages MESSAGES = GWT.create(GeodeskMessages.class);

	private GwtUserApplication geodesk;

	private LoadingScreen loadScreen;

	private String securityToken;

	private TokenRequestHandler fallbackHandler;

	/**
	 * Constructor for the GeodeskApplicationLoader.
	 */
	public GeodeskApplicationLoader() {
	}

	/**
	 * Load a geodesk application. If needed this will first ask for the correct user role and then load the
	 * application.
	 * 
	 * The presentation is added to the layout using a {@link GwtUserApplication}, the key for this application is
	 * loaded from the configuration. User application must be registered to the {@link UserApplicationRegistry}.
	 * 
	 * @param callback
	 *            called when everything is drawn and ready to add to the application
	 */
	public void loadApplication(final LoadingCallback callback) {
		loadApplication(callback, null);
	}

	/**
	 * Load a geodesk application. If needed this will first ask for the correct user role and then load the
	 * application.
	 * 
	 * The presentation is added to the layout using a {@link GwtUserApplication}, the key for this application is
	 * loaded from the configuration. User application must be registered to the {@link UserApplicationRegistry}.
	 * 
	 * You can add a user application handler that is called once the user application is loaded and added to the
	 * layout.
	 * 
	 * @param callback
	 *            called when everything is drawn and ready to add to the layout
	 * @param handler
	 *            the user application fallbackHandler
	 */
	public void loadApplication(final LoadingCallback callback, final UserApplicationHandler handler) {
		// First Install a loading screen
		// FIXME: i18n
		loadScreen = new LoadingScreen();
		loadScreen.setZIndex(GdmLayout.loadingZindex);
		loadScreen.draw();

		String geodeskId = GdmLayout.geodeskIdUtil.parseGeodeskId(Window.Location.getHref());
		if (geodeskId == null) {
			Window.alert(MESSAGES.noGeodeskIdGivenError());
			return;
		}

		GeodeskInitializer initializer = new GeodeskInitializer(fallbackHandler);
		initializer.addHandler(new GeodeskInitializationHandler() {

			public void initialized(GetConfigurationResponse response) {
				DeskmanagerApplicationInfoUserData userData =
						(DeskmanagerApplicationInfoUserData) response.getApplication().getUserData();
				GdmLayout.version = userData.getDeskmanagerVersion();
				GdmLayout.build = userData.getDeskmanagerBuild();

				// Load geodesk from registry
				UserApplicationConfiguration userApplication = UserApplicationRegistry.getInstance().get(userData
						.getUserApplicationKey());

				if (userApplication != null && userApplication instanceof GwtUserApplication) {
					geodesk = (GwtUserApplication) userApplication;
					geodesk.setApplicationId(response.getApplication().getId());
					geodesk.setClientApplicationInfo(response.getApplication());

					// Register the geodesk to the loading screen (changes banner, and name), and set the page title
					loadScreen.registerGeodesk(geodesk);
					if (null != Document.get()) {
						Document.get().setTitle(geodesk.getName());
					}

					// Load the geodesk
					// Build main layout
					callback.loaded(geodesk.loadGeodesk());

					if (handler != null) {
						handler.onUserApplicationLoad(new UserApplicationEvent(geodesk));
					}
					
				} else {
					Window.alert(MESSAGES.noSuchGeodeskExists());
				}
			}
		});

		// Get application info for the geodesk
		initializer.loadApplication(geodeskId, new DeskmanagerTokenRequestHandler(geodeskId, this.fallbackHandler));
	}

	@Override
	public void setTokenRequestHandler(TokenRequestHandler fallbackHandler) {
		this.fallbackHandler = fallbackHandler;
	}

	/**
	 * Callback that is called when the geodesk is loaded.
	 * 
	 * @author Oliver May
	 * 
	 */
	public interface LoadingCallback {

		void loaded(Widget w);
	}

}
