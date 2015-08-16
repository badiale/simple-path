package br.com.badiale.simplepath.controller.map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando o mapa já existe na base.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "map already exists")
public class MapAlreadyExistsException extends RuntimeException {

}
