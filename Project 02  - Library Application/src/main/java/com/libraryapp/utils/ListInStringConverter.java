package com.libraryapp.utils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class ListInStringConverter {

	public Set<Long> convertListInStringToSetInLong(String listInString){
		
		Set<Long> converted = new LinkedHashSet<Long>();
		
		if (listInString.length() <= 2) {
			return converted;
		} else {
			String idsInString = listInString.substring(1, listInString.length() -1);
			List<String> idsStringArrayList = Arrays.asList(idsInString.split(", "));
			for (String id : idsStringArrayList) converted.add(Long.parseLong(id));
			return converted;
		}
	}
}
