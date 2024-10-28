package com.workintech.fswebs18challengemaven.controller;

import com.workintech.fswebs18challengemaven.repository.CardRepository;
import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.util.CardValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardRepository cardRepository;


    @Autowired
    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @GetMapping("/byColor/{color}")
    public List<Card> getByColor(@PathVariable("color") String color) {
        return cardRepository.findByColor(color);
    }

    @PostMapping
    public Card save(@RequestBody Card card) {
        CardValidation.validateCard(card);
        return cardRepository.save(card);
    }

    @PutMapping("/")
    public Card update(@RequestBody Card card) {
        long id = card.getId();
        card.setId(id);
        return cardRepository.update(card);
    }

    @DeleteMapping("{id}")
    public Card remove(@PathVariable long id) {
        return cardRepository.remove(id);
    }

    @GetMapping("/byValue/{value}")
    public List<Card> getByValue(@PathVariable("value") Integer value) {
        return cardRepository.findByValue(value);
    }


    @GetMapping("/byType/{type}")
    public List<Card> getByType(@PathVariable("type") String type) {
        return cardRepository.findByType(type);
    }
}
