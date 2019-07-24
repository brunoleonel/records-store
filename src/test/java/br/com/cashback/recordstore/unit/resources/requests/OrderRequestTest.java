package br.com.cashback.recordstore.unit.resources.requests;

import br.com.cashback.recordstore.resources.requests.OrderItemRequest;
import br.com.cashback.recordstore.resources.requests.OrderRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRequestTest {

    @Test
    public void testShouldCreateOrderRequest() {

        OrderItemRequest item1 = new OrderItemRequest();
        item1.setRecordId("aaaaa");

        OrderItemRequest item2 = new OrderItemRequest();
        item1.setRecordId("bbbbb");

        OrderRequest request = new OrderRequest();
        request.setRecords(Arrays.asList(item1, item2));

        assertThat(request.getRecords().get(0)).isEqualTo(item1);
        assertThat(request.getRecords().get(1)).isEqualTo(item2);
    }
}
