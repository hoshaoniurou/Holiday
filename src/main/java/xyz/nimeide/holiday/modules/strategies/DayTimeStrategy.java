package xyz.nimeide.holiday.modules.strategies;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import xyz.nimeide.holiday.modules.dto.HolidayDO;


/**
 * @author hoshaoniurou
 */
@Service("day")
public class DayTimeStrategy implements TimeStrategy {
    @Override
    public Flux<HolidayDO> select(@NotNull String str) {
        return null;
    }
}
