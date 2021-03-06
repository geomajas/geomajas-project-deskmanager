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
package org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.plugin.deskmanager.client.gwt.manager.common.AbstractConfigurationLayout;
import org.geomajas.plugin.deskmanager.client.gwt.manager.common.SaveButtonBar;
import org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.panels.GenericUploadForm;
import org.geomajas.plugin.deskmanager.client.gwt.manager.i18n.ManagerMessages;
import org.geomajas.plugin.deskmanager.client.gwt.manager.service.DataCallback;
import org.geomajas.plugin.deskmanager.client.gwt.manager.service.ManagerCommandService;
import org.geomajas.plugin.deskmanager.command.manager.dto.ProcessShapeFileResponse;
import org.geomajas.plugin.deskmanager.domain.dto.LayerModelDto;

/**
 * @author Kristof Heirwegh
 */
public class DatalayerShapeUpload extends AbstractConfigurationLayout {

	private static final ManagerMessages MESSAGES = GWT.create(ManagerMessages.class);

	
	private GenericUploadForm form;

	private SaveButtonBar buttonBar;

	private LayerModelDto lmd;

	private Dialog loadDialog;

	public DatalayerShapeUpload() {
		super();
		setMargin(5);
		setWidth100();

		buttonBar = new SaveButtonBar(this);
		addMember(buttonBar);

		// ----------------------------------------------------------

		form = new GenericUploadForm();
		form.setWidth100();
		form.setDisabled(true);

		VLayout group = new VLayout();
		group.setPadding(10);
		group.addMember(form);
		group.setOverflow(Overflow.AUTO);

		addMember(group);
	}

	public void setLayerModel(LayerModelDto lmd) {
		this.lmd = lmd;
		fireChangedHandler();
	}

	// -- SaveButtonBar events --------------------------------------------------------

	public boolean onEditClick(ClickEvent event) {
		form.setDisabled(false);
		return true;
	}

	public boolean onSaveClick(ClickEvent event) {
		if (validate()) {
			SC.ask(MESSAGES.datalayerShapeUploadOverwriteConfirmTitle(), 
					MESSAGES.datalayerShapeUploadOverwriteConfirmQuestion(), new BooleanCallback() {

				public void execute(Boolean value) {
					if (value) {
						form.upload(new DataCallback<String>() {

							public void execute(String fileId) {
								showProcessingDialog();

								ManagerCommandService.processShapeFileUpload(fileId, lmd,
										new DataCallback<ProcessShapeFileResponse>() {
									@Override
									public void execute(ProcessShapeFileResponse commandResult) {
										hideProcessingDialog();
										buttonBar.doCancelClick(null);
									}
								});
							}
						});
					}
				}
			});
		}
		return false; // not ending editsession yet
	}

	public boolean onCancelClick(ClickEvent event) {
		form.setDisabled(true);
		return true;
	}

	public boolean validate() {
		if (!form.validate()) {
			SC.say(MESSAGES.datalayerShapeUploadNoFileSelected());
			return false;
		}
		return true;
	}
	

	public boolean onResetClick(ClickEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isDefault() {
		// TODO Auto-generated method stub
		return true;
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
