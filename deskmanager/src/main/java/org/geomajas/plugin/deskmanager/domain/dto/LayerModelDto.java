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
package org.geomajas.plugin.deskmanager.domain.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.geomajas.annotation.Api;
import org.geomajas.configuration.client.ClientWidgetInfo;
import org.geomajas.configuration.client.ScaleInfo;

/**
 * Dto object for the LayerModel.
 * 
 * @author Kristof Heirwegh
 * @since 1.0.0
 */
@Api
public class LayerModelDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private boolean active;

	private boolean publiek;

	private String name;

	private String layerType;

	private String clientLayerId; // referenced Bean name

	private String owner;

	private ScaleInfo minScale;

	private ScaleInfo maxScale;

	private boolean defaultVisible;

	private boolean readOnly;

	private DynamicLayerConfiguration layerConfiguration;

	private Map<String, ClientWidgetInfo> widgetInfo = new HashMap<String, ClientWidgetInfo>();

	// ------------------------------------------------------------------

	/**
	 * Get the id for this layermodel.
	 * @return the id
	 * @since 1.0.0
	 */
	@Api
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Is this layermodel active.
	 * @return true if active
	 * @since 1.0.0
	 */
	@Api
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Is this layermodel public.
	 * @return true if public
	 * @since 1.0.0
	 */
	@Api
	public boolean isPublic() {
		return publiek;
	}

	public void setPublic(boolean publiek) {
		this.publiek = publiek;
	}

	/**
	 * Get the name of this layermodel.
	 * @return the name
	 * @since 1.0.0
	 */
	@Api
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the client layerid for this layermodel.
	 * @return the id
	 * @since 1.0.0
	 */
	@Api
	public String getClientLayerId() {
		return clientLayerId;
	}

	public void setClientLayerId(String clientLayerId) {
		this.clientLayerId = clientLayerId;
	}

	/**
	 * Get the minimum scale for this layermodel.
	 * @return the minimum scale
	 * @since 1.0.0
	 */
	@Api
	public ScaleInfo getMinScale() {
		return minScale;
	}

	public void setMinScale(ScaleInfo minScale) {
		this.minScale = minScale;
	}

	/**
	 * Get the max scale for this layermodel.
	 * @return the id
	 * @since 1.0.0
	 */
	@Api
	public ScaleInfo getMaxScale() {
		return maxScale;
	}

	public void setMaxScale(ScaleInfo maxScale) {
		this.maxScale = maxScale;
	}

	// ----------------------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientLayerId == null) ? 0 : clientLayerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LayerModelDto other = (LayerModelDto) obj;
		if (clientLayerId == null) {
			if (other.clientLayerId != null) {
				return false;
			}
		} else if (!clientLayerId.equals(other.clientLayerId)) {
			return false;
		}
		return true;
	}

	/**
	 * Is this layermodel default visible.
	 * @return true if visible
	 * @since 1.0.0
	 */
	@Api
	public boolean isDefaultVisible() {
		return defaultVisible;
	}

	public void setDefaultVisible(boolean defaultVisible) {
		this.defaultVisible = defaultVisible;
	}

	public String getLayerType() {
		return layerType;
	}

	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}

	public DynamicLayerConfiguration getLayerConfiguration() {
		return layerConfiguration;
	}

	public void setLayerConfiguration(DynamicLayerConfiguration layerConfiguration) {
		this.layerConfiguration = layerConfiguration;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setWidgetInfo(Map<String, ClientWidgetInfo> widgetInfo) {
		this.widgetInfo = widgetInfo;
	}

	/**
	 * Get the widget infos for this layermodel.
	 * @return the widget infos
	 * @since 1.0.0
	 */
	@Api
	public Map<String, ClientWidgetInfo> getWidgetInfo() {
		return widgetInfo;
	}

	// -------------------------------------------------

	public String getParameterValue(String key) {
		if (key == null || "".equals(key) || layerConfiguration == null) {
			return null;
		} else {
			return layerConfiguration.getParameterValue(key);
		}
	}

}
