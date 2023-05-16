package api.colaboradores.utils;

import org.springframework.stereotype.Service;

@Service
public class MoneyUtils {
  public Integer stringToInt(String valueString) {
    String[] values = valueString.split(",");
   
    Integer fullPart = Integer.parseInt(values[0]) * 100;
    Integer decimalPart = Integer.parseInt(values[1]);

    return (fullPart + decimalPart);
  }
}
