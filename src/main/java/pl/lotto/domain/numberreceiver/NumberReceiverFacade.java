package pl.lotto.domain.numberreceiver;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numberreceiver.dto.InputNumbersResultDto;
import pl.lotto.domain.numberreceiver.dto.TicketDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static pl.lotto.domain.numberreceiver.dto.InputNumbersResultDto.builder;

@AllArgsConstructor
public class NumberReceiverFacade {
    private NumberValidator validator;
    private NumberReceiverRepository repository;
    private Clock clock;

    public InputNumbersResultDto inputNumbers(Set<Integer> numbersFromUser) {
        boolean areAllNumbersInRange = validator.areAllNumbersInRange(numbersFromUser);
        if (areAllNumbersInRange) {
            String ticketId = UUID.randomUUID().toString();
            LocalDateTime drawDate = LocalDateTime.now(clock);
            Ticket savedTicket = repository.save(new Ticket(ticketId, drawDate, numbersFromUser));
            return builder()
                    .ticketID(savedTicket.ticketId())
                    .drawDate(savedTicket.drawDate())
                    .numbersFromUser(numbersFromUser)
                    .message("success")
                    .build();
        }
        return builder()
                .message("failed")
                .build();
    }

    public List<TicketDto> userNumbers(LocalDateTime date){
        List<Ticket> allTicketsByDrawDate = repository.findAllTicketsByDrawDate(date);
       return allTicketsByDrawDate
                .stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }


}
