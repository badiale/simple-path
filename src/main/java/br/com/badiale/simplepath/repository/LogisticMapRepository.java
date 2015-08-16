package br.com.badiale.simplepath.repository;

import br.com.badiale.simplepath.model.LogisticMap;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface LogisticMapRepository extends GraphRepository<LogisticMap> {
    /**
     * Busca um mapa dado seu nome.
     *
     * @param name Nome do mapa.
     * @return Mapa com o dado nome, ou null caso não seja encontrado.
     */
    LogisticMap findByName(String name);
}
