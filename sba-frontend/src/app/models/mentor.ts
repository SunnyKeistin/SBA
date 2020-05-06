
import { MentorSkill } from './mentor_skills';

export interface Mentor {
    mentorId: number;
    email: String;
    firstName:String;
    lastName:String;
    mobile:String;
    avatarPath:String;
    skills: MentorSkill;
}
