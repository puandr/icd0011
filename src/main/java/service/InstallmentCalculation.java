package service;

import model.Installment;
import model.Order;
import model.OrderRow;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class InstallmentCalculation {

    private static List<Installment> listToReturn;
    private static boolean thereIsReminder;
    private static int reminder = 0;
    private static int reminderToAddToTwoLastMonth = 0;
    private static int reminderToAddToMonthBeforeLastMonth = 0;
    private static int reminderToAddToLastMonth = 0;
    private static boolean reminderDevidesByTwo = false;
    private static LocalDate paymentDate;
    private static Installment payment = new Installment();


    public static List<Installment> calculate(Order order, LocalDate startDate, LocalDate endDate){

        listToReturn = new ArrayList<>();

        int totalPrice = totalPrice(order);

        if (totalPrice < 3) {
            return null;
        }

        Period period = Period.between(startDate, endDate);
        int installmentMonths = period.getMonths();

        if(period.getDays() > 1) {
            installmentMonths++;
        }
        if(endDate.getDayOfMonth() == 1) {
            installmentMonths++;
        }

        if (totalPrice / installmentMonths < 3) {
            installmentMonths = totalPrice / 3;
        }

        int paymentPerMonth = totalPrice / installmentMonths;

        checkReminder(totalPrice, installmentMonths);


        payment.setAmount(paymentPerMonth);
        payment.setDate(startDate);
        agregate(paymentPerMonth, startDate);
        paymentDate = startDate;

        for (int i = 0; i < installmentMonths - 1; i++ ) {
            paymentDate = paymentDate.with(TemporalAdjusters.firstDayOfNextMonth());
            agregate(payment.getAmount(),  paymentDate);
        }

        if (thereIsReminder) {
            if (reminderDevidesByTwo) {
                listToReturn.get(listToReturn.size() - 1).setAmount(paymentPerMonth + reminderToAddToTwoLastMonth);
                listToReturn.get(listToReturn.size() - 2).setAmount(paymentPerMonth + reminderToAddToTwoLastMonth);
            } else {
                listToReturn.get(listToReturn.size() - 1).setAmount(paymentPerMonth + reminderToAddToMonthBeforeLastMonth);
                listToReturn.get(listToReturn.size() - 2).setAmount(paymentPerMonth + reminderToAddToLastMonth);
            }
        }

        return listToReturn;
    }

    private static void agregate (int amount, LocalDate date) {
        Installment newInstallment = new Installment();
        newInstallment.setAmount(amount);
        newInstallment.setDate(date);
        listToReturn.add(newInstallment);
    }

    private static void checkReminder(int totalPrice, int installmentMonths) {
        thereIsReminder = false;

        if (totalPrice % installmentMonths > 0) {
            reminder = totalPrice % installmentMonths;
            thereIsReminder = true;
        }

        if (reminder % 2 == 0) {
            reminderToAddToTwoLastMonth = reminder / 2;
            reminderDevidesByTwo = true;
        } else {
            reminderToAddToMonthBeforeLastMonth = reminder / 2;
            reminderToAddToLastMonth = reminder /2 + 1;
        }
    }

    private static int totalPrice(Order order) {
        int totalPrice = 0;
        for (OrderRow row : order.getOrderRows()) {
            totalPrice = totalPrice + (row.getQuantity() * row.getPrice());
        }
        return totalPrice;
    }
}
