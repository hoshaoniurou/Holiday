package xyz.nimeide.holiday.modules.strategies;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import xyz.nimeide.holiday.modules.dao.HolidayRepository;
import xyz.nimeide.holiday.modules.entity.Holiday;

/**
 * @author hoshaoniurou
 */
@Service("month")
public class MonthTimeStrategy implements TimeStrategy {

    private final HolidayRepository holidayRepository;

    public MonthTimeStrategy(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }


    @Override
    public Flux<Holiday> select(@NotNull String str) {
        return holidayRepository.findByDateLike(str + "%");
    }
}
