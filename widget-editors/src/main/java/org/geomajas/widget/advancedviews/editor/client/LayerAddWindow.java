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
package org.geomajas.widget.advancedviews.editor.client;

import java.util.LinkedHashMap;

import org.geomajas.configuration.client.ClientLayerInfo;
import org.geomajas.plugin.deskmanager.client.gwt.manager.service.DataCallback;
import org.geomajas.plugin.deskmanager.client.gwt.manager.service.ManagerCommandService;
import org.geomajas.plugin.deskmanager.client.gwt.manager.util.GeodeskDtoUtil;
import org.geomajas.plugin.deskmanager.domain.dto.LayerDto;
import org.geomajas.plugin.deskmanager.domain.dto.LayerModelDto;
import org.geomajas.widget.advancedviews.client.AdvancedViewsMessages;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

/**
 * 
 * @author Oliver May
 * 
 */
public class LayerAddWindow extends Window {

	private static final AdvancedViewsMessages MESSAGES = GWT.create(AdvancedViewsMessages.class);
	private DynamicForm form;
	private ComboBoxItem layerSelect;

	private static final int FORMITEM_WIDTH = 300;

	public LayerAddWindow(final ThemeConfigurationPanel themeConfiguration, final LayerAddCallback callback) {
		super();
		setAutoSize(true);
		setCanDragReposition(true);
		setCanDragResize(false);
		setKeepInParentRect(true);
		setOverflow(Overflow.HIDDEN);
		setAutoCenter(true);
		setTitle(MESSAGES.themeConfigLayerAdd());
		setShowCloseButton(false);
		setShowMinimizeButton(false);
		setShowMaximizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		
		form = new DynamicForm();
		form.setAutoFocus(true);
		form.setWidth(FORMITEM_WIDTH + 100);
		
		layerSelect = new ComboBoxItem();
		layerSelect.setTitle(MESSAGES.themeConfigLayerSelect());
		layerSelect.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				for (LayerDto layerDto : GeodeskDtoUtil.getMainMapLayers(themeConfiguration.getGeodesk())) {
					if (layerDto.getLayerModel().getId().equals(layerSelect.getValueAsString())) {
						ManagerCommandService.getLayerModel(layerDto.getLayerModel().getId(),
								new DataCallback<LayerModelDto>() {

							public void execute(LayerModelDto result) {
								callback.execute(result.getLayerConfiguration().getClientLayerInfo());
							}
						});
						break;
					}
				}
				hide();
				destroy();
			}
		});
		
		LinkedHashMap<String, String> layers = new LinkedHashMap<String, String>();
		for (LayerDto layerDto : GeodeskDtoUtil.getMainMapLayers(themeConfiguration.getGeodesk())) {
			layers.put(layerDto.getLayerModel().getId(), layerDto.getLayerModel().getName());
		}
		layerSelect.setValueMap(layers);
		
		
		form.setFields(layerSelect);
		
		addItem(form);
		
		show();
	}
	
	/**
	 * Callback interface for selecting a layer.
	 * 
	 * @author Oliver May
	 *
	 */
	public interface LayerAddCallback {
		/**
		 * Called when layer is selected.
		 * @param layer
		 */
		void execute(ClientLayerInfo layer);
	}
	
}