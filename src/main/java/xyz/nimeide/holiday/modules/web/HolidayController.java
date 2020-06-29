package xyz.nimeide.holiday.modules.web;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.nimeide.holiday.common.config.CommonThreadFactory;
import xyz.nimeide.holiday.modules.dao.HolidayRepository;
import xyz.nimeide.holiday.modules.entity.Holiday;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
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

    @Autowired
    private HolidayRepository holidayRepository;

    @SneakyThrows
    @GetMapping("/sync-internet-day/{year}")
    public Mono<String> geInternetDay(@PathVariable("year") String year) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("http://api.haoshenqi.top/holiday?date=" + year)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String res = Objects.requireNonNull(response.body()).string();
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, type, jsonDeserializationContext) -> {
                            String datetime = json.getAsJsonPrimitive().getAsString();
                            return LocalDate.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        }).create();
                List<Holiday> holidayList = Arrays.asList(gson.fromJson(res, Holiday[].class));
                Mono<Boolean> booleanMono = holidayRepository.deleteHolidayByYear(year);
                System.out.println(booleanMono.block());
                EXECUTOR.execute(() -> {
                    Flux<Holiday> holidayFlux = holidayRepository.saveAll(holidayList);
                    System.out.println(Thread.currentThread().getName());
                    holidayFlux.toIterable().forEach(System.out::println);
                });
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
        });
        return Mono.create(monoSink -> monoSink.success("success"));
    }
}
