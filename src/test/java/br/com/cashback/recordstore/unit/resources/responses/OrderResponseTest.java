package br.com.cashback.recordstore.unit.resources.responses;

import br.com.cashback.recordstore.models.Record;
import br.com.cashback.recordstore.resources.responses.OrderRecordResponse;
import br.com.cashback.recordstore.resources.responses.OrderResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderResponseTest {

    @Test
    public void testShouldCreateOrderResponse() {

        LocalDate date = LocalDate.now();

        Record rec1 = new Record();
        Record rec2 = new Record();

        OrderRecordResponse ordRec1 = new OrderRecordResponse(rec1, 1f);
        OrderRecordResponse ordRec2 = new OrderRecordResponse(rec2, 0.5f);

        OrderResponse response = new OrderResponse();
        response.setDate(date);
        response.setId(1);
        response.setCashback(1.5f);
        response.setRecords(Arrays.asList(ordRec1, ordRec2));

        assertThat(response.getDate()).isEqualTo(date);
        assertThat(response.getCashback()).isEqualTo(1.5f);
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getRecords().get(0)).isEqualTo(ordRec1);
        assertThat(response.getRecords().get(1)).isEqualTo(ordRec2);
    }
}
