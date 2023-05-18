# Colaboradores API Spring
- Autor: Guilherme Icaro F Real [@GitHub](https://www.github.com/guilhermeicarofr) [@LinkedIn](https://www.linkedin.com/in/guilhermeicarofr/)
- Tecnologias utilizadas: Java, Spring Boot Web, Orientação a Objetos, PostgreSQL, Docker, Docker-Compose, Lombok, Validation I/O
- Objetivos: Aplicação capaz de criar, listar, editar e apagar Colaboradores de uma organização com suas respectivas relações de hierarquia. É possível também listar por ano de admissão e encontrar o colaborador responsável hierarquicamente por dois funcionários
- O código no repositório possui uma branch VersaoComentada, onde é possível acompanhar passo a passo as lógicas da aplicação através dos comentários

#

## Como executar:
- Primeiro passo - clonar este mesmo repositório para sua máquina:
````
git clone "https://gitlab.com/guilhermeicarofr/avaliacao-conhecimento-desenvolvedor-guilherme-icaro"

ou

git clone "https://github.com/guilhermeicarofr/RegistroColaboradores-JavaSpring"

````
- Daqui existem duas opções para executar o projeto:

### Opção1 - Rodar em um container Docker (recomendado):
- Desta forma não será necessária nenhuma configuração ou instalação de nenhuma versão de Java ou Postgres e pode ser executado de forma mais simples
- Com docker e docker-compose instalado na máquina (veja: https://docs.docker.com/compose/install/), acessar a pasta do projeto e executar o comando a seguir. Pode ser necessário autorização de superusuário.
```
sudo docker-compose up
```
- A aplicação estará disponível no endpoint http://localhost:8080/

### Opção2 - Rodar localmente (sem Docker):
- Necessária instalação do banco PosgreSQL
- Postgres user=postgres password=password
- Porta padrão do Postgres localhost:5432

#### Configurando o banco:
- Criar o DATABASE "colaboradores" no banco Postgres através do CLI psql ou algum SGBD(Ex: DBeaver)
- Executar o script SQL contido na pasta /db no arquivo:
```
database_scheme.sql
```
- Dessa forma temos o banco Postgres "colaboradores" configurado com suas tabelas

#### Executando a aplicação:
- O projeto compilado .jar está disponível na pasta /colaboradores/jar para execução
- O arquivo para execução local é:
```
colaboradores-0.0.1-SNAPSHOT-LOCAL.jar
```
- Também é possível executar diretamente através do editor VSCode acessando o arquivo ColaboradoresApplication.java e clicando em Run:
```
public class ColaboradoresApplication {
  [run]
  void main() {}
}
```
- A aplicação estará disponível no endpoint http://localhost:8080/

#

## Banco de dados:
- O banco de dados de colaboradores é modelado da seguinte forma:
```
colaborador: {
  id: Long
  cpf: String unique
  nome: String
  admissao: String
  funcao: String
  remuneracao: String
}

subordinacao: {
  id: Long
  gerente: Long referencia colaborador.id
  subordinado: Long unique referencia colaborador.id
}
```
- A entidade colaborador representa os dados do colaborador, sendo que cpf é único
- A entidade subordinacao representa uma relacao de hierarquia entre dois colaboradores
- Um colaborador só pode possuir um gerente, portanto o id subordinado no banco é único, só fazendo uma relação
- Um colaborador só pode não possuir gerente se tiver a funcao "presidente", portanto não vai existir relacao de subordinacao com seu id como subordinado
- Um colaborador pode possuir vários ou nenhum subordinado, portanto o id gerente em subordinacao não é único
- Caso um colaborador não tenha nenhum subordinado, não existirá nenhuma subordinacao com seu id no campo gerente

#

## Dados na aplicação:
- Os seguintes formatos de dados são usados e retornados na aplicação:
```
Colaborador: {
  id: Long
  cpf: String
  nome: String
  admissao: String
  funcao: String
  remuneracao: String
}

ColaboradorComHierarquias: {
  colaborador: Colaborador
  gerente: Colaborador ou null
  subordinados: Colaborador[]
}
```
- O seguinte formato de dados é utilizado e VALIDADO como ColaboradorDTO:
```
ColaboradorInput: {
  cpf: String (Numérica formato 123.456.789-10)
  nome: String (Não vazia)
  admissao: String (Númérica formato dd/mm/aaaa)
  funcao: String (Não vazia apenas letras minúsculas)
  remuneracao: String (Numérica formato 9999,99)
  gerente: Long(id) ou null (Null em caso "presidente")
  subordinados: Long(id)[] (Lista de ids)
}
```
- O input de Colaborador é o formato validado para as requisições de POST(Criação) e PUT(Edição) de um Colaborador

#

## Como usar:
- Fazer as requisições HTTP a seguir para o endpoint da aplicação como descrito acima
- A forma como a aplicação lida com as incosistências e reorganização de hierarquias será esclarecida a frente
- Nas rotas que possuem paginação, o tamanho da página foi configurada para 5 itens para facilitar a visualização da paginação

### - GET /health
- Rota de teste para verificar se a aplicação está rodando

## Rotas de Colaborador:
### - POST /colaborador
- Cria um novo colaborador e reorganiza as relações de subordinação envolvidas
- Input body json validado no formato ColaboradorInput descrito acima
- Retorna status 201 e o dado Colaborador em caso de sucesso
- Em caso de inputs inválidos retorna status 400 e lista de erros
- Em caso de cpf já em uso retorna status 409 e mensagem de erro
- Em caso de algum id não existente retorna status 404 e mensagem de erro com id
- Em caso de alguma incosistencia de hierarquia retorna status 409 e mensagem com o erro

### - GET /colaborador?page={número da pagina}
- Retorna uma lista paginada de colaboradores
- Primeira página corresponde ao valor 0
- Retorna status 200 e Lista de 5 tipos Colaborador em caso de sucesso
- Em caso de falta de valor de page, retorna status 400 e mensagem de erro

### - GET /colaborador/{id}
- Retorna um colaborador com seus dados completos
- Retorna status 200 e ColaboradorComHierarquias em caso de sucesso
- Em caso de id inexistente retorna status 404 e mensagem de erro

### - PUT /colaborador/{id}
- Edita um colaborador e retorna seus dados completos
- Reorganiza as relações de subordinação envolvidas
- Input body json validado no formato ColaboradorInput descrito acima
- Retorna status 200 e ColaboradorComHierarquias em caso de sucesso
- Em caso de inputs inválidos retorna status 400 e lista de erros
- Em caso de algum id não existente retorna status 404 e mensagem de erro com id
- Em caso de alguma incosistencia de hierarquia retorna status 409 e mensagem com o erro

### - DELETE /colaborador/{id}
- Deleta um colaborador e reorganiza as relações de subordinação envolvidas
- Retorna status 204 em caso de sucesso
- Em caso de id não existente retorna status 404 e mensagem de erro com id

### - GET /colaborador/admissao/{ano}
- Retorna uma lista paginada de colaboradores contratados no ano informado
- Primeira página corresponde ao valor 0
- Ano deve ser uma string numérica de 4 digitos
- Retorna status 200 e Lista de 5 tipos Colaborador em caso de sucesso
- Em caso de falta de valor de page retorna status 400 e mensagem de erro
- Em caso de ano inválido retorna status 400 e mensagem de erro

## Rotas de Hierarquia:
- O formato de input na requisição de hierarquia é validado pelo seguint DTO
```
ResponsavelHierarquiaInput: {
  colaborador1: Long(id)
  colaborador2: Long(id)
}
```

### - GET /hierarquia/responsavel
- Retorna o colaborador reponsavel na hierarquia por outros dois colaboradores informados
- O serviço percorre toda a hierarquia dos colaboradores informados para encontrar qual o gerente comum responsável por ambos
- Essa consulta é feita de forma eficiente com uso de uma HashMap, enquando a hierarquia do colaborador1 é percorrida, os ids são armazenados na HashMap para fácil acesso e comparação instantânea quando a hierarquia do colaborador2 for percorrida
- Input body json validado no formato ResponsavelHierarquiaInput descrito acima
- Retorna status 200 e tipo ColaboradorComHierarquias do colaborador responsável encontrado
- Em caso de id não existente retorna status 404 e mensagem de erro com id
- Em caso de não encontrado o responsável retorna status 409 e mensagem de erro

#

## Estrutura do Código:
- Cada camada do código corresponde a uma pasta do diretório colaboradores/src/main/java/api/colaboradores

### Model:
- Representam as classes responsáveis por modelar as entidades do banco de dados e dados que trafegam na aplicação como Colaborador, Subordinacao, etc

### DTO:
- Representa o modelo de formato de input da aplicação, que validações são feitas nos bodys das requisições para inicializar um model

### Controller:
- São as classes que mapeiam as rotas das requisições recebidas, recebem os inputs dados a aplicação e determinam as respostas enviadas ao cliente para dar prosseguimento a execução do serviço
- O controller ExceptionHandlerController é responsável por capturar as Exceptions lançadas em casos de erros, incosistências de dados informados, etc na aplicação e tratar a informação para responder corretamente

### Service:
- A camada Service é responsável por tratar os dados recebidos e validados, verificar incosistências de regras de negócio e operar a lógica da aplicação
### Repository:
- As classes Repository extendem a JPA Repository e permitem a criação e uso de métodos para acessar o banco de dados fazendo consultas, inserções, etc
### Exceptions:
- As Exceptions são objetos throwable usados em caso de algum erro na aplicação, são capturadas pelo ExceptionHandlerController para tratamento do erro e informação correta na resposta

#

## Dockerização da Aplicação:
- O arquivo docker-compose executa uma construção e conexão de containers da aplicação Spring Boot e do banco de dados Postgres
- O banco Postgres é inicializado e automaticamente o database colaboradores é criado
- Então o container postgres executa o script SQL do arquivo db/docker/01_create_tables.sql criando as tabelas necessárias caso não existam
- Com o banco de dados de pé, a imagem da aplicação Spring é contruída através do arquivo colaboradores/Dockerfile, usando um arquivo da aplicação pre-compilado com maven para executar no container Docker com Java a aplicação e a conexão com o banco ocorre no endpoint db:5432
- Ao parar e executar novamente os containers os dados do banco persistem devido ao uso de volumes, exceto se forem apagados
- Desta forma é possível ter a aplicação e banco de dados rodando sem que seja necessário possuir nenhuma versão de Java ou Postgres instalado na máquina

#

## Questões de hierarquia:
- Como mencionado anteriormente, serão esclarecidos o que são conflitos de hierarquia e o que são reorganizações de hierarquia na aplicação

### Conflitos:
- Uma Exception de conflito de hierarquia é qualquer incosistência que possa existir nos dados informados no input da requisição com relação as relações de subordinação e gerencia
- Possíveis conflitos:
  - Presidente já existe e tenta-se criar outro
  - Presidente ter um gerente
  - Colaborador que não seja o presidente não ter gerente
  - Informar o presidente como um subordinado
  - Informar o mesmo id como gerente e como subordinado
  - Responsável entre dois colaboradores não encontrado

### Reorganizações:
- Uma reorganização de hierarquia é a forma como a aplicação trata uma inserção ou remoção de relação de subordinação entre colaboradores
- Inserção:
  - Quando um colaborador é criado ou editado, os ids passados como gerente e subordinados tem suas relações alteradas
  - Em caso de criação, o gerente informado passa a ser gerente do colaborador criado, e os subordinados informados perdem suas relações com seus antigos gerentes e passam a ter o novo colaborador como gerente, inserindo na hierarquia
- Edição:
  - Em caso de edição, o colaborador editado perde sua relação com o gerente antigo e passa a ter relação com o novo gerente informado.
  - Os subordinados antigos passam a ser subordinados ao antigo gerente, removendo o colaborador editado da hierarquia.
  - Os subordinados novos perdem relação com seus antigos gerentes e criam com o colaborador editado.
- Remoção:
  - Quando um colaborador é deletado do banco, seus subordinados e seu gerente tem relações alteradas
  - Ao remover um colaborador, a sua relação com seu gerente é removida.
  - As relações dos subordinados com o colaborador removido são eliminadas e os subordinados passam a responder ao gerente do colaborador removido, sem quebrar a cadeia hierárquica
- Caso especial - "presidente":
  - O colaborador de função presidente não possui gerentes, e isso altera as reorganizações
  - Em caso de editar o presidente e tentar adicionar gerente não será possível, exceto que se altere também a funcao
  - Em caso de editar o presidente removendo alguns ou todos os seus subordinados, estes não serão alterados pois não existe gerente acima do presidente para subistituí-lo
  - Em caso de remover o presidente, todos os seus subordinados diretos passarão a ser subordinados ao primeiro Colaborador da lista de subordinados. O substituto não se torna o presidente, mas passa a não ter um gerente acima. Para que seja o presidente deve ter sua função alterada na edição

#
