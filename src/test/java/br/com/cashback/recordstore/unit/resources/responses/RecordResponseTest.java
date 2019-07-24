package br.com.cashback.recordstore.unit.resources.responses;

import br.com.cashback.recordstore.models.Record;
import br.com.cashback.recordstore.resources.responses.RecordResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordResponseTest {

    @MockBean
    private Record record;

    @Test
    public void testShouldCreateRecordResponseTest() {
        RecordResponse response = new RecordResponse(record);

        assertThat(response.getId()).isEqualTo(record.getId());
        assertThat(response.getGenre()).isEqualTo(record.getGenre());
        assertThat(response.getPrice()).isEqualTo(record.getPrice());
        assertThat(response.getTitle()).isEqualTo(record.getTitle());
    }
}
