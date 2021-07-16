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
export class AuthenticationService {
  public host: string = environment.apiUrl;
  private token: string;
  private loggedInUserName: string;
  private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient) { }

  public login(authRequest: AuthRequest): Observable<HttpResponse<any> | HttpErrorResponse>
  {
      return this.http.post<HttpResponse<any> | HttpErrorResponse>
      (`${this.host}/api/user/login`, authRequest, {observe: 'response'});
  }

  public register(userData: UserData): Observable<UserData | HttpErrorResponse>
  {
      return this.http.post<UserData | HttpErrorResponse>
      (`${this.host}/api/user/register`, userData);
  }

  public logOut(): void
  {
      this.token = null;
      this.loggedInUserName = null;
      localStorage.removeItem('user');
      localStorage.removeItem('token');

  }

    public saveToken(token: string): void
    {
      this.token = token;
      localStorage.setItem('token',token);
    }

    public addUserToLocalCache(userData: UserData): void
    {
      localStorage.setItem('user',JSON.stringify(userData));
    }

    public getUserFromLocalCache(): UserData
    {
      return JSON.parse(localStorage.getItem('user'));
    }

    public loadToken(): void
    {
      this.token = localStorage.getItem('token');
    }

    public getToken(): string
    {
      return this.token;
    }

    public isUserLoggedIn(): boolean
    {
        this.loadToken();
        if(this.token != null && this.token !== '')
        {
          if(this.jwtHelper.decodeToken(this.token).sub != null || '')
          {
              if(!this.jwtHelper.isTokenExpired(this.token))
              {
                this.loggedInUserName = this.jwtHelper.decodeToken(this.token).sub;
                return true;
              }
          }
        }
        else
        {
          this.logOut();
          return false;
        }
        return false;
    }

}
