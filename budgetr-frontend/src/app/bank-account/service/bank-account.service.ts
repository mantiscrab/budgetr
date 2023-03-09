import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BankAccount } from '../common/bank-account';

@Injectable({
  providedIn: 'root'
})
export class BankAccountService {

  constructor(private httpClient: HttpClient) { }

  private baseUrl = 'http://localhost:8080/bank-account'

  getAccount(bankAccountId: number): Observable<BankAccount> {
    const searchUrl = `${this.baseUrl}/${bankAccountId}`;
    return this.httpClient.get<BankAccount>(searchUrl);
  }
}
