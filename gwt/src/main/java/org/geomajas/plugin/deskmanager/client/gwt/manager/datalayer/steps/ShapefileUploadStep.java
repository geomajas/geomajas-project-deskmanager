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
package org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.steps;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import org.geomajas.gwt.client.util.Notify;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.NewLayerModelWizardWindow;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.Wizard;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.WizardStepPanel;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.panels.GenericUploadForm;
import org.geomajas.plugin.deskmanager.client.gwt.manager.i18n.ManagerMessages;
import org.geomajas.plugin.deskmanager.client.gwt.manager.service.DataCallback;
import org.geomajas.plugin.deskmanager.client.gwt.manager.service.ManagerCommandService;
import org.geomajas.plugin.deskmanager.command.manager.dto.ProcessShapeFileResponse;
import org.geomajas.plugin.deskmanager.domain.dto.DynamicLayerConfiguration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kristof Heirwegh
 */
public class ShapefileUploadStep extends WizardStepPanel {

	private static final ManagerMessages MESSAGES = GWT.create(ManagerMessages.class);

	private GenericUploadForm form;

	private boolean first = true;

	private Map<String, String> connectionProps = new LinkedHashMap<String, String>();

	private Dialog loadDialog;

	public ShapefileUploadStep(Wizard parent) {
		super(NewLayerModelWizardWindow.STEP_SHAPEFILE_UPLOAD, MESSAGES.shapefileUploadStepNumbering() + " "  + 
				MESSAGES.shapefileUploadStepTitle(), false, parent);
		setWindowTitle(MESSAGES.shapefileUploadStepTitle());

		form = new GenericUploadForm();
		form.setWidth100();
		form.setColWidths("125", "*");

		form.addItemChangedHandler(new ItemChangedHandler() {

			public void onItemChanged(ItemChangedEvent event) {
				fireChangedEvent();
			}
		});
		addMember(form);
	}

	@Override
	public void initialize() {
		connectionProps.clear();
		connectionProps.put(DynamicLayerConfiguration.PARAM_SOURCE_TYPE, DynamicLayerConfiguration.SOURCE_TYPE_SHAPE);
	}

	@Override
	public boolean isValid() {
		// don't check first time, otherwise errors are immediately shown
		if (first) {
			first = !first;
			return false;
		} else {
			return form.validate();
		}
	}

	@Override
	public String getNextStep() {
		return NewLayerModelWizardWindow.STEP_VECTOR_EDIT_LAYER_ATTRIBUTES;
	}

	@Override
	public String getPreviousStep() {
		return NewLayerModelWizardWindow.STEP_CHOOSE_TYPE;
	}

	@Override
	public void reset() {
		form.reset();
	}

	public String getFileName() {
		String raw = form.getFileName();
		if (raw != null && !"".equals(raw) && raw.lastIndexOf("/") > 0) {
			raw = raw.substring(raw.lastIndexOf("/") + 1);
		}
		return raw;
	}

	@Override
	public boolean stepFinished() {
		final VectorEditLayerAttributesStep nextStep = (VectorEditLayerAttributesStep) parent
				.getStep(NewLayerModelWizardWindow.STEP_VECTOR_EDIT_LAYER_ATTRIBUTES);
		if (nextStep != null) {
			form.upload(new DataCallback<String>() {

				public void execute(String fileId) {
					showProcessingDialog();

					ManagerCommandService.processShapeFileUpload(fileId, new DataCallback<ProcessShapeFileResponse>() {
						@Override
						public void execute(ProcessShapeFileResponse commandResult) {
							hideProcessingDialog();
							String result = commandResult.getLayerName();
							nextStep.setPreviousStep(NewLayerModelWizardWindow.STEP_SHAPEFILE_UPLOAD);
							if (result != null && !"".equals(result)) {
								nextStep.setData(connectionProps, result);
								nextStep.initialize();
							} else {
								nextStep.setWarning(MESSAGES.shapefileUploadStepErrorDuringUpload());
							}
						}
					});
				}
			});
			return true;
		}
		Notify.error(MESSAGES.shapefileUploadStepNextStepNotFound());
		return false;
	}

	private void showProcessingDialog() {
		if (loadDialog == null) {
			HTMLFlow msg = new HTMLFlow(MESSAGES.uploadShapefileUploadingFile());
			msg.setWidth100();
			msg.setHeight100();
			msg.setAlign(Alignment.CENTER);
			msg.setPadding(20);
			msg.setOverflow(Overflow.HIDDEN);
			loadDialog = new Dialog();
			loadDialog.setShowCloseButton(false);
			loadDialog.setWidth(330);
			loadDialog.setHeight(100);
			loadDialog.setIsModal(true);
			loadDialog.setShowModalMask(true);
			loadDialog.setTitle(MESSAGES.titlePleaseWait());
			loadDialog.addItem(msg);
		}
		loadDialog.show();
	}

	private void hideProcessingDialog() {
		if (loadDialog != null) {
			loadDialog.hide();
		}
	}

}
