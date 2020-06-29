package xyz.nimeide.holiday.modules.dao;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import xyz.nimeide.holiday.modules.entity.Holiday;

/**
 * @author wangjian
 */
@Repository
public interface HolidayRepository extends R2dbcRepository<Holiday, Integer> {

    Mono<Boolean> deleteHolidayByYear(String year);
}
