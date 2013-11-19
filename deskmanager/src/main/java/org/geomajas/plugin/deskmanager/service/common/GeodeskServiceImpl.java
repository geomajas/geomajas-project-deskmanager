/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2013 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
package org.geomajas.plugin.deskmanager.service.common;

import java.util.Date;
import java.util.List;

import org.geomajas.global.ExceptionCode;
import org.geomajas.plugin.deskmanager.client.gwt.common.GdmLayout;
import org.geomajas.plugin.deskmanager.domain.Geodesk;
import org.geomajas.plugin.deskmanager.domain.security.Territory;
import org.geomajas.plugin.deskmanager.domain.security.dto.Role;
import org.geomajas.plugin.deskmanager.security.DeskmanagerSecurityContext;
import org.geomajas.security.GeomajasSecurityException;
import org.geomajas.security.SecurityContext;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Kristof Heirwegh
 */
@Repository
@Transactional(readOnly = true)
public class GeodeskServiceImpl implements GeodeskService {

	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(GeodeskServiceImpl.class);

	@Autowired
	private SessionFactory factory;

	@Autowired
	private SecurityContext securityContext;

	public Geodesk getGeodeskById(String uuid) throws GeomajasSecurityException {
		Geodesk l = (Geodesk) factory.getCurrentSession().get(Geodesk.class, uuid);
		if (l != null) {
			if (((DeskmanagerSecurityContext) securityContext).readAllowed(l)) {
				return l;
			} else {
				throw new GeomajasSecurityException(ExceptionCode.COMMAND_ACCESS_DENIED, "Read Geodesk",
						securityContext.getUserName());
			}
		} else {
			return null;
		}
	}

	public boolean geodeskExists(String publicId) {
		Query q = factory.getCurrentSession().createQuery(
				"select id from Geodesk l WHERE l.geodeskId = :id AND " + "l.deleted = false");
		q.setParameter("id", publicId);
		q.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

		Object result = q.uniqueResult();
		return (result != null);
	}

	public boolean geodeskIdExists(String publicId) {
		Query q = factory.getCurrentSession().createQuery("select id from Geodesk l WHERE l.geodeskId = :id");
		q.setParameter("id", publicId);
		q.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
		Object result = q.uniqueResult();
		return (result != null);
	}

	/**
	 * Will throw a securityException if no accessrights, or not active.
	 */
	public Geodesk getGeodeskByPublicId(String id) throws GeomajasSecurityException {
		if (geodeskExists(id)) {
			if (((DeskmanagerSecurityContext) securityContext).isGeodeskUseAllowed(id)) {
				Query q = factory.getCurrentSession().createQuery(
						"FROM Geodesk l WHERE l.geodeskId = :id AND " + "l.deleted = false AND "
								+ "l.active = true AND l.blueprint.geodesksActive = true");
				q.setParameter("id", id);
				q.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
				Geodesk l = (Geodesk) q.uniqueResult();
				if (l == null) {
					throw new GeomajasSecurityException(GdmLayout.EXCEPTIONCODE_GEODESKINACTIVE);
				}
				return l;
			} else {
				// FIXME: i18n
				throw new GeomajasSecurityException(ExceptionCode.COMMAND_ACCESS_DENIED, "Geodesk openen",
						securityContext.getUserName());
			}
		} else {
			throw new GeomajasSecurityException(ExceptionCode.APPLICATION_NOT_FOUND, id);
		}
	}

