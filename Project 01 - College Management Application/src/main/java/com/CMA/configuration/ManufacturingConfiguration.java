package com.CMA.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.CMA.other.CourseSelector;

@Configuration
public class ManufacturingConfiguration {

	@Bean
	public CourseSelector newCourseSelector() {
		return new CourseSelector();
	}
}
