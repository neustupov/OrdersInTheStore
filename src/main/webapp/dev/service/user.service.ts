import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {DateTimeTransformer} from "../shared/date-time.transformer";
import {Observable} from "rxjs/Observable";
import {User} from "../component/model/user.model";
import {basePath, registerPath, reqOptions, reqOptionsJson, usersPath} from "../shared/config";

@Injectable()
export class UserService {

    constructor(private http: Http,
                private dateTimeTransformer: DateTimeTransformer) {
    }

    registerUser(value: User): Observable<Response> {
        return this.http.post(basePath + registerPath, JSON.stringify(value), reqOptionsJson)
    }

    getUsers(): Observable<User[]> {
        return this.http.get(basePath + usersPath, reqOptions).map(res => res.json());
    }

    delete(user: User): Observable<Response> {
        return this.http.delete(basePath + usersPath + '/' + user.id, reqOptions);
    }

    saveUser(user: User): Observable<Response> {
        if (user.id) {
            return this.updateUser(user);
        } else {
            return this.createUser(user);
        }
    }

    changeActiveStatus(user: User): Observable<Response> {
        return this.http.patch(basePath + usersPath + '/' + user.id + '/' + user.enabled, null);
    }

    private updateUser(user: User): Observable<Response> {
        return this.http.put(basePath + usersPath + '/' + user.id, JSON.stringify(user), reqOptionsJson);
    }

    private createUser(user: User): Observable<Response> {

        return this.http.post(basePath + usersPath, JSON.stringify(user), reqOptionsJson);
    }
}