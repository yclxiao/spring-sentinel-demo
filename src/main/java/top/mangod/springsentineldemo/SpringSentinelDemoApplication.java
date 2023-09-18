package top.mangod.springsentineldemo;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@PropertySources({
		@PropertySource("classpath:/sentinel.properties"),
		@PropertySource(value = "file:${user.home}/conf/sentinel.properties", ignoreResourceNotFound = true)
})
@SpringBootApplication
public class SpringSentinelDemoApplication {

	public static void main(String[] args) {
//        System.setProperty("csp.sentinel.config.file", "sentinel.properties");

		initSentinelRule();
		SpringApplication.run(SpringSentinelDemoApplication.class, args);
	}

	private static void initSentinelRule() {
		// 资源限流
		FlowRule flowRule = new FlowRule("test")
				.setCount(1)
				.setGrade(RuleConstant.FLOW_GRADE_QPS);
		List<FlowRule> list = new ArrayList<>();
        /*if (method) {
            FlowRule flowRule1 = new FlowRule("test:sayHello(java.lang.String)")
                    .setCount(5)
                    .setGrade(RuleConstant.FLOW_GRADE_QPS);
            list.add(flowRule1);
        }*/
		list.add(flowRule);
		FlowRuleManager.loadRules(list);

        // 异常降级
        /*List<DegradeRule> DegradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule("");
        degradeRule.setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType());
        degradeRule.setCount(0.7); // Threshold is 70% error ratio
        degradeRule.setMinRequestAmount(100)
                .setStatIntervalMs(30000) // 30s
                .setTimeWindow(10);
        DegradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(DegradeRules);*/

		// 系统负载保护
        /*List<SystemRule> systemRules = new ArrayList<>();
        SystemRule systemRule = new SystemRule();
        systemRule.setHighestSystemLoad(10);
        systemRules.add(systemRule);
        SystemRuleManager.loadRules(systemRules);*/

		// 黑白名单授权访问
		/*AuthorityRule rule = new AuthorityRule();
		rule.setResource("test");
		rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
		rule.setLimitApp("appA,appB");
		AuthorityRuleManager.loadRules(Collections.singletonList(rule));*/
	}
}
