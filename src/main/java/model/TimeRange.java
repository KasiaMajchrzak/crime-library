package model;

import java.time.LocalDateTime;

public interface TimeRange {
	LocalDateTime getFrom();
	LocalDateTime getTo();
}
