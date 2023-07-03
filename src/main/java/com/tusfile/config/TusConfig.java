package com.tusfile.config;

import me.desair.tus.server.TusFileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
public class TusConfig {

    @Value("${tus.server.setting.path}")
    String tusStoragePath;

    @Value("${tus.server.setting.expiration}")
    Long tusExpirationPeriod;

    @PreDestroy
    public void exit() throws IOException {
        tus().cleanup();
    }

    @Bean
    public TusFileUploadService tus(){
        return new TusFileUploadService()
                    .withStoragePath(tusStoragePath)
                    .withDownloadFeature()
                    .withUploadExpirationPeriod(tusExpirationPeriod)
                    .withUploadURI("/api/tus/file/upload");
    }
}
