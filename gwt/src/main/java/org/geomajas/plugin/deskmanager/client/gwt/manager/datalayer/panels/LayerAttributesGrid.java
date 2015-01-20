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
package org.geomajas.plugin.deskmanager.client.gwt.manager.datalayer.panels;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.ChangeEvent;
import com.smartgwt.client.widgets.grid.events.ChangeHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.configuration.AttributeInfo;
import org.geomajas.configuration.FeatureInfo;
import org.geomajas.configuration.PrimitiveAttributeInfo;
import org.geomajas.configuration.PrimitiveType;
import org.geomajas.gwt.client.Geomajas;
import org.geomajas.plugin.deskmanager.client.gwt.manager.i18n.ManagerMessages;
import org.geomajas.plugin.deskmanager.command.manager.dto.DynamicVectorLayerConfiguration;

import java.util.LinkedHashMap;

/**
 * @author Kristof Heirwegh
 */
@SuppressWarnings("deprecation")
public class LayerAttributesGrid extends VLayout {

	private static final ManagerMessages MESSAGES = GWT.create(ManagerMessages.class);

	private static final String FLD_NAME = "name";

	private static final String FLD_TYPE = "type";

	private static final String FLD_IDENTIFYING = "identifying";

	private static final String FLD_IDFIELD = "idField";

	private static final String FLD_LABELFIELD = "labelField";

	private static final String FLD_LABEL = "label";

	private static final String FLD_OBJECT = "_object";

	private DynamicVectorLayerConfiguration layerConfig;

	private ListGrid grid;

	private Label warnings;

	private ListGridRecord currentIdField;

	private ListGridRecord currentLabelField;

