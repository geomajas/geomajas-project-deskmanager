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
package org.geomajas.plugin.deskmanager.domain.types;

import org.geomajas.geometry.Bbox;
import org.geomajas.geometry.service.BboxService;
import org.junit.Assert;
import org.junit.Test;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author Jan De Moerloose
 *
 */
public final class XmlSerialisationTypeTest {
	
	private boolean success;

	@Test
	public void testBboxSerialization() throws UnsupportedEncodingException, InterruptedException {
		XmlSerialisationType type = new XmlSerialisationType();
		Bbox box = new Bbox(1, 2, 3, 5);
		Assert.assertTrue(BboxService.equals(box, deserialize(serialize(box)), 0.001));
		ThreadGroup g = new ThreadGroup("other");
		new Thread(g, new Runnable() {

			@Override
			public void run() {
				Bbox box = new Bbox(1, 2, 3, 5);
				try {
					Assert.assertTrue(BboxService.equals(box, deserialize(serialize(box)), 0.001));
					success = true;
				} catch (UnsupportedEncodingException e) {
					// 
				}

			}

		}, "other").start();
		Thread.currentThread().sleep(500);
		Assert.assertTrue(success);
	}

	private String serialize(Bbox box) throws UnsupportedEncodingException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(baos);
		encoder.writeObject(box);
		encoder.close();
		return baos.toString("UTF-8");
	}

	private Bbox deserialize(String box) throws UnsupportedEncodingException {
		XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(box.getBytes("UTF-8")));
		return (Bbox) decoder.readObject();
	}

}
