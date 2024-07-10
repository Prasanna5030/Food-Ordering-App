package com.sl.foodorderingsystem.controller;

import com.sl.foodorderingsystem.entity.Bill;
import com.sl.foodorderingsystem.service.BIllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/home/bill")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class BillController {
    @Autowired
    private BIllService billService;

    @PostMapping("/generatereport")
    public ResponseEntity<String> generateReport(@RequestBody Map<String , Object> requestMap){

        try{
            return billService.generateReport(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong" , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getbills")
    public ResponseEntity<List<Bill>> getBills(){
      return  billService.getBills();
    }

    @PostMapping("/getpdf")
    public ResponseEntity<byte[]> getPdf(@RequestBody Map<String , Object> requestMap){
        return billService.getPdf(requestMap);

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteBill(@PathVariable Integer id){
        return billService.deleteBill(id);

    }

}
