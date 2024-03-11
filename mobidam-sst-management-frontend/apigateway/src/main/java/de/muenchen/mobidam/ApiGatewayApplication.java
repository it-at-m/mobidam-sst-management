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
package de.muenchen.mobidam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * To do some base configuration for the non blocking client-server framework
 * named Netty via properties use the properties listed in the link down below:
 *
 * @see <a href=
 *      "https://projectreactor.io/docs/netty/release/api/constant-values.html">https://projectreactor.io/docs/netty/release/api/constant-values.html</a>
 *
 *      As listed below, this above mentioned properties should be set before the application
 *      startup:
 *
 *      <ul>
 *      <li>As command line argument: e.g. -Dreactor.netty.pool.maxConnections=1000.
 *      <li>As environmental property in Openshift: e.g. with key REACTOR_NETTY_POOL_MAXCONNECTIONS
 *      and value 1000.
 *      <li>As programatically set property before call {@link SpringApplication#run} in
 *      {@link ApiGatewayApplication#main}: e.g.
 *      <code>System.setProperty("reactor.netty.pool.maxConnections", "1000");</code>.
 *      </ul>
 *
 *      To get more information about Spring Cloud Gateway visit the following link:
 *
 * @see <a href=
 *      "https://cloud.spring.io/spring-cloud-gateway/reference/html/">https://cloud.spring.io/spring-cloud-gateway/reference/html/</a>
 */
@SpringBootApplication(scanBasePackages = { "de.muenchen.mobidam" })
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
