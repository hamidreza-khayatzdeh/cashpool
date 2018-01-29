package com.invia.challenge.cashpool.formater;

import com.invia.challenge.cashpool.service.dto.TravelerDto;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by khayatzadeh on 1/27/2018.
 */
@Component
public class TravelerDtoFormatter implements Formatter<TravelerDto> {

    @Override
    public TravelerDto parse(String s, Locale locale) throws ParseException {
        Long id = Long.valueOf(s);
        TravelerDto travelerDto = new TravelerDto();
        travelerDto.setId(id);
        return travelerDto;
    }

    @Override
    public String print(TravelerDto travelerDto, Locale locale) {
        return travelerDto.getId().toString();
    }

}
