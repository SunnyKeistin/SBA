import { KEYS } from './constants';
import { User } from '../models/user.model'
export function tokenGetter() {
    return localStorage.getItem(KEYS.JWT_TOKEN);
}

export function saveUserInfo(response: any) {
    const user: User = {
        userName: response.username,
        role: response.role
    };
    localStorage.setItem(KEYS.CURRENT_USER, JSON.stringify(user));
    localStorage.setItem(KEYS.JWT_TOKEN, response.token);
}

export function getUserInfo(): User {
    const userString: string = localStorage.getItem(KEYS.CURRENT_USER);
    if (userString) {
        return JSON.parse(userString);
    }
    return null;
}