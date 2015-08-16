package br.com.badiale.simplepath.controller.path;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lançada quando o ponto não é encontrado.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PointNotFoundException extends RuntimeException {
    public PointNotFoundException(String pointName) {
        super("point not found:" + pointName);
    }
}
