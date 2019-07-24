package br.com.cashback.recordstore.unit.services;

import br.com.cashback.recordstore.infrastructure.repositories.CashbackIndexRepositoryInterface;
import br.com.cashback.recordstore.services.CashbackIndexService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CashbackIndexServiceTest {

    @Mock
    private CashbackIndexRepositoryInterface cashbackIndexRepository;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShouldgGetCashBackIndexByGenreForDayOfWeek() {
        when(this.cashbackIndexRepository.getCashbackIndexByGenreForDayOfWeek(eq("POP"), anyString()))
                .thenReturn(1.5f);

        CashbackIndexService cashbackIndexService = new CashbackIndexService();
        cashbackIndexService.setCashbackIndexRepository(this.cashbackIndexRepository);
        float result = cashbackIndexService.getCashBackIndexByGenreForDayOfWeek("POP");

        assertThat(result).isEqualTo(1.5f);
    }

    @Test
    public void testShouldgGetGenres() {
        when(this.cashbackIndexRepository.getGenres())
                .thenReturn(Arrays.asList("POP", "ROCK", "CLASSICAL", "MPB"));

        CashbackIndexService cashbackIndexService = new CashbackIndexService();
        cashbackIndexService.setCashbackIndexRepository(this.cashbackIndexRepository);
        List<String> result = cashbackIndexService.getGenres();

        assertThat(result).contains("POP");
        assertThat(result).contains("ROCK");
        assertThat(result).contains("CLASSICAL");
        assertThat(result).contains("MPB");
    }
}
