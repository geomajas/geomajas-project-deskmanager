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
package org.geomajas.plugin.deskmanager.service.usernamepasswordsecurity.impl;

import org.geomajas.plugin.deskmanager.domain.usernamepasswordsecurity.AuthenticationSession;
import org.geomajas.plugin.deskmanager.domain.usernamepasswordsecurity.User;
import org.geomajas.plugin.deskmanager.service.usernamepasswordsecurity.AuthenticationSessionService;
import org.geomajas.plugin.deskmanager.service.usernamepasswordsecurity.UserService;
import org.geomajas.security.GeomajasSecurityException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Default implementation of
 * {@link org.geomajas.plugin.deskmanager.service.usernamepasswordsecurity.AuthenticationSessionService}.
 *
 * @author Jan Venstermans
 *
 */
@Repository
@Transactional(rollbackFor = { Exception.class })
public class AuthenticationSessionServiceImpl implements AuthenticationSessionService {

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationSessionServiceImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserService userService;

	@Override
	public AuthenticationSession getActiveSessionOfToken(String token) {
		if (token != null) {
			AuthenticationSession session = (AuthenticationSession) sessionFactory.
					getCurrentSession().get(AuthenticationSession.class, token);
			if (session != null && isActive(session)) {
				return session;
			}
		}
		return null;
	}

	@Override
	public List<AuthenticationSession> getActiveSessionsOfUser(Long userId) {
		try {
			User user = userService.findById(userId);
			Date currentDate = new Date();
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(AuthenticationSession.class);
			crit.add(Restrictions.eq("user", user));
			crit.add(Restrictions.ge("expirationTime", currentDate));
			crit.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
			return crit.list();
		} catch (GeomajasSecurityException e) {
			LOG.error("Error getting active sessions of user, user unknown.");
		}
		return new ArrayList<AuthenticationSession>();
	}

	@Override
	public AuthenticationSession createSession(User user, int lifetimeInDays) {
		if (lifetimeInDays < 1) {
			throw new IllegalArgumentException("Lifetime attribute must be a positive integer");
		}
		AuthenticationSession session = new AuthenticationSession(generateToken(),
				user, getCurrentDatePlusDays(lifetimeInDays));
		sessionFactory.getCurrentSession().saveOrUpdate(session);
		return session;
	}

	@Override
	public void dropSession(AuthenticationSession session) {
		sessionFactory.getCurrentSession().delete(session);
	}

	@Override
	public boolean isActive(AuthenticationSession session) {
		return session.getExpirationTime().after(new Date());
	}

	private String generateToken() {
		return UUID.randomUUID().toString();
	}

	private Date getCurrentDatePlusDays(int numberOfDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, numberOfDays);
		return c.getTime();
	}
}
