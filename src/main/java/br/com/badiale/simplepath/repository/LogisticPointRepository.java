package br.com.badiale.simplepath.repository;

import br.com.badiale.simplepath.model.LogisticPoint;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface LogisticPointRepository extends GraphRepository<LogisticPoint> {
    /**
     * Busca um ponto dado seu nome.
     *
     * @param name Nome do ponto.
     * @return Ponto com o dado nome, ou null caso não seja encontrado.
     */
    LogisticPoint findByName(String name);
}
