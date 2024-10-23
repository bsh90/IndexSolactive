package service.index.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import service.index.dto.*;

import static java.util.Arrays.asList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void happyPath_createIndex() {
        IndexDto indexDto = getIndexDto();
        CreationInputDto input = new CreationInputDto(indexDto);

        webTestClient
                .post()
                .uri("/create")
                .body(Mono.just(input), CreationInputDto.class)
                .exchange()
                .expectStatus().isEqualTo(201);
    }

    private IndexDto getIndexDto() {
        IndexshareDto indexshareDto1 = new IndexshareDto("A.OQ", 10.0, 20.0);
        IndexshareDto indexshareDto2 = new IndexshareDto("B.OQ", 20.0, 30.0);
        IndexshareDto indexshareDto3 = new IndexshareDto("C.OQ", 30.0, 40.0);
        IndexshareDto indexshareDto4 = new IndexshareDto("D.OQ", 40.0, 50.0);
        return new IndexDto("INDEX_1", asList(indexshareDto1, indexshareDto2, indexshareDto3,
                indexshareDto4));
    }


    @Test
    void happyPath_adjustIndex_additionOperation() {
        happyPath_createIndex();
        AdditionOperationDto additionOperationDto = new AdditionOperationDto("E.OQ", 10.0, 20.0, "INDEX_1");
        AdjustmentInputDto input = new AdjustmentInputDto(additionOperationDto, null, null);

        webTestClient
                .post()
                .uri("/indexAdjustment")
                .body(Mono.just(input), AdjustmentInputDto.class)
                .exchange()
                .expectStatus().isEqualTo(201);
    }

    @Test
    void happyPath_adjustIndex_deletionOperation() {
        happyPath_createIndex();
        DeletionOperationDto deletionOperationDto = new DeletionOperationDto("DELETION", "D.OQ", "INDEX_1");
        AdjustmentInputDto input = new AdjustmentInputDto(null, deletionOperationDto, null);

        webTestClient
                .post()
                .uri("/indexAdjustment")
                .body(Mono.just(input), AdjustmentInputDto.class)
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    void happyPath_adjustIndex_dividendOperation() {
        happyPath_createIndex();
        DividendOperationDto dividendOperationDto = new DividendOperationDto("CASH_DIVIDEND", "A.OQ", 2.0);
        AdjustmentInputDto input = new AdjustmentInputDto(null, null, dividendOperationDto);

        webTestClient
                .post()
                .uri("/indexAdjustment")
                .body(Mono.just(input), AdjustmentInputDto.class)
                .exchange()
                .expectStatus().isEqualTo(200);
    }

    @Test
    void happyPath_getState() {
        IndexshareDto indexshareDto1 = new IndexshareDto("A.OQ", 8.0, 36.8574199806014);
        IndexshareDto indexshareDto2 = new IndexshareDto("B.OQ", 20.0, 55.286129970902);
        IndexshareDto indexshareDto3 = new IndexshareDto("C.OQ", 30.0, 73.7148399612027);
        IndexshareDto indexshareDto4 = new IndexshareDto("E.OQ", 10.0, 38.7972841901067);
        IndexDto indexDto = new IndexDto("INDEX_1", asList(indexshareDto1, indexshareDto2, indexshareDto3,
                indexshareDto4));
        CreationInputDto input = new CreationInputDto(indexDto);

        webTestClient
                .post()
                .uri("/create")
                .body(Mono.just(input), CreationInputDto.class)
                .exchange()
                .expectStatus().isEqualTo(201);

        webTestClient
                .get()
                .uri("/indexState")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody().equals(input);
    }

    @Test
    void happyPath_getLatestState() {
        happyPath_createIndex();
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/indexState")
                        .queryParam("index_name", "INDEX_1").build())
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody().equals(getIndexDto());
    }
}