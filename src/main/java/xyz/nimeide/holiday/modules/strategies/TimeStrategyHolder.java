package xyz.nimeide.holiday.modules.strategies;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author hoshaoniurou
 */
@Component
public class TimeStrategyHolder {
    private final Map<String, TimeStrategy> timeStrategyMap;

    public TimeStrategyHolder(Map<String, TimeStrategy> timeStrategyMap) {
        this.timeStrategyMap = timeStrategyMap;
    }

    public TimeStrategy getBy(String type) {
        return timeStrategyMap.get(type);
    }
}
