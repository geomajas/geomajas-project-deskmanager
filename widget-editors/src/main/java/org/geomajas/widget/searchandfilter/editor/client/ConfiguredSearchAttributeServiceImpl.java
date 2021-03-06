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
package org.geomajas.widget.searchandfilter.editor.client;

import com.google.gwt.core.client.GWT;
import org.geomajas.configuration.PrimitiveAttributeInfo;
import org.geomajas.configuration.client.ClientVectorLayerInfo;
import org.geomajas.widget.searchandfilter.client.util.AttributeCriterionUtil;
import org.geomajas.widget.searchandfilter.search.dto.ConfiguredSearchAttribute;
import org.geomajas.widget.searchandfilter.editor.client.i18n.SearchAndFilterEditorMessages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton implementation of
 * {@link ConfiguredSearchesStatus}.
 *
 * @author Jan Venstermans
 */
public final class ConfiguredSearchAttributeServiceImpl implements ConfiguredSearchAttributeService {

	private final SearchAndFilterEditorMessages messages =
			GWT.create(SearchAndFilterEditorMessages.class);

	private static ConfiguredSearchAttributeService instance;

	private ConfiguredSearchAttributeServiceImpl() {

	}

	public static ConfiguredSearchAttributeService getInstance() {
		if (instance == null) {
			instance = new ConfiguredSearchAttributeServiceImpl();
		}
		return instance;
	}

	@Override
	public Map<org.geomajas.configuration.PrimitiveAttributeInfo, ConfiguredSearchAttribute.AttributeType>
	getPrimitiveAttributesMap() {
		Map<org.geomajas.configuration.PrimitiveAttributeInfo, ConfiguredSearchAttribute.AttributeType>
				attributesMap =
				new HashMap<org.geomajas.configuration.PrimitiveAttributeInfo,
						ConfiguredSearchAttribute.AttributeType>();
		ClientVectorLayerInfo clientVectorLayerInfo =
				SearchAndFilterEditor.getConfiguredSearchesStatus().getClientVectorLayerInfo();
		if (clientVectorLayerInfo != null) {
			List<org.geomajas.configuration.AttributeInfo> attributeInfoList =
					clientVectorLayerInfo.getFeatureInfo().getAttributes();
			for (org.geomajas.configuration.AttributeInfo attr : attributeInfoList) {
				if (attr instanceof PrimitiveAttributeInfo) {
					PrimitiveAttributeInfo primAttr = (PrimitiveAttributeInfo) attr;
					ConfiguredSearchAttribute.AttributeType attributeType = null;
					switch (primAttr.getType()) {
						case STRING:
							attributeType = ConfiguredSearchAttribute.AttributeType.String;
							break;
						case INTEGER:
							attributeType = ConfiguredSearchAttribute.AttributeType.Integer;
							break;
					}
					if (attributeType != null) {
						attributesMap.put(primAttr, attributeType);
					}
				}
			}
		}
		return attributesMap;
	}

	@Override
	public LinkedHashMap<ConfiguredSearchAttribute.Operation, String> getOperationsValueMap(
			ConfiguredSearchAttribute.AttributeType attributeType) {
		LinkedHashMap<ConfiguredSearchAttribute.Operation, String> result
				= new LinkedHashMap<ConfiguredSearchAttribute.Operation, String>();
		for (ConfiguredSearchAttribute.Operation operation : attributeType.getOperations()) {
			result.put(operation, AttributeCriterionUtil.getOperationStringRepresentation(attributeType, operation));
		}
		return result;
	}

	@Override
	public LinkedHashMap<ConfiguredSearchAttribute.InputType, String> getInputTypeMap(
			ConfiguredSearchAttribute.AttributeType attributeType) {
		LinkedHashMap<ConfiguredSearchAttribute.InputType, String> result
				= new LinkedHashMap<ConfiguredSearchAttribute.InputType, String>();
		for (ConfiguredSearchAttribute.InputType type : attributeType.getInputTypes()) {
			result.put(type, AttributeCriterionUtil.getInputTypeStringRepresentation(attributeType, type));
		}
		return result;
	}
}
