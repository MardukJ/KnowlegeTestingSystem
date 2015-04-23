//package ua.epam.rd.repository;
//
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import ua.epam.rd.domain.Pizza;
//import ua.epam.rd.domain.PizzaType;
//import ua.epam.rd.template.UTRepositoryTestsTemplate;
//
//import static org.junit.Assert.assertNotNull;
//
//public class JPAPizzaRepositoryTest extends UTRepositoryTestsTemplate {
//
//    @Autowired
//    private PizzaRepository pizzaRepository;
//
//    @Ignore
//    @Test
//    public void testSomeMethod() {
//        Pizza pizza = new Pizza();
//        pizza.setName("Montenegro");
//        pizza.setPizzaType(PizzaType.SEA);
//        pizza.setPrice(100);
//
//        Long id = pizzaRepository.savePizza(pizza);
//        System.out.println(id);
//
//        assertNotNull(id);
//        //fail("The test case is a prototype.");
//    }
//
//}
