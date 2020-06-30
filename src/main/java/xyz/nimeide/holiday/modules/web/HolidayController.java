package xyz.nimeide.holiday.modules.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import lombok.SneakyThrows;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.nimeide.holiday.config.CommonThreadFactory;
import xyz.nimeide.holiday.modules.dao.HolidayRepository;
import xyz.nimeide.holiday.modules.dto.HolidayDO;
import xyz.nimeide.holiday.modules.entity.Holiday;
import xyz.nimeide.holiday.modules.service.HolidayService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangjian
 */
@RestController
@RequestMapping("/holiday")
public class HolidayController {

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            5, 10,
            1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(10),
            new CommonThreadFactory("抓取数据"),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    private final HolidayRepository holidayRepository;
    private final HolidayService holidayService;

    public HolidayController(HolidayRepository holidayRepository, HolidayService holidayService) {
        this.holidayRepository = holidayRepository;
        this.holidayService = holidayService;
    }

    @SneakyThrows
    @GetMapping("/sync-internet-day/{year}")
    public Mono<Boolean> geInternetDay(@PathVariable("year") String year) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("http://api.haoshenqi.top/holiday?date=" + year)
                .build();
        Call call = client.newCall(request);
        final String res = Objects.requireNonNull(call.execute().body()).string();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                (json, type, jsonDeserializationContext) -> {
                    String datetime = json.getAsJsonPrimitive().getAsString();
                    return LocalDate.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }).create();
        List<Holiday> holidayList = Arrays.asList(gson.fromJson(res, Holiday[].class));
        holidayRepository.deleteHolidayByYear(year).subscribe();
        return holidayRepository.saveAll(holidayList)
                .all(v -> !ObjectUtils.isEmpty(v));
    }

    /**
     * 传入时间返回状态
     * @param time 2020 / 2020-01 /2020-01-01
     * @return HolidayDO
     */
    @GetMapping("/{time}")
    public Flux<HolidayDO> time(@PathVariable("time") @NotNull("数据错误") String time){
        return holidayService.time(time);
    }
}
