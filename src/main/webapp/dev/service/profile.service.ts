import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {basePath, reqOptions, reqOptionsJson} from "../shared/config";
import {DateTimeTransformer} from "../shared/date-time.transformer";
import {User} from "../component/model/user.model";

@Injectable()
export class ProfileService {


    constructor(private http: Http,
                private dateTimeTransformer: DateTimeTransformer) {
    }

    getOwnProfile(): Observable<Response> {
        return this.http.get(basePath + profilePath, reqOptions);
    }

    saveOwnProfle(value: User): Observable<Response> {
        return this.http.put(basePath + profilePath, JSON.stringify(value), reqOptionsJson);
    }
}