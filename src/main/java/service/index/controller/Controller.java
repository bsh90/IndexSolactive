package service.index.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/indexState")
    public ResponseEntity getState() {
        return indexService.getState();
    }

    @GetMapping("/indexState/{index_name}")
    public ResponseEntity getLatestState(@RequestParam("index_name") String index_name) {
        return indexService.getLatestState(index_name);
    }
}
