package by.vchaikovski.coffeehouse.controller.command.impl.client;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.BankCard;
import by.vchaikovski.coffeehouse.model.entity.Bill;
import by.vchaikovski.coffeehouse.model.entity.FoodOrder;
import by.vchaikovski.coffeehouse.model.service.BankCardService;
import by.vchaikovski.coffeehouse.model.service.BillService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.*;
import static by.vchaikovski.coffeehouse.model.entity.FoodOrder.OrderStatus.CANCELLED;
import static by.vchaikovski.coffeehouse.model.entity.FoodOrder.OrderStatus.COMPLETED;

/**
 * @author VChaikovski
 * The type Pay command.
 * @project Coffeehouse
 */
public class PayCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

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
        Router router = new Router(PagePath.ORDER_CREATION_PAGE);
        router.setRouterType(Router.RouterType.REDIRECT);

        return router;
    }

    private void payBill(long billId) throws ServiceException {
        BillService billService = ServiceProvider.getInstance().getBillService();
        billService.updateBillStatus(billId, Bill.BillStatus.PAID);
        billService.updateBillPaymentTime(billId, LocalDate.now().toString());
    }
}