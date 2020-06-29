package xyz.nimeide.holiday.modules.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import xyz.nimeide.holiday.modules.entity.Holiday;

import java.time.LocalDate;

/**
 * @author wangjian
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HolidayDO extends Holiday {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

}
