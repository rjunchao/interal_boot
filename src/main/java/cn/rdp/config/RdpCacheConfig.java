package cn.rdp.config;
import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月23日 上午10:19:41
*   desc: 
*/
@Configuration
public class RdpCacheConfig {

	@Bean("rdpKeyGenerator")
    public KeyGenerator keyGenerator(){
        return new KeyGenerator(){

            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName() + "___" + params[0];
            }
        };
    }
}
