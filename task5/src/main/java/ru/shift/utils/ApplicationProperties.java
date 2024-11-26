package ru.shift.utils;

import lombok.Builder;

@Builder
public record ApplicationProperties(int storageSize,
                                    int producerCount, int consumerCount,
                                    int producerTime, int consumerTime,
                                    String producerName, String consumerName) {
}
