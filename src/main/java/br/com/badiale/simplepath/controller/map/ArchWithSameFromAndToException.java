package br.com.badiale.simplepath.controller.map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "arch has the same from and to")
public class ArchWithSameFromAndToException extends RuntimeException {

}
