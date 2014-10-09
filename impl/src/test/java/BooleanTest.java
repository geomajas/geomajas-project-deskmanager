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

import org.junit.Test;

/**
 * @author Oliver May
 */
public class BooleanTest {


	@Test
	public void testBooleans() {

//		System.out.println(false && false || false);
		System.out.println(false && (false || true));
		System.out.println(false && false || true);
//		System.out.println(false && true || false);
//		System.out.println(false && true || true);
//		System.out.println(true && false || false);
//		System.out.println(true && false || true);
//		System.out.println(true && true || false);
//		System.out.println(true && true || true);


	}


	private static boolean[][] buildBooleans(int i) {
		boolean[][] booleans = new boolean[2^i][i];

		for (boolean[] b : booleans) {

		}
		return booleans;
	}


}
