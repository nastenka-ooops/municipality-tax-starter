package com.testapp.municipalitytax.repository;

import static com.testapp.municipalitytax.entity.QTaxEntity.taxEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.testapp.municipalitytax.domain.MunicipalityTax;
import com.testapp.municipalitytax.domain.TaxesRepository;
import com.testapp.municipalitytax.entity.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityTaxesRepository implements TaxesRepository {

  private final TaxesJpaRepository taxesJpaRepository;
  private final ConversionService conversionService;

  public MunicipalityTaxesRepository(
      TaxesJpaRepository taxesJpaRepository, ConversionService conversionService) {
    this.taxesJpaRepository = taxesJpaRepository;
    this.conversionService = conversionService;
  }

  @Override
  public MunicipalityTax save(MunicipalityTax municipalityTax) {
    TaxEntity taxEntity = conversionService.convert(municipalityTax, TaxEntity.class);
    if (taxEntity == null) {
      throw new NullPointerException();
    }
    TaxEntity tax = taxesJpaRepository.save(taxEntity);
    return conversionService.convert(tax, MunicipalityTax.class);
  }

  public int update(MunicipalityTax municipalityTax) {
    TaxEntity tax = conversionService.convert(municipalityTax, TaxEntity.class);
    Optional<TaxEntity> existingTaxOptional = taxesJpaRepository.findById(tax.getId());
    if (existingTaxOptional.isEmpty()) return 0;
    TaxEntity existingTax = existingTaxOptional.get();
    existingTax.setMunicipality(tax.getMunicipality());
    existingTax.setStartDate(tax.getStartDate());
    existingTax.setEndDate(tax.getEndDate());
    existingTax.setTax(tax.getTax());
    taxesJpaRepository.save(existingTax);
    return 1;
  }

  @Override
  public List<MunicipalityTax> findByMunicipalityAndDate(String municipality, LocalDate date) {
    BooleanExpression predicate =
        taxEntity
            .municipality
            .eq(municipality)
            .and(taxEntity.startDate.loe(date))
            .and(taxEntity.endDate.goe(date));
    Iterable<TaxEntity> filteredTaxes = taxesJpaRepository.findAll(predicate);
    return StreamSupport.stream(filteredTaxes.spliterator(), false)
        .map(t -> conversionService.convert(t, MunicipalityTax.class))
        .toList();
  }

  @Override
  public List<MunicipalityTax> getAllMunicipalityTaxes() {
    Stream<TaxEntity> stream =
        StreamSupport.stream(taxesJpaRepository.findAll().spliterator(), false);
    return stream.map(t -> conversionService.convert(t, MunicipalityTax.class)).toList();
  }
}
