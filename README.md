# SimplePath

Este projeto é um sistema simples, que permite a busca pelo menor caminho entre dois pontos.

O conceito básico é que os pontos são cadastrados no sistema à partir de mapas, e então é possível
procurar o menor caminho entre dois pontos, de qualquer mapa.

## Requisitos

Este projeto foi desenvolvido em Java 8, com Mavem, então é necessário ter a JDK8 e o Maven instalado.

## Executando os testes

Para executar testes, basta executar o seguinte comando na raiz do projeto:

    mvn test

## Subindo o servidor

Para subir o servidor, basta executar o comando:

    mvn spring-boot:run

## Serviços expostos

Com o sistema rodando, os seguintes serviços ficam expostos na porta 8080 de sua máquina.

### Inserção de mapas

Para inserir um mapa, é necessário fazer um request POST na url `http://localhost:8080/map`, sendo que o corpo
da requisição deve ser da forma:

    {
        "name": "map",
        "arches": [
            {"from": "A", "to": "B", "distance": 10},
            {"from": "B", "to": "D", "distance": 15},
            {"from": "A", "to": "C", "distance": 20},
            {"from": "C", "to": "D", "distance": 30},
            {"from": "B", "to": "E", "distance": 50},
            {"from": "D", "to": "E", "distance": 30}
        ]
    }

### Busca pelo menor caminho nos pontos cadastrados.

Para encontrar o menor caminho é necessário fazer um request GET na url `http://localhost:8080/path?from=A&to=D&autonomy=10&gasValue=2.5`,
sendo que os parâmetros tem os seguintes significados:

* `from`: Nome do ponto inicial do caminho à ser buscado.
* `to`: Nome do ponto de destino.
* `autonomy`: Autonomia do veículo que fará a rota, dado em km/h
* `gasValue`: Valor do litro da gasolina

Exemplo de resposta do servidor:

    {
      "path": [
        "A",
        "B",
        "D"
      ],
      "value": 6.25
    }

# Ferramentas utilizadas

Para este projeto, foi utilizado o [http://start.spring.io/](http://start.spring.io/) para gerar o boilerplate
necessário para este de projeto.

Além disto, foram utilizados outros projetos do spring, como SpringBoot, SpringRest e SpringDataNeo4J.

Para modelagem do problema, dado sua natureza relacionada à grafos, foi utilizado o Neo4J para mapeamento dos dados,
já que Neo4J é uma base especifica para se trabalhar com grafos, e o SpringDataNeo4J torna o trabalho de mapeamento
bem simples.
