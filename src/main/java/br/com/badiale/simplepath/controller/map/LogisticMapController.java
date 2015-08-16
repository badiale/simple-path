package br.com.badiale.simplepath.controller.map;

import br.com.badiale.simplepath.model.LogisticMap;
import br.com.badiale.simplepath.model.LogisticPoint;
import br.com.badiale.simplepath.repository.LogisticMapRepository;
import br.com.badiale.simplepath.repository.LogisticPointRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/map")
public class LogisticMapController {
    @Resource
    private LogisticMapRepository mapRepository;

    @Resource
    private LogisticPointRepository pointRepository;

    /**
     * Persiste um mapa na base de dados.
     *
     * @param inputMap Mapa serializável.
     * @return O próprio mapa serializado caso haja sucesso.
     * @throws MapAlreadyExistsException                     caso já exista um mapa com este nome.
     * @throws ArchHasDistanceLessThanOrEqualToZeroException quando a distância de um arco seja menor ou igual à zero.
     * @throws ArchWithSameFromAndToException                caso um arco seja entre um único ponto.
     * @throws ArchAlreadyExistsInMapException               caso o arco já exista.
     */
    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public SerializedLogisticMap convertAndPersist(@RequestBody SerializedLogisticMap inputMap) {
        if (mapRepository.findByName(inputMap.getName()) != null) {
            throw new MapAlreadyExistsException();
        }

        LogisticMap map = mapRepository.save(new LogisticMap(inputMap.getName()));

        for (SerializedLogisticArch arch : inputMap.getArches()) {
            if (arch.getDistance() <= 0) {
                throw new ArchHasDistanceLessThanOrEqualToZeroException();
            }

            if (arch.getFrom().equals(arch.getTo())) {
                throw new ArchWithSameFromAndToException();
            }

            LogisticPoint from = getPoint(arch.getFrom());
            LogisticPoint to = getPoint(arch.getTo());

            map.addPoint(from);
            map.addPoint(to);

            if (from.hasSibling(to)) {
                throw new ArchAlreadyExistsInMapException();
            }

            from.addSibling(to, arch.getDistance());
            pointRepository.save(from);
        }

        mapRepository.save(map);

        return inputMap;
    }

    /**
     * Busca no banco um ponto pelo nome, mas se não encontrar, retorna um novo ponto persistido.
     *
     * @param pointName nome do ponto.
     * @return Ponto persistido.
     */
    private LogisticPoint getPoint(String pointName) {
        LogisticPoint result = pointRepository.findByName(pointName);
        if (result != null) {
            return result;
        }
        return pointRepository.save(new LogisticPoint(pointName));
    }
}
