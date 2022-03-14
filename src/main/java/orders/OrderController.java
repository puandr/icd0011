package orders;

import config.DbConfig;
import config.PostgresDataSource;
import model.Installment;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import service.InstallmentCalculation;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderDao dao;

    public void init() {
        if (this.dao == null) {
            ConfigurableApplicationContext ctx =
                    new AnnotationConfigApplicationContext(DbConfig.class, PostgresDataSource.class);
            dao = ctx.getBean(OrderDao.class);
        }
    }

    @GetMapping("orders")
    public List<Order> getOrders() {
        return dao.findAllOrders();
    }

    @GetMapping("orders/{id}")
    public Order getOrderById(@PathVariable Long id){
        return dao.getOrderById(id);
    }


//    @Valid töötab tänu ValidationAdvice-le
//    @RequesttBody ütleb, et andmed tulid JSonist mitte Formist
    @PostMapping("orders")
    public Order saveOrders(@RequestBody @Valid Order order) {
        Long returnId = 0L;
        returnId = dao.addOrderWithResponse(order);
        return dao.getOrderById(returnId);
    }

    @DeleteMapping("orders/{id}")
    public void deleteOrder(@PathVariable Long id) {
        dao.deleteOrderById(id);
    }

    @GetMapping("orders/{id}/installments")
        public List<Installment> calculateIntallment (@PathVariable Long id, @RequestParam("start") @DateTimeFormat(pattern="yyyy-MM-dd")
                LocalDate startDate, @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate) {

            return InstallmentCalculation.calculate(dao.getOrderById(id), startDate, endDate);
    }


}