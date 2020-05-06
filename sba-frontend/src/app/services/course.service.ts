import { Injectable } from '@angular/core';
import { API_GATEWAY } from '../utils/constants';
import { Mentor } from '../site/mentor';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccountService } from './account.service';
import { Course } from '../models/course';

@Injectable()
export class CourseService {
  url: string = API_GATEWAY.ORIGIN ;

  mentor:Mentor = null;

  constructor(private http:HttpClient,private authService:AccountService) { }

  sendCourseRequest(course:Course):Observable<any> {
    return this.http.post(this.url+"/course/api/v1/courseRequest",course);
  }


  changeStatus(course:Course):Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    return this.http.post(this.url + "/course/api/v1/editStatus/", course, {headers});
  }


}