	public Geodesk getGeodeskByPublicIdInternal(String id) {
		if (geodeskExists(id)) {
			Query q = factory.getCurrentSession().createQuery(
					"FROM Geodesk l WHERE l.geodeskId = :id AND " + "l.deleted = false AND " + "l.active = true AND "
							+ "l.blueprint.geodesksActive = true");
			q.setParameter("id", id);
			q.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
			return (Geodesk) q.uniqueResult();
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Geodesk> getGeodesks() throws GeomajasSecurityException {
		Criteria crit = factory.getCurrentSession().createCriteria(Geodesk.class);
		crit.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
		crit.add(Restrictions.eq("deleted", false));
		Criterion filter = ((DeskmanagerSecurityContext) securityContext).getFilterGeodesks();
		if (filter != null) {
			crit.add(filter);
		}

		return crit.list();
	}

	@Transactional(rollbackFor = { Exception.class })
	public void deleteGeodesk(Geodesk l) throws GeomajasSecurityException {
		if (((DeskmanagerSecurityContext) securityContext).deleteAllowed(l)) {
			factory.getCurrentSession().delete(l);
		} else {
			throw new GeomajasSecurityException(ExceptionCode.COMMAND_ACCESS_DENIED, "Verwijderen Geodesk",
					securityContext.getUserName());
		}
	}

	@Transactional(rollbackFor = { Exception.class })
	public void saveOrUpdateGeodesk(Geodesk l) throws GeomajasSecurityException {
		if (((DeskmanagerSecurityContext) securityContext).saveAllowed(l)) {
			Date date = new Date();
			String fullName = ((DeskmanagerSecurityContext) securityContext).getFullName();
			if (l.getCreationBy() == null) {
				l.setCreationBy(fullName);
			}
			if (l.getCreationDate() == null) {
				l.setCreationDate(date);
			}
			l.setLastEditDate(date);
			l.setLastEditBy(fullName);

			factory.getCurrentSession().saveOrUpdate(l);
		} else {
			throw new GeomajasSecurityException(ExceptionCode.COMMAND_ACCESS_DENIED, "Opslaan Geodesk",
					securityContext.getUserName());
		}
	}

	public String getGeodeskNameByPublicId(String id) throws GeomajasSecurityException {
		Query q = factory.getCurrentSession().createQuery("SELECT name FROM Geodesk l WHERE l.geodeskId = :id");
		q.setParameter("id", id);
		q.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
		String name = (String) q.uniqueResult();
		return (name == null ? "??" : name);
	}

	/**
	 * group can be null, in which case the geodesk will need to be public.
	 */
	public boolean isGeodeskUseAllowed(String id, Role role, Territory territory) {
		Query q;
		Geodesk desk = getGeodeskByPublicIdInternal(id);
		if (desk == null) {
			return false;
		}
		switch (role) {
			case ADMINISTRATOR:
				return true;
			case DESK_MANAGER:
				// Allow desk usage for the deskmanager
				return desk.getOwner().equals(territory);
			case CONSULTING_USER:
			case EDITING_USER:
				q = factory.getCurrentSession().createQuery(
						"select l.id from Geodesk l join l.groups as t with t.code like :code WHERE"
								+ " l.geodeskId = :id AND l.deleted = false");
				q.setParameter("code", territory.getCode());
				break;
			case GUEST:
				q = factory.getCurrentSession().createQuery(
						"select id from Geodesk l WHERE l.geodeskId = :id AND " + "l.publicc = true AND "
								+ "l.deleted = false");
				break;
			default:
				return false;
		}
		q.setParameter("id", id);
		q.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
		Object result = q.uniqueResult();
		return (result != null);
	}

	public boolean isGeodeskReadAllowed(Geodesk geodesk, Role role, Territory group) {
		switch (role) {
			case ADMINISTRATOR:
				return true;
			case DESK_MANAGER:
				return geodesk.getOwner().equals(group);
		}
		return false;
	}

	public boolean isGeodeskSaveAllowed(Geodesk geodesk, Role role, Territory group) {
		switch (role) {
			case ADMINISTRATOR:
				return true;
			case DESK_MANAGER:
				return geodesk.getOwner().equals(group);
		}
		return false;
	}

	public boolean isGeodeskDeleteAllowed(Geodesk geodesk, Role role, Territory group) {
		switch (role) {
			case ADMINISTRATOR:
				return true;
			case DESK_MANAGER:
				return geodesk.getOwner().equals(group);
		}
		return false;
	}
}
