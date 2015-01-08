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
package org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.steps;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.DataReceiverWizardStep;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.NewLayerModelWizardWindow;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.Wizard;
import org.geomajas.plugin.deskmanager.client.gwt.manager.i18n.ManagerMessages;
import org.geomajas.plugin.deskmanager.client.gwt.manager.service.DataCallback;
import org.geomajas.plugin.deskmanager.client.gwt.manager.service.DiscoveryCommService;
import org.geomajas.plugin.deskmanager.command.manager.dto.RasterCapabilitiesInfo;

import java.util.List;

/**
 * @author Jan De Moerloose
 * @author Jan Venstermans
 */
public class WmsChooseLayerStep extends AbstractChooseLayerStep implements DataReceiverWizardStep {

	private static final ManagerMessages MESSAGES = GWT.create(ManagerMessages.class);

	private static final String FLD_NAME = "name";

	private static final String FLD_CRS = "crs";

	private static final String FLD_DESC = "description";

	private static final String FLD_INFO = "info";

	/**
	 * Default constructor.
	 *
	 * @param parent
	 */
	public WmsChooseLayerStep(final Wizard parent) {
		super(NewLayerModelWizardWindow.STEP_WMS_CHOOSE_LAYER, 
			MESSAGES.wmsChooseLayerStepNumbering() + " "  + MESSAGES.wmsChooseLayerStepTitle(), 
				false, parent, NewLayerModelWizardWindow.STEP_WMS_PROPS,
				NewLayerModelWizardWindow.STEP_WMS_PREVIEW_LAYER);
		setWindowTitle(MESSAGES.wmsChooseLayerStepTitle());
	}

	@Override
	protected void createFieldsOfGrid() {
		grid = new ListGrid();
		grid.setWidth100();
		grid.setHeight("*");
		grid.setSelectionType(SelectionStyle.SINGLE);
		grid.setShowAllRecords(true);
		grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {

			public void onCellDoubleClick(CellDoubleClickEvent event) {
				parent.fireNextStepEvent();
			}
		});

		ListGridField nameFld = new ListGridField(FLD_NAME, MESSAGES.wmsChooseLayerStepName());
		nameFld.setType(ListGridFieldType.TEXT);
		nameFld.setWidth("*");

		ListGridField crsFld = new ListGridField(FLD_CRS, MESSAGES.wmsChooseLayerStepCRS());
		crsFld.setType(ListGridFieldType.TEXT);
		crsFld.setWidth(75);

		ListGridField descFld = new ListGridField(FLD_DESC, MESSAGES.wmsChooseLayerStepDescription());
		descFld.setType(ListGridFieldType.TEXT);
		descFld.setWidth("*");

		grid.setFields(nameFld, crsFld, descFld);
	}

	@Override
	protected void executeCapabilitiesStep() {
		DiscoveryCommService.getRasterCapabilities(connectionProps,
				new DataCallback<List<RasterCapabilitiesInfo>>() {

					public void execute(List<RasterCapabilitiesInfo> result) {
						//First add all map crs
						for (RasterCapabilitiesInfo wli : result) {
							ListGridRecord lgr = new ListGridRecord();
							lgr.setAttribute(FLD_NAME, wli.getName());
							lgr.setAttribute(FLD_CRS, wli.getCrs());
							lgr.setAttribute(FLD_DESC, wli.getDescription());
							lgr.setAttribute(FLD_INFO, wli);
							grid.addData(lgr);
						}
						//First add all other crs
					}
				}, new DataCallback<String>() {

					public void execute(String result) {
						showFailMessage(result);
					}
				});
	}

	@Override
	public boolean stepFinished() {
		final WmsPreviewLayerStep nextStep = (WmsPreviewLayerStep) parent.getStep(getNextStep());
		RasterCapabilitiesInfo info = (RasterCapabilitiesInfo) grid.getSelectedRecord().getAttributeAsObject(FLD_INFO);
		nextStep.setData(connectionProps, info);
		return super.stepFinished();
	}
}
