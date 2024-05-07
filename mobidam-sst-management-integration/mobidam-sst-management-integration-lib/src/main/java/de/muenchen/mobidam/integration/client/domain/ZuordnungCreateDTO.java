/*
 * The MIT License
 * Copyright © 2023 Landeshauptstadt München | it@M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/*
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package de.muenchen.mobidam.integration.client.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * ZuordnungCreateDTO
 */
@JsonPropertyOrder({
  ZuordnungCreateDTO.JSON_PROPERTY_SCHNITTSTELLE,
  ZuordnungCreateDTO.JSON_PROPERTY_BENUTZERKENNUNG,
  ZuordnungCreateDTO.JSON_PROPERTY_FACHBEREICH,
  ZuordnungCreateDTO.JSON_PROPERTY_FUNKTIONSADRESSE,
  ZuordnungCreateDTO.JSON_PROPERTY_GUELTIG_AB,
  ZuordnungCreateDTO.JSON_PROPERTY_GUELTIG_BIS
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-03-22T16:23:15.259024800+01:00[Europe/Berlin]")
public class ZuordnungCreateDTO {
  public static final String JSON_PROPERTY_SCHNITTSTELLE = "schnittstelle";
  private UUID schnittstelle;

  public static final String JSON_PROPERTY_BENUTZERKENNUNG = "benutzerkennung";
  private String benutzerkennung;

  public static final String JSON_PROPERTY_FACHBEREICH = "fachbereich";
  private String fachbereich;

  public static final String JSON_PROPERTY_FUNKTIONSADRESSE = "funktionsadresse";
  private String funktionsadresse;

  public static final String JSON_PROPERTY_GUELTIG_AB = "gueltigAb";
  private LocalDate gueltigAb;

  public static final String JSON_PROPERTY_GUELTIG_BIS = "gueltigBis";
  private LocalDate gueltigBis;

  public ZuordnungCreateDTO() {
  }

  public ZuordnungCreateDTO schnittstelle(UUID schnittstelle) {
    
    this.schnittstelle = schnittstelle;
    return this;
  }

   /**
   * Get schnittstelle
   * @return schnittstelle
  **/
  @javax.annotation.Nonnull
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "")
  @JsonProperty(JSON_PROPERTY_SCHNITTSTELLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public UUID getSchnittstelle() {
    return schnittstelle;
  }


  @JsonProperty(JSON_PROPERTY_SCHNITTSTELLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSchnittstelle(UUID schnittstelle) {
    this.schnittstelle = schnittstelle;
  }


  public ZuordnungCreateDTO benutzerkennung(String benutzerkennung) {
    
    this.benutzerkennung = benutzerkennung;
    return this;
  }

   /**
   * Get benutzerkennung
   * @return benutzerkennung
  **/
  @javax.annotation.Nonnull
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "")
  @JsonProperty(JSON_PROPERTY_BENUTZERKENNUNG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getBenutzerkennung() {
    return benutzerkennung;
  }


  @JsonProperty(JSON_PROPERTY_BENUTZERKENNUNG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setBenutzerkennung(String benutzerkennung) {
    this.benutzerkennung = benutzerkennung;
  }


  public ZuordnungCreateDTO fachbereich(String fachbereich) {
    
    this.fachbereich = fachbereich;
    return this;
  }

   /**
   * Get fachbereich
   * @return fachbereich
  **/
  @javax.annotation.Nonnull
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "")
  @JsonProperty(JSON_PROPERTY_FACHBEREICH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getFachbereich() {
    return fachbereich;
  }


  @JsonProperty(JSON_PROPERTY_FACHBEREICH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setFachbereich(String fachbereich) {
    this.fachbereich = fachbereich;
  }


  public ZuordnungCreateDTO funktionsadresse(String funktionsadresse) {
    
    this.funktionsadresse = funktionsadresse;
    return this;
  }

   /**
   * Get funktionsadresse
   * @return funktionsadresse
  **/
  @javax.annotation.Nonnull
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "")
  @JsonProperty(JSON_PROPERTY_FUNKTIONSADRESSE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getFunktionsadresse() {
    return funktionsadresse;
  }


  @JsonProperty(JSON_PROPERTY_FUNKTIONSADRESSE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setFunktionsadresse(String funktionsadresse) {
    this.funktionsadresse = funktionsadresse;
  }


  public ZuordnungCreateDTO gueltigAb(LocalDate gueltigAb) {
    
    this.gueltigAb = gueltigAb;
    return this;
  }

   /**
   * Get gueltigAb
   * @return gueltigAb
  **/
  @javax.annotation.Nonnull
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "")
  @JsonProperty(JSON_PROPERTY_GUELTIG_AB)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public LocalDate getGueltigAb() {
    return gueltigAb;
  }


  @JsonProperty(JSON_PROPERTY_GUELTIG_AB)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setGueltigAb(LocalDate gueltigAb) {
    this.gueltigAb = gueltigAb;
  }


  public ZuordnungCreateDTO gueltigBis(LocalDate gueltigBis) {
    
    this.gueltigBis = gueltigBis;
    return this;
  }

   /**
   * Get gueltigBis
   * @return gueltigBis
  **/
  @javax.annotation.Nonnull
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "")
  @JsonProperty(JSON_PROPERTY_GUELTIG_BIS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public LocalDate getGueltigBis() {
    return gueltigBis;
  }


  @JsonProperty(JSON_PROPERTY_GUELTIG_BIS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setGueltigBis(LocalDate gueltigBis) {
    this.gueltigBis = gueltigBis;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ZuordnungCreateDTO zuordnungCreateDTO = (ZuordnungCreateDTO) o;
    return Objects.equals(this.schnittstelle, zuordnungCreateDTO.schnittstelle) &&
        Objects.equals(this.benutzerkennung, zuordnungCreateDTO.benutzerkennung) &&
        Objects.equals(this.fachbereich, zuordnungCreateDTO.fachbereich) &&
        Objects.equals(this.funktionsadresse, zuordnungCreateDTO.funktionsadresse) &&
        Objects.equals(this.gueltigAb, zuordnungCreateDTO.gueltigAb) &&
        Objects.equals(this.gueltigBis, zuordnungCreateDTO.gueltigBis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(schnittstelle, benutzerkennung, fachbereich, funktionsadresse, gueltigAb, gueltigBis);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ZuordnungCreateDTO {\n");
    sb.append("    schnittstelle: ").append(toIndentedString(schnittstelle)).append("\n");
    sb.append("    benutzerkennung: ").append(toIndentedString(benutzerkennung)).append("\n");
    sb.append("    fachbereich: ").append(toIndentedString(fachbereich)).append("\n");
    sb.append("    funktionsadresse: ").append(toIndentedString(funktionsadresse)).append("\n");
    sb.append("    gueltigAb: ").append(toIndentedString(gueltigAb)).append("\n");
    sb.append("    gueltigBis: ").append(toIndentedString(gueltigBis)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

