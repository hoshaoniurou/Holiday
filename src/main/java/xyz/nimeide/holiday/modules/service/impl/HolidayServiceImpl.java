package xyz.nimeide.holiday.modules.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import xyz.nimeide.holiday.modules.dto.HolidayDO;
import xyz.nimeide.holiday.modules.service.HolidayService;
import xyz.nimeide.holiday.modules.strategies.TimeStrategyHolder;

import java.time.LocalDate;

/**
 * @author hoshaoniurou
 */
@Service
public class HolidayServiceImpl implements HolidayService {

    private final TimeStrategyHolder timeStrategyHolder;

    public HolidayServiceImpl(TimeStrategyHolder timeStrategyHolder) {
        this.timeStrategyHolder = timeStrategyHolder;
    }

    @Override
    public Flux<HolidayDO> time(String time) {
        String type;
        if (time.length() == 4 && Integer.parseInt(time) <= LocalDate.now().getYear()){
            type = "year";
        }else if (time.length() == 7 ){
            type = "month";
        }else if (time.length() == 10 ) {
            type = "day";
        }else {
            return Flux.just();
        }
        return timeStrategyHolder.getBy(type).select(time);
    }
}
