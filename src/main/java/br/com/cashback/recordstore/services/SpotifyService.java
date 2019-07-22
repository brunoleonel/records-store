package br.com.cashback.recordstore.services;

import br.com.cashback.recordstore.infrastructure.services.CashbackIndexServiceInterface;
import br.com.cashback.recordstore.infrastructure.services.RecordServiceInterface;
import br.com.cashback.recordstore.infrastructure.services.SpotifyServiceInterface;
import br.com.cashback.recordstore.models.Record;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SpotifyService implements SpotifyServiceInterface {

    @Value("${spotify.client_id}")
    private String clientId;

    @Value("${spotify.client_secret}")
    private String clientSecret;

    @Autowired
    private RecordServiceInterface recordService;

    @Autowired
    private CashbackIndexServiceInterface cashbackIndexService;

    private SpotifyApi.Builder spotifyApiBuilder;
    private String accessToken;

    public SpotifyService() {
        this.spotifyApiBuilder = new SpotifyApi.Builder();
    }

    @Transactional
    @Override
    public void syncWithSpotify() {
        recordService.checkRecordsAbscence();
        List<String> genres = cashbackIndexService.getGenres();
        List<List<Record>> results = genres.stream()
            .map(genre -> {
                Paging<AlbumSimplified> albums = getRecordsFromSpotify(genre);
                List<Record> recordList = getRecordListFromSpotifyResult(albums, genre);
                return recordList;
            })
            .collect(Collectors.toList());

        List<Record> records = results.stream()
            .reduce(new ArrayList<>(), (prior, next) -> {
                prior.addAll(next);
                return prior;
            });

        recordService.saveAll(records);
    }

    private Paging<AlbumSimplified> getRecordsFromSpotify(String genre) {
        try {
            String query = String.format("\"%s\"", genre);

            this.accessToken = getAccessToken();
            SpotifyApi spotifyApi = this.spotifyApiBuilder
                    .setAccessToken(this.accessToken)
                    .build();
            SearchAlbumsRequest request = spotifyApi.searchAlbums(query)
                    .limit(50)
                    .build();

            Paging<AlbumSimplified> albums = request.execute();
            return albums;
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error occurred while getting records from Spotify",
                e
            );
        }
    }

    private String getAccessToken() {

        try {
            SpotifyApi spotifyApi = this.spotifyApiBuilder
                .setClientId(this.clientId)
                .setClientSecret(this.clientSecret)
                .build();

            ClientCredentials credentials = spotifyApi.clientCredentials()
                .build()
                .execute();

            return credentials.getAccessToken();
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error occurred while getting records from Spotify",
                e
            );
        }
    }

    private float getRandomPrice() {
        Random rand = new Random();
        return 1f + (rand.nextFloat() * 29f);
    }

    public List<Record> getRecordListFromSpotifyResult(Paging<AlbumSimplified> albums, String genre) {
        return Arrays.stream(albums.getItems())
            .map(album -> {
                Record record = new Record();
                record.setId(album.getId());
                record.setTitle(album.getName());
                record.setGenre(genre);
                record.setPrice(getRandomPrice());
                return record;
            })
            .collect(Collectors.toList());
    }
}
