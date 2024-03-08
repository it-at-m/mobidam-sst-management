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
package de.muenchen.mobidam.domain.mappers;

import de.muenchen.mobidam.domain.Schnittstelle;
import de.muenchen.mobidam.domain.dtos.SchnittstelleCreateDTO;
import de.muenchen.mobidam.domain.dtos.SchnittstelleDTO;
import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SchnittstelleMapper {

    SchnittstelleDTO toDTO(Schnittstelle schnittstelle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "zuordnungen", ignore = true)
    @Mapping(target = "datentransfers", ignore = true)
    @Mapping(target = "erstellungsdatum", source = "erstellungsdatum")
    @Mapping(target = "editDate", ignore = true)
    Schnittstelle toEntity(SchnittstelleCreateDTO schnittstelleCreateDTO, LocalDate erstellungsdatum);

    @Mapping(target = "zuordnungen", ignore = true)
    @Mapping(target = "datentransfers", ignore = true)
    Schnittstelle toEntityWithId(SchnittstelleDTO schnittstelleDTO);

}
