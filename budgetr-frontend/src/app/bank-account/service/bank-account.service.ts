import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BankAccount } from '../common/bank-account';

@Injectable({
  providedIn: 'root'
})
export class BankAccountService {

  private baseUrl = 'http://localhost:8080/accounts'

  constructor(private httpClient: HttpClient) { }

  getAccounts(): Observable<BankAccount[]> {
    return this.httpClient.get<BankAccount[]>(this.baseUrl)
    // .pipe(
    //   map(response => response.bankAccounts)
    // )
    ;
  }
}

interface GetResponse {
  bankAccounts: BankAccount[]
}
