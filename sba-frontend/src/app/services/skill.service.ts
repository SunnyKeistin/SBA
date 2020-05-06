import { Injectable } from '@angular/core';
import { API_GATEWAY } from '../utils/constants';
import { Mentor } from '../site/mentor';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MentorSkill } from '../models/mentor_skills';

@Injectable()
export class SkillService {
  url: string = API_GATEWAY.ORIGIN ;

  mentor:Mentor = null;

  constructor(private http:HttpClient) { }

  getAllSkills(): Observable<any> {
    return this.http.get(this.url+"/mentor/api/v1/skills");
  }
  
  addMentorSkill(mentorSkill:MentorSkill):Observable<any> {
    return this.http.post(this.url+"/mentor/api/v1/addMentorSkill", mentorSkill);
  }

  deleteMentorSkill(mentorSkill:MentorSkill):Observable<any> {
    return this.http.post(this.url+"/mentor/api/v1/deleteMentorSkill", mentorSkill);
  }

  getMentorSkills(mentorId:number):Observable<any> {
    return this.http.post(this.url+"/mentor/api/v1/getMentorSkills", mentorId);
  }

}
