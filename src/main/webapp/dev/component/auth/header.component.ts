import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {ErrorModel} from "../model/error.model";
import {I18nService} from "../../service/i18n.service";
import {I18Enum} from "../model/i18.enum";
import {ExceptionService} from "../../service/exception.service";

@Component({
    templateUrl: '../../../templates/auth/header.html',
    selector: 'header-component',
    styleUrls: ["../../../resources/css/i18n.css"]
})
export class HeaderComponent implements OnInit {

    private errors: ErrorModel[] = [];

    loginForm: FormGroup = this.formBuilder.group({
        "login": ["", Validators.required],
        "password": ["", Validators.required]
    });

    constructor(private authService: AuthService,
                private router: Router,
                private formBuilder: FormBuilder,
                private i18Service: I18nService,
                private exceptionService: ExceptionService) {
    }

    ngOnInit(): void {
        this.exceptionService.errorStream.subscribe(
            e => {
                this.errors.push(e);
            }
        )
    }

    onLogout() {
        this.authService.logout();
        this.router.navigate(["login"]);
    }

    onSubmit() {
        this.authService.login(this.loginForm.value);
    }

    chooseEng() {
        this.i18Service.reloadLocale(I18Enum.en);
    }

    chooseRu() {
        this.i18Service.reloadLocale(I18Enum.ru);
    }
}