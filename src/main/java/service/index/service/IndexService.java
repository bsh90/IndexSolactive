package service.index.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.index.dto.AdjustmentInputDto;
import service.index.dto.CreationInputDto;
import service.index.dto.IndexDto;
import service.index.dto.IndexshareDto;
import service.index.entity.CreationInputEntity;
import service.index.entity.IndexEntity;
import service.index.entity.IndexshareEntity;
import service.index.mapper.CreationInputMapper;
import service.index.mapper.IndexMapper;
import service.index.respository.IndexRepository;
import service.index.respository.IndexshareRepository;
import service.index.respository.InputRepository;

import java.util.List;
import java.util.Optional;

@Service
public class IndexService {

    @Autowired
    IndexRepository indexRepository;

    @Autowired
    InputRepository inputRepository;

    @Autowired
    IndexshareRepository indexshareRepository;

    @Autowired
    CreationInputMapper creationInputMapper;

    @Autowired
    IndexMapper indexMapper;

    public IndexService(IndexRepository indexRepository,
                        InputRepository inputRepository,
                        IndexshareRepository indexshareRepository,
                        CreationInputMapper creationInputMapper,
                        IndexMapper indexMapper) {
        this.indexRepository = indexRepository;
        this.inputRepository = inputRepository;
        this.indexshareRepository = indexshareRepository;
        this.creationInputMapper = creationInputMapper;
        this.indexMapper = indexMapper;
    }

    public ResponseEntity<CreationInputDto> createIndex(CreationInputDto creationInputDto) {
        HttpHeaders responseHeaders = new HttpHeaders();

        List<IndexEntity> availableIndexEntities = indexRepository.findByIndexName(creationInputDto.getIndex().getIndexName());
        if (!availableIndexEntities.isEmpty()) {
            return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(409));
        }
        if (!validation(creationInputDto.getIndex())) {
            return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(400));
        }

        CreationInputEntity creationInputEntity = creationInputMapper.to(creationInputDto);
        CreationInputEntity createdCreationInputEntity = inputRepository.saveAndFlush(creationInputEntity);
        CreationInputDto createdCreationInputDto = creationInputMapper.from(createdCreationInputEntity);
        return new ResponseEntity<>(createdCreationInputDto, responseHeaders, HttpStatusCode.valueOf(201));
    }

    private boolean validation(IndexDto indexDto) {
        List<IndexshareDto> indexshareDtosBlankName = indexDto.getIndexshares().stream()
                .filter(share -> share.getShareName().isBlank()).toList();
        List<IndexshareDto> indexshareDtosNegativePrice = indexDto.getIndexshares().stream()
                .filter(share -> share.getSharePrice() <= 0).toList();
        List<IndexshareDto> indexshareDtosNegativeShareNumber = indexDto.getIndexshares().stream()
                .filter(share -> share.getNumberOfshares() <= 0).toList();
        return indexshareDtosBlankName.isEmpty() &&
                indexshareDtosNegativePrice.isEmpty() &&
                indexshareDtosNegativeShareNumber.isEmpty() &&
                !indexDto.getIndexName().isBlank() &&
                indexDto.getIndexshares().size() >= 2;
    }

    public ResponseEntity adjustIndex(AdjustmentInputDto adjustmentInputDto) {
        HttpHeaders responseHeaders = new HttpHeaders();

        if (adjustmentInputDto.getAdditionOperation() != null) {
            List<IndexEntity> indexEntities = indexRepository.findByIndexName(adjustmentInputDto.getAdditionOperation().getIndexName());
            if (indexEntities.isEmpty()) {
                return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(404));
            }
            IndexEntity indexEntity = indexEntities.get(0);
            if (!validation(indexMapper.from(indexEntity))) {
                return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(400));
            }
            List<IndexshareEntity> existingIndexshareEntities = indexshareRepository.findByShareName(
                    adjustmentInputDto.getAdditionOperation().getShareName());
            if (!existingIndexshareEntities.isEmpty()) {
                return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(202));
            }

            List<IndexshareEntity> indexshareEntities = indexEntity.getIndexshares();
            IndexshareEntity newIndexshareEntity = new IndexshareEntity();
            newIndexshareEntity.setShareName(adjustmentInputDto.getAdditionOperation().getShareName());
            newIndexshareEntity.setSharePrice(adjustmentInputDto.getAdditionOperation().getSharePrice());
            newIndexshareEntity.setNumberOfshares(adjustmentInputDto.getAdditionOperation().getNumberOfshares());
            indexshareEntities.add(newIndexshareEntity);
            indexEntity.setIndexshares(indexshareEntities);
            indexRepository.saveAndFlush(indexEntity);
            return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(201));
        } else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(404));
        }
    }
}
