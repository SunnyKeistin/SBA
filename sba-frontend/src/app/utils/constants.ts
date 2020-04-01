const PROTOCAL = 'http://';
const HOST = 'localhost:8082';

export const API_GATEWAY = {
    PROTOCAL,
    HOST,
    ORIGIN: PROTOCAL + HOST,
};

export const API_ENDPOINTS = {
    LOGIN: API_GATEWAY.ORIGIN + '/auth/login',
    REGISTER: API_GATEWAY.ORIGIN + '/account/api/v1/register',
};

export const KEYS = {
    JWT_TOKEN: 'jwt_token',
    CURRENT_USER: 'current_user',
};
