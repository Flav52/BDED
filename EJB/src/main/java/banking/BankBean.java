/**
 * JOnAS: Java(TM) Open Application Server
 * Copyright (C) 1999-2008 Bull S.A.S.
 * Contact: jonas-team@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * --------------------------------------------------------------------------
 * $Id: WriterBean.java 17350 2009-05-13 14:15:43Z fornacif $
 * --------------------------------------------------------------------------
 */

package banking;

import entity.Compte;

import static entity.Compte.QN.FIND_COMPTE;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This is an example of Session Bean, stateless, secured, available
 * with a Local and a Remote interface (with the same methods).
 * @author JOnAS team
 */
@Stateless
@Local(LocalBank.class)
public class BankBean implements LocalBank {

    public List<Compte> comptes = new ArrayList<Compte>();

    @Override
    public void addCompte(int id) {
        Iterator<Compte> iter = comptes.iterator();
        while(iter.hasNext()){
            if(iter.next().getId()==id)
                return;
        }
        comptes.add(new Compte(id));
    }
    @Override
    public void addCompte(Compte compte) {
        Iterator<Compte> iter = comptes.iterator();
        while(iter.hasNext()){
            if(iter.next().getId()==compte.getId())
                return;
        }
        comptes.add(compte);
    }

    @Override
    public void removeCompte(Compte compte) {
        comptes.remove(compte);
    }

    @Override
    public List<Compte> allCompte() {
        return comptes;
    }

    @Override
    public Compte findCompte(int id) {
        Iterator<Compte> iter = comptes.iterator();
        while(iter.hasNext()){
            Compte nxt = iter.next();
            if(nxt.getId()==id)
                return nxt;
        }
        return null;
    }

    /**
     * Entity manager used by this bean.
     */
 /*   @PersistenceContext
    private EntityManager entityManager = null;


    @Override
    public void addCompte(int id) {
        entityManager.persist(new Compte(id));
    }

    @Override
    public void removeCompte(Compte compte) {
        entityManager.remove(compte);
    }

    @Override
    public List allCompte() {
        return entityManager.createNamedQuery(FIND_COMPTE).getResultList();
    }

    @Override
    public Compte findCompte(int id) {
        Query query = entityManager.createNamedQuery(FIND_COMPTE);
        query.setParameter("IDENTIFIANT", id);
        List<Compte> comptes = query.getResultList();
        if (comptes != null && comptes.size() > 0) {
            return comptes.get(0);
        }
        return null;
    }
    */
}
