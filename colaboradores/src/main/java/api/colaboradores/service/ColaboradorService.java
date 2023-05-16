package api.colaboradores.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import api.colaboradores.dto.ColaboradorDTO;
import api.colaboradores.model.Colaborador;
import api.colaboradores.repository.ColaboradorRepository;
import api.colaboradores.utils.DateUtils;
import api.colaboradores.utils.MoneyUtils;

@Service
public class ColaboradorService {
  @Autowired private DateUtils dateUtil;
  @Autowired private MoneyUtils moneyUtil;

  @Autowired
  private ColaboradorRepository repository;

  public void create(ColaboradorDTO dto) {
    Date convertedDateAdmissao = dateUtil.stringToDate(dto.admissao());
    Integer convertedValueRemuneracao = moneyUtil.stringToInt(dto.remuneracao());

    Colaborador colaborador = new Colaborador(dto, convertedDateAdmissao, convertedValueRemuneracao);

    repository.save(colaborador);
  }

  public List<Colaborador> readByPage(Pageable pageable) {
    return repository.findAllBy(pageable).getContent();
  }

  public void update(long id, ColaboradorDTO dto) {
    repository.findById(id).map(colaborador -> {
      colaborador.setNome(dto.nome());
      //colaborador.setAdmissao(dto.admissao());
      colaborador.setFuncao(dto.funcao());
      //colaborador.setRemuneracao(dto.remuneracao());
      return repository.save(colaborador);
    });
  }
}
