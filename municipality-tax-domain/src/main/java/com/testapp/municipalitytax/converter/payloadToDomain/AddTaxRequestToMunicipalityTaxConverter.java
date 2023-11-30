package com.testapp.municipalitytax.converter.payloadToDomain;

import com.testapp.municipalitytax.domain.MunicipalityTax;
import com.testapp.municipalitytax.domain.Schedule;
import com.testapp.municipalitytax.web.payload.AddTaxRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class AddTaxRequestToMunicipalityTaxConverter
    implements Converter<AddTaxRequest, MunicipalityTax> {
  @Override
  public MunicipalityTax convert(AddTaxRequest source) {
    LocalDate startDate = LocalDate.parse(source.startDate(), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    Schedule schedule = Schedule.valueOf(source.schedule());
    return new MunicipalityTax(null, source.municipality(), source.tax(), startDate, schedule);
  }
}
