import { TestBed } from '@angular/core/testing';

import { BankAccountsService } from './bank-accounts.service';

describe('BankAccountService', () => {
  let service: BankAccountsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BankAccountsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
