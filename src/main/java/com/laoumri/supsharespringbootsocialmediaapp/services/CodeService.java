package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.enums.ECode;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.Code;

public interface CodeService {
    Code save(String email, ECode type);
    void verify(String code, String email);
}
