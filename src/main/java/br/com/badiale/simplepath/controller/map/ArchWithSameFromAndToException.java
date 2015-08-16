package br.com.badiale.simplepath.controller.map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ArchWithSameFromAndToException extends RuntimeException {
    public ArchWithSameFromAndToException(String from) {
        super("arch has the same from and to: " + from);
    }
}
