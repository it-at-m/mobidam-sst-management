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

package de.muenchen.mobidam.integration.service;

import de.muenchen.mobidam.domain.DatentransferCreateDTO;
import de.muenchen.mobidam.integration.domain.SchnittstellenStatus;
import de.muenchen.mobidam.rest.DatentransferControllerApi;
import de.muenchen.mobidam.rest.SchnittstelleControllerApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Slf4j
public class SstManagementIntegrationService {

    private final DatentransferControllerApi datentransferControllerApi;
    private final SchnittstelleControllerApi schnittstelleControllerApi;

    public void logDatentransfer(final DatentransferCreateDTO dto) {
        log.debug("Called logDatentransfer for: {}", dto.getSchnittstelle());
        Mono<Object> res = datentransferControllerApi.createDatentransfer(dto);
        res.subscribe(datentransfer -> log.info("Datentransfer created: {}", datentransfer));
    }

    public boolean isActivated(final String sstId) throws Exception {
        log.debug("Called isActivated for: {}", sstId);
        Object result = schnittstelleControllerApi.getStatus(sstId).block();
        if (result instanceof LinkedHashMap<?, ?> map) {
            String status = (String) map.get("status");
            log.debug("Status ({}): {}", sstId, status);
            return SchnittstellenStatus.AKTIVIERT.name().equals(status);
        } else {
            throw new Exception("Unexpected result class: " + result.getClass().getName());
        }
    }

}
