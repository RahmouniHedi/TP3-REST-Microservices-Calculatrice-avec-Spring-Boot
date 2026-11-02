package com.exemple.calcul.controller;

import com.exemple.calcul.model.Resultat;
import com.exemple.calcul.service.Calculatrice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calcul")
public class CalculController {
    private final Calculatrice calculService;
    // Injection par constructeur : Spring fournit l'objet Calculatrice
    public CalculController(Calculatrice calculService) {
        this.calculService = calculService;
    }
    @GetMapping(value = "/somme", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> somme(@RequestParam("a") int a,
                                        @RequestParam("b") int b) {
        int result = calculService.somme(a, b);
        return ResponseEntity.ok(String.valueOf(result));
    }
    // D.1 : Soustraction
    @GetMapping(value = "/soustraction", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> soustraction(@RequestParam("a") int a,
                                               @RequestParam("b") int b) {
        int result = calculService.soustraction(a, b);
        return ResponseEntity.ok(String.valueOf(result));
    }

    // D.1 : Produit
    @GetMapping(value = "/produit", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> produit(@RequestParam("a") int a,
                                          @RequestParam("b") int b) {
        int result = calculService.produit(a, b);
        return ResponseEntity.ok(String.valueOf(result));
    }

    // D.2 : Division et gestion d'erreur (400 si b == 0)
    @GetMapping(value = "/division", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> division(@RequestParam("a") int a,
                                           @RequestParam("b") int b) {
        if (b == 0) {
            return ResponseEntity.badRequest()
                    .body("Erreur : division par 0 impossible");
        }
        double result = calculService.division(a, b);
        return ResponseEntity.ok(String.valueOf(result));
    }

    // D.4 : Paramètres dans le chemin (@PathVariable)
    @GetMapping(value = "/produit/{a}/{b}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> produitChemin(@PathVariable("a") int a,
                                                @PathVariable("b") int b) {
        int result = calculService.produit(a, b);
        return ResponseEntity.ok(String.valueOf(result));
    }

    // E.2 : Somme en JSON
    @GetMapping("/somme/detail")
    public Resultat sommeDetail(@RequestParam("a") int a,
                                @RequestParam("b") int b) {
        return new Resultat("somme", a, b, calculService.somme(a, b));
    }

    // E.4 : Division en JSON (400 Bad Request si b == 0)
    @GetMapping("/division/detail")
    public ResponseEntity<Resultat> divisionDetail(@RequestParam("a") int a,
                                                   @RequestParam("b") int b) {
        if (b == 0) {
            return ResponseEntity.badRequest().build();
        }
        Resultat resultat = new Resultat("division", a, b, calculService.division(a, b));
        return ResponseEntity.ok(resultat);
    }
}