package br.com.cashback.recordstore.unit.resources.responses;

import br.com.cashback.recordstore.models.Record;
import br.com.cashback.recordstore.resources.responses.OrderRecordResponse;
import br.com.cashback.recordstore.resources.responses.RecordResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRecordResponseTest {

    @MockBean
    private Record record;

    @Test
    public void testShouldCreateOrderRecordResponse() {
        OrderRecordResponse response = new OrderRecordResponse(record, 1.5f);

        assertThat(response.getCashback()).isEqualTo(1.5f);
        assertThat(response.getRecord()).isInstanceOf(RecordResponse.class);
        assertThat(response.getRecord().getTitle()).isEqualTo(record.getTitle());
        assertThat(response.getRecord().getPrice()).isEqualTo(record.getPrice());
        assertThat(response.getRecord().getGenre()).isEqualTo(record.getGenre());
        assertThat(response.getRecord().getId()).isEqualTo(record.getId());
    }

    @Test
    public void testShouldSetRecordResponse() {
        OrderRecordResponse response = new OrderRecordResponse(record, 1.5f);
        RecordResponse recResponse = new RecordResponse(record);
        response.setRecord(recResponse);

        assertThat(response.getRecord()).isEqualTo(recResponse);
    }

    @Test
    public void testShouldSetCashback() {
        OrderRecordResponse response = new OrderRecordResponse(record, 1.5f);
        response.setCashback(1);

        assertThat(response.getCashback()).isEqualTo(1);
    }
}
