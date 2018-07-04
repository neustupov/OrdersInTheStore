import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../service/auth.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class AuthActivateGuard implements CanActivate {


    constructor(private authService: AuthService,
                private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
        return this.authService.isAuthenticated().map(res => {
            if (!res) {
                this.router.navigate(["login"]);
            }
            return res;
        });
    }
}