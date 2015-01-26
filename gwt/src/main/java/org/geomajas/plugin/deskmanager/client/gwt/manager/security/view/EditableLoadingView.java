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
package org.geomajas.plugin.deskmanager.client.gwt.manager.security.view;

/**
 * Interface for a view with a list of buttons and that can be in a loading state.
 *
 * @author Jan De Moerloose
 * @author Jan Venstermans
 */
public interface EditableLoadingView extends EditableView {

	void setLoading();

	void setLoaded();
}
