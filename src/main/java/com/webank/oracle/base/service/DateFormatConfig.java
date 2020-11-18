package com.webank.oracle.base.service;

/**
 *
 */

//@Slf4j
//@Configuration
//public class DateFormatConfig {
//
//    /**
//     * Date格式化字符串
//     */
//    private static final String DATE_FORMAT = "yyyy-MM-dd";
//    /**
//     * DateTime格式化字符串
//     */
//    @Value("${spring.jackson.date-format}")
//    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
//    /**
//     * Time格式化字符串
//     */
//    private static final String TIME_FORMAT = "HH:mm:ss";
//
//    /**
//     * 自定义Bean
//     *
//     * @return
//     */
//    @Bean
//    @Primary
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//        return builder -> builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
////                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)))
////                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT)))
////                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)))
////                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT)))
//                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
//    }
//
//}
