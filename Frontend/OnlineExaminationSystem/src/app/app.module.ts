import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms'
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthenticationService } from './service/authentication/authentication.service';
import { UserService } from './service/user/user.service';
import { AuthenticationGuard } from './guard/authentication.guard';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { NotificationModule } from './notification/notification.module';
import { NotificationService } from './service/notification/notification.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    NotificationModule
  ],
  providers: [NotificationService, AuthenticationGuard, AuthenticationService, UserService,
  {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}],
  bootstrap: [AppComponent]
})

export class AppModule { }
