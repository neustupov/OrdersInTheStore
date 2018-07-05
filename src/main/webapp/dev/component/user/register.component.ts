import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ExceptionService} from "../../service/exception.service";
import {UserService} from "../../service/user.service";

@Component({
    templateUrl: "../../../templates/user/profile.html"
})
export class RegisterComponent implements OnInit {

    private registerForm: FormGroup;

    constructor(private formBuilder: FormBuilder,
                private userService: UserService,
                private router: Router,
                private exceptionService: ExceptionService) {
    }

    ngOnInit(): void {
        this.registerForm = this.formBuilder.group({
            'name': ['', Validators.required],
            'email': ['', Validators.required],
            'password': ['', Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(64)])]
        });
    }

    save() {
        this.userService.registerUser(this.registerForm.value).subscribe(
            res => {
                this.router.navigate(['/login']);
            },
            err => {
                this.exceptionService.onError(err);
            }
        );
    }

}