package com.codemaster.service.privilige;

import com.codemaster.repository.privilege.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriviligeService {
    private PrivilegeRepository privilegeRepository;

    @Autowired
    public PriviligeService(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }




}
