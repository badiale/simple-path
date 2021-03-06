package br.com.badiale.simplepath.controller.map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando o mapa já existe na base.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MapAlreadyExistsException extends RuntimeException {
    public MapAlreadyExistsException(String mapName) {
        super("map already exists: " + mapName);
    }
}
