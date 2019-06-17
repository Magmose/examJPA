package facade;

import entity.history.History;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.PuSelector;

public class HistoryDB {

    EntityManager em;

    public HistoryDB() {
        this.em = PuSelector.getEntityManagerFactory("pu").createEntityManager();
    }

    public History addSearchHistory(History history) {
        try {
            em.getTransaction().begin();
            em.persist(history);
            em.getTransaction().commit();
            return history;
        } finally {
            em.close();
        }
    }

    public List<History> getAllHistories() {
        try {
            return (List<History>) em.createQuery("SELECT h FROM History h").getResultList();
        } finally {
            em.close();
        }
    }
}
