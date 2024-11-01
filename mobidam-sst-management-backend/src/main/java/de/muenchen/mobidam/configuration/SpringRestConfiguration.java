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
package de.muenchen.mobidam.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy.RepositoryDetectionStrategies;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class SpringRestConfiguration implements RepositoryRestConfigurer {

    /**
     * The method returns a {@link Validator} to get correct validation error messages.
     *
     * @return A {@link LocalValidatorFactoryBean} to get correct validation error messages.
     */
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * See {@link RepositoryRestConfigurer#configureRepositoryRestConfiguration}
     */
    @Override
    public void configureRepositoryRestConfiguration(final RepositoryRestConfiguration config, final CorsRegistry corsRegistry) {
        config.setRepositoryDetectionStrategy(RepositoryDetectionStrategies.ANNOTATED);
    }

    /**
     * See {@link RepositoryRestConfigurer#configureValidatingRepositoryEventListener}
     */
    @Override
    public void configureValidatingRepositoryEventListener(final ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("afterCreate", validator());
        validatingListener.addValidator("beforeCreate", validator());
        validatingListener.addValidator("afterSave", validator());
        validatingListener.addValidator("beforeSave", validator());
        validatingListener.addValidator("beforeLinkSave", validator());
        validatingListener.addValidator("afterLinkSave", validator());
    }

}
