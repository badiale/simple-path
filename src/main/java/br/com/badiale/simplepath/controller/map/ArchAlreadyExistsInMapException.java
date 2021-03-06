package br.com.badiale.simplepath.controller.map;

import br.com.badiale.simplepath.model.LogisticPoint;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lançada quando um arco já existe entre dois pontos.
 * <p>
 * A existência de um arco depende das relações entre os pontos, e é dada pelo método
 * {@link br.com.badiale.simplepath.model.LogisticPoint#hasSibling(LogisticPoint)}
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ArchAlreadyExistsInMapException extends RuntimeException {
    public ArchAlreadyExistsInMapException(String from, String to) {
        super("arch already exists in map: from=" + from + ", to=" + to);
    }
}
