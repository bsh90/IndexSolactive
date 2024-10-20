package service.index.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.index.dto.IndexshareDto;
import service.index.dto.InputDto;
import service.index.entity.IndexEntity;
import service.index.entity.InputEntity;
import service.index.mapper.InputMapper;
import service.index.respository.IndexRepository;
import service.index.respository.InputRepository;

import java.util.List;

@Service
public class IndexService {

    @Autowired
    IndexRepository indexRepository;

    @Autowired
    InputRepository inputRepository;

    @Autowired
    InputMapper inputMapper;

    public IndexService(IndexRepository indexRepository,
                        InputRepository inputRepository,
                        InputMapper inputMapper) {
        this.indexRepository = indexRepository;
        this.inputRepository = inputRepository;
        this.inputMapper = inputMapper;
    }

    public ResponseEntity<InputDto> create(InputDto inputDto) {
        HttpHeaders responseHeaders = new HttpHeaders();

        List<IndexEntity> availableIndexEntities = indexRepository.findByIndexName(inputDto.index.indexName);
        if (!availableIndexEntities.isEmpty()) {
            return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(409));
        }
        if (validation(inputDto)) {
            return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(400));
        }


        InputEntity inputEntity = inputMapper.to(inputDto);
        InputEntity createdInputEntity = inputRepository.save(inputEntity);
        InputDto createdInputDto = inputMapper.from(createdInputEntity);
        return new ResponseEntity<>(createdInputDto, responseHeaders, HttpStatusCode.valueOf(201));
    }

    private boolean validation(InputDto inputDto) {
        List<IndexshareDto> indexshareDtosBlankName = inputDto.index.getIndexshares().stream().filter(share -> share.shareName.isBlank()).toList();
        List<IndexshareDto> indexshareDtosNegativePrice = inputDto.index.getIndexshares().stream().filter(share -> share.sharePrice <= 0).toList();
        List<IndexshareDto> indexshareDtosNegativeShareNumber = inputDto.index.getIndexshares().stream().filter(share -> share.numberOfshares <= 0).toList();
        return indexshareDtosBlankName.isEmpty() &&
                indexshareDtosNegativePrice.isEmpty() &&
                indexshareDtosNegativeShareNumber.isEmpty() &&
                !inputDto.index.indexName.isBlank() &&
                inputDto.index.getIndexshares().size() >= 2;
    }
}
