package com.hevi.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DemoController {

    @Data
    public class Per {
        private Integer count;
        private Double rollResult;

        public Per(Integer count, Double rollResult) {
            this.count = count;
            this.rollResult = rollResult;
        }
    }

    @GetMapping(value = "/roll")
    public List roll() {
        List result = new ArrayList();
        int count = 0;
        double random = 0;
        do {
            count++;
            random = Math.ceil(Math.random() * 100);
            Per per = new Per(count, random);
            result.add(per);
        } while (random != 100);
        return result;
    }
}
