//package ua.epam.rd.service;
//
//import org.junit.Ignore;
//import org.junit.Test;
//import ua.epam.rd.domain.Order;
//import ua.epam.rd.domain.OrderStatus;
//import ua.epam.rd.repository.OrderRepository;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class OrderServiceSampleTest {
//    @Test(expected = IllegalArgumentException.class)
//    public void testPlaceOrder_EmptyOrderItems() {
//        OrderRepository mockRepository = mock(OrderRepository.class);
//        OrderServiceSample orderServiceSample = new OrderServiceSample(mockRepository);
//
//        Order order = new Order();
//       order.setOrderStatus(OrderStatus.NEW);
//
//        orderServiceSample.placeOrder(order);
//
//        verify(mockRepository, times(1)).addOrder(order);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testPlaceOrder_StatusNotNew() {
//        OrderRepository mockRepository = mock(OrderRepository.class);
//        OrderServiceSample orderServiceSample = new OrderServiceSample(mockRepository);
//
//        Order order = mock(Order.class, RETURNS_DEEP_STUBS);
//        when(order.getOrderItems().isEmpty()).thenReturn(Boolean.FALSE);
////        order.getOrderItems().add(0, new OrderItem());
////        order.setOrderStatus(OrderStatus.DONE);
//
//        orderServiceSample.placeOrder(order);
//
//        verify(mockRepository, times(1)).addOrder(order);
//    }
//
////    @Test
////    public void testPlaceOrder_Ok() {
////        OrderRepository mockRepository = mock(OrderRepository.class);
////        OrderServiceSample orderServiceSample = new OrderServiceSample(mockRepository);
////
////        Order order = new Order();
////        order.getOrderItems().add(0, new OrderItem());
////        order.setOrderStatus(OrderStatus.NEW);
////
////        orderServiceSample.placeOrder(order);
////
////        verify(mockRepository, times(1)).addOrder(order);
////    }
//
//    @Test(expected = IllegalStateException.class)
//    public void testPlaceOrder_OnSunday() {
//        OrderRepository mockRepository = mock(OrderRepository.class);
//        OrderServiceSample orderServiceSample = spy(new OrderServiceSample(mockRepository));
//
//        when(orderServiceSample.isWorkingDay()).thenReturn(Boolean.FALSE);
//
//        Order order = new Order();
//
//        orderServiceSample.placeOrder(order);
//
//        verify(mockRepository, times(1)).addOrder(order);
//    }
//
//    @Ignore
//    @Test
//    public void testCancelOrder_NoSuchOrder() {
//        OrderRepository mockRepository = mock(OrderRepository.class);
//        OrderServiceSample orderServiceSample = new OrderServiceSample(mockRepository);
//
//        Order order = new Order();
//        order.setOrderStatus(OrderStatus.CANCELLED);
//        when(mockRepository.getOrderById(anyLong())).thenReturn(order);
//
//        orderServiceSample.cancelOrder(123L, "dte");
//
//        orderServiceSample.updateOrder(order);
//        verify(mockRepository).updateOrder(order);
//        assertEquals(OrderStatus.CANCELLED, order.getOrderStatus());
//    }
//}
