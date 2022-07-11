package spring.springassignment.seeder;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.springassignment.entity.*;
import spring.springassignment.entity.enums.OrderSimpleStatus;
import spring.springassignment.repository.OrderRepository;
import spring.springassignment.util.DateTimeHelper;
import spring.springassignment.util.NumberUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Component
public class OrderSeeder {
    public static List<Order> orders;
    private static final int NUMBER_OF_ORDER = 1000;
    private static final int NUMBER_OF_DONE_ORDER = 700;
    private static final int NUMBER_OF_CANCELED_ORDER = 100;
    private static final int NUMBER_OF_PENDING_ORDER = 200;
    private static final int MIN_ORDER_DETAIL = 2;
    private static final int MAX_ORDER_DETAIL = 5;
    private static final int MIN_PRODUCT_QUANTITY = 1;
    private static final int MAX_PRODUCT_QUANTITY = 10;

    @Autowired
    OrderRepository orderRepository;

    List<OrderSeederByTime> seeder;

    public void configData() {
        seeder = new ArrayList<>();
        seeder.add(
                OrderSeederByTime.builder()
                        .orderStatus(OrderSimpleStatus.DONE).seedTypeByTime(OrderSeedByTimeType.DAY).day(21).month(Month.JUNE).year(2022).orderCount(350)
                        .build()
        );
        seeder.add(
                OrderSeederByTime.builder()
                        .orderStatus(OrderSimpleStatus.DONE).seedTypeByTime(OrderSeedByTimeType.DAY).day(20).month(Month.JUNE).year(2022).orderCount(300)
                        .build());
        seeder.add(
                OrderSeederByTime.builder()
                        .orderStatus(OrderSimpleStatus.DONE).seedTypeByTime(OrderSeedByTimeType.MONTH).month(Month.JUNE).year(2022).orderCount(250)
                        .build());
        seeder.add(
                OrderSeederByTime.builder()
                        .orderStatus(OrderSimpleStatus.PROCESSING).seedTypeByTime(OrderSeedByTimeType.MONTH).month(Month.JUNE).year(2022).orderCount(10)
                        .build());
        seeder.add(
                OrderSeederByTime.builder()
                        .orderStatus(OrderSimpleStatus.DONE).seedTypeByTime(OrderSeedByTimeType.MONTH).month(Month.MAY).year(2022).orderCount(40)
                        .build());
        seeder.add(
                OrderSeederByTime.builder()
                        .orderStatus(OrderSimpleStatus.DONE).seedTypeByTime(OrderSeedByTimeType.MONTH).month(Month.APRIL).year(2022).orderCount(50)
                        .build());
        seeder.add(
                OrderSeederByTime.builder()
                        .orderStatus(OrderSimpleStatus.PROCESSING).seedTypeByTime(OrderSeedByTimeType.MONTH).month(Month.APRIL).year(2022).orderCount(4)
                        .build());
        seeder.add(
                OrderSeederByTime.builder()
                        .orderStatus(OrderSimpleStatus.DONE).seedTypeByTime(OrderSeedByTimeType.MONTH).month(Month.MARCH).year(2022).orderCount(102)
                        .build());
    }

    public void orderSeeder() {
        configData();
        Faker faker = new Faker();
        orders = new ArrayList<>();
        for (OrderSeederByTime orderSeederByTime : seeder) {
            int numberOfOrder = orderSeederByTime.getOrderCount();
            for (int i = 0; i < numberOfOrder; i++) {
                //lấy random account ID
                int randomAccountIndex = NumberUtil.getRandomNumber(0, AccountSeeder.accounts.size() - 1);
                Account account = AccountSeeder.accounts.get(randomAccountIndex);
                //tạo mới đơn hàng
                Order order = new Order();
                order.setId(UUID.randomUUID().toString());
                order.setStatus(orderSeederByTime.getOrderStatus());
                LocalDateTime orderCreatedTime = calculateOrderCreatedTime(orderSeederByTime);
                order.setCreatedAt(orderCreatedTime);
                order.setUpdatedAt(orderCreatedTime);
                order.setAccountId(account.getId());
                //tạo danh sách order detail cho order
                Set<OrderDetail> orderDetailSet = new HashSet<>();
                //map này dùng để check sự tồn tại của sản phẩm trong order detail
                HashMap<String, Product> productHashMap = new HashMap<>();
                //generate số lượng của order detail
                int orderDetailNumber = NumberUtil.getRandomNumber(MIN_ORDER_DETAIL, MAX_ORDER_DETAIL);
                for (int j = 0; j < orderDetailNumber; j++) {
                    //lấy random product
                    int randomProductIndex = NumberUtil.getRandomNumber(0, ProductSeeder.products.size() - 1);
                    Product product = ProductSeeder.products.get(randomProductIndex);
                    //check tồn tại
                    if (productHashMap.containsKey(product.getId())) {
                        continue;//bỏ qua
                    }
                    //tạo order detail theo sản phẩm random
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setId(new OrderDetailId(order.getId(), product.getId()));
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(product);
                    orderDetail.setQuantity(NumberUtil.getRandomNumber(MIN_PRODUCT_QUANTITY, MAX_PRODUCT_QUANTITY));
                    orderDetail.setUnitPrice(product.getPrice());
                    orderDetailSet.add(orderDetail);
                    productHashMap.put(product.getId(), product);
                }
                //set quan hệ với order
                order.setOrderDetails(orderDetailSet);
                order.CalculateTotalPrice();
                orders.add(order);
            }
        }
        orderRepository.saveAll(orders);
    }

    private LocalDateTime calculateOrderCreatedTime(OrderSeederByTime orderSeederByTime) {
        LocalDateTime result = null;
        LocalDateTime tempLocalDateTime = null;
        int tempMonth = 1;
        int tempYear = 2022;
        int tempHour = 0;
        int tempMinute = 0;
        int tempSecond = 0;
        switch (orderSeederByTime.getSeedTypeByTime()) {
            case YEAR:
                //theo năm thì random tháng và ngày
                tempMonth = DateTimeHelper.getRandomMonth().getValue();
                tempYear = orderSeederByTime.getYear();
                tempLocalDateTime = LocalDateTime.of(tempYear, tempMonth, 1,0,0,0);
                result = tempLocalDateTime.plusMonths(1).minusDays(1);
                break;
            case MONTH:
                // nếu theo tháng, năm thì random ngày.
                tempMonth = orderSeederByTime.getMonth().getValue();
                tempYear = orderSeederByTime.getYear();
                tempLocalDateTime = LocalDateTime.of(tempYear, tempMonth, 1, 0, 0, 0);
                LocalDateTime lastDayOfMonth = tempLocalDateTime.plusMonths(1).minusDays(1);
                int randomDay = NumberUtil.getRandomNumber(1, lastDayOfMonth.getDayOfMonth());
                result = LocalDateTime.of(tempYear, tempMonth, randomDay, 0, 0, 0);
                if (result.isAfter(LocalDateTime.now())) {
                    // nếu sau thời gian hiện tại, tức là tháng năm đang thời gian hiện tại
                    randomDay = NumberUtil.getRandomNumber(1, LocalDateTime.now().getDayOfMonth());
                    result = LocalDateTime.of(tempYear, tempMonth, randomDay, 0, 0, 0);
                }
                break;
            case DAY:
                // nếu là ngày thì fix
                result = LocalDateTime.of(orderSeederByTime.getYear(), orderSeederByTime.getMonth(), orderSeederByTime.getDay(), 0, 0, 0);
                break;
        }
        return result;
    }
}

