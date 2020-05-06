import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { API_GATEWAY } from '../utils/constants';
import { Observable } from 'rxjs';
import { UserService } from './user.service';
import { Router } from '@angular/router';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private _httpClient: HttpClient, private userService: UserService, public router: Router) { }

  private authenticationApiUrl = API_GATEWAY.ORIGIN;
  private token: string;
  username: string;
  add: boolean = false;
  loggedInUser = { loggedOut: true };
  redirectUrl = '/';
  loggedIn: boolean = false;
  user:boolean = false;
  mentor:boolean = false;
  id:number;
  type:string;


  validCredentials: boolean = true;

  public setToken(token: string) {
    this.token = token;
  }
  public getToken() {
    return this.token;
  }

  authenticate(user: string, password: string): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Basic ' + btoa(user + ':' + password));
    return this._httpClient.get(this.authenticationApiUrl + "sba-account/api/v1/authenticate", { headers })
  }

  authenticateUser(user) {
    for (let validUser of this.userService.users) {
      if (validUser.username == user.username && validUser.password == user.password) {
        this.loggedInUser = user;
        this.validCredentials = true;
        this.add = true;

        this.router.navigate(['Menu']);
        this.loggedIn = true;
      }
      else
        this.validCredentials = false;
    }
  }

  logout() {
    this.loggedInUser = { loggedOut: true };
    this.loggedIn = false;
    this.router.navigate(['login']);
  }

  getUser():Observable<any>{
    return this._httpClient.get(this.authenticationApiUrl+'sba-account/api/v1/findUser/'+this.username)
  }


  
}
