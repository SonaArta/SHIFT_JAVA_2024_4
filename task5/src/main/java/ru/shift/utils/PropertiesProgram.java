package ru.shift.utils;

public record PropertiesProgram(int storageSize,
                                int producerCount, int consumerCount,
                                int producerTime, int consumerTime,
                                String nameProducer, String nameConsumer) {
}
