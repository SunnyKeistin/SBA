import { Mentor } from './Mentor';
import { Skill } from './skill';

export interface MentorSkill {
    userId:number,
    skillId:number,
    skillName:string,
    years:number,
    skillDescription:String
}