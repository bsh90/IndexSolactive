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
import service.index.mapper.IndexshareMapper;
import service.index.respository.IndexRepository;
import service.index.respository.IndexshareRepository;
import service.index.respository.InputRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    IndexshareMapper indexshareMapper;

    public IndexService(IndexRepository indexRepository,
                        InputRepository inputRepository,
                        IndexshareRepository indexshareRepository,
                        CreationInputMapper creationInputMapper,
                        IndexMapper indexMapper,
                        IndexshareMapper indexshareMapper) {
        this.indexRepository = indexRepository;
        this.inputRepository = inputRepository;
        this.indexshareRepository = indexshareRepository;
        this.creationInputMapper = creationInputMapper;
        this.indexMapper = indexMapper;
        this.indexshareMapper = indexshareMapper;
    }

    public ResponseEntity<CreationInputDto> createIndex(CreationInputDto creationInputDto) {
        HttpHeaders responseHeaders = new HttpHeaders();

        List<IndexEntity> availableIndexEntities = indexRepository.findByIndexName(creationInputDto.getIndex().getIndexName());
        if (!availableIndexEntities.isEmpty()) {
            return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(409));
        }
        if (!indexValidation(creationInputDto.getIndex())) {
            return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(400));
        }

        CreationInputEntity creationInputEntity = creationInputMapper.to(creationInputDto);
        CreationInputEntity createdCreationInputEntity = inputRepository.saveAndFlush(creationInputEntity);
        CreationInputDto createdCreationInputDto = creationInputMapper.from(createdCreationInputEntity);
        return new ResponseEntity<>(createdCreationInputDto, responseHeaders, HttpStatusCode.valueOf(201));
    }

    private boolean indexValidation(IndexDto indexDto) {
        return shareValidation(indexDto.getIndexshares()) && !indexDto.getIndexName().isBlank();
    }

    private boolean shareValidation(List<IndexshareDto> indexshareDtos) {
        List<IndexshareDto> indexshareDtosBlankName = indexshareDtos.stream()
                .filter(share -> share.getShareName().isBlank()).toList();
        List<IndexshareDto> indexshareDtosNegativePrice = indexshareDtos.stream()
                .filter(share -> share.getSharePrice() <= 0).toList();
        List<IndexshareDto> indexshareDtosNegativeShareNumber = indexshareDtos.stream()
                .filter(share -> share.getNumberOfshares() <= 0).toList();
        return indexshareDtosBlankName.isEmpty() &&
                indexshareDtosNegativePrice.isEmpty() &&
                indexshareDtosNegativeShareNumber.isEmpty();
//                &&
//                indexshareDtos.size() >= 2;
    }

    public ResponseEntity adjustIndex(AdjustmentInputDto adjustmentInputDto) {
        HttpHeaders responseHeaders = new HttpHeaders();

        if (adjustmentInputDto.getAdditionOperation() != null) {
            List<IndexEntity> indexEntities = indexRepository.findByIndexName(adjustmentInputDto.getAdditionOperation().getIndexName());
            if (indexEntities.isEmpty()) {
                return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(404));
            }
            IndexEntity indexEntity = indexEntities.get(0);
            if (!indexValidation(indexMapper.from(indexEntity))) {
                return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(400));
            }
            List<IndexshareEntity> existingIndexshareEntities = indexshareRepository.findByShareName(
                    adjustmentInputDto.getAdditionOperation().getShareName());
            if (!existingIndexshareEntities.isEmpty()) {
                return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(202));
            }
            addNewIndexShareToIndexEntity(indexEntity, adjustmentInputDto);

            return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(201));
        }
        if (adjustmentInputDto.getDeletionOperation() != null) {
            List<IndexEntity> indexEntities = indexRepository.findByIndexName(adjustmentInputDto.getDeletionOperation().getIndexName());
            if (indexEntities.isEmpty()) {
                return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(404));
            }
            IndexEntity indexEntity = indexEntities.get(0);
            if (!indexValidation(indexMapper.from(indexEntity))) {
                return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(400));
            }
            boolean getDeleted = indexEntity.getIndexshares().removeIf(share ->
                    share.getShareName().equals(adjustmentInputDto.getDeletionOperation().getShareName()));
            if (!getDeleted) {
                return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(401));
            } else {
                if (indexEntity.getIndexshares().size() < 2) {
                    return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(405));
                } else {
                    indexRepository.saveAndFlush(indexEntity);
                    return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(200));
                }
            }
        }
        if (adjustmentInputDto.getDividendOperation() != null) {
            List<IndexshareEntity> indexshareEntities = indexshareRepository.findByShareName(
                    adjustmentInputDto.getDividendOperation().getShareName());
            if (indexshareEntities.isEmpty()) {
                return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(401));
            }
            List<IndexshareDto> indexshareDtos = indexshareEntities.stream().map(share ->
                    indexshareMapper.from(share)).toList();
            if (!shareValidation(indexshareDtos)) {
                return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(400));
            }
            indexshareEntities.forEach(share -> {
                Double newPrice = share.getSharePrice() / adjustmentInputDto.getDividendOperation().getDividendValue();
                share.setSharePrice(newPrice);
                indexshareRepository.saveAndFlush(share);
            });
            return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(200));
        }

        else {
            return new ResponseEntity<>(null, responseHeaders, HttpStatusCode.valueOf(404));
        }
    }

    private void addNewIndexShareToIndexEntity(IndexEntity indexEntity, AdjustmentInputDto adjustmentInputDto) {
        List<IndexshareEntity> indexshareEntities = indexEntity.getIndexshares();
        IndexshareEntity newIndexshareEntity = new IndexshareEntity();
        newIndexshareEntity.setShareName(adjustmentInputDto.getAdditionOperation().getShareName());
        newIndexshareEntity.setSharePrice(adjustmentInputDto.getAdditionOperation().getSharePrice());
        newIndexshareEntity.setNumberOfshares(adjustmentInputDto.getAdditionOperation().getNumberOfshares());
        newIndexshareEntity.setIndex(indexEntity);
        indexshareEntities.add(newIndexshareEntity);
        indexEntity.setIndexshares(indexshareEntities);
        indexRepository.saveAndFlush(indexEntity);
    }

    public ResponseEntity getState() {
        HttpHeaders responseHeaders = new HttpHeaders();
        List<IndexEntity> indexEntities = indexRepository.findAll();
        List<IndexDto> indexDtos = indexEntities.stream().map(index -> indexMapper.from(index)).toList();
        return new ResponseEntity<>(indexDtos, responseHeaders, HttpStatusCode.valueOf(200));
    }

    public ResponseEntity getLatestState(String index_name) {
        HttpHeaders responseHeaders = new HttpHeaders();
        List<IndexEntity> indexEntities = indexRepository.findByIndexName(index_name);
        List<IndexDto> indexDtos = indexEntities.stream().map(index -> indexMapper.from(index)).toList();
        return new ResponseEntity<>(indexDtos, responseHeaders, HttpStatusCode.valueOf(200));
    }
}
