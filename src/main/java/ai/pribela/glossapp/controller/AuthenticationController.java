package ai.pribela.glossapp.controller;

import ai.pribela.glossapp.repository.data.Learner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @GetMapping("validate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void validate(@AuthenticationPrincipal Learner learner) {
        log.info("User {} validated", learner.getUsername());
    }
}
