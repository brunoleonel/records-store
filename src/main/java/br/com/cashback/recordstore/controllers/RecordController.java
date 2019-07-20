package br.com.cashback.recordstore.controllers;

import br.com.cashback.recordstore.infrastructure.repositories.CashbackIndexRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/records")
public class RecordController {

    @Autowired
    private CashbackIndexRepositoryInterface cashbackRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String getRecords() {
        return "getRecords";
    }

    @RequestMapping(path = "/{id}")
    public String getRecord(@PathVariable int id) {
        return "getRecord: " + id ;
    }

    @RequestMapping(path = "/index")
    public float cashbackIndexList() {
        return this.cashbackRepository.getCashbackIndexByGenreForDayOfWeek("POP", "friday");
    }
}
