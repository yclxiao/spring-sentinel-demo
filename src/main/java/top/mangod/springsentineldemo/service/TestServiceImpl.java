/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.mangod.springsentineldemo.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * @author Eric Zhao
 */
@Service
public class TestServiceImpl implements top.mangod.springsentineldemo.service.TestService {

    @Override
    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {top.mangod.springsentineldemo.service.ExceptionUtil.class})
    public void test() {
        // 默认情况下，超出配置的流控阈值后，直接抛出 FlowException（BlockException） 异常，使用blockHandler自定义
        System.out.println("Test");
    }

    @Override
    @SentinelResource(value = "hello", fallback = "helloFallback")
    public String hello(long s) {
        // fallback用于配置熔断降级的方法，当发生慢调用、异常数、异常比例数，会调用fallback方法
        if (s < 0) {
            throw new IllegalArgumentException("invalid arg");
        }
        return String.format("Hello at %d", s);
    }

    @Override
    @SentinelResource(value = "helloAnother", defaultFallback = "defaultFallback",
        exceptionsToIgnore = {IllegalStateException.class})
    public String helloAnother(String name) {
        // 可以针对部分异常情况做忽略处理，不再触发熔断降级
        if (name == null || "bad".equals(name)) {
            throw new IllegalArgumentException("oops");
        }
        if ("foo".equals(name)) {
            throw new IllegalStateException("oops");
        }
        return "Hello, " + name;
    }

    public String helloFallback(long s, Throwable ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at " + s;
    }

    public String defaultFallback() {
        System.out.println("Go to default fallback");
        return "default_fallback";
    }
}
