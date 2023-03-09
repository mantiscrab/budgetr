import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { BankAccountsComponent } from './bank-account/component/bank-accounts/bank-accounts.component';
import { BankAccountComponent } from './bank-account/component/bank-account/bank-account.component';
import { Route, RouterModule } from '@angular/router';
import { BankAccountService } from './bank-account/service/bank-account.service';
import { BankAccountsService } from './bank-account/service/bank-accounts.service';

const routes: Route[] = [
  { path: 'bank-account/:id', component: BankAccountComponent },
  { path: 'bank-accounts', component: BankAccountsComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    BankAccountsComponent,
    BankAccountComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
