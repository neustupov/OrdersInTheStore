import {APP_INITIALIZER, NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {CalendarModule, DataTableModule, GrowlModule} from "primeng/primeng";
import {DatePipe} from "@angular/common";
import {routing} from "./app.routes";

@NgModule({
    imports: [BrowserModule, FormsModule, ReactiveFormsModule, HttpModule, routing, CalendarModule, DataTableModule,
        GrowlModule],
    declarations: [AppComponent, MealListComponent, EntryComponent,
        EditMealComponent, HeaderComponent,
        ProfileComponent,
        RegisterComponent,
        UserListComponent,
        UserEditComponent,
        I18nPipe
    ],
    bootstrap: [AppComponent],
    providers: [AuthService, AuthActivateGuard, MealService, UserService, ProfileService,
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