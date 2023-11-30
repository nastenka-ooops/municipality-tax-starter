package com.testapp.municipalitytax.entity;

import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "taxes")
public class TaxEntity {

  @Id
  @Column
  private UUID id;
  @Column
  private String municipality;
  @Column
  private Double tax;
  @Column
  private LocalDate startDate;
  @Column
  private LocalDate endDate;

  public TaxEntity() {
  }

  public TaxEntity(
          UUID id, String municipality, Double tax, LocalDate startDate, LocalDate endDate) {
    this.id = id;
    this.municipality = municipality;
    this.tax = tax;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public TaxEntity(TaxEntity taxEntity, String municipality) {}

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getMunicipality() {
    return municipality;
  }

  public void setMunicipality(String municipality) {
    this.municipality = municipality;
  }

  public Double getTax() {
    return tax;
  }

  public void setTax(Double tax) {
    this.tax = tax;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
}