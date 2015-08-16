package br.com.badiale.simplepath.controller.path;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lançada a autonomia fornecida é zero ou menos.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "autonomy is zero or less")
public class AutonomyIsZeroOsLessException extends RuntimeException {
}
