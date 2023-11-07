package pl.lotto.infrastructure.resultannouncer.controller.error;

import org.springframework.http.HttpStatus;

public record RessultAnnouncerErrorResponse(String message, HttpStatus status) {
}
