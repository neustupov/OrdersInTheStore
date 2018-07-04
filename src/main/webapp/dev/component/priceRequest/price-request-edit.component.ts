import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Component, EventEmitter, OnInit, Output} from "@angular/core";
import {PriceRequest} from "../model/price-request.model";

@Component({
    templateUrl: '../../templates/priceRequest/price-request-edit.html',
    selector: 'edit-price-request'
})
export class EditPriceRequestComponent implements OnInit {

    showToggle: boolean = false;

    priceRequestForm: FormGroup;

    @Output()
    onSaveEvent: EventEmitter<PriceRequest> = new EventEmitter<PriceRequest>();

    constructor(private formBuilder: FormBuilder) {
    }

    ngOnInit(): void {
        this.priceRequestForm = this.formBuilder.group({
            id: [''],
            dateTime: [null, Validators.required],
            user: [``, Validators.required],
            order: [``, Validators.required],
            client: [``, Validators.required],
            ready: [false, Validators.required],
            products: [null, Validators.required]
        });
    }

    fillMyForm(priceRequest: PriceRequest) {
        this.priceRequestForm.patchValue(priceRequest);
    }

    resetForm() {
        this.priceRequestForm.reset();
    }

    onSave() {
        let value = this.priceRequestForm.value;
        this.onSaveEvent.emit(value);
        this.priceRequestForm.reset();
    }
}