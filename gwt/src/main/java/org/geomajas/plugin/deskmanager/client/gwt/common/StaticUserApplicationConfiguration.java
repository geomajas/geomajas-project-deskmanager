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
package org.geomajas.plugin.deskmanager.client.gwt.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Configurable User Application Configuration.
 *
 * @author Oliver May
 *
 */
public class StaticUserApplicationConfiguration implements UserApplicationConfiguration {

	private String clientApplicationKey;

	private String clientApplicationName;

	private List<String> supportedLayerWidgetKeys = new ArrayList<String>();

	private List<String> supportedOverviewMapWidgetKeys = new ArrayList<String>();

	private List<String> supportedMainMapWidgetKeys = new ArrayList<String>();

	private List<String> supportedApplicationWidgetKeys = new ArrayList<String>();

	public StaticUserApplicationConfiguration() {

	}

	public StaticUserApplicationConfiguration(String clientApplicationKey, String clientApplicationName) {
		this.clientApplicationKey = clientApplicationKey;
		this.clientApplicationName = clientApplicationName;
	}

	public StaticUserApplicationConfiguration(String clientApplicationKey, String clientApplicationName,
			List<String> supportedLayerWidgetKeys, List<String> supportedOverviewMapWidgetKeys,
			List<String> supportedMainMapWidgetKeys, List<String> supportedApplicationWidgetKeys) {
		this.clientApplicationKey = clientApplicationKey;
		this.clientApplicationName = clientApplicationName;
		this.supportedLayerWidgetKeys = supportedLayerWidgetKeys;
		this.supportedOverviewMapWidgetKeys = supportedOverviewMapWidgetKeys;
		this.supportedMainMapWidgetKeys = supportedMainMapWidgetKeys;
		this.supportedApplicationWidgetKeys = supportedApplicationWidgetKeys;
	}

	public void setClientApplicationKey(String clientApplicationKey) {
		this.clientApplicationKey = clientApplicationKey;
	}

	public void setClientApplicationName(String clientApplicationName) {
		this.clientApplicationName = clientApplicationName;
	}

	public void setSupportedLayerWidgetKeys(List<String> supportedLayerWidgetKeys) {
		this.supportedLayerWidgetKeys = supportedLayerWidgetKeys;
	}

	public void setSupportedOverviewMapWidgetKeys(List<String> supportedOverviewMapWidgetKeys) {
		this.supportedOverviewMapWidgetKeys = supportedOverviewMapWidgetKeys;
	}

	public void setSupportedMainMapWidgetKeys(List<String> supportedMainMapWidgetKeys) {
		this.supportedMainMapWidgetKeys = supportedMainMapWidgetKeys;
	}

	public void setSupportedApplicationWidgetKeys(List<String> supportedApplicationWidgetKeys) {
		this.supportedApplicationWidgetKeys = supportedApplicationWidgetKeys;
	}

	@Override
	public String getClientApplicationKey() {
		return clientApplicationKey;
	}

	@Override
	public String getClientApplicationName() {
		return clientApplicationName;
	}

	@Override
	public List<String> getSupportedApplicationWidgetKeys() {
		return supportedApplicationWidgetKeys;
	}

	@Override
	public List<String> getSupportedMainMapWidgetKeys() {
		return supportedMainMapWidgetKeys;
	}

	@Override
	public List<String> getSupportedOverviewMapWidgetKeys() {
		return supportedOverviewMapWidgetKeys;
	}

	@Override
	public List<String> getSupportedLayerWidgetKeys() {
		return supportedLayerWidgetKeys;
	}
}
