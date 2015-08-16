package br.com.badiale.simplepath.controller.path;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lançada quando o caminho não existe entre os pontos fornecidos.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "path not found")
public class PathNotFoundException extends RuntimeException {
}
