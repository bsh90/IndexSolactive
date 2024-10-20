package service.index.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.index.dto.InputDto;
import service.index.service.IndexService;

@RestController
public class Controller {

    @Autowired
    IndexService indexService;

    public Controller(IndexService indexService) {
        this.indexService = indexService;
    }

    @PostMapping("/create")
    public ResponseEntity<InputDto> create(@RequestBody InputDto inputDto){
        return indexService.create(inputDto);
    }
}
