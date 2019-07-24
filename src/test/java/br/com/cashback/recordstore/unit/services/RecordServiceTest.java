package br.com.cashback.recordstore.unit.services;

import br.com.cashback.recordstore.infrastructure.repositories.RecordRepositoryInterface;
import br.com.cashback.recordstore.models.Record;
import br.com.cashback.recordstore.resources.responses.RecordResponse;
import br.com.cashback.recordstore.services.RecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordServiceTest {

    @Mock
    RecordRepositoryInterface recordRepository;

    @Test
    public void testShouldGetRecordsByIdIn() {

        Record rec1 = new Record();
        Record rec2 = new Record();

        when(this.recordRepository.getRecordByIdIn("aaaaa", "bbbbb"))
                .thenReturn(Arrays.asList(rec1, rec2));

        RecordService service = new RecordService();
        service.setRecordRepository(this.recordRepository);
        List<Record> result = service.getRecordsByIdIn("aaaaa", "bbbbb");

        assertThat(result).contains(rec1);
        assertThat(result).contains(rec2);
    }

    @Test
    public void testShouldGetRecordById() {

        Record rec = new Record();
        rec.setId("aaaaa");

        when(this.recordRepository.findById("aaaaa")).thenReturn(Optional.of(rec));

        RecordService service = new RecordService();
        service.setRecordRepository(recordRepository);

        RecordResponse result = service.getRecordById("aaaaa");

        assertThat(result.getId()).isEqualTo("aaaaa");
    }

    @Test(expected = ResponseStatusException.class)
    public void testShouldntGetRecordById() {

        Record rec = new Record();
        rec.setId("aaaaa");

        when(this.recordRepository.findById("aaaaa")).thenReturn(Optional.empty());

        RecordService service = new RecordService();
        service.setRecordRepository(recordRepository);

        service.getRecordById("aaaaa");
    }

    @Test
    public void testShouldGetRecordsByGenreOrderByTitle() {

        Record rec1 = new Record();
        rec1.setId("aaaaa");

        Record rec2 = new Record();
        rec2.setId("bbbbb");

        List<Record> recList = Arrays.asList(rec1, rec2);

        Page<Record> page = new PageImpl(recList);
        Pageable pageable = Mockito.mock(Pageable.class);
        when(this.recordRepository.getRecordsByGenreOrderByTitle(eq("POP"), eq(pageable)))
                .thenReturn(page);

        RecordService service = new RecordService();
        service.setRecordRepository(recordRepository);
        Page<RecordResponse> result = service.getRecords("POP", pageable);

        assertThat(result.getContent().get(0).getId()).isEqualTo("aaaaa");
        assertThat(result.getContent().get(1).getId()).isEqualTo("bbbbb");
    }

    @Test
    public void testShouldGetRecordsOrderByTitle() {

        Record rec1 = new Record();
        rec1.setId("aaaaa");

        Record rec2 = new Record();
        rec2.setId("bbbbb");

        List<Record> recList = Arrays.asList(rec1, rec2);

        Page<Record> page = new PageImpl(recList);
        Pageable pageable = Mockito.mock(Pageable.class);
        when(pageable.getPageNumber()).thenReturn(0);
        when(pageable.getPageSize()).thenReturn(10);
        when(this.recordRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        RecordService service = new RecordService();
        service.setRecordRepository(recordRepository);
        Page<RecordResponse> result = service.getRecords(null, pageable);

        assertThat(result.getContent().get(0).getId()).isEqualTo("aaaaa");
        assertThat(result.getContent().get(1).getId()).isEqualTo("bbbbb");
    }

    @Test
    public void testShouldSaveAll() {

        Record rec1 = new Record();
        rec1.setId("aaaaa");

        Record rec2 = new Record();
        rec2.setId("bbbbb");

        List<Record> recList = Arrays.asList(rec1, rec2);

        when(recordRepository.saveAll(recList)).thenReturn(recList);

        RecordService recordService = new RecordService();
        recordService.setRecordRepository(recordRepository);
        recordService.saveAll(recList);

        assertThat(recList.get(0)).isEqualTo(rec1);
        assertThat(recList.get(1)).isEqualTo(rec2);
    }

    @Test
    public void testShouldCheckRecordAbscence() {
        when(recordRepository.count()).thenReturn(0l);
        RecordService service = new RecordService();
        service.setRecordRepository(recordRepository);
        service.checkRecordsAbscence();
    }

    @Test(expected = ResponseStatusException.class)
    public void testShouldCheckRecordAbscenceWithRecords() {
        when(recordRepository.count()).thenReturn(1l);
        RecordService service = new RecordService();
        service.setRecordRepository(recordRepository);
        service.checkRecordsAbscence();
    }
}
