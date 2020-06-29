package xyz.nimeide.holiday.modules.service;

import reactor.core.publisher.Flux;
import xyz.nimeide.holiday.modules.dto.HolidayDO;

/**
 * @author hoshaoniurou
 */
public interface HolidayService {

    /**
     * 传入时间返回状态
     * @param time
     * @return
     */
    Flux<HolidayDO> time(String time);
}
