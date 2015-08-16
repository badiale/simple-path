package br.com.badiale.simplepath.controller.path;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lançada o valor da gasolina fornecido é zero ou menos.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "gas value is zero or less")
public class GasValueIsZeroOsLessException extends RuntimeException {
}
