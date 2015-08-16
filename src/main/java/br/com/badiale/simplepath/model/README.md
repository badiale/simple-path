# Entidades de modelo

As entidades neste pacote representam o modelo de negócio da aplicação.

Pela natureza das entidades, elas são representadas como grafos, e mapeadas como tal, utilizando Neo4J.

Porém, para que esta arquitetura funcione corretamente, o `hashCode` e `equals` das entidades deve ser
implementado da forma mais simples possível, istó é, utilizando apenas o `@GraphId` da entidade, para evitar
`StackOverflow`s. Isso implica que as entidades precisam sempre ser persistidas antes de serem adicionadas em
coleções que dependam de `hashCode`/`equals`.