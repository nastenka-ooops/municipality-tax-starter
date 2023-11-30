package com.testapp.municipalitytax.service;

import com.testapp.municipalitytax.domain.MunicipalityTax;
import com.testapp.municipalitytax.domain.Schedule;
import com.testapp.municipalitytax.domain.TaxesRepository;
import com.testapp.municipalitytax.web.TaxesService;
import com.testapp.municipalitytax.web.payload.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class MunicipalityTaxService implements TaxesService {

  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
  private final TaxesRepository taxesRepository;
  private final ConversionService conversionService;

  public MunicipalityTaxService(
          TaxesRepository taxesRepository, ConversionService conversionService) {
    this.taxesRepository = taxesRepository;
    this.conversionService = conversionService;
  }

  @Override
  public UUIDResponse addTax(AddTaxRequest addTaxRequest) {
    MunicipalityTax tax = conversionService.convert(addTaxRequest, MunicipalityTax.class);
    MunicipalityTax saved = taxesRepository.save(tax);
    return new UUIDResponse(saved.id());
  }

  @Override
  public void updateTax(UUID taxId, UpdateTaxRequest updateTaxRequest) {
    MunicipalityTax tax = conversionService.convert(updateTaxRequest, MunicipalityTax.class);
    tax = new MunicipalityTax(taxId, null, tax.tax(), tax.startDate(), tax.taxSchedule());
    taxesRepository.update(tax);
  }

  @Override
  public TaxResponse findTax(String municipality, String date) {
    LocalDate parsedDate = LocalDate.parse(date, DATE_TIME_FORMATTER);
    List<MunicipalityTax> taxes = taxesRepository.findByMunicipalityAndDate(municipality, parsedDate);
    return new TaxResponse(taxes.stream().map(MunicipalityTax::tax).toList());
  }

  @Override
  public TaxListResponse getAllMunicipalityTaxes() {
    List<MunicipalityTax> taxes = taxesRepository.getAllMunicipalityTaxes();
    List<FullTaxInfo> fullTaxInfos = taxes.stream()
            .map(t -> conversionService.convert(t, FullTaxInfo.class)).toList();
    return new TaxListResponse(taxes.size(), fullTaxInfos);
  }
}