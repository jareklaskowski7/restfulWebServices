package com.in28minutes.restfulWebservices.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.in28minutes.restfulWebservices.models.SomeBean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {
    // Arguments:
    // bean(s)
    // name of the filter in the string format
    // array of fields in the string format indicating which fields to exclude
    public MappingJacksonValue getMappingJacksonValues(Object bean, String nameFilter, String[] fields) {
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter(nameFilter, simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(bean);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    @GetMapping("/staticFilteringSomeBean")
    public SomeBean staticRetrieveSomeBean() {
        int value2 = 7;
        SomeBean someBean = new SomeBean("value1", value2, "value3");

        return someBean;
    }

    //field1, field2
    @GetMapping("/dynamicFilteringSomeBean")
    public MappingJacksonValue dynamicRetrieveSomeBean() {
        int value2 = 7;
        SomeBean someBean = new SomeBean("value1", value2, "value3");

        String[] fields = new String[2];
        fields[0] = "field1";
        fields[1] = "field2";

        MappingJacksonValue mappingJacksonValue = getMappingJacksonValues(someBean, "dynamicSomeBeanFilter", fields);

        return mappingJacksonValue;
    }

    @GetMapping("/staticFilteringSomeBeans")
    public List<SomeBean> staticRetrieveSomeBeans() {
        int value2 = 7;
        int value22 = 27;
        List<SomeBean> someBeans = Arrays.asList(
                new SomeBean("value1", value2, "value3"),
                new SomeBean("value12", value22, "value32")
        );

        return someBeans;
    }

    //field2, field3
    @GetMapping("/dynamicFilteringSomeBeans")
    public MappingJacksonValue dynamicRetrieveSomeBeans() {
        int value2 = 7;
        int value22 = 27;
        List<SomeBean> someBeans = Arrays.asList(
                new SomeBean("value1", value2, "value3"),
                new SomeBean("value12", value22, "value32"));

        String[] fields = new String[2];
        fields[0] = "field2";
        fields[1] = "field3";

        MappingJacksonValue mappingJacksonValue = getMappingJacksonValues(someBeans, "dynamicSomeBeanFilter", fields);

        return mappingJacksonValue;
    }
}
