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

package org.geomajas.plugin.deskmanager.client.gwt.geodesk.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.command.dto.GetConfigurationResponse;
import org.geomajas.gwt.client.Geomajas;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.command.event.DispatchStoppedEvent;
import org.geomajas.gwt.client.command.event.DispatchStoppedHandler;
import org.geomajas.gwt.client.i18n.I18nProvider;
import org.geomajas.gwt.client.map.event.MapModelChangedEvent;
import org.geomajas.gwt.client.map.event.MapModelChangedHandler;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.plugin.deskmanager.client.gwt.common.GdmLayout;
import org.geomajas.plugin.deskmanager.client.gwt.common.GwtUserApplication;
import org.geomajas.plugin.deskmanager.client.gwt.common.util.DeskmanagerLayout;
import org.geomajas.plugin.deskmanager.client.gwt.geodesk.GeodeskInitializationHandler;
import org.geomajas.plugin.deskmanager.client.gwt.geodesk.event.UserApplicationEvent;
import org.geomajas.plugin.deskmanager.client.gwt.geodesk.event.UserApplicationHandler;
import org.geomajas.plugin.deskmanager.client.gwt.geodesk.i18n.GeodeskMessages;

/**
 * <p>
 * A widget that covers the entire browser window, displaying application loading progress, and that fades out after the
 * application has been loaded. Your basic loading screen.
 * </p>
 * <p>
 * This screen by default displays a Geomajas logo, a title, and a progress bar that shows the loading progress. Both
 * the logo and the title can be changed to your wishes. The title is given to the constructor and the logo (together
 * with it's width) can be given through getters and setters. Note that you have to set these before you call the draw.
 * Also note that when setting a new logo, you have to set it's width. This width may not be larger than 480 pixels.
 * </p>
 * <p>
 * Use: add this loading screen to the main page by simply calling it's <code>draw</code> method. Do this AFTER you have
 * drawn your application layout, so the loading screen is placed on top of it.
 * </p>
 * 
 * @author Oliver May
 * @author Pieter De Graef
 */
public class LoadingScreen extends VLayout implements GeodeskInitializationHandler {

	private Label label;

	private Progressbar progressBar;

	private int progressPercentage;

	private boolean fadingDone;

	private HandlerRegistration onLoadLoketRegistration;

	private HandlerRegistration onLoadRegistration;

	// private Label titleLabel;

	private Img logoImg;

	private static final GeodeskMessages MESSAGES = GWT.create(GeodeskMessages.class);

	// -------------------------------------------------------------------------
	// Constructor:
	// -------------------------------------------------------------------------

	/**
	 * Create a loading screen.
	 */
	public LoadingScreen() {
		this(MESSAGES.loadingScreenMessage());
	}

	/**
	 * Create a loading screen with a title to be displayed.
	 * 
	 * @param applicationTitle
	 *            The application's title. This will be displayed right under the logo image.
	 */
	public LoadingScreen(String applicationTitle) {
		super();

		setCursor(Cursor.WAIT);

		VLayout inner = new VLayout();
		inner.setAlign(Alignment.CENTER);
		inner.setWidth(DeskmanagerLayout.loadingScreenWidth);
		inner.setStyleName(DeskmanagerLayout.STYLE_LOADING_SCREEN);
		inner.setHeight100();

		logoImg = new Img(GdmLayout.loadingLogo);
		logoImg.setAlign(Alignment.CENTER);
		logoImg.setHeight100();
		inner.addMember(logoImg);

		VLayout progressLayout = new VLayout();
		progressLayout.setStyleName(DeskmanagerLayout.STYLE_PROGRESSBAR_LAYOUT);
		progressLayout.setHeight(80);
		progressLayout.setPadding(15);
		progressLayout.setAlign(Alignment.CENTER);

		label = new Label(I18nProvider.getGlobal().loadScreenDownLoadText());
		label.setLayoutAlign(Alignment.CENTER);
		label.setHeight(15);
		label.setOpacity(100);
		label.setStyleName(DeskmanagerLayout.STYLE_PROGRESSBAR_LABEL);
		progressLayout.addMember(label);

		progressBar = new Progressbar();
		progressBar.setStyleName(DeskmanagerLayout.STYLE_PROGRESSBAR);
		progressBar.setHeight(DeskmanagerLayout.progressBarHeight);
		progressBar.setWidth100();
		progressBar.setVertical(false);
		progressBar.setLayoutAlign(Alignment.CENTER);
		progressBar.setLayoutAlign(VerticalAlignment.CENTER);
		progressLayout.addMember(progressBar);
		inner.addMember(progressLayout);

		HLayout hCenter = new HLayout();
		hCenter.setAlign(Alignment.CENTER);
		hCenter.setWidth100();
		hCenter.setHeight(DeskmanagerLayout.loadingScreenHeight);
		hCenter.addMember(inner);

		setHeight100();
		setWidth100();
		setAlign(Alignment.CENTER);
		addMember(hCenter);
	}

	public void registerGeodesk(final GwtUserApplication geoDesk) {
		if (geoDesk != null) {
			onLoadLoketRegistration = geoDesk.addUserApplicationLoadedHandler(new UserApplicationHandler() {

				public void onUserApplicationLoad(UserApplicationEvent event) {
					if (geoDesk.getBannerUrl() != null) {
						logoImg.setSrc(Geomajas.getDispatcherUrl() + geoDesk.getBannerUrl());
					}
					onLoadLoketRegistration.removeHandler();
					registerMap(event.getUserApplication().getMainMapWidget());
				}
			});
		}
	}

	private void registerMap(final MapWidget mapWidget) {
		if (mapWidget != null) {
			onLoadRegistration = mapWidget.getMapModel().addMapModelChangedHandler(new MapModelChangedHandler() {

				public void onMapModelChanged(MapModelChangedEvent event) {
					onLoadRegistration.removeHandler();
					label.setContents(I18nProvider.getGlobal().loadScreenLoadText());
					if (GwtCommandDispatcher.getInstance().isBusy()) {
						GwtCommandDispatcher.getInstance().addDispatchStoppedHandler(new DispatchStoppedHandler() {

							public void onDispatchStopped(DispatchStoppedEvent event) {
								fadeOut();
							}
						});
					} else {
						fadeOut();
					}
				}
			});
		}
	}

	// -------------------------------------------------------------------------
	// Private methods:
	// -------------------------------------------------------------------------

	protected void onDraw() {
		super.onDraw();
		Timer timer = new Timer() {

			public void run() {
				progressPercentage += 10;
				progressBar.setPercentDone(progressPercentage);
				if (progressPercentage < 100) {
					schedule(50);
				}
			}
		};
		timer.schedule(50);
	}

	public void fadeOut() {
		if (!fadingDone) {

			Timer fadeOutDelay = new Timer() {

				@Override
				public void run() {
					// progressBar.setPercentDone(100);
					label.setContents(I18nProvider.getGlobal().loadScreenReadyText());
					setCursor(Cursor.DEFAULT);

					// FIXME: in globale config
					setAnimateTime(1000);

					animateFade(0, new AnimationCallback() {

						public void execute(boolean earlyFinish) {
							LoadingScreen.this.destroy();
						}
					});
					fadingDone = true;
				}
			};
			// delay another second omdat' schoon moet zijn :)
			fadeOutDelay.schedule(1000);
		}
	}

	public void initialized(GetConfigurationResponse response) {
		// TODO
	}
}
