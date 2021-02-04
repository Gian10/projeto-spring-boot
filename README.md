# Pandemic Combat Aid System

# Descrição do problema
Quando o mundo é atingido por uma pandemia sem remédio imediato, além das habilidades dos profissionais de saúde, é preciso ter um bom sistema de informações para ajudar nas tomadas de decisões, visando amenizar ao máximo seus impactos.
Assim, ainda que não seja da área de saúde, você pode ajudar no combate. Para isso,  foi designado para desenvolver um sistema que irá coletar informações de todo país, organizá-las e prover informações com base nelas.

# Requisitos
Desenvolva uma API RESTFul (a ideia é que facilmente outros sistemas consigam se integrar para prover e obter dados), ao qual irá armazenar informação sobre os hospitais, seus recursos (pessoais e materiais), pacientes em atendimento (versões futuras), etc, ajudando no intercâmbio de recursos.

* Adicionar hospitais
Um hospotal deve ter um nome, endereço, cnpj, localização (latitude, longitude, etc.).
Ao adicionar o hospital, junto deve ser adicionado seus recursos atuais bem como seu percentual de ocupação.

* Atualizar percentual de ocupação de um hospital
Um hospital deve poder reportar seu percentual de ocupação a todo instante, de forma que isso possa ser usado no processo de intercâmbio de recursos.

* Hospitais não podem Adicionar/Remover recursos
Os recursos dos hospitais só podem ser alterados via intercâmbio. Aquisição de recursos avulso será feita em outra API, pois requer um processo específico.

* Intercâmbio de recursos
Os hospitais poderão trocar recursos entre eles.

# Relatórios
A API deve oferecer os seguintes relatórios:

* Porcentagem de hospitais com ocupação maior que 90%.
* Porcentagem de hospitais com ocupação menor que 90%.
* Quantidade média de cada tipo de recurso por hospital (Ex: 2 tomógrafos por hospital).
* Hospital em super-lotação (ocupação maior que 90%) a mais tempo.
* Hospital em abaixo de super-lotação (ocupação maior que 90%) a mais tempo.
* Histórico de negociação.

# Notas

* Deverá ser utilizado Java, Spring boot, Spring Data, Hibernate (pode ser usado o banco de dados H2) e como gerenciador de dependência Maven ou Gradle.
* Documentar a API.
