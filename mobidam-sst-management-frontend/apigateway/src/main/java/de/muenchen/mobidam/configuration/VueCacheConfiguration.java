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

import java.time.Duration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * VueCacheConfigurer setzt Cache Header für die von Vue gebauten Resourcen. Sie enthalten im Namen
 * ein Hash, deswegen sollte das Caching hier keine Probleme machen.
 */
@Configuration
public class VueCacheConfiguration implements WebFluxConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(30)));
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(30)));
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts/")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(30)));
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(30)));
    }
}
