package service.index.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.index.dto.AdjustmentInputDto;
import service.index.dto.CreationInputDto;
import service.index.service.IndexService;

@RestController
public class Controller {

    @Autowired
    IndexService indexService;

    public Controller(IndexService indexService) {
        this.indexService = indexService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreationInputDto> createIndex(@RequestBody CreationInputDto creationInputDto){
        return indexService.createIndex(creationInputDto);
    }

    @PostMapping("/indexAdjustment")
    public ResponseEntity adjustIndex(@RequestBody AdjustmentInputDto adjustmentInputDto){
        return indexService.adjustIndex(adjustmentInputDto);
    }
}
