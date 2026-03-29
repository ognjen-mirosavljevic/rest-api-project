/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ognje
 */
public class DB {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
    
    public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }
}
