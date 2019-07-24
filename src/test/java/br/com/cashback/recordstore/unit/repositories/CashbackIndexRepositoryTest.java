package br.com.cashback.recordstore.unit.repositories;

import br.com.cashback.recordstore.infrastructure.repositories.CashbackIndexRepositoryInterface;
import br.com.cashback.recordstore.repositories.CashbackIndexRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CashbackIndexRepositoryTest {

    @Test
    public void testShouldGetCashbackIndexByGenreForDayOfWeek() {

        EntityManager em = Mockito.mock(EntityManager.class);
        TypedQuery<Float> tq = Mockito.mock(TypedQuery.class);

        when(tq.getSingleResult()).thenReturn(1.5f);
        when(em.createQuery(any(String.class), eq(Float.class))).thenReturn(tq);

        CashbackIndexRepositoryInterface cashbackIndexRepo = new CashbackIndexRepository(em);
        float result = cashbackIndexRepo.getCashbackIndexByGenreForDayOfWeek("POP", "monday");

        assertThat(result).isEqualTo(1.5f);
    }

    @Test
    public void testShouldGetGenres() {

        EntityManager em = Mockito.mock(EntityManager.class);
        TypedQuery<String> tq = Mockito.mock(TypedQuery.class);

        when(tq.getResultList()).thenReturn(Arrays.asList("POP", "ROCK", "CLASSICAL", "MPB"));
        when(em.createQuery(any(String.class), eq(String.class))).thenReturn(tq);

        CashbackIndexRepositoryInterface cashbackIndexRepo = new CashbackIndexRepository(em);
        List<String> result = cashbackIndexRepo.getGenres();

        assertThat(result).contains("POP");
        assertThat(result).contains("ROCK");
        assertThat(result).contains("CLASSICAL");
        assertThat(result).contains("MPB");
    }
}
