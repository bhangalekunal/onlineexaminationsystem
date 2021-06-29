package com.codemaster.controller.appconfig;

import com.codemaster.entity.appconfig.AppConfig;
import com.codemaster.exceptionhandler.EntityNotFoundException;
import com.codemaster.exceptionhandler.InvalidInputException;
import com.codemaster.service.appconfig.AppConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AppConfigController {

    @Autowired
    AppConfigService appConfigService;

    @GetMapping("/appConfigs")
    public ResponseEntity<List<AppConfig>> getAllAppConfigs()
    {
        return ResponseEntity.ok().body(appConfigService.getAllAppConfigs());
    }

    @GetMapping("/appConfigs/{keyName}")
    public  ResponseEntity<AppConfig> getAppConfigByKeyName(@PathVariable("keyName") String keyName) throws EntityNotFoundException, InvalidInputException
    {
        if(keyName == null || keyName.trim().equals(""))
        {
            throw new InvalidInputException("keyName is not a valid");
        }
        AppConfig appConfig = appConfigService.getAppConfigByKeyName(keyName);

        if(appConfig == null)
        {
            throw new EntityNotFoundException("AppConfig not found for kayname: "+keyName);
        }
        return ResponseEntity.ok().body(appConfig);
    }

    @PostMapping("/appConfigs")
    public ResponseEntity<AppConfig> createAppConfig(@Valid @RequestBody AppConfig appConfig)
    {
        return ResponseEntity.ok().body(appConfigService.addAppConfig(appConfig));
    }

    @PutMapping("/appConfigs/{keyName}")
    public ResponseEntity<AppConfig> updateAppConfig(@PathVariable("keyName") String keyName, @Valid @RequestBody AppConfig appConfig)
            throws EntityNotFoundException,InvalidInputException
    {
        if(keyName == null || keyName.trim().equals(""))
        {
            throw new InvalidInputException("keyName is not a valid");
        }
        AppConfig updatedAppCongig = appConfigService.updateAppConfig(keyName, appConfig);
        return ResponseEntity.ok().body(updatedAppCongig);
    }

    @DeleteMapping("/appConfigs/{keyName}")
    public Map<String, Boolean> deleteAppConfig(@PathVariable("keyName") String keyName) throws EntityNotFoundException,InvalidInputException
    {
        if(keyName == null || keyName.trim().equals(""))
        {
            throw new InvalidInputException("keyName is not a valid");
        }
        return appConfigService.deleteAppConfig(keyName);
    }
}
