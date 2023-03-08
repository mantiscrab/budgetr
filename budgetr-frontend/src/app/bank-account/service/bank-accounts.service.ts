import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BankAccount } from '../common/bank-account';

@Injectable({
  providedIn: 'root'
})
export class BankAccountsService {

  private baseUrl = 'http://localhost:8080/bank-accounts'

  constructor(private httpClient: HttpClient) { }

  getAccounts(): Observable<BankAccount[]> {
    return this.httpClient.get<BankAccount[]>(this.baseUrl);
  }
}
