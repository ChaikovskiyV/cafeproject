package by.vchaikovski.coffeeshop.controller.command.impl.card;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.BankCard;
import by.vchaikovski.coffeeshop.model.entity.Bill;
import by.vchaikovski.coffeeshop.model.entity.FoodOrder;
import by.vchaikovski.coffeeshop.model.service.BankCardService;
import by.vchaikovski.coffeeshop.model.service.BillService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.*;
import static by.vchaikovski.coffeeshop.model.entity.FoodOrder.OrderStatus.CANCELLED;
import static by.vchaikovski.coffeeshop.model.entity.FoodOrder.OrderStatus.COMPLETED;

public class PayCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:ss";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BankCardService cardService = ServiceProvider.getInstance().getBankCardService();
        HttpSession session = request.getSession();
        String cardNumber = request.getParameter(CARD_NUMBER);
        String expirationDate = request.getParameter(CARD_EXPIRATION_DATE);
        FoodOrder order = (FoodOrder) session.getAttribute(ORDER);
        try {
            Optional<BankCard> optionalCard = cardService.findCardByNumberAndDate(cardNumber, expirationDate);
            if (optionalCard.isPresent()) {
                BankCard card = optionalCard.get();
                long cardId = card.getId();
                session.setAttribute(CARD_ID, cardId);
                String amount = request.getParameter(TOTAL_PRICE);
                FoodOrder.OrderStatus orderStatus = order.getStatus();
                boolean isPayed = orderStatus != CANCELLED && orderStatus != COMPLETED &&
                        cardService.withdrawMoneyCard(cardId, amount);
                if (isPayed) {
                    payBill(order.getBillId());
                }
                session.setAttribute(IS_PAYED, isPayed);
                String message = isPayed ? "The order was payed" : "The order wasn't payed";
                logger.debug(message);
            } else {
                logger.debug("Bank card wasn't found");
            }
        } catch (ServiceException e) {
            String message = "Account refill can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        Router router = new Router(PagePath.CART_PAGE);
        router.setRouterType(Router.RouterType.REDIRECT);

        return router;
    }

    private void payBill(long billId) throws ServiceException {
        BillService billService = ServiceProvider.getInstance().getBillService();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        billService.updateBillStatus(billId, Bill.BillStatus.PAID);
        billService.updateBillPaymentTime(billId, LocalDateTime.now().format(formatter));
    }
}