import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BankAccount } from '../../common/bank-account';
import { BankAccountService } from '../../service/bank-account.service';

@Component({
  selector: 'app-bank-account',
  templateUrl: './bank-account.component.html',
  styleUrls: ['./bank-account.component.scss']
})
export class BankAccountComponent implements OnInit {
  bankAccountId: number = 1;
  bankAccount: BankAccount | undefined;
  constructor(
    private bankAccountService: BankAccountService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() =>
      this.requestBankAccount())
  }
  requestBankAccount(): void {
    const hasBankAccountId = this.route.snapshot.paramMap.has("id");

    if(hasBankAccountId) {
      this.bankAccountId = +this.route.snapshot.paramMap.get("id")!;
    }
    else {
      this.bankAccountId = 1;
    }

    this.bankAccountService.getAccount(this.bankAccountId).subscribe(
      bankAccount => this.bankAccount = bankAccount
    )
  }


}
