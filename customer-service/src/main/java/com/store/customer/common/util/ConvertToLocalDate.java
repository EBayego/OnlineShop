package com.store.customer.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConvertToLocalDate {

	public static LocalDate stringToLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		try {
			LocalDate fecha = LocalDate.parse(date, formatter);

			return fecha;
		} catch (DateTimeParseException e) {
			System.out.println("Error al parsear la fecha: " + e.getMessage());
		}
		return null;
	}
}
