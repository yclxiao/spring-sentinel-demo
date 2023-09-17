package top.mangod.springsentineldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@PropertySources({
		@PropertySource("classpath:/sentinel.properties"),
		@PropertySource(value = "file:${user.home}/conf/sentinel.properties", ignoreResourceNotFound = true)
})
@SpringBootApplication
public class SpringSentinelDemoApplication {

	public static void main(String[] args) {
//        System.setProperty("csp.sentinel.config.file", "sentinel.properties");

		initFlowRule(1, false);
		SpringApplication.run(SpringSentinelDemoApplication.class, args);
	}

	private static void initFlowRule(int interfaceFlowLimit, boolean method) {
		FlowRule flowRule = new FlowRule("test")
				.setCount(interfaceFlowLimit)
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


        /*List<DegradeRule> DegradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule("");
        degradeRule.setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType());
        degradeRule.setCount(0.7); // Threshold is 70% error ratio
        degradeRule.setMinRequestAmount(100)
                .setStatIntervalMs(30000) // 30s
                .setTimeWindow(10);
        DegradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(DegradeRules);*/


        /*List<SystemRule> systemRules = new ArrayList<>();
        SystemRule systemRule = new SystemRule();
        systemRule.setHighestSystemLoad(10);
        systemRules.add(systemRule);
        SystemRuleManager.loadRules(systemRules);*/

	}
}
