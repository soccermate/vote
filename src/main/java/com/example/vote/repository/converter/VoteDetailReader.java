package com.example.vote.repository.converter;

import com.example.vote.repository.dto.voteDetail.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;


@ReadingConverter
@Slf4j
@RequiredArgsConstructor
public class VoteDetailReader implements Converter<Row, VoteDetail> {

    private final ObjectMapper objectMapper;



    @Override
    @SneakyThrows
    public VoteDetail convert(Row row) {
        Long voteId = row.get("vote_id", Long.class);
        Long groupId = row.get("group_id", Long.class);
        LocalDateTime createdTime = row.get("created_time", LocalDateTime.class);
        String title = row.get("title", String.class);

        List<TimesOptionDetail> timesOptionDetails = objectMapper.readValue(row.get("times_options", String.class),
                new TypeReference<List<TimesOptionDetail>>() {});

        List<CategoryOptionDetail> categoryOptionDetails = objectMapper.readValue(row.get("category_options", String.class)
                ,  new TypeReference<List<CategoryOptionDetail>>() {});

        List<DateOptionDetail> dateOptionDetails = objectMapper.readValue(row.get("date_options", String.class),
                new TypeReference<List<DateOptionDetail>>() {});

        List<LocationOptionDetail> locationOptionDetails = objectMapper.readValue(row.get("location_options", String.class)
                , new TypeReference<List<LocationOptionDetail>>() {});

        return VoteDetail.builder()
                .groupId(groupId)
                .title(title)
                .createdTime(createdTime)
                .voteId(voteId)
                .categoryOptionDetail(categoryOptionDetails)
                .dateOptionDetail(dateOptionDetails)
                .locationOptionDetail(locationOptionDetails)
                .timesOptionDetail(timesOptionDetails)
                .build();
    }
}
