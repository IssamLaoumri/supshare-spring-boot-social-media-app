package com.laoumri.supsharespringbootsocialmediaapp.services;

import com.laoumri.supsharespringbootsocialmediaapp.nodes.Code;

public interface CodeService {
    Code save(String email);
    void verify(String code, String email);
}
