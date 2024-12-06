package ru.shekhovtsov.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shekhovtsov.model.Limit;
import ru.shekhovtsov.service.LimitService;

@RestController
@RequestMapping("/limits")
public class LimitController {

    @Autowired
    private LimitService limitService;

    @GetMapping("/{clientId}")
    public ResponseEntity<Limit> getLimit(@PathVariable Long clientId) {
        Limit limit = limitService.getLimit(clientId);
        return ResponseEntity.ok(limit);
    }
}