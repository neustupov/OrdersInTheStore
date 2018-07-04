import {RequestOptions, Headers} from "@angular/http";

export const basePath: string = '/ordersInTheStore-rs';
export const loginPath: string = "/spring_security_check";
export const priceRequestPath: string = '/rest/admin/priceRequests';
export const adminPath: string = '/rest/admin';
export const registerPath: string = '/register';
export const usersPath: string = '/rest/admin/users';
export const i18nPath: string = '/i18n';

export const headers: Headers = new Headers({
    'Content-Type': 'application/json'
});
export const reqOptions: RequestOptions = new RequestOptions({
    withCredentials: true
});
export const reqOptionsJson: RequestOptions = new RequestOptions({
    withCredentials: true,
    headers: headers
});