	public LayerAttributesGrid() {
		super(5);
		grid = new ListGrid();
		grid.setWidth100();
		grid.setHeight("*");
		grid.setSelectionType(SelectionStyle.SINGLE);
		grid.setShowAllRecords(true);
		grid.setSelectionType(SelectionStyle.SIMPLE);
		grid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		grid.setCanEdit(true);
		grid.setEditEvent(ListGridEditEvent.NONE);
		grid.setEditByCell(false);
		grid.setShowEmptyMessage(true);
		grid.setEmptyMessage("<i>" + MESSAGES.layerAttributesGridLoadingText() +
				" <img src='" + Geomajas.getIsomorphicDir()
				+ "/images/circle.gif' style='height: 1em' /></i>");

		ListGridField nameFld = new ListGridField(FLD_NAME, MESSAGES.layerAttributesGridColumnAttribute());
		nameFld.setType(ListGridFieldType.TEXT);
		nameFld.setWidth("*");
		nameFld.setCanEdit(false);

		ListGridField typeFld = new ListGridField(FLD_TYPE, MESSAGES.layerAttributesGridColumnType());
		typeFld.setType(ListGridFieldType.TEXT);
		typeFld.setCanEdit(true);
		typeFld.setWidth(55);
		SelectItem editor = new SelectItem();
		editor.setValueMap(getTypes());
		typeFld.setEditorType(editor);

		ListGridField identifyingFld = new ListGridField(FLD_IDENTIFYING, MESSAGES.layerAttributesGridColumnCoreInfo());
		identifyingFld.setType(ListGridFieldType.BOOLEAN);
		identifyingFld.setCanEdit(true);
		identifyingFld.setPrompt(MESSAGES.layerAttributesGridColumnCoreInfoTooltip());
		identifyingFld.addChangeHandler(new ChangeHandler() {

			public void onChange(ChangeEvent event) {
				Object value = event.getValue();
				if (value instanceof Boolean && Boolean.TRUE.equals(value)) {
					grid.selectRecord(grid.getRecord(event.getRowNum()));
				}
			}
		});

		ListGridField idFieldFld = new ListGridField(FLD_IDFIELD, MESSAGES.layerAttributesGridColumnIdField());
		idFieldFld.setType(ListGridFieldType.BOOLEAN);
		idFieldFld.setCanEdit(true);
		idFieldFld.setPrompt(MESSAGES.layerAttributesGridColumnIdFieldTooltip());
		idFieldFld.addChangeHandler(new ChangeHandler() {

			public void onChange(ChangeEvent event) {
				ListGridRecord lgr = grid.getRecord(event.getRowNum());
				if (!lgr.equals(currentIdField)) {
					// deselect id checkbox in former id field
					currentIdField.setAttribute(FLD_IDFIELD, false);
					// update currentIdField
					currentIdField = lgr;
					if (!isRecordSelected(lgr)) {
						grid.selectRecord(lgr);
					}
				}
			}
		});

		ListGridField labelFieldFld = new ListGridField(FLD_LABELFIELD, MESSAGES.layerAttributesGridColumnLabelField());
		labelFieldFld.setType(ListGridFieldType.BOOLEAN);
		labelFieldFld.setCanEdit(true);
		labelFieldFld.setPrompt(MESSAGES.layerAttributesGridColumnLabelFieldTooltip());
		labelFieldFld.addChangeHandler(new ChangeHandler() {

			public void onChange(ChangeEvent event) {
				ListGridRecord lgr = grid.getRecord(event.getRowNum());
				if (!lgr.equals(currentLabelField)) {
					// deselect label checkbox in former label field
					currentLabelField.setAttribute(FLD_LABELFIELD, false);
					// update currentLabelField
					currentLabelField = lgr;
					if (!isRecordSelected(lgr)) {
						grid.selectRecord(lgr);
					}
				}
			}
		});

		ListGridField labelFld = new ListGridField(FLD_LABEL, MESSAGES.layerAttributesGridColumnName());
		labelFld.setType(ListGridFieldType.TEXT);
		labelFld.setCanEdit(true);
		labelFld.setPrompt(MESSAGES.layerAttributesGridColumnNameTooltip());

		grid.setFields(nameFld, typeFld, identifyingFld, idFieldFld, labelFieldFld, labelFld);
		grid.setCanResizeFields(true);
		grid.addSelectionChangedHandler(new SelectionChangedHandler() {

			public void onSelectionChanged(SelectionEvent event) {
				ListGridRecord lgr = event.getRecord();
				// on deselection of record, set FLD_IDENTIFYING false
				if (!isRecordSelected(lgr)) {
					lgr.setAttribute(FLD_IDENTIFYING, false);
				}
				checkSelectionStatusOfRecordForIdAndLabelFields(event.getRecord());
			}
		});

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

	public void setData(DynamicVectorLayerConfiguration layerConfig) {
		this.layerConfig = layerConfig;
		FeatureInfo fi = layerConfig.getClientVectorLayerInfo().getFeatureInfo();
		String labelFieldName = layerConfig.getClientVectorLayerInfo().getNamedStyleInfo().getLabelStyle()
				.getLabelAttributeName();
		PrimitiveAttributeInfo idField = fi.getIdentifier();
		for (AttributeInfo ai : fi.getAttributes()) {
			if (ai instanceof PrimitiveAttributeInfo) {
				ListGridRecord lgr = new ListGridRecord();
				PrimitiveAttributeInfo pai = (PrimitiveAttributeInfo) ai;
				lgr.setAttribute(FLD_NAME, pai.getName());
				lgr.setAttribute(FLD_TYPE, pai.getType().toString());
				lgr.setAttribute(FLD_IDENTIFYING, pai.isIdentifying());
				lgr.setAttribute(FLD_IDFIELD, (pai.equals(idField)));
				lgr.setAttribute(FLD_LABELFIELD, pai.getName().equals(labelFieldName));
				lgr.setAttribute(FLD_LABEL, pai.getLabel());
				lgr.setAttribute(FLD_OBJECT, pai);
				grid.addData(lgr);
				if (pai.getName().equals(labelFieldName)) {
					currentLabelField = lgr;
				}
				if (pai.equals(idField)) {
					currentIdField = lgr;
				}
				if (!pai.isHidden()) {
					grid.selectRecord(lgr);
				}
			} // else unsupported
		}
	}

	public DynamicVectorLayerConfiguration getData() {
		if (currentIdField == null || currentLabelField == null) {
			return null;
		}

		FeatureInfo fi = layerConfig.getClientVectorLayerInfo().getFeatureInfo();
		fi.setIdentifier((PrimitiveAttributeInfo) currentIdField.getAttributeAsObject(FLD_OBJECT));
		layerConfig.getClientVectorLayerInfo().getNamedStyleInfo().getLabelStyle()
				.setLabelAttributeName(currentLabelField.getAttributeAsString(FLD_NAME));
		for (ListGridRecord r : grid.getRecords()) {
			PrimitiveAttributeInfo pai = (PrimitiveAttributeInfo) r.getAttributeAsObject(FLD_OBJECT);
			pai.setLabel(r.getAttributeAsString(FLD_LABEL));
			pai.setIdentifying(r.getAttributeAsBoolean(FLD_IDENTIFYING));
			pai.setHidden(!grid.isSelected(r));
			pai.setType(PrimitiveType.fromValue(r.getAttributeAsString(FLD_TYPE)));
		}
		return layerConfig;
	}

	public void reset() {
		grid.deselectAllRecords();
		grid.setData(new Record[]{});
		warnings.setVisible(false);
		warnings.setContents("");
	}

	public void setWarning(String warning) {
		if (warning == null || "".equals(warning)) {
			warnings.setVisible(false);
			warnings.setContents("");
		} else {
			warnings.setVisible(true);
			warnings.setContents(warning);
		}
	}

	public boolean isValid() {
		return (!warnings.isVisible());
	}

	// -------------------------------------------------

	private LinkedHashMap<String, String> getTypes() {
		LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
		for (PrimitiveType type : PrimitiveType.values()) {
			values.put(type.toString(), type.toString());
		}
		return values;
	}

	/**
	 * Checks whether {@link ListGridRecord} can be deselected. When deselected, but the record
	 * contains id or label check, the record is selected again and a warning is shown.
	 *
	 * @param lgr
	 */
	private void checkSelectionStatusOfRecordForIdAndLabelFields(ListGridRecord lgr) {
		if (!isRecordSelected(lgr)) { // deselected
			warnings.setVisible(false);
			if (lgr.equals(currentIdField)) {
				warnings.setContents(MESSAGES.layerAttributesGriDeselectIdAttribute());
				warnings.setVisible(true);
				grid.selectRecord(lgr);
			} else if (lgr.equals(currentLabelField)) {
				warnings.setContents(MESSAGES.layerAttributesGriDeselectLabelAttribute());
				warnings.setVisible(true);
				grid.selectRecord(lgr);
			}
		}
	}

	private boolean isRecordSelected(ListGridRecord lgr) {
		return grid.isSelected(lgr);
	}
}
