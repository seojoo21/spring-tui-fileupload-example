package com.tusfile.service.impl;

import com.tusfile.service.TusUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TusUploadServiceImpl implements TusUploadService {

    private final TusFileUploadService tusFileUploadService;

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Process a tus upload request
            tusFileUploadService.process(request, response);

            // Get upload information
            UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(request.getRequestURI());

            if(uploadInfo != null && !uploadInfo.isUploadInProgress()){
                // Progress status is successful: Create File
                createFile(tusFileUploadService.getUploadedBytes(request.getRequestURI()), uploadInfo.getFileName());

                // Delete an upload associated with the given upload url
                tusFileUploadService.deleteUpload(request.getRequestURI());
            }

        } catch (IOException | TusException e) {
            log.error("TusUploadServiceImpl Process Exception has occurred. Message :: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void createFile(InputStream is, String fileName) throws IOException {
        File file = new File("destination/",fileName);
        FileUtils.copyInputStreamToFile(is, file);
    }
}
