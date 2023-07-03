package com.tusfile.controller;

import com.tusfile.service.TusUploadService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@RestController
@RequiredArgsConstructor
public class TusUploadController {

    private final TusUploadService tusUploadService;

    @RequestMapping(value={"/api/tus/file/upload", "/api/tus/file/upload/**"},
                    method={RequestMethod.POST, RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity uploadFileByTus(HttpServletRequest request, HttpServletResponse response){
        tusUploadService.process(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
