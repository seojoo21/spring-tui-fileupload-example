package com.tusfile.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TusUploadService {
    public void process(HttpServletRequest request, HttpServletResponse response);
}
