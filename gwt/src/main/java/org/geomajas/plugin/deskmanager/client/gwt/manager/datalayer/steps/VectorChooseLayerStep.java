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
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import org.geomajas.gwt.client.util.Notify;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.DataReceiverWizardStep;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.NewLayerModelWizardWindow;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.Wizard;
import org.geomajas.plugin.deskmanager.client.gwt.manager.i18n.ManagerMessages;
import org.geomajas.plugin.deskmanager.client.gwt.manager.service.DataCallback;
import org.geomajas.plugin.deskmanager.client.gwt.manager.service.DiscoveryCommService;
import org.geomajas.plugin.deskmanager.command.manager.dto.VectorCapabilitiesInfo;

import java.util.List;

/**
 * @author Kristof Heirwegh
 * @author Jan Venstermans
 */
public class VectorChooseLayerStep extends AbstractChooseLayerStep implements DataReceiverWizardStep {

	private static final ManagerMessages MESSAGES = GWT.create(ManagerMessages.class);

	private static final String FLD_GROUP = "namespace";

	private static final String FLD_NAME = "name";

	private static final String FLD_CRS = "crs";

	private static final String FLD_DESC = "description";

	private static final String FLD_GEOMTYPE = "geomtype";

	private static final String FLD_TYPENAME = "_typeName";

	private String previousStep = NewLayerModelWizardWindow.STEP_WFS_PROPS;

	public VectorChooseLayerStep(final Wizard parent) {
		super(NewLayerModelWizardWindow.STEP_VECTOR_CHOOSE_LAYER, MESSAGES.vectorChooseLayerStepNumbering() + " "  +
				MESSAGES.vectorChooseLayerStepTitle(), false, parent, NewLayerModelWizardWindow.STEP_WFS_PROPS,
				NewLayerModelWizardWindow.STEP_VECTOR_EDIT_LAYER_ATTRIBUTES);
		setWindowTitle(MESSAGES.vectorChooseLayerStepTitle());
	}

	@Override
	protected void createFieldsOfGrid() {
		ListGridField groupFld = new ListGridField(FLD_GROUP, MESSAGES.vectorChooseLayerStepParametersGroup());
		groupFld.setType(ListGridFieldType.TEXT);
		groupFld.setWidth(50);

		ListGridField nameFld = new ListGridField(FLD_NAME, MESSAGES.vectorChooseLayerStepParametersName());
		nameFld.setType(ListGridFieldType.TEXT);
		nameFld.setWidth("*");

		ListGridField crsFld = new ListGridField(FLD_CRS, "CRS");
		crsFld.setType(ListGridFieldType.TEXT);
		crsFld.setWidth(75);

		ListGridField geomFld = new ListGridField(FLD_GEOMTYPE, MESSAGES.vectorChooseLayerStepParametersType());
		geomFld.setType(ListGridFieldType.TEXT);
		geomFld.setWidth(70);

		ListGridField descFld = new ListGridField(FLD_DESC, MESSAGES.vectorChooseLayerStepParametersDescription());
		descFld.setType(ListGridFieldType.TEXT);
		descFld.setWidth("*");

		grid.setFields(groupFld, nameFld, crsFld, geomFld, descFld);
	}

	@Override
	protected void executeCapabilitiesStep() {
		DiscoveryCommService.getVectorCapabilities(connectionProps,
				new DataCallback<List<VectorCapabilitiesInfo>>() {

					public void execute(List<VectorCapabilitiesInfo> result) {
						for (VectorCapabilitiesInfo wli : result) {
							ListGridRecord lgr = new ListGridRecord();
							lgr.setAttribute(FLD_GROUP, wli.getNamespace());
							lgr.setAttribute(FLD_NAME, wli.getName());
							lgr.setAttribute(FLD_CRS, wli.getCrs());
							lgr.setAttribute(FLD_DESC, wli.getDescription());
							lgr.setAttribute(FLD_GEOMTYPE, wli.getGeometryType());
							lgr.setAttribute(FLD_TYPENAME, wli.getTypeName());
							grid.addData(lgr);
						}
					}
				}, new DataCallback<String>() {

					public void execute(String result) {
						showFailMessage(result);
					}
				});
	}

	@Override
	public boolean stepFinished() {
		VectorEditLayerAttributesStep nextStep = (VectorEditLayerAttributesStep) parent.getStep(getNextStep());
		if (nextStep != null) {
			nextStep.setData(connectionProps, grid.getSelectedRecord().getAttribute(FLD_TYPENAME));
			nextStep.setPreviousStep(getPreviousStep());
			return super.stepFinished();
		}
		Notify.error(MESSAGES.vectorChooseLayerStepNextStepNotFound());
		return false;
	}
}
