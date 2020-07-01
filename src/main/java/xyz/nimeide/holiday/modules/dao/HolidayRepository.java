package xyz.nimeide.holiday.modules.dao;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.nimeide.holiday.modules.dto.HolidayDO;
import xyz.nimeide.holiday.modules.entity.Holiday;

/**
 * @author wangjian
 */
@Repository
public interface HolidayRepository extends R2dbcRepository<Holiday, Integer> {

    @Modifying
    Mono<Void> deleteHolidayByYear(String year);

    Flux<Holiday> findByDate(String day);

    Flux<Holiday> findByDateLike(String yearMonth);

    Flux<Holiday> findByYear(String year);
}
