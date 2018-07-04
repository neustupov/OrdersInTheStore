import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";

@Component({
    templateUrl: '../../../templates/auth/entry.html'
})
export class EntryComponent implements OnInit{

    private loginForm: FormGroup;

    constructor(private authService: AuthService,
                private formBuilder: FormBuilder,
                private router: Router) {
    }

    ngOnInit(): void {
        this.loginForm = this.formBuilder.group({
            "login": ["", Validators.required],
            "password": ["", Validators.required]
        })
    }
}