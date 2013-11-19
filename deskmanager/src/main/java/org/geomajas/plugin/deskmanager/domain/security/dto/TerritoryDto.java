/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2013 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
package org.geomajas.plugin.deskmanager.domain.security.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geomajas.plugin.deskmanager.domain.dto.BlueprintDto;
import org.geomajas.plugin.deskmanager.domain.dto.GeodeskDto;

/**
 * DTO version of Territory, does not include geometry! 
 * See {@link org.geomajas.plugin.deskmanager.domain.security.Territory}
 * 
 * @author Oliver May
 * @author Kristof Heirwegh
 * 
 */
public class TerritoryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String name;

	private String code;

	private CategoryDto category;

	private List<BlueprintDto> blueprints = new ArrayList<BlueprintDto>();

	private List<GeodeskDto> geodesks = new ArrayList<GeodeskDto>();
	// ----------------------------------------------------------

	/**
	 * Get the identifier of this territory.
	 * 
	 * @return the identifier.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set the identifier of this territory. Usually auto generated by hibernate.
	 * 
	 * @param id
	 *            the identifier.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get the name of the territory.
	 * 
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the territory.
	 * 
	 * @param name
	 *            the name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the code of the territory. For example the ISO country code.
	 * 
	 * @return the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set the code of the territory. For example the ISO country code.
	 * 
	 * @param code
	 *            the code.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Get the optional category on the territory.
	 * 
	 * @return the category.
	 */
	public CategoryDto getCategory() {
		return category;
	}

	/**
	 * Set the optional category on the territory.
	 * 
	 * @param category
	 *            the category.
	 */
	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	/**
	 * Get a list of all the blueprints managed by this territory.
	 * 
	 * @return the list of blueprints.
	 */
	public List<BlueprintDto> getBlueprints() {
		return blueprints;
	}

	/**
	 * Set a list of all the blueprints managed by this territory.
	 * 
	 * @param blueprints
	 *            the list of blueprints.
	 */
	public void setBlueprints(List<BlueprintDto> blueprints) {
		this.blueprints = blueprints;
	}

	/**
	 * Get a list of geodesks managed by this territory.
	 * 
	 * @return the list of geodesks.
	 */
	public List<GeodeskDto> getGeodesks() {
		return geodesks;
	}

	/**
	 * Set a list of geodesks managed by this territory.
	 * 
	 * @param geodesks
	 *            the list of geodesks.
	 */
	public void setGeodesks(List<GeodeskDto> geodesks) {
		this.geodesks = geodesks;
	}
	
}
