import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthRequest } from 'src/app/basicmodels/AuthRequest';
import { UserData } from 'src/app/basicmodels/UserData';
import { HeaderType } from 'src/app/enum/header-type.enum';
import { NotificationType } from 'src/app/enum/notification-type.enum';
import { AuthenticationService } from 'src/app/service/authentication/authentication.service';
import { NotificationService } from 'src/app/service/notification/notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];
  constructor(private router: Router,
              private authenticationService: AuthenticationService,
              private notificationService: NotificationService) { }
  

  ngOnInit(): void {
    if(this.authenticationService.isUserLoggedIn())
    {
      this.router.navigateByUrl('/pages');
    }
    else{
      this.router.navigateByUrl('/login');
    }
  }

  public onLogin(authRequest: AuthRequest): void
  {
    console.log(authRequest);
    this.subscriptions.push(
      this.authenticationService.login(authRequest).subscribe(
        (response: HttpResponse<UserData>) =>{
          const token = response.headers.get(HeaderType.JWT_TOKEN);
          this.authenticationService.saveToken(token);
          this.authenticationService.addUserToLocalCache(response.body);
          this.router.navigateByUrl('/pages');
        },
        (errorResponse: HttpErrorResponse) =>{
          console.log(errorResponse);
          this.sendErrorNotification(NotificationType.ERROR, errorResponse.error.message);
        }
      )
    );
  }
  private sendErrorNotification(notificationType: NotificationType, message: string): void {
    if(message){
      this.notificationService.notify(notificationType, message);
    }
    else{
      this.notificationService.notify(notificationType, 'An error occured. Please try again');
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
