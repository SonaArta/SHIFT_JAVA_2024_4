package ru.shift.load;

import lombok.Builder;

@Builder
public record ApplicationProperties(int port,
                                    String host) {
}