# Teste para Avaliação de Conhecimento

### Desenvolvedor de Software 
-----------------------------------------

Para avaliar as habilidades do candidato à vaga de desenvolvedor de software será necessário que o mesmo implemente um mini projeto para **Gerenciamento de Funcionários** com:
- [x] Serviços que permitam incluir, remover e alterar **COLABORADORES**. Campos: 
    - Número no cadastro de pessoas físicas (com máscara padrão 000.000.000-00);
    - Nome;
    - Data de admissão (com máscara padrão dd/mm/aaaa);
    - Função exercida;
    - Remuneração em reais (com máscara padrão 0,00);
    - Gerente (atenção à relação com **GERENTE**, pois **um** colaborador possui apenas **um** gerente direto. Sendo o presidente o último nível da hierarquia, não havendo níveis superiores a este.); 
    - Subordinados (atenção à relação com **SUBORDINADOS**, pois **um** colaborador pode ter **nenhum ou vários** subordinados.)
- [x] Um serviço que permita pesquisar todos os colaboradores admitidos no ano. Sendo "ano" um parâmetro do serviço.

{+ Observe que um GERENTE e um SUBORDINADO são também COLABORADORES! +}

```
    DESAFIO: O cliente do produto que você está desenvolvendo precisa de um serviço que indique o gerente
    responsável por responder por problemas entre dois colaboradores. Observe que o serviço criado deve 
    respeitar a hierarquia e o responsável deve ser o gerente comum mais próximo a ambos os colaboradores.

```


#### ENTREGA
-----------------------------------------

**O candidato deve criar um fork deste projeto e, ao final, realizar um pull request para o projeto principal.**


Como a principal característica do cargo a ser ocupado é atuar como dev em equipe de desenvolvimento de software, o projeto em questão deverá ser elaborado observando os seguintes critérios:

- [x] Todas as consultas devem conter recursos de **paginação**.
- [x] **Linguagem de programação Java utilizando Spring Boot.**
- [x] Banco de dados **PostgreSQL**.
- [x] Código **orientado a objetos**.
- [x] **Código limpo** (Conforme boas práticas do livro Clean Code, do Robert Martin).
- [x] **Commits pequenos** com descrição do que foi implementado.
- [x] **Sistema escalonável**. Seu projeto deve ser codificado de forma a permitir alterações e adições de novas *features*.


Caso o candidato não consiga entregar todos os requisitos, é importante descrever no README do projeto o que foi entregue, os critérios de priorização e se houveram impedimentos.

Além disso, o candidato deverá **gravar um vídeo explicativo COM ÁUDIO da solução implementada, duração máxima de 15 (QUINZE) minutos**, mostrando de forma INTERCALADA e CORRELACIONANDO:

- [x] **código-fonte**: principais pontos.
- [x] **banco de dados**: principais estruturas.
- [x] **a aplicação em funcionamento**. 
- [x] simulação na prática de um **debug de código** (com inspeção de atributos, break points, step into, step over.)

{+ Não se esqueça de dedicar um tempo do vídeo para explicar sua solução ao desafio. Você pode investir em representações gráficas, se considerar que facilitará sua explicação. +}

Sugerimos o Camtasia® ou o oCAM como softwares para gravação do vídeo com áudio, formato de exportação **.mp4** e com **áudio**. ATENÇÃO! Usar o formato de compressão sugerido ou outro que gere um arquivo em um tamanho e qualidade de vídeo e áudio razoáveis para download, visualização e entendimento da explicação. 	

Ao finalizar os trabalhos, o BANCO DE DADOS e o VÍDEO deverão ser compactados e postados no [WeTransfer](https://www.wetransfer.com/) enviando o link para download para a [Memory](rh@memory.com.br). Caso o projeto seja aceito, o candidato será convidado a apresentá-lo à empresa.


>IMPORTANTE: 
O vídeo com áudio é parte imprescindível da entrega. Caso o candidato envie somente o código-fonte e banco de dados, a prova nem será avaliada. 


> DICA: 
Use esse texto como um checklist! Principalmente os itens destacados como tal, além do desafio. A correção e sua pontuação serão baseadas exatamente na execução dos itens obrigatórios. Atenção também ao **tempo do vídeo, data de entrega** e aos **detalhes do enunciado**, assim como um desenvolvedor tem que ter com as entregas de um projeto e detalhes de especificação. 



**Sucesso no teste!** :smile:
