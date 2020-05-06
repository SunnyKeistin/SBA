import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_ENDPOINTS } from '../utils/constants';
import { API_GATEWAY } from '../utils/constants';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable()
export class UserService {

  url : string = API_GATEWAY.ORIGIN;

  constructor(private http: HttpClient) {
  }

  login(user: object) {

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/x-www-form-urlencoded'
      })
    };

    const body = `userName=${user['userName']}&password=${user['password']}`;

    return this.http.post(API_ENDPOINTS.LOGIN, body, httpOptions).pipe(catchError(error => of(error)));
  }

  findUser(username:String){
    return this.http.post(this.url+"/sba-account/api/v1/findUser", username);
  }

  getUsersByIds(userId:String){
    return this.http.post(this.url+"/sba-account/api/v1/getUsersByIds", userId);
  }
}
