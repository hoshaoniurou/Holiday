package xyz.nimeide.holiday.modules.strategies;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;
import xyz.nimeide.holiday.modules.dto.HolidayDO;
import xyz.nimeide.holiday.modules.entity.Holiday;

/**
 * @author hoshaoniurou
 */
public interface TimeStrategy {

    Flux<HolidayDO> select(@NotNull String str);
}
