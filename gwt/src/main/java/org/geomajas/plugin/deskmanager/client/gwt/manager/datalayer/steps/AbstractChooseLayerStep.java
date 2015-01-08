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
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import org.geomajas.gwt.client.Geomajas;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.DataReceiverWizardStep;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.Wizard;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.WizardStepPanel;
import org.geomajas.plugin.deskmanager.client.gwt.manager.i18n.ManagerMessages;

import java.util.Map;

/**
 * Abstract class for executing getCapabilities command and showing the resulting layers in a grid.
 *
 * @author Kristof Heirwegh
 * @author Jan Venstermans
 */
public abstract class AbstractChooseLayerStep extends WizardStepPanel implements DataReceiverWizardStep {

	private static final ManagerMessages MESSAGES = GWT.create(ManagerMessages.class);

	protected Map<String, String> connectionProps;

	protected ListGrid grid;

	private Label warnings;

	private String previousStep;
	private final String nextStep;

	private HandlerRegistration selectionUpdatedHandlerRegistration;

	public AbstractChooseLayerStep(String name, String title, boolean isLastStep, final Wizard parent,
								   String previousStep, String nextStep) {
		super(name, title, isLastStep, parent);
		setPreviousStep(previousStep);
		this.nextStep = nextStep;

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

		createFieldsOfGrid();
		grid.setCanResizeFields(true);

		// -------------------------------------------------

		warnings = new Label();
		warnings.setWidth100();
		warnings.setAutoHeight();
		warnings.setPadding(3);
		warnings.setOverflow(Overflow.VISIBLE);
		warnings.setVisible(false);
		warnings.setBackgroundColor("#FFCCCC");

		addMember(grid);
		addMember(warnings);
	}

	/**
	 * Set fields of grid.
	 */
	protected abstract void createFieldsOfGrid();

	/**
	 * Perform server call for getting layers of a GetCapabilities request. Service specific.
	 */
	protected abstract void executeCapabilitiesStep();

	@Override
	public void initialize() {
		if (connectionProps != null) {
			reset();
			registerSelectionUpdateHandler(true);
			grid.setShowEmptyMessage(true);
			grid.setEmptyMessage("<i>" + MESSAGES.requestingInfoFromServer() +
					" <img src='" + Geomajas.getIsomorphicDir() +
					"/images/circle.gif' style='height: 1em' /></i>");
			grid.redraw();
			executeCapabilitiesStep();
		}
	}

	@Override
	public void setData(Map<String, String> connectionProps) {
		this.connectionProps = connectionProps;
	}

	@Override
	public boolean isValid() {
		return (grid.getSelectedRecord() != null);
	}

	@Override
	public String getPreviousStep() {
		return previousStep;
	}

	@Override
	public void setPreviousStep(String previousStep) {
		this.previousStep = previousStep;
	}

	@Override
	public String getNextStep() {
		return nextStep;
	}

	@Override
	public void reset() {
		grid.deselectAllRecords();
		grid.setData(new Record[] {});
		warnings.setVisible(false);
		warnings.setContents("");
	}

	@Override
	public boolean stepFinished() {
		registerSelectionUpdateHandler(false);
		return true;
	}

	protected void showFailMessage(String result) {
		// remove the loading message
		grid.setShowEmptyMessage(false);
		reset();
		warnings.setVisible(true);
		warnings.setContents("<b><i>" + result + "</i></b>");
	}

	private void registerSelectionUpdateHandler(boolean register) {
		if (register) {
			selectionUpdatedHandlerRegistration = grid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {

				public void onSelectionUpdated(SelectionUpdatedEvent event) {
					fireChangedEvent();
				}
			});
		} else  {
			selectionUpdatedHandlerRegistration.removeHandler();
		}
	}
}
