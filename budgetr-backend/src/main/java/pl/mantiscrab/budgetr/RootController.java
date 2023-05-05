package pl.mantiscrab.budgetr;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RootController {

    @GetMapping("/")
    ResponseEntity<BudgetrNavigation> root() {
        BudgetrNavigation model = new BudgetrNavigation();
//		model.add(linkTo(methodOn(BankAccountController.class).getAccounts()).withRel("bankAccounts"));
        return ResponseEntity.ok(model);
    }
}
