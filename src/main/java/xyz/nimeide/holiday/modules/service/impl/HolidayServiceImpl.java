package xyz.nimeide.holiday.modules.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import xyz.nimeide.holiday.modules.entity.Holiday;
import xyz.nimeide.holiday.modules.service.HolidayService;
import xyz.nimeide.holiday.modules.strategies.TimeStrategyHolder;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * @author hoshaoniurou
 */
@Service
public class HolidayServiceImpl implements HolidayService {

    private final TimeStrategyHolder timeStrategyHolder;

    public HolidayServiceImpl(TimeStrategyHolder timeStrategyHolder) {
        this.timeStrategyHolder = timeStrategyHolder;
    }

    private static final int YEAR_SIZE = 4;
    private static final int MONTH_SIZE = 7;
    private static final int DAY_SIZE = 10;

    private static final String YEAR_PATTERN = "\\d{4}";
    private static final String MONTH_PATTERN = "\\d{4}-\\d{2}";
    private static final String DAY_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    @Override
    public Flux<Holiday> time(String time) {
        String type;
        if (time.length() == YEAR_SIZE && Pattern.matches(YEAR_PATTERN, time) && Integer.parseInt(time) <= LocalDate.now().getYear()) {
            type = "year";
        } else if (time.length() == MONTH_SIZE && Pattern.matches(MONTH_PATTERN, time)) {
            type = "month";
        } else if (time.length() == DAY_SIZE && Pattern.matches(DAY_PATTERN, time)) {
            type = "day";
        } else {
            return Flux.just();
        }
        return timeStrategyHolder.getBy(type).select(time);
    }
}
