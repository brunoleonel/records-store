package br.com.cashback.recordstore.repositories;

import br.com.cashback.recordstore.infrastructure.repositories.CashbackIndexRepositoryInterface;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CashbackIndexRepository implements CashbackIndexRepositoryInterface {

    EntityManager em;

    public CashbackIndexRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public float getCashbackIndexByGenreForDayOfWeek(String genre, String day) {

        final String query = String.format(
                "SELECT c.%s FROM CashbackIndex c WHERE c.genre = :genre",
                day.toLowerCase()
        );

        final TypedQuery<Float> tQuery = this.em.createQuery(query, Float.class);
        tQuery.setParameter("genre", genre);
        final Float result = tQuery.getSingleResult();
        return result;
    }

    @Override
    public List<String> getGenres() {
        TypedQuery<String> query = this.em.createQuery("SELECT c.genre FROM CashbackIndex c", String.class);
        List<String> genres = query.getResultList();
        return genres;
    }
}
