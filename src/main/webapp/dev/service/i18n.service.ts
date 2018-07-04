import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {basePath, i18nPath} from "../shared/config";
import {Observable} from "rxjs/Rx";
import {I18Enum} from "../component/model/i18.enum";

@Injectable()
export class I18nService {

    private cachedMessages: {string: string} = null;

    activeLocale: I18Enum = null;

    constructor(private http: Http) {

    }

    getMessage(key: string) {
        if (this.cachedMessages) {
            return this.cachedMessages[key];
        }
    }

    reloadLocale(locale: I18Enum) {
        this.activeLocale = locale;
        this.http.get(basePath + i18nPath + '/' + I18Enum[locale]).subscribe(
            res => {
                this.cachedMessages = res.json();
            }
        );
    }

    getCurrentLocale() {
        return I18Enum[this.activeLocale];
    }

    initMessages(locale: I18Enum) {
        this.activeLocale = locale;

        return new Promise((resolve, reject) => {
            this.http.get(basePath + i18nPath + '/' + I18Enum[locale]).map(res => res.json()).catch((error: any): any => {
                reject(false);
                return Observable.throw(error.json().error || 'Server error');
            }).subscribe((callResult: {string: string}) => {
                this.cachedMessages = callResult;
                resolve(true);
            });

        });
    }
}