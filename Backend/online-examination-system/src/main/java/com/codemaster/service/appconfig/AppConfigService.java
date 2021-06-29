package com.codemaster.service.appconfig;

import com.codemaster.entity.appconfig.AppConfig;
import com.codemaster.exceptionhandler.EntityNotFoundException;
import com.codemaster.repository.appconfig.AppConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppConfigService {
    @Autowired
    AppConfigRepository appConfigRepository;
    public List<AppConfig> getAllAppConfigs()
    {
        return appConfigRepository.findAll();
    }

    public AppConfig getAppConfigByKeyName(String keyName)
    {
        return appConfigRepository.findByKeyName(keyName);
    }

    public AppConfig addAppConfig(AppConfig appConfig)
    {
        return appConfigRepository.save(appConfig);
    }

    public AppConfig updateAppConfig(String keyName,AppConfig appConfigDetails) throws EntityNotFoundException
    {
        AppConfig appConfig = appConfigRepository.findByKeyName(keyName);
        if(appConfig == null)
        {
            throw new EntityNotFoundException("AppConfig not found for keyname: "+keyName);
        }

        appConfig.setValue(appConfigDetails.getValue());
        appConfig.setStatus(appConfigDetails.getStatus());
        final AppConfig updatedAppConfig = appConfigRepository.save(appConfig);
        return updatedAppConfig;
    }

    public Map<String, Boolean> deleteAppConfig(String keyName) throws EntityNotFoundException
    {
        AppConfig appConfig = appConfigRepository.findByKeyName(keyName);
        if(appConfig == null)
        {
            throw new EntityNotFoundException("AppConfig not found for keyname: "+keyName);
        }
        appConfigRepository.delete(appConfig);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
