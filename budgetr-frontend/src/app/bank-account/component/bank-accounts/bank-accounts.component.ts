import { Component, OnInit } from '@angular/core';
import { BankAccount } from '../../common/bank-account';
import { BankAccountsService } from '../../service/bank-accounts.service';

@Component({
  selector: 'app-bank-accounts',
  templateUrl: './bank-accounts.component.html',
  styleUrls: ['./bank-accounts.component.scss']
})
export class BankAccountsComponent implements OnInit {
  accounts: BankAccount[] = [];

  constructor(private bankAccountService: BankAccountsService) { }

  ngOnInit(): void {
    this.bankAccountService.getAccounts().subscribe(
      bankAccountsFromServer => this.accounts = bankAccountsFromServer
    )
  }

}
