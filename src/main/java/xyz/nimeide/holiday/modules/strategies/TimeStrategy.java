package xyz.nimeide.holiday.modules.strategies;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;
import xyz.nimeide.holiday.modules.entity.Holiday;

/**
 * @author hoshaoniurou
 */
public interface TimeStrategy {

    /**
     * 查询逻辑
     * @param str 时间
     * @return
     */
    Flux<Holiday> select(@NotNull String str);
}
