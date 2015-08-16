package br.com.badiale.simplepath.controller.path;

import br.com.badiale.simplepath.model.LogisticPoint;
import br.com.badiale.simplepath.repository.LogisticPointRepository;
import com.google.common.collect.Lists;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller que expõe serviços para busca de menor caminho.
 */
@RestController
@RequestMapping("/path")
public class PathController {
    @Resource
    private PathService pathService;

    @Resource
    private LogisticPointRepository pointRepository;

    /**
     * Encontra o menor caminho entre dois pontos.
     * <p>
     * Também podem ser fornecido para este serviço a autonomia do caminhão,
     * isto é, a quantidade de km que ele percorre por litro de gasolina, e
     * o valor do litro da gasolina. Com estes dados, o serviço calcula o
     * valor total da viagem.
     * Se estes valores não forem fornecidos, é assumido que a autonomia é 10,
     * e o valor do litro é 2,5.
     *
     * @param fromName Nome do ponto de origem.
     * @param toName   Nome do ponto de destino.
     * @param autonomy Autonomia do veículo.
     * @param gasValue Valor do litro da gasolina.
     * @return Caminho mais curto entre o início e fim, e o valor gasto para percorrê-lo.
     * @throws PointNotFoundException caso os pontos fornecidos não sejam encontrados.
     * @throws PathNotFoundException  caso não exista um caminho entre os pontos.
     * @see br.com.badiale.simplepath.controller.path.PathService
     */
    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PathControllerResponse getShortPath(
            @RequestParam("from") String fromName,
            @RequestParam("to") String toName,
            @RequestParam(value = "autonomy", defaultValue = "10") Double autonomy,
            @RequestParam(value = "gasValue", defaultValue = "2.5") Double gasValue
    ) {
        LogisticPoint from = pointRepository.findByName(fromName);
        if (from == null) {
            throw new PointNotFoundException(fromName);
        }

        LogisticPoint to = pointRepository.findByName(toName);
        if (to == null) {
            throw new PointNotFoundException(toName);
        }

        Path path = pathService.shorthestPath(from, to);
        List<String> pointNames = Lists.newArrayList(from.getName());
        Double value = (path.getDistance() / autonomy) * gasValue;
        for (LogisticPoint point : path.getPath()) {
            pointNames.add(point.getName());
        }
        return new PathControllerResponse(pointNames, value);
    }
}
