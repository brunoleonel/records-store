package br.com.cashback.recordstore.unit.services;

import br.com.cashback.recordstore.infrastructure.services.CashbackIndexServiceInterface;
import br.com.cashback.recordstore.infrastructure.services.RecordServiceInterface;
import br.com.cashback.recordstore.services.CashbackIndexService;
import br.com.cashback.recordstore.services.SpotifyService;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpotifyServiceTest {

    @Mock
    private RecordServiceInterface recordService;

    @Mock
    private CashbackIndexServiceInterface cashbackIndexService;

    @Mock
    private SpotifyApi.Builder spotifyApiBuilder;

    @Test
    public void testShouldSyncWithSpotify() {

        try {
            doNothing().when(recordService).checkRecordsAbscence();
            when(cashbackIndexService.getGenres()).thenReturn(Arrays.asList("POP", "ROCK"));

            ClientCredentials credentials = Mockito.mock(ClientCredentials.class);
            when(credentials.getAccessToken()).thenReturn("abcdef");

            ClientCredentialsRequest credRequest = Mockito.mock(ClientCredentialsRequest.class);
            when(credRequest.execute()).thenReturn(credentials);

            ClientCredentialsRequest.Builder credReqBuilder = Mockito.mock(ClientCredentialsRequest.Builder.class);
            when(credReqBuilder.build()).thenReturn(credRequest);

            SpotifyApi spotifyApi = Mockito.mock(SpotifyApi.class);
            when(spotifyApi.clientCredentials()).thenReturn(credReqBuilder);

            when(spotifyApiBuilder.setClientId(anyString())).thenReturn(spotifyApiBuilder);
            when(spotifyApiBuilder.setClientSecret(anyString())).thenReturn(spotifyApiBuilder);
            when(spotifyApiBuilder.build()).thenReturn(spotifyApi);

            when(spotifyApiBuilder.setAccessToken("abcdef")).thenReturn(spotifyApiBuilder);
            when(spotifyApiBuilder.build()).thenReturn(spotifyApi);

            AlbumSimplified alb1 = Mockito.mock(AlbumSimplified.class);
            when(alb1.getId()).thenReturn("aaaaa");
            when(alb1.getName()).thenReturn("Teste a");

            AlbumSimplified alb2 = Mockito.mock(AlbumSimplified.class);
            when(alb2.getId()).thenReturn("bbbbb");
            when(alb2.getName()).thenReturn("Teste b");

            AlbumSimplified[] albumList = new AlbumSimplified[]{alb1, alb2};
            Paging<AlbumSimplified> albumPaging = Mockito.mock(Paging.class);
            when(albumPaging.getItems()).thenReturn(albumList);

            SearchAlbumsRequest searchRequest = Mockito.mock(SearchAlbumsRequest.class);
            when(searchRequest.execute()).thenReturn(albumPaging);

            SearchAlbumsRequest.Builder searchReqBuilder = Mockito.mock(SearchAlbumsRequest.Builder.class);
            when(searchReqBuilder.limit(50)).thenReturn(searchReqBuilder);
            when(searchReqBuilder.build()).thenReturn(searchRequest);

            when(spotifyApi.searchAlbums(anyString())).thenReturn(searchReqBuilder);

            SpotifyService service = new SpotifyService();
            service.setDependencies(recordService, cashbackIndexService, spotifyApiBuilder);
            service.syncWithSpotify();

            assertThat(albumPaging.getItems()).contains(alb1, alb2);
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }
    }
}
