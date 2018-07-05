import {APP_INITIALIZER, NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {CalendarModule, DataTableModule, GrowlModule} from "primeng/primeng";
import {DatePipe} from "@angular/common";
import {routing} from "./app.routes";
import {HeaderComponent} from "./component/auth/header.component";
import {PriceRequestService} from "./service/price-request.service";
import {AuthService} from "./service/auth.service";
import {PriceRequestListComponent} from "./component/priceRequest/price-request-list.component";
import {EditPriceRequestComponent} from "./component/priceRequest/price-request-edit.component";
import {AuthActivateGuard} from "./shared/auth.activate.guard";
import {I18nPipe} from "./shared/pipe/i18n.pipe";
import {ProfileComponent} from "./component/user/profile.component";
import {ProfileService} from "./service/profile.service";
import {I18nService} from "./service/i18n.service";
import {ExceptionService} from "./service/exception.service";
import {I18Enum} from "./component/model/i18.enum";
import {EntryComponent} from "./component/auth/entry.component";
import {RegisterComponent} from "./component/user/register.component";
import {UserService} from "./service/user.service";
import {DateTimeTransformer} from "./shared/date-time.transformer";
import {UserEditComponent} from "./component/user/user-edit.component";
import {UserListComponent} from "./component/user/user-list.component";
import {AppComponent} from "./app.component";

@NgModule({
    imports: [BrowserModule, FormsModule, ReactiveFormsModule, HttpModule, routing, CalendarModule, DataTableModule,
        GrowlModule],
    declarations: [AppComponent, PriceRequestListComponent, EntryComponent,
        EditPriceRequestComponent, HeaderComponent,
        ProfileComponent,
        RegisterComponent,
        UserListComponent,
        UserEditComponent,
        I18nPipe
    ],
    bootstrap: [AppComponent],
    providers: [AuthService, AuthActivateGuard, PriceRequestService, UserService, ProfileService,
        I18nService, DateTimeTransformer, DatePipe, ExceptionService,
        {
            provide: APP_INITIALIZER,
            // useFactory: (i18NService: I18nService) => () => i18NService.initMessages(I18Enum.ru),
            // or
            useFactory: initApp,
            deps: [I18nService],
            multi: true
        }
    ]
})
export class OrdersInTheStoreModule {

}

export function initApp(i18nService: I18nService) {
    // Do initing of services that is required before app loads
    // NOTE: this factory needs to return a function (that then returns a promise)
    return () => i18nService.initMessages(I18Enum.ru);  // + any other services...
}