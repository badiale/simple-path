package br.com.badiale.simplepath.controller.map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception que é lançada quando a distância entre dois pontos é zero ou menos.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "arch has distance less than or equal zero")
public class ArchHasDistanceLessThanOrEqualToZeroException extends RuntimeException {
}
