package com.payment.payment.controller;

import com.payment.payment.model.Health;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/health")
public class HealthController {

    @GetMapping()
    public ResponseEntity<Health> getHealth() {
        return ResponseEntity.ok().body(new Health("OK"));
    }
}
