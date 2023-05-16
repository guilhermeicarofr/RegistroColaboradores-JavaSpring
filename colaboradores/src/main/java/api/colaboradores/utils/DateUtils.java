package api.colaboradores.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateUtils {
  public Date stringToDate(String dateString) {
    String[] dateList = dateString.split("/");
    String formattedDateString = dateList[1] + "/" + dateList[0]+ "/" + dateList[2];

    SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
    try {
      return format.parse(formattedDateString);      
    } catch (ParseException e) {
      throw new Error("Data inválida");
    }
  }
}
