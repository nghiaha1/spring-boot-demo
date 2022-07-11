package spring.springassignment.seeder;

import lombok.*;
import spring.springassignment.entity.enums.OrderSimpleStatus;
import spring.springassignment.seeder.OrderSeedByTimeType;

import java.time.Month;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSeederByTime {
    /**
     * Loại gồm 3 kiểu:
     * - Theo ngày (generate chính xác theo ngày tháng năm)
     * - Theo tháng (generate chính xác theo tháng và năm, ngày sinh random)
     * - Theo năm (generate chính xác theo năm, ngày sinh và tháng random)
     */
    private OrderSeedByTimeType seedTypeByTime;
    private int year; // năm nào
    private Month month; // tháng nào
    private int day; // ngày nào
    private int orderCount; // số lượng order trong thời gian này
    private OrderSimpleStatus orderStatus; // trạng thái order cần sinh.
}