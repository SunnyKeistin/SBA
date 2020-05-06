
import { Mentor } from './mentor';
import { Skill } from './skill';

export interface Course {

    courseId:number;
    userId:number;
    mentorId:number;
    skillId:number;
    skillName:string;
    courseName:string;
    startDate:Date;
    endDate:Date;
    fee:DoubleRange;
    status:number;
    
}
