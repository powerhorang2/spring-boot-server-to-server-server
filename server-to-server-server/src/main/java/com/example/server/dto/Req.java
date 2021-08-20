package com.example.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Req<T> {

	private Header header;
	
	@JsonProperty(value = "resBody")
	private T Body;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Header {
		private String responseCode;
	}
}
