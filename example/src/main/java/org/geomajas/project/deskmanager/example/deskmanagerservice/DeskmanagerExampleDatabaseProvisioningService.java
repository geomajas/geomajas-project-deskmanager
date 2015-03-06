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
package org.geomajas.project.deskmanager.example.deskmanagerservice;

import org.geomajas.geometry.service.WktException;
import org.geomajas.global.GeomajasException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Oliver May
 *
 */
public interface DeskmanagerExampleDatabaseProvisioningService {

	@Transactional
	void createData() throws WktException, GeomajasException;

}