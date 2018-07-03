import {RouterModule, Routes} from "@angular/router";
import {ModuleWithProviders} from "@angular/core";
const appRoutes: Routes = [
    {
        path: "",
        pathMatch: "full",
        redirectTo: "/meal-list",
    },
    {
        path: "login",
        component: EntryComponent,
    },
    {
        path: "register",
        component: RegisterComponent
    },
    {
        path: "meal-list",
        component: MealListComponent,
        canActivate: [AuthActivateGuard],
    },
    {
        path: "profile",
        component: ProfileComponent,
        canActivate: [AuthActivateGuard]
    },
    {
        path: "user-list",
        component: UserListComponent,
        canActivate: [AuthActivateGuard]
    }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes, {useHash: true});