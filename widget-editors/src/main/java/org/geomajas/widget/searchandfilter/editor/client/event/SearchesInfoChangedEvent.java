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
package org.geomajas.widget.searchandfilter.editor.client.event;

import com.google.web.bindery.event.shared.Event;

/**
 * Event to indicate a new search has been created.
 * 
 * @author Jan Venstermans
 * 
 */
public class SearchesInfoChangedEvent extends Event<SearchesInfoChangedEvent.Handler> {

	/**
	 * Handler for this event.
	 *
	 * @author Jan De Moerloose
	 *
	 */
	public interface Handler {

		/**
		 * Notifies tab selection.
		 *
		 * @param event the event
		 */
		void onSearchInfoChanged(SearchesInfoChangedEvent event);

	}

	public SearchesInfoChangedEvent() {
	}
	
	private static final Type<Handler> TYPE = new Type<Handler>();

	public static Type<Handler> getType() {
		return TYPE;
	}

	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onSearchInfoChanged(this);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "SearchesInfoChangedEvent[" + "]";
	}

}