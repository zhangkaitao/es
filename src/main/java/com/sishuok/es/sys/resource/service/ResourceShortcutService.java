package com.sishuok.es.sys.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sishuok.es.common.plugin.serivce.BaseMovableService;
import com.sishuok.es.sys.auth.service.UserAuthService;
import com.sishuok.es.sys.resource.entity.ResourceShortcut;

/**
 * <p>Version: 1.0
 */
@Service
public class ResourceShortcutService extends BaseMovableService<ResourceShortcut, Long> {

    @Autowired
    private UserAuthService userAuthService;

}
