package com.laoumri.supsharespringbootsocialmediaapp.services.impl;

import com.laoumri.supsharespringbootsocialmediaapp.enums.ECode;
import com.laoumri.supsharespringbootsocialmediaapp.enums.code.ECodeVerif;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.BadRequestException;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.Code;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.User;
import com.laoumri.supsharespringbootsocialmediaapp.repositories.neo4j.CodeRepository;
import com.laoumri.supsharespringbootsocialmediaapp.services.CodeService;
import com.laoumri.supsharespringbootsocialmediaapp.services.UserService;
import com.laoumri.supsharespringbootsocialmediaapp.utils.ZipUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {
    private final CodeRepository codeRepository;
    private final UserService userService;

    @Override
    public Code save(String email, ECode type) {
        String generatedCode = ZipUtils.generateCode(5);
        User user = userService.findUserByEmail(email);
        Code code = Code.builder()
                .code(generatedCode)
                .validUntil(Instant.now().plus(Duration.ofMinutes(5)))
                .user(user)
                .type(type)
                .build();
        return codeRepository.save(code);
    }

    @Override
    public void verify(String code, String email) {
        User user = userService.findUserByEmail(email);
        Code codeNode = codeRepository.findByCodeAndUserId(code, user.getId()).orElseThrow(
                () -> new BadRequestException(ECodeVerif.CODE_VERIFICATION_FAILED, "Incorrect code")
        );
        codeRepository.deleteById(codeNode.getId());
    }
}
