import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { AuthRequest } from '../../basicmodels/AuthRequest';
import { UserData } from '../../basicmodels/UserData';
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private host: string = environment.apiUrl;

  constructor(private http: HttpClient) { }

  public getUsers(): Observable<UserData[] | HttpErrorResponse>
  {
     return this.http.get<UserData[]>(`${this.host}/api/user/list`);
  }
}
