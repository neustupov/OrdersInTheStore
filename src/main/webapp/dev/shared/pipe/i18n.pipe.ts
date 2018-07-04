import {Pipe, PipeTransform} from "@angular/core";
import {I18nService} from "../../service/i18n.service";

@Pipe({name: 'i18nPipe',
    pure: false})
export class I18nPipe implements PipeTransform {

    constructor(private i18Service: I18nService) {
    }

    transform(value: any, args: any): any {
        return this.i18Service.getMessage(value);
    }
}