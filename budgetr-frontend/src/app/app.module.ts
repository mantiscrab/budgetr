import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BankAccountsComponent } from './bank-account/component/bank-accounts/bank-accounts.component';
import { BankAccountService } from './bank-account/service/bank-account.service';

@NgModule({
  declarations: [
    AppComponent,
    BankAccountsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [BankAccountService],
  bootstrap: [AppComponent]
})
export class AppModule { }
