import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { FieldDetails } from 'src/app/basicmodels/FieldDetails';
import { UserData } from 'src/app/basicmodels/UserData';
import { NotificationType } from 'src/app/enum/notification-type.enum';
import { NotificationService } from 'src/app/service/notification/notification.service';
import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {
  searchString: string = "";
  searchField: string = "";
  public users: UserData[];
  public refreshing: boolean;
  private subscriptions: Subscription[] = [];
  fields: FieldDetails[] = [
    {
      displayFieldName: 'Department Code',
      actualFieldName: 'departmentCode'
    },
    {
      displayFieldName: 'Department Name',
      actualFieldName: 'departmentName'
    },
    {
      displayFieldName: 'Status',
      actualFieldName: 'status'
    }
  ];

  constructor(private userService: UserService,
              private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.getUsers(true);
  }

  public getUsers(showNotification: boolean): void
  {
    this.refreshing = true;
    this.subscriptions.push(
      this.userService.getUsers().subscribe(
        (response: UserData[]) => {
          this.users = response;
          this.refreshing = false;
          if(showNotification){
            this.sendNotification(NotificationType.SUCCESS,`${response.length} user(s) loaded successfully`);
          }
        },
        (errorResponse: HttpErrorResponse) => {
            this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
            this.refreshing = false;
        }
      )
    );
  }

  private sendNotification(notificationType: NotificationType, message: string): void {
    if(message){
      this.notificationService.notify(notificationType, message);
    }
    else{
      this.notificationService.notify(notificationType, 'An error occured. Please try again');
    }
  }

  isFilterChange(event:any) {
    this.searchField = event.searchFieldName;
    this.searchString = event.searchFieldValue;
  }

}
