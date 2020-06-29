package xyz.nimeide.holiday.modules.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author wangjian
 */
@Data
@Table
public class Holiday implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("日期")
    private LocalDate date;

    @ApiModelProperty("年")
    private Integer year;

    @ApiModelProperty("月份")
    private Integer month;

    @ApiModelProperty("当月天数")
    private Integer day;

    @ApiModelProperty("0普通工作日 1周末双休日 2需要补班的工作日 3法定节假日")
    private Integer status;

    @ApiModelProperty("节假日名称")
    @Column("holiday_name")
    private String holidayName;


}
