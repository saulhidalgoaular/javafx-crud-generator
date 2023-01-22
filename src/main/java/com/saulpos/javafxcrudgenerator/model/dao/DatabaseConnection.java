/*
 * Copyright (C) 2012-2023 Saúl Hidalgo <saulhidalgoaular at gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.saulpos.javafxcrudgenerator.model.dao;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.beans.PropertyVetoException;
import java.util.List;
import java.util.Map;

public class DatabaseConnection {
    private static DatabaseConnection INSTANCE = null;
    public SessionFactory sessionFactory;

    private DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() throws PropertyVetoException {
        if ( INSTANCE == null ){
            INSTANCE = new DatabaseConnection();
            INSTANCE.initialize();
        }
        return INSTANCE;
    }

    protected void initialize() throws PropertyVetoException {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    public void runHqlQuery(String query, Map<String, Object> parameters) throws PropertyVetoException {
        Session session = getInstance().sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();

            org.hibernate.query.Query sessionQuery = session.createQuery(query);
            if ( parameters != null ){
                for ( String key : parameters.keySet() ){
                    sessionQuery.setParameter(key, parameters.get(key));
                }
            }
            sessionQuery.executeUpdate();

            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public List listHqlQuery(String query, Map<String, Object> parameters) throws PropertyVetoException {
        List ans = null;
        Session session = getInstance().sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();

            org.hibernate.query.Query sessionQuery = session.createQuery(query);
            if ( parameters != null ){
                for ( String key : parameters.keySet() ){
                    sessionQuery.setParameter(key, parameters.get(key));
                }
            }
            ans = sessionQuery.list();

            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return ans;
    }

    public List listAll(String entityName) throws PropertyVetoException {
        return listHqlQuery("FROM " + entityName, null);
    }

    public Integer createEntry(Object newEntry) throws PropertyVetoException {

        Session session = null;
        Transaction tx = null;
        Integer id = null;

        try
        {
            session = getInstance().sessionFactory.openSession();
            tx = session.beginTransaction();
            id = (Integer) session.save(newEntry);
            tx.commit();
        }
        catch (HibernateException e)
        {
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        }
        finally
        {
            session.close();

        }
        return id;
    }

    public void delete(Object entry) throws PropertyVetoException{
        Session session = getInstance().sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.delete(entry);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void update(Object entry) throws PropertyVetoException {
        Session session = getInstance().sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(entry);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void saveOrUpdate(Object entry) throws PropertyVetoException {
        Session session = getInstance().sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.saveOrUpdate(entry);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
