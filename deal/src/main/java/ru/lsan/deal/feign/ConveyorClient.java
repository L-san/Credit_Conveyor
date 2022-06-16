package ru.lsan.deal.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.lsan.deal.dto.LoanApplicationRequestDTO;
import ru.lsan.deal.dto.LoanOfferDTO;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "conveyor",url="https://localhost:8080/conveyor")
public interface ConveyorClient {

    @PostMapping("/offers")
    public ResponseEntity<Optional<List<LoanOfferDTO>>> offers(@RequestBody LoanApplicationRequestDTO dto);

}
