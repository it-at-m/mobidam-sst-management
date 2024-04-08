/*
 * The MIT License
 *  Copyright © 2023 Landeshauptstadt München | it@M
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
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

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * DatentransferCreateDTO
 */
@JsonPropertyOrder({
  DatentransferCreateDTO.JSON_PROPERTY_PROZESS_ID,
  DatentransferCreateDTO.JSON_PROPERTY_ZEITSTEMPEL,
  DatentransferCreateDTO.JSON_PROPERTY_EREIGNIS,
  DatentransferCreateDTO.JSON_PROPERTY_INFO,
  DatentransferCreateDTO.JSON_PROPERTY_SCHNITTSTELLE
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-03-22T16:23:15.259024800+01:00[Europe/Berlin]")
public class DatentransferCreateDTO {
  public static final String JSON_PROPERTY_PROZESS_ID = "prozessId";
  private String prozessId;

  public static final String JSON_PROPERTY_ZEITSTEMPEL = "zeitstempel";
  private LocalDateTime zeitstempel;

  public static final String JSON_PROPERTY_EREIGNIS = "ereignis";
  private String ereignis;

  public static final String JSON_PROPERTY_INFO = "info";
  private String info;

  public static final String JSON_PROPERTY_SCHNITTSTELLE = "schnittstelle";
  private UUID schnittstelle;

  public DatentransferCreateDTO() {
  }

  public DatentransferCreateDTO prozessId(String prozessId) {
    
    this.prozessId = prozessId;
    return this;
  }

   /**
   * Get prozessId
   * @return prozessId
  **/
  @javax.annotation.Nonnull
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "")
  @JsonProperty(JSON_PROPERTY_PROZESS_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getProzessId() {
    return prozessId;
  }


  @JsonProperty(JSON_PROPERTY_PROZESS_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setProzessId(String prozessId) {
    this.prozessId = prozessId;
  }


  public DatentransferCreateDTO zeitstempel(LocalDateTime zeitstempel) {
    
    this.zeitstempel = zeitstempel;
    return this;
  }

   /**
   * Get zeitstempel
   * @return zeitstempel
  **/
  @javax.annotation.Nonnull
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "")
  @JsonProperty(JSON_PROPERTY_ZEITSTEMPEL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public LocalDateTime getZeitstempel() {
    return zeitstempel;
  }


  @JsonProperty(JSON_PROPERTY_ZEITSTEMPEL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setZeitstempel(LocalDateTime zeitstempel) {
    this.zeitstempel = zeitstempel;
  }


  public DatentransferCreateDTO ereignis(String ereignis) {
    
    this.ereignis = ereignis;
    return this;
  }

   /**
   * Get ereignis
   * @return ereignis
  **/
  @javax.annotation.Nonnull
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "")
  @JsonProperty(JSON_PROPERTY_EREIGNIS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getEreignis() {
    return ereignis;
  }


  @JsonProperty(JSON_PROPERTY_EREIGNIS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEreignis(String ereignis) {
    this.ereignis = ereignis;
  }


  public DatentransferCreateDTO info(String info) {
    
    this.info = info;
    return this;
  }

   /**
   * Get info
   * @return info
  **/
  @javax.annotation.Nullable
  @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "")
  @JsonProperty(JSON_PROPERTY_INFO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getInfo() {
    return info;
  }


  @JsonProperty(JSON_PROPERTY_INFO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setInfo(String info) {
    this.info = info;
  }


  public DatentransferCreateDTO schnittstelle(UUID schnittstelle) {
    
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatentransferCreateDTO datentransferCreateDTO = (DatentransferCreateDTO) o;
    return Objects.equals(this.prozessId, datentransferCreateDTO.prozessId) &&
        Objects.equals(this.zeitstempel, datentransferCreateDTO.zeitstempel) &&
        Objects.equals(this.ereignis, datentransferCreateDTO.ereignis) &&
        Objects.equals(this.info, datentransferCreateDTO.info) &&
        Objects.equals(this.schnittstelle, datentransferCreateDTO.schnittstelle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prozessId, zeitstempel, ereignis, info, schnittstelle);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatentransferCreateDTO {\n");
    sb.append("    prozessId: ").append(toIndentedString(prozessId)).append("\n");
    sb.append("    zeitstempel: ").append(toIndentedString(zeitstempel)).append("\n");
    sb.append("    ereignis: ").append(toIndentedString(ereignis)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
    sb.append("    schnittstelle: ").append(toIndentedString(schnittstelle)).append("\n");
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
