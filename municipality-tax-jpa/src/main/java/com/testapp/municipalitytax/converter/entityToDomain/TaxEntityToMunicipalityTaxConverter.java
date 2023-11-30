package com.testapp.municipalitytax.converter.entityToDomain;

import static java.time.temporal.ChronoUnit.*;

import com.testapp.municipalitytax.domain.MunicipalityTax;
import com.testapp.municipalitytax.domain.Schedule;
import com.testapp.municipalitytax.entity.TaxEntity;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaxEntityToMunicipalityTaxConverter implements Converter<TaxEntity, MunicipalityTax> {
    @Override
    public MunicipalityTax convert(TaxEntity source) {
        Schedule schedule = dateToSchedule(source.getStartDate(), source.getEndDate());
        return new MunicipalityTax(source.getId(),
                source.getMunicipality(),
                source.getTax(),
                source.getStartDate(),
                schedule);
    }

    private Schedule dateToSchedule(LocalDate startDate, LocalDate endDate) {
        if (DAYS.between(startDate, endDate) <= 1) {
            return Schedule.DAILY;
        } else if (WEEKS.between(startDate, endDate) <= 1) {
            return Schedule.WEEKLY;
        } else if (MONTHS.between(startDate, endDate) <= 1) {
            return Schedule.MONTHLY;
        } else {
            return Schedule.YEARLY;
        }
    }
}
