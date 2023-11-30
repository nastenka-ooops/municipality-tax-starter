package com.testapp.municipalitytax.converter.domainToEntity;

import com.testapp.municipalitytax.domain.MunicipalityTax;
import com.testapp.municipalitytax.domain.Schedule;
import com.testapp.municipalitytax.entity.TaxEntity;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityTaxToTaxEntityConverter implements Converter<MunicipalityTax, TaxEntity> {

  @Override
  public TaxEntity convert(MunicipalityTax source) {
    LocalDate endDate = scheduleToDate(source.startDate(), source.taxSchedule());
    return new TaxEntity(UUID.randomUUID(), source.municipality(), source.tax(), source.startDate(), endDate);
  }

  private LocalDate scheduleToDate(LocalDate startDate, Schedule schedule) {
    return switch (schedule) {
      case YEARLY -> startDate.plusYears(1).minusDays(1);
      case MONTHLY -> startDate.plusMonths(1).minusDays(1);
      case WEEKLY -> startDate.plusWeeks(1).minusDays(1);
      case DAILY -> startDate;
    };
  }
}
