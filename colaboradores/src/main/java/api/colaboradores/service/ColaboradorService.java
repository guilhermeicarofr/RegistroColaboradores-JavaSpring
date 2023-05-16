package api.colaboradores.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.colaboradores.dto.ColaboradorDTO;
import api.colaboradores.model.Colaborador;
import api.colaboradores.repository.ColaboradorRepository;
import api.colaboradores.utils.DateUtils;
import api.colaboradores.utils.MoneyUtils;

@Service
public class ColaboradorService {
  @Autowired
  private DateUtils dateUtil;
  private MoneyUtils moneyUtil;

  @Autowired
  private ColaboradorRepository repository;

  public void create(ColaboradorDTO dto) {
    Date convertedDateAdmissao = dateUtil.stringToDate(dto.admissao());
    Integer convertedValueRemuneracao = moneyUtil.stringToInt(dto.remuneracao());

    Colaborador colaborador = new Colaborador(dto, convertedDateAdmissao, convertedValueRemuneracao);

    repository.save(colaborador);
  }
}
