import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_ENDPOINTS } from '../utils/constants';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable()
export class UserService {

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
}